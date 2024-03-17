package com.jthou.coroutines.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


// 代码段19

class ChannelModel {
    // 对外只提供读取功能
    val channel: ReceiveChannel<Int> by ::_channel
    private val _channel: Channel<Int> = Channel()

    suspend fun init() {
        (1..3).forEach {
            _channel.send(it)
        }
    }
}

fun main() = runBlocking {
    val model = ChannelModel()
    launch {
        model.init()
    }

    model.channel.consumeEach {
        println(it)
    }
}