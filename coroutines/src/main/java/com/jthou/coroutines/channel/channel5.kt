package com.jthou.coroutines

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// 代码段7

fun main() = runBlocking {
    // 变化在这里
    val channel = Channel<Int>(
        capacity = 3,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    launch {
        (1..3).forEach {
            channel.send(it)
            println("Send: $it")
        }

        channel.send(4) // 被丢弃
        println("Send: 4")
        channel.send(5) // 被丢弃
        println("Send: 5")

        channel.close()
    }

    launch {
        for (i in channel) {
            println("Receive: $i")
        }
    }

    println("end")
}

/*
输出结果：
end
Send: 1
Send: 2
Send: 3
Send: 4
Send: 5
Receive: 1
Receive: 2
Receive: 3
*/