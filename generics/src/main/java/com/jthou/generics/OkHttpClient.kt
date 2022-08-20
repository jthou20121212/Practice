package com.jthou.generics

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.closeQuietly
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.charset.Charset

fun main() {
//     val request = Request.Builder().url("http://guolin.tech").build()
     val request = Request.Builder().url("https://www.baidu.com").build()
//     val request = Request.Builder().url("https://publicobject.com/helloworld.txt").build()
     val call = OkHttpClient().newCall(request)
    val execute = call.execute()
    String(execute.body.byteStream().readBytes()).log()
//    execute.body.contentType().log()
//    val file = File(".", "temp.html")
//    val byteStream = execute.body.byteStream()
//    val bufferedInputStream = BufferedInputStream(byteStream)
//    val fileOutputStream = FileOutputStream(file)
//    bufferedInputStream.copyTo(fileOutputStream)
//    fileOutputStream.flush()
//    fileOutputStream.closeQuietly()
//    bufferedInputStream.closeQuietly()
}