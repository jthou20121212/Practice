package com.jthou.coroutines

import kotlinx.coroutines.*
import java.util.concurrent.Executors

// 代码段5

val mySingleDispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()

fun main() = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        logX("Coroutine start!")
        delay(4000L) // 变化在这里
    }
    delay(500L)
    job.log()
    job.start()
    job.log()
    delay(1100L)
    job.log()
    delay(2000L)
    logX("Process end!")
}

/** * 打印Job的状态信息 */
fun Job.log() {
    logX(""" isActive = $isActive isCancelled = $isCancelled isCompleted = $isCompleted """.trimIndent())
}

/** * 控制台输出带协程信息的log */
fun logX(any: Any?) {
    println("""================================$any Thread:${Thread.currentThread().name}================================""".trimIndent())
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
isActive = true
isCancelled = false
isCompleted = false
Thread:main @coroutine#1
================================
================================
Process end!
Thread:main @coroutine#1
================================
到这里，job仍然还在delay，整个程序并没有完全退出。
*/