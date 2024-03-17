package com.jthou.coroutines.flow

import com.jthou.coroutines.logX
import com.jthou.coroutines.mySingleDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


// 代码段21

fun main() = runBlocking {
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