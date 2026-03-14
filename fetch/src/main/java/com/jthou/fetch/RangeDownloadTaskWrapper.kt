package com.jthou.fetch

import com.tencent.mmkv.MMKV
import kotlinx.coroutines.*
import okhttp3.*
import java.io.File
import java.io.RandomAccessFile
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * 范围请求任务 (协程版)
 *
 * @author jthou
 * @since 1.0.0
 * @date 01-09-2022
 */
class RangeDownloadTaskWrapper(
    private val fetch: Fetch,
    private val url: String,
    private val contentLength: Long
) : IDownload {

    private val downloadLength = AtomicLong()
    private val downloadFinishedCount = AtomicInteger()
    private val downloadProgress = AtomicInteger()
    
    private var downloadJob: Job? = null

    override fun pause() {
        downloadJob?.cancel()
    }

    override fun download() {
        downloadJob = fetch.coroutineScope.launch {
            val md5 = url.md5().toHex()
            val fileName = url.getFileNameNoExtension()
            val dir = File(fetch.downloadDir, Fetch.TAG.lowercase(Locale.getDefault()))
            if (!dir.exists() && !dir.mkdirs()) return@launch
            
            val tempFile = File(dir, fileName + Constants.TEMP_FILE_SUFFIX)
            val blockLength = contentLength / Constants.THREAD_COUNT

            val jobs = (0 until Constants.THREAD_COUNT).map { threadId ->
                async {
                    val key = "$md5-$threadId"
                    val defaultIndex = threadId * blockLength
                    val startIndex = MMKV.defaultMMKV().decodeLong(key, defaultIndex)
                    val endIndex = if (threadId == Constants.THREAD_COUNT - 1) 
                        contentLength - 1 else (threadId + 1) * blockLength - 1

                    if (startIndex > endIndex) {
                        downloadLength.addAndGet(blockLength)
                        downloadFinishedCount.incrementAndGet()
                        return@async
                    } else if (startIndex > defaultIndex) {
                        downloadLength.addAndGet(startIndex - defaultIndex)
                    }

                    runDownloadTask(
                        key, tempFile, startIndex, endIndex
                    )
                }
            }
            jobs.awaitAll()
        }
    }

    private suspend fun runDownloadTask(
        key: String, 
        tempFile: File,
        startIndex: Long, 
        endIndex: Long
    ) = withContext(Dispatchers.IO) {
        val md5 = url.md5().toHex()
        val download = fetch.hashMap[md5] ?: return@withContext

        var currentPos = startIndex
        val okHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder()
            .url(url)
            .header(Constants.RANGE, Constants.BOUNDARY.format(currentPos, endIndex))
            .build()

        try {
            val response = okHttpClient.newCall(request).await()
            if (!response.isSuccessful) return@withContext

            response.body.use { body ->
                RandomAccessFile(tempFile, "rw").use { raf ->
                    raf.seek(currentPos)
                    val inputStream = body.byteStream()
                    val buffer = ByteArray(8192)
                    var read = 0

                    while (coroutineContext.isActive && !download.paused.get() &&
                        inputStream.read(buffer).also { read = it } != -1) {

                        raf.write(buffer, 0, read)
                        currentPos += read
                        MMKV.defaultMMKV().encode(key, currentPos)

                        val total = downloadLength.addAndGet(read.toLong())
                        val progress = floor(total * 100f / contentLength).roundToInt()

                        if (downloadProgress.getAndSet(progress) != progress) {
                            download.emitStatus(DownloadStatus.Progress(progress))
                        }
                    }
                }
            }
            
            if (coroutineContext.isActive && !download.paused.get()) {
                val finished = downloadFinishedCount.incrementAndGet()
                if (finished == Constants.THREAD_COUNT) {
                    finalizeDownload(tempFile)
                }
            }
        } catch (e: Exception) {
            if (e !is CancellationException) {
                fetch.errorInternal(url, e)
            }
        }
    }

    private suspend fun finalizeDownload(tempFile: File) {
        val fileName = url.getFileName()
        val dir = File(fetch.downloadDir, Fetch.TAG.lowercase(Locale.getDefault()))
        val downloadFile = File(dir, fileName)
        if (tempFile.renameTo(downloadFile)) {
            fetch.completeInternal(url, downloadFile)
        }
    }
}
