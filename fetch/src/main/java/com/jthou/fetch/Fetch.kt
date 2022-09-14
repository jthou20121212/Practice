package com.jthou.fetch

import android.content.Context
import android.net.Uri
import android.util.Log
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import okhttp3.*
import okio.IOException
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * 入口
 *
 * @author jthou
 * @since 1.0.0
 * @date 06-07-2022
 */
class Fetch internal constructor(builder: Builder) {

    companion object {
        const val TAG = "Fetch"

        fun setup(context: Context) {
            MMKV.initialize(context.applicationContext, MMKVLogLevel.LevelWarning)
        }
    }

    internal val downloadDir: File? = builder.downloadDir
    internal val threadPool: ExecutorService = builder.threadPool

    internal val hashMap = Collections.synchronizedMap(mutableMapOf<String, Download>())

    fun go(url: String, callback: DownloadListener) {
        // 封装任务
        val md5 = url.md5().toHex()
        val iDownload = hashMap[md5]
        if (iDownload != null) {
            Log.i(TAG, "正在下载")
            return
        }

        hashMap[md5] = Download(status = AtomicBoolean(false), callback = callback)

        val okHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder().url(url).head().build()
        val newCall = okHttpClient.newCall(request)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 取消 java.net.SocketException: Socket closed
                // 超时 java.net.SocketTimeoutException: Read timed out
                val download = hashMap[md5] ?: return
                download.callback.downloadFailure(e)
                hashMap.remove(md5)
            }

            override fun onResponse(call: Call, response: Response) {
                // 如果暂停了任务
                if (hashMap[md5]!!.status.get()) {
                    hashMap.remove(md5)
                    Log.i(TAG, "head 方法返回后发现任务已暂停（取消）")
                    return
                }

                // Log.i(TAG, response.toString())
                // 支持范围请求
                // 如果 length 为 -1 说明是分块传输
                val contentLength = response.headers[Constants.CONTENT_LENGTH]?.toLongOrNull() ?: -1L
                if ((-1L != contentLength) && (response.code == Constants.PARTIAL_CONTENT || response.headers[Constants.ACCEPT_RANGES] == Constants.BYTES)) {
                    // 这种判断方式不太好 再保存一个是否下载完成的标记 ？
                    if (getProgress(url) == 100) {
                        val dir = File(downloadDir, TAG.lowercase(Locale.getDefault()))
                        val file = File(dir, url.getFileName())
                        if (file.exists()) {
                            val download = hashMap[md5] ?: return
                            download.callback.downloadCompleted(file)
                            hashMap.remove(md5)
                            return
                        }
                    }

                    // 如果已经暂停则 hashMap[md5] 为 null
                    val download = hashMap[md5] ?: return

                    download.call = null
                    download.callback.supportRangeRequest(true)
                    // 记录文件长度后续考虑 md5 或者 etag ？
                    val contentLengthKey = getContentLengthKey(url)
                    MMKV.defaultMMKV().encode(contentLengthKey, contentLength)
                    val wrapper = RangeDownloadTaskWrapper(this@Fetch, url, contentLength)
                    wrapper.download()
                    download.task = wrapper
                } else {
                    val download = hashMap[md5] ?: return
                    download.callback.supportRangeRequest(false)
                    hashMap.remove(md5)
                }
            }
        })
        hashMap[md5]!!.call = newCall
    }

    fun pause(url: String) {
        val md5 = url.md5().toHex()
        val download = hashMap[md5] ?: return

        download.status.set(true)
        download.call?.cancel()
        download.task?.pause()

        // 避免还没真正开始下载就暂停了这个时候没有删除时机了所以要在这里删除
        hashMap.remove(md5)
    }

    fun delete(url: String) {
        // 如果正在进行中先暂停
        pause(url)
        val tempFileName = url.getFileNameNoExtension()
        val dir = File(downloadDir, TAG.lowercase(Locale.getDefault()))
        val tempFile = File(dir, tempFileName + Constants.TEMP_FILE_SUFFIX)
        if (tempFile.exists()) {
            // 之前有下载未完成
            tempFile.delete()
        } else {
            // 下载已完成
            val downloadFileName = Uri.parse(url).buildUpon().clearQuery().build().toString().getFileName()
            val downloadFile = File(dir, downloadFileName)
            if (downloadFile.exists()) {
                downloadFile.delete()
            }
        }
        val md5 = url.md5().toHex()
        for (threadId in 0 until Constants.THREAD_COUNT) {
            val key = "$md5-$threadId"
            MMKV.defaultMMKV().remove(key)
        }
        MMKV.defaultMMKV().remove(getContentLengthKey(url))
    }

    // false 有可能是没有在进行中 再考虑考虑 用个枚举值 ?
    fun isPause(url: String): Boolean {
        return hashMap[url.md5().toHex()]?.status?.get() ?: false
    }

    fun getFileName(url: String): String {
        return Uri.parse(url).buildUpon().clearQuery().build().toString().getFileName()
    }

    fun getProgress(url: String): Int {
        val contentLengthKey = getContentLengthKey(url)
        val contentLength = MMKV.defaultMMKV().decodeLong(contentLengthKey, -1L)
        if (contentLength == -1L) return 0
        val blockLength = contentLength / Constants.THREAD_COUNT
        var downloadTotalLength = 0L
        val md5 = url.md5().toHex()
        for (threadId in 0 until Constants.THREAD_COUNT) {
            val key = "$md5-$threadId"
            // 下载到位置
            val downloadLength = MMKV.defaultMMKV().decodeLong(key, 0L)
            // 如果这一段还没有下载
            if (downloadLength == 0L) continue
            // 开始下载的位置
            val startLength = threadId * blockLength
            // 累加下载的长度
            downloadTotalLength += downloadLength - startLength
        }
        return floor(downloadTotalLength * 100f / contentLength).roundToInt()
    }

    class Builder(context: Context) {

        internal var downloadDir: File? = context.filesDir
        internal var threadPool: ExecutorService = getDownloadThreadPool()

        fun downloadDir(downloadDir: File) = apply {
            this.downloadDir = downloadDir
        }

        fun threadPool(threadPool: ExecutorService) = apply {
            this.threadPool = threadPool
        }

        fun build(): Fetch = Fetch(this)

    }

    // task 对应的真正下载任务
    // status true 表示暂停 false 表示下载中
    // callback 回调
    // call 对应发送的 head 请求
    class Download(var task: IDownload? = null, val status: AtomicBoolean, val callback: DownloadListener, var call: Call? = null)

}