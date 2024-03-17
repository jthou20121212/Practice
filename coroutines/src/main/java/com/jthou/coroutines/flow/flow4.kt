package com.jthou.coroutines

import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 代码段7
fun main() = runBlocking {
    launch {
        flow {
            emit(1)
            emit(2)
            emit(3)
        }.onCompletion { println("onCompletion first: $it") }
            .collect {
                println("collect: $it")
                if (it == 2) {
                    cancel()            // 1
                    println("cancel")
                }
            }
    }

    delay(100L)

    flowOf(4, 5, 6)
        .onCompletion { println("onCompletion second: $it") }
        .collect {
            println("collect: $it")
            // 仅用于测试，生产环境不应该这么创建异常
            throw IllegalStateException() // 2
        }
}

/*
collect: 1
collect: 2
cancel
onCompletion first: JobCancellationException: // 3
collect: 4
onCompletion second: IllegalStateException    // 4
*/