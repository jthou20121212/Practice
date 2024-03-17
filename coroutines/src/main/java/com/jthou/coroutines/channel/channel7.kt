package com.jthou.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 代码段9

fun main() = runBlocking {
    // 变化在这里
    val channel: ReceiveChannel<Int> = produce {
        (1..3).forEach {
            send(it)
            println("Send: $it")
        }
    }

    launch {
        // 3，接收数据
        for (i in channel) {
            println("Receive: $i")
        }
    }

    println("end")
}