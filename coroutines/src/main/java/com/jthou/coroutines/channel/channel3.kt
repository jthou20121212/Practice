package com.jthou.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// 代码段5

fun main() = runBlocking {
    // 变化在这里
    // CONFLATED，代表了容量为 1，新的数据会替代旧的数据；
    // 发送方也会一直发送数据，而且，对于接收方来说，它永远只能接收到最后一条数据
    val channel = Channel<Int>(capacity = Channel.Factory.CONFLATED)

    launch {
        (1..3).forEach {
            channel.send(it)
            println("Send: $it")
        }

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
Receive: 3
*/