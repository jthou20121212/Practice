package com.jthou.coroutines

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


// 代码段14

fun main() = runBlocking {
    val channel: ReceiveChannel<Int> = produce(capacity = 3) {
        (1..300).forEach {
            send(it)
            println("Send $it")
        }
    }

    // 变化在这里
    channel.consumeEach {
        println("Receive $it")
    }

    println("end")
}

/*
输出结果：

正常
*/