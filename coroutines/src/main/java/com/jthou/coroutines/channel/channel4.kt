package com.jthou.coroutines

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// 代码段6
// 可以运用 onBufferOverflow 与 capacity，来实现 CONFLATED 的效果
fun main() = runBlocking {
    // 变化在这里
    val channel = Channel<Int>(
        capacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

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