@file:JvmName("Coroutine5")
package com.jthou.coroutines

import kotlinx.coroutines.*



// 代码段3

fun main() = runBlocking {
    //                  变化在这里
    //                      ↓
    val job = launch(start = CoroutineStart.LAZY) {
        logX("Coroutine start!")
        delay(1000L)
    }
    delay(500L)
    job.log()
//    job.start()     // 变化在这里
//    job.log()
    delay(500L)
    job.cancel()
    delay(500L)
    job.log()
    delay(2000L)
    logX("Process end!")
}

/*
输出结果：
================================
isActive = false
isCancelled = false
isCompleted = false
Thread:main @coroutine#1
================================
================================
isActive = true
isCancelled = false
isCompleted = false
Thread:main @coroutine#1
================================
================================
Coroutine start!
Thread:main @coroutine#2
================================
================================
isActive = false
isCancelled = true
isCompleted = true
Thread:main @coroutine#1
================================
================================
Process end!
Thread:main @coroutine#1
================================
*/