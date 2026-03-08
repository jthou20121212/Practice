package com.jthou.coroutines.channel

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// 代码段8
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val channel1 = produce {
        send(1)
        delay(50L)
        send(2)
        delay(50L)
        send(3)
        delay(150L)
    }

    val channel2 = produce {
        delay(100L)
        send("a")
        delay(200L)
        send("b")
        delay(200L)
        send("c")
    }

    async {
        channel1.consumeEach {
            println(it)
        }
    }

    async {
        channel2.consumeEach {
            println(it)
        }
    }

    println("Time cost: ${System.currentTimeMillis() - startTime}")
}

/*
输出结果
1
2
3
a
b
c
Time cost: 989
*/