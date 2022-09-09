package com.jthou.fetch

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.tencent.mmkv.MMKV
import okhttp3.*
import okio.IOException
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService
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
            MMKV.initialize(context.applicationContext)
        }
    }

    internal val downloadDir: File? = builder.downloadDir
    internal val threadPool: ExecutorService = builder.threadPool
    internal val downloadListener: DownloadListener? = builder.downloadListener

    internal val hashMap = mutableMapOf<String, IDownload>()

    val handler = Handler(Looper.getMainLooper())

    fun go(url: String) {
        // 封装任务
        val okHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder().url(url).head().build()
        val newCall = okHttpClient.newCall(request)
        newCall.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 网络失败抛出异常
                downloadListener?.downloadFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                // Log.i(TAG, response.toString())
                // 支持范围请求
                // 如果 length 为 -1 说明是分块传输
                val contentLength = response.headers[Constants.CONTENT_LENGTH]?.toLongOrNull() ?: -1L
                if ((-1L != contentLength) && (response.code == Constants.PARTIAL_CONTENT || response.headers[Constants.ACCEPT_RANGES] == Constants.BYTES)) {
                    val contentLengthKey = getContentLengthKey(url)
                    val localSaveLength = MMKV.defaultMMKV().decodeLong(contentLengthKey, -1L)
                    if (localSaveLength == contentLength) {
                        val dir = File(downloadDir, TAG.lowercase(Locale.getDefault()))
                        val file = File(dir, url.getFileName())
                        if (file.exists()) {
                            downloadListener?.downloadCompleted(file)
                        }
                        return
                    }
                    downloadListener?.supportRangeRequest(true)
                    // 记录文件长度后续考虑 md5 或者 etag ？
                    MMKV.defaultMMKV().encode(contentLengthKey, contentLength)
                    val wrapper = RangeDownloadTaskWrapper(this@Fetch, url, contentLength)
                    val md5 = url.md5().toHex()
                    hashMap[md5] = wrapper
                    // TODO 需要考虑处理没有开始下载就暂停/删除的情况
                    handler.post { wrapper.resume() }
                } else {
                    downloadListener?.supportRangeRequest(false)
                }
            }
        })

        // 加入线程池
        // 支持使用不同的网络库下载

        // 下载数据实时写入数据
    }

    fun pause(url: String) {
        hashMap[url.md5().toHex()]?.pause() ?: kotlin.run { Log.i(TAG, "pause 没有相应任务") }
    }

    fun resume(url: String) {
        hashMap[url.md5().toHex()]?.resume() ?: kotlin.run { Log.i(TAG, "resume 没有相应任务") }
    }

    fun delete(url: String) {
        val md5 = url.md5().toHex()
        // 如果正在现在先暂停
        hashMap[md5]?.pause() ?: kotlin.run { Log.i(TAG, "delete 没有相应任务") }
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
        for (threadId in 0 until Constants.THREAD_COUNT) {
            val key = "$md5-$threadId"
            MMKV.defaultMMKV().remove(key)
        }
        MMKV.defaultMMKV().remove(getContentLengthKey(url))
    }

    fun isPause(url: String): Boolean {
        return (hashMap[url.md5().toHex()] as? RangeDownloadTaskWrapper)?.isPause ?: false
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
        internal var downloadListener: DownloadListener? = null

        fun downloadDir(downloadDir: File) = apply {
            this.downloadDir = downloadDir
        }

        fun threadPool(threadPool: ExecutorService) = apply {
            this.threadPool = threadPool
        }

        fun downloadListener(downloadListener: DownloadListener) = apply {
            this.downloadListener = downloadListener
        }

        fun build(): Fetch = Fetch(this)

    }

}