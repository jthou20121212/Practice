package com.jthou.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 代码段11

fun main() = runBlocking {
    val channel: Channel<Int> = Channel()

    launch {
        (1..3).forEach {
            channel.send(it)
        }
    }

    // 调用4次receive()
    println("Receive: ${channel.receive()}")
    println("Receive: ${channel.receive()}")
    println("Receive: ${channel.receive()}")
    channel.receive()       // 永远挂起

    println("end")
}

/*
输出结果
Receive: 1
Receive: 2
Receive: 3
*/