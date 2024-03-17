package com.jthou.coroutines

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


// 代码段10

fun main() = runBlocking {
    // 1，创建管道
    val channel: ReceiveChannel<Int> = produce {
        // 发送3条数据
        (1..3).forEach {
            send(it)
        }
    }

    // 调用4次receive()
    channel.receive() // 1
    channel.receive() // 2
    channel.receive() // 3
    channel.receive() // 异常

    println("end")
}

/*
输出结果：
ClosedReceiveChannelException: Channel was closed
*/