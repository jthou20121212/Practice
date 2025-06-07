@file:JvmName("Coroutine4")
package com.jthou.coroutines

import kotlinx.coroutines.*


// 代码段15

fun main() = runBlocking {
    val job = launch {
        println("First coroutine start!")
        delay(1000L)
        println("First coroutine end!")
    }

    job.join()
    val job2 = launch(job) {
        println("Second coroutine start!")
        delay(1000L)
        println("Second coroutine end!")
    }
    job2.join()
    println("Process end!")
}