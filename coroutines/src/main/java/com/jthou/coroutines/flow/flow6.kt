package com.jthou.coroutines.flow

import com.jthou.coroutines.logX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

// 代码段21

fun main() = runBlocking {
    val mySingleDispatcher = Executors.newSingleThreadExecutor {
        Thread(it, "MySingleThread")
    }.asCoroutineDispatcher()
    fun loadData() = flow {
        repeat(3){
            delay(100L)
            emit(it)
            logX("emit $it")
        }
    }

    // 模拟Android、Swing的UI
    val uiScope = CoroutineScope(mySingleDispatcher)

    loadData()
        .map { it * 2 }
        .flowOn(Dispatchers.IO) // 1，耗时任务
        .onEach {
            logX("onEach $it")
        }
        .launchIn(uiScope)      // 2，UI任务

    delay(1000L)
}