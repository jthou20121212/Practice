package com.jthou.fetch

import android.util.Log
import com.tencent.mmkv.MMKV
import okhttp3.*
import okhttp3.internal.closeQuietly
import okhttp3.internal.http2.StreamResetException
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.net.SocketTimeoutException
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * 范围请求任务
 *
 * @author jthou
 * @since 1.0.0
 * @date 01-09-2022
 */
class RangeDownloadTaskWrapper(private val fetch: Fetch, private val url: String, private val contentLength: Long) : IDownload {

    // 任务下载长度
    private val downloadLength = AtomicLong()

    // 任务状态是否暂停
    private val downloadStatus = AtomicBoolean()

    // 下载任务对应的所有网络任务
    private val downloadList = CopyOnWriteArrayList<Call>()

    // 网络任务完成数量
    private val downloadFinishedCount = AtomicInteger()

    internal val isPause
        get() = downloadStatus.get()

    override fun pause() {
        // 避免重复调用
        if (downloadStatus.get()) return
        // 避免未开始前直接调用暂停
        if (downloadList.isEmpty()) {
            return
        }
        // 取消网络请求
        downloadList.forEach { it.cancel() }
        downloadList.clear()
        // 如果响应已经返回不再写入文件
        downloadStatus.compareAndSet(false, true)
    }

    override fun resume() {
        // 重新执行任务重新计算长度和完成数量
        downloadLength.set(0)
        downloadStatus.set(false)
        downloadFinishedCount.set(0)
        val md5 = url.md5().toHex()
        val fileName = url.getFileNameNoExtension()
        // 文件下载目录 内部不处理权限 如果不设置使用默认的 App 文件目录
        val dir = File(fetch.downloadDir, Fetch.TAG.lowercase(Locale.getDefault()))
        if (!dir.exists() && !dir.mkdirs()) return
        val tempFile = File(dir, fileName + Constants.TEMP_FILE_SUFFIX)
        val blockLength = contentLength / Constants.THREAD_COUNT
        for (threadId in 0 until Constants.THREAD_COUNT) {
            // 线程开始下载的位置
            val key = "$md5-$threadId"
            val defaultIndex = threadId * blockLength
            val startIndex = MMKV.defaultMMKV().decodeLong(key, defaultIndex)
            // 线程结束下载的位置
            // 如果是最后一个线程,将剩下的文件全部交给这个线程完成
            val endIndex = if (threadId == Constants.THREAD_COUNT - 1) contentLength - 1 else (threadId + 1) * blockLength - 1
            if (endIndex <= startIndex) {
                // 已下载完成
                Log.i(Fetch.TAG, "此范围已下载完成")
                downloadLength.addAndGet(blockLength)
                downloadFinishedCount.incrementAndGet()
                continue
            } else if (startIndex > defaultIndex) {
                // 下载过一部分
                downloadLength.addAndGet(startIndex - defaultIndex)
            } else {
                // 没有下载过
            }
            fetch.threadPool.execute(
                RangeDownloadTask(
                    fetch,
                    url,
                    key,
                    tempFile,
                    startIndex,
                    endIndex,
                    contentLength,
                    downloadFinishedCount,
                    downloadStatus,
                    downloadLength,
                    downloadList
                )
            )
        }
    }

//    override fun cancel() {
//        pause()
//        val md5 = url.md5().toHex()
//        val fileName = url.getFileNameNoExtension()
//        // 文件下载目录 内部不处理权限 如果不设置使用默认的 App 文件目录
//        val dir = File(fetch.downloadDir, Fetch.TAG.lowercase(Locale.getDefault()))
//        val tempFile = File(dir, fileName + Constants.TEMP_FILE_SUFFIX)
//        tempFile.delete()
////        // 取消时不能删除下载进度否则获取不到进度
////        for (threadId in 0 until Constants.THREAD_COUNT) {
////            val key = "$md5-$threadId"
////            MMKV.defaultMMKV().remove(key)
////        }
//    }

