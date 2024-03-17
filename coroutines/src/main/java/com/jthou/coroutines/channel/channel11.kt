package com.jthou.coroutines

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


// 代码段13

fun main() = runBlocking {
    // 变化在这里
    val channel: ReceiveChannel<Int> = produce(capacity = 3) {
        // 变化在这里
        (1..300).forEach {
            send(it)
            println("Send $it")
        }
    }



    while (!channel.isClosedForReceive) {
        val i = channel.receive()
        println("Receive $i")
    }

    println("end")
}

/*
输出结果
// 省略部分
Receive 300
Send 300
ClosedReceiveChannelException: Channel was closed
*/