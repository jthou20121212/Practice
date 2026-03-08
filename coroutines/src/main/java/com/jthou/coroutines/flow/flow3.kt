package com.jthou.coroutines.flow

import com.jthou.coroutines.logX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

// 代码段15

fun main() = runBlocking {
    val mySingleDispatcher = Executors.newSingleThreadExecutor {
        Thread(it, "MySingleThread")
    }.asCoroutineDispatcher()
    val scope = CoroutineScope(mySingleDispatcher)
    flowOf(1, 2, 3, 4, 5)
        .filter { it > 2 }
        .map { it * 2 }
        .take(2)
        .flowOn(Dispatchers.IO)
        .filter {
            logX("Filter: $it")
            it > 2
        }
        .onEach {
            logX("onEach $it")
        }
        .launchIn(scope)

    Unit
}
/*
输出结果：
onEach{}将运行在MySingleThread
filter{}运行在MySingleThread
flow{}运行在DefaultDispatcher
*/