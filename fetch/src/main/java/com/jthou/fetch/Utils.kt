package com.jthou.fetch

import android.net.Uri
import android.util.Log
import java.io.File
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.*

fun Any?.log() = Log.i(Fetch.TAG, this?.toString() ?: "null")

fun String.md5(): ByteArray = MessageDigest.getInstance("MD5").digest(this.toByteArray(Charsets.UTF_8))

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

fun String?.getFileName(): String {
    if (this == null) return ""
    // 空格转义字符
    val oldString = this.replace("%20", " ")
    if (isSpace(oldString)) return ""
    val newString = Uri.parse(oldString).buildUpon().clearQuery().build().toString()
    val lastSep = newString.lastIndexOf(File.separator)
    return if (lastSep == -1) newString else newString.substring(lastSep + 1)
}

fun getFileNameNoExtension(file: File?): String {
    return file?.path?.getFileNameNoExtension() ?: ""
}

fun String?.getFileNameNoExtension(): String {
    if (this == null) return ""
    // 空格转义字符
    val oldString = this.replace("%20", " ")
    if (isSpace(oldString)) return ""
    val newString = Uri.parse(oldString).buildUpon().clearQuery().build().toString()
    val lastPoi = newString.lastIndexOf('.')
    val lastSep = newString.lastIndexOf(File.separator)
    if (lastSep == -1) {
        return if (lastPoi == -1) newString else newString.substring(0, lastPoi)
    }
    return if (lastPoi == -1 || lastSep > lastPoi) {
        newString.substring(lastSep + 1)
    } else newString.substring(lastSep + 1, lastPoi)
}

private fun isSpace(s: String?): Boolean {
    if (s == null) return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}

fun getDownloadThreadPool(): ExecutorService {
    val cpuCoreCount = Runtime.getRuntime().availableProcessors()
    return ThreadPoolExecutor(
        cpuCoreCount * 2,
        Int.MAX_VALUE,
        1, TimeUnit.MINUTES,
        SynchronousQueue(),
        NamingThreadFactory("fetch-download") { runnable -> Thread(runnable) }
    )
}

fun getContentLengthKey(url: String): String {
    val md5 = url.md5().toHex()
    return "$md5-${Constants.CONTENT_LENGTH.lowercase(Locale.getDefault())}"
}