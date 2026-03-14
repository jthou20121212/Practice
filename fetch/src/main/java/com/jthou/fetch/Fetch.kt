package com.jthou.fetch

import android.content.Context
import android.net.Uri
import android.util.Log
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.*
import java.io.File
import java.util.*
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

    // --- 关键定义：使用 SupervisorJob 确保任务互不影响 ---
    internal val coroutineScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )
    internal val hashMap = Collections.synchronizedMap(mutableMapOf<String, Download>())

    // 使用 ConcurrentHashMap 存储每个 URL 对应的锁
    private val mutexMap = java.util.concurrent.ConcurrentHashMap<String, Mutex>()

    private fun getMutex(url: String): Mutex {
        return mutexMap.computeIfAbsent(url.md5().toHex()) { Mutex() }
    }

    private fun removeMutex(url: String) {
        mutexMap.remove(url.md5().toHex())
    }

    private fun getDownloadFile(url: String): File {
        val dir = File(downloadDir, TAG.lowercase(Locale.getDefault()))
        return File(dir, url.getFileName())
    }

    private fun getETagKey(url: String) = "${url.md5().toHex()}-etag"
    private fun getLastModifiedKey(url: String) = "${url.md5().toHex()}-last-modified"

    suspend fun download(url: String): Flow<DownloadStatus> {
        val md5 = url.md5().toHex()
        val mutex = getMutex(url)

        return mutex.withLock {
            // 在锁内部进行双重检查
            hashMap[md5]?.let { return@withLock it.statusFlow }

            val download = Download(paused = AtomicBoolean(false))
            hashMap[md5] = download

            try {
                val okHttpClient = OkHttpClient.Builder().build()
                val request = Request.Builder().url(url).head().build()
                val response = okHttpClient.newCall(request).await()

                if (download.paused.get()) {
                    hashMap.remove(md5)
                    return@withLock download.statusFlow
                }

                val serverETag = response.header(Constants.ETAG)
                val serverLastModified = response.header(Constants.LAST_MODIFIED)

                val localETag = MMKV.defaultMMKV().decodeString(getETagKey(url))
                val localLastModified = MMKV.defaultMMKV().decodeString(getLastModifiedKey(url))

                // 校验版本是否发生变化
                val isVersionChanged = (localETag != null && serverETag != null && localETag != serverETag) ||
                        (localLastModified != null && serverLastModified != null && localLastModified != serverLastModified)

                if (isVersionChanged) {
                    Log.i(TAG, "检测到远程文件已更新，正在重置本地缓存...")
                    pauseInternal(url) // 确保先停止
                    deleteInternal(url)
                }

                val contentLength = response.headers[Constants.CONTENT_LENGTH]?.toLongOrNull() ?: -1L
                val supportRange = response.code == Constants.PARTIAL_CONTENT || response.headers[Constants.ACCEPT_RANGES] == Constants.BYTES
                if (contentLength != -1L && supportRange) {
                    // 优雅的完成检查：文件存在且大小一致
                    val downloadFile = getDownloadFile(url)
                    if (downloadFile.exists() && downloadFile.length() == contentLength) {
                        download.emitStatus(DownloadStatus.Completed(downloadFile))
                        hashMap.remove(md5)
                        return@withLock download.statusFlow
                    }

                    // 存储最新的版本标识
                    serverETag?.let { MMKV.defaultMMKV().encode(getETagKey(url), it) }
                    serverLastModified?.let { MMKV.defaultMMKV().encode(getLastModifiedKey(url), it) }

                    download.emitStatus(DownloadStatus.Started(true))
                    MMKV.defaultMMKV().encode(
                        getContentLengthKey(url),
                        contentLength
                    )

                    val wrapper = RangeDownloadTaskWrapper(
                        this@Fetch, url,
                        contentLength
                    )
                    download.task = wrapper
                    wrapper.download()
                } else {
                    download.emitStatus(DownloadStatus.Started(false))
                    hashMap.remove(md5)
                }
            } catch (e: Exception) {
                download.emitStatus(DownloadStatus.Error(e))
                hashMap.remove(md5)
            }
            download.statusFlow
        }
    }

    fun pause(url: String) {
        coroutineScope.launch {
            getMutex(url).withLock {
                pauseInternal(url)
            }
        }
    }

    private fun pauseInternal(url: String) {
        val md5 = url.md5().toHex()
        val download = hashMap[md5] ?: return

        download.paused.set(true)
        download.call?.cancel()
        download.task?.pause()

        // 避免还没真正开始下载就暂停了这个时候没有删除时机了所以要在这里删除
        hashMap.remove(md5)
    }

    fun delete(url: String) {
        coroutineScope.launch { deleteInternal(url) }
    }

    private suspend fun deleteInternal(url: String) {
        val mutex = getMutex(url)
        mutex.withLock {
            // 如果正在进行中先暂停
            pauseInternal(url)
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
        // removeMutex(url)
    }

    // false 有可能是没有在进行中 再考虑考虑 用个枚举值 ?
    fun isPause(url: String): Boolean {
        return hashMap[url.md5().toHex()]?.paused?.get() ?: false
    }

    internal suspend fun completeInternal(url: String, file: File) {
        val md5 = url.md5().toHex()
        getMutex(url).withLock {
            val download = hashMap[md5] ?: return@withLock
            download.emitStatus(DownloadStatus.Completed(file))
            hashMap.remove(md5)
        }
    }

    internal suspend fun errorInternal(url: String, e: Exception) {
        val md5 = url.md5().toHex()
        getMutex(url).withLock {
            val download = hashMap[md5] ?: return@withLock
            download.emitStatus(DownloadStatus.Error(e))
            hashMap.remove(md5)
        }
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

        fun downloadDir(downloadDir: File) = apply {
            this.downloadDir = downloadDir
        }

        fun build(): Fetch = Fetch(this)

    }

    // task 对应的真正下载任务
    // paused true 表示暂停 false 表示下载中
    // callback 回调
    // call 对应发送的 head 请求
    class Download(
        val paused: AtomicBoolean,
        var task: IDownload? = null,
        var call: Call? = null,
        private val _statusFlow:MutableSharedFlow<DownloadStatus> = MutableSharedFlow(replay = 1)
    ) {

        val statusFlow: Flow<DownloadStatus> = _statusFlow.asSharedFlow()

        fun emitStatus(newStatus: DownloadStatus) {
            _statusFlow.tryEmit(newStatus)
        }

    }

}