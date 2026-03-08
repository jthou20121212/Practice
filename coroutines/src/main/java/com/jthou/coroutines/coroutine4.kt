@file:JvmName("Coroutine4")
package com.jthou.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 代码段2

fun main() = runBlocking {
    val job = launch {
        delay(1000L)
    }
    job.log()       // ①
    job.cancel()    // ②
    job.log()       // ③
    delay(1500L)
}

/**
 * 打印Job的状态信息
 */
fun Job.log() {
    logX("""
        isActive = $isActive
        isCancelled = $isCancelled
        isCompleted = $isCompleted
    """.trimIndent())
}

/**
 * 控制台输出带协程信息的log
 */
fun logX(any: Any?) {
    println("""
================================
$any
Thread:${Thread.currentThread().name}
================================""".trimIndent())
}


/*
输出结果：
================================
isActive = true
isCancelled = false
isCompleted = false
Thread:main @coroutine#1
================================
================================
isActive = false
isCancelled = true
isCompleted = false
Thread:main @coroutine#1
================================
*/