package com.jthou.coroutines.flow

import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


// 代码段19

fun main() = runBlocking {
    // 冷数据流
    val flow = flow {
        (1..3).forEach {
            println("flow Before send $it")
            emit(it)
            println("Send $it")
        }
    }

    // 热数据流
    val channel = produce<Int>(capacity = 3) {
        (1..3).forEach {
            println("channel Before send $it")
            send(it)
            println("Send $it")
        }
    }

    println("end")
}

/*
输出结果：
end
Before send 1
// Flow 当中的代码并未执行
*/