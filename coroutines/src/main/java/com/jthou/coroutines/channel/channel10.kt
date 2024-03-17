package com.jthou.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 代码段12

fun main() = runBlocking {
    // 1，创建管道
    val channel: ReceiveChannel<Int> = produce {
        // 发送3条数据
        (1..3).forEach {
            send(it)
            println("Send $it")
        }
    }

    // 使用while循环判断isClosedForReceive
    while (!channel.isClosedForReceive) {
        val i = channel.receive()
        println("Receive $i")
    }

    println("end")
}

/*
输出结果
Send 1
Receive 1
Receive 2
Send 2
Send 3
Receive 3
end
*/