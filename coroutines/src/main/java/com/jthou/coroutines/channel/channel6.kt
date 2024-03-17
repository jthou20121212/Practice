package com.jthou.coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking

// 代码段8

fun main() = runBlocking {
    // 无限容量的管道
    val channel = Channel<Int>(Channel.UNLIMITED) {
        println("onUndeliveredElement = $it")
    }

    // 等价这种写法
//    val channel = Channel<Int>(Channel.UNLIMITED, onUndeliveredElement = { println("onUndeliveredElement = $it") })

    // 放入三个数据
    (1..3).forEach {
        channel.send(it)
    }

    // 取出一个，剩下两个
    channel.receive()

    // 取消当前channel
    channel.cancel()
}

/*
输出结果：
onUndeliveredElement = 2
onUndeliveredElement = 3
*/