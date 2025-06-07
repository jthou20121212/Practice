@file:JvmName("Coroutine6")
package com.jthou.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

// 代码段6

val mySingleDispatcher = Executors.newSingleThreadExecutor {
    Thread(it, "MySingleThread").apply { isDaemon = true }
}.asCoroutineDispatcher()

// 代码段14

@OptIn(ExperimentalStdlibApi::class)
fun main() = runBlocking {
    // 注意这里
    val scope = CoroutineScope(Job() + mySingleDispatcher)

    scope.launch {
        // 注意这里
        println(coroutineContext[CoroutineDispatcher] == mySingleDispatcher)
        delay(1000L)
        println("First end!")  // 不会执行
    }

    delay(500L)
    scope.cancel()
    delay(1000L)
}
/*
输出结果：
================================
true
Thread:MySingleThread @coroutine#2
================================
*/