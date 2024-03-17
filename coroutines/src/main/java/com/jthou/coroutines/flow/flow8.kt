package com.jthou.coroutines.flow

import com.jthou.coroutines.logX
import com.jthou.coroutines.mySingleDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


// 代码段17
fun main() = runBlocking {
    val scope = CoroutineScope(mySingleDispatcher)
    val flow = flow {
        logX("Start")
        emit(1)
        logX("Emit: 1")
        emit(2)
        logX("Emit: 2")
        emit(3)
        logX("Emit: 3")
    }
        .flowOn(Dispatchers.IO)
        .filter {
            logX("Filter: $it")
            it > 2
        }
        .onEach {
            logX("onEach $it")
        }

    scope.launch { // 注意这里
        flow.collect()
    }

    delay(100L)
}