    class RangeDownloadTask(
        private val fetch: Fetch, private val url: String, private val key: String,
        private val tempFile: File, private val startIndex: Long, private val endIndex: Long, private val contentLength: Long,
        // atomicInteger 下载任务对应的完成数量 atomicBoolean 下载任务状态是否暂停
        private val atomicInteger: AtomicInteger, private val atomicBoolean: AtomicBoolean,
        private val downloadLength: AtomicLong, private val downloadList: MutableList<Call>
    ) : Runnable {

        override fun run() {
            val okHttpClient = OkHttpClient.Builder().build()
            val value = Constants.BOUNDARY.format(startIndex, endIndex)
            val request = Request.Builder().url(url).header(Constants.RANGE, value).build()
            val call = okHttpClient.newCall(request)
            var currentRunnableDownloadLength = startIndex
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // 失败是否重试? okhttp 拦截器已经处理了重试逻辑还是失败不再重试
                    if (e is SocketTimeoutException) {
                        fetch.threadPool.execute(copy())
                        Log.i(Fetch.TAG, "下载超时再次发起下载任务")
                    } else {
                        fetch.downloadListener?.downloadFailure(e)
                    }
                    downloadList.remove(call)
                }

                override fun onResponse(call: Call, response: Response) {
                    // 已取消
                    if (atomicBoolean.get()) return
                    // 不是期望的状态码
                    if (response.code != 200 && response.code != 206) return

                    try {
                        val buffer = ByteArray(1024 shl 2)
                        var length = 0
                        val randomAccessFile = RandomAccessFile(tempFile, "rw")
                        randomAccessFile.seek(startIndex)
                        // 记录上一次下载的进度
                        while (!atomicBoolean.get() && response.body.byteStream().read(buffer).also { length = it } > 0) {
                            randomAccessFile.write(buffer, 0, length)
                            currentRunnableDownloadLength += length
                            MMKV.defaultMMKV().encode(key, currentRunnableDownloadLength)
                            val newValue = downloadLength.addAndGet(length.toLong())
                            fetch.downloadListener?.downloadProgress(floor(newValue * 100f / contentLength).roundToInt())
                        }
                        val incrementAndGet = atomicInteger.incrementAndGet()
                        Log.i(Fetch.TAG, "incrementAndGet : $incrementAndGet")
                        if (incrementAndGet == Constants.THREAD_COUNT) {
                            randomAccessFile.close()
                            val fileName = url.getFileName()
                            val dir = File(fetch.downloadDir, Fetch.TAG.lowercase(Locale.getDefault()))
                            val downloadFile = File(dir, fileName)
                            val renameTo = tempFile.renameTo(downloadFile)
                            Log.i(Fetch.TAG, "renameTo : $renameTo")
                            fetch.downloadListener?.downloadCompleted(downloadFile)
                            // 任务完成从 map 中移除
                            fetch.hashMap.remove(url.md5().toHex())
                        }
                    } catch (e: StreamResetException) {
                        Log.i(Fetch.TAG, "stream was reset: CANCEL")
                    } catch (e: SocketTimeoutException) {
                        // 读超时再次发起任务
                        e.printStackTrace()
                        Log.i(Fetch.TAG, "java.net.SocketTimeoutException: timeout")
                        Log.i(Fetch.TAG, "下载超时再次发起下载任务")
                        fetch.threadPool.execute(copy(startIndex = currentRunnableDownloadLength))
                    } finally {
                        downloadList.remove(call)
                        response.closeQuietly()
                        Log.i(Fetch.TAG, "finally")
                    }
                }
            })
            downloadList.add(call)
        }

        internal fun copy(
            fetch: Fetch = this.fetch,
            url: String = this.url,
            key: String = this.key,
            tempFile: File = this.tempFile,
            startIndex: Long = this.startIndex,
            endIndex: Long = this.endIndex,
            contentLength: Long = this.contentLength,
            atomicInteger: AtomicInteger = this.atomicInteger,
            atomicBoolean: AtomicBoolean = this.atomicBoolean,
            downloadLength: AtomicLong = this.downloadLength,
            downloadList: MutableList<Call> = this.downloadList
        ) = RangeDownloadTask(
            fetch, url, key, tempFile, startIndex, endIndex, contentLength, atomicInteger, atomicBoolean, downloadLength, downloadList
        )

    }

}