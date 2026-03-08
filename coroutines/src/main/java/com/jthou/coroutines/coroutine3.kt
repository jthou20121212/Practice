@file:JvmName("Coroutine3")
package com.jthou.coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val deferred: Deferred<String> = async {
        println("In async:${Thread.currentThread().name}")
        delay(1000L) // 模拟耗时操作
        println("In async after delay!")
        return@async "Task completed!"
    }

    // 不再调用 deferred.await()
    delay(2000L)
}