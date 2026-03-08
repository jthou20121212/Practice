@file:JvmName("Coroutine6")

package com.jthou.coroutines

import kotlinx.coroutines.*
import java.util.concurrent.Executors

// 代码段2

val mySingleDispatcher = Executors.newSingleThreadExecutor {
    Thread(it, "MySingleThread").apply { isDaemon = true }
}.asCoroutineDispatcher()

fun main() = runBlocking(mySingleDispatcher) {
    val user = getUserInfo()
    logX(user)
}

suspend fun getUserInfo(): String {
    logX("Before IO Context.")
    withContext(Dispatchers.IO) {
        logX("In IO Context.")
        delay(1000L)
    }
    logX("After IO Context.")
    return "BoyCoder"
}

/*
输出结果：
================================
Before IO Context.
Thread:main @coroutine#1
================================
================================
In IO Context.
Thread:DefaultDispatcher-worker-1 @coroutine#1
================================
================================
After IO Context.
Thread:main @coroutine#1
================================
================================
BoyCoder
Thread:main @coroutine#1
================================
*/