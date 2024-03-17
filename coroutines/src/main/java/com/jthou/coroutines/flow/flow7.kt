package com.jthou.coroutines.flow

import com.jthou.coroutines.logX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

// 代码段11
fun main() = runBlocking {
    val flow = flow {
        logX("Start")
        emit(1)
        logX("Emit: 1")
        emit(2)
        logX("Emit: 2")
        emit(3)
        logX("Emit: 3")
    }

    flow.filter {
        logX("Filter: $it")
        it > 2
    }
        .flowOn(Dispatchers.IO)  // 注意这里
        .collect {
            logX("Collect $it")
        }
}

/*
输出结果
================================
Start
Thread:DefaultDispatcher-worker-1 @coroutine#2
================================
================================
Filter: 1
Thread:DefaultDispatcher-worker-1 @coroutine#2
================================
================================
Emit: 1
Thread:DefaultDispatcher-worker-1 @coroutine#2
================================
================================
Filter: 2
Thread:DefaultDispatcher-worker-1 @coroutine#2
================================
================================
Emit: 2
Thread:DefaultDispatcher-worker-1 @coroutine#2
================================
================================
Filter: 3
Thread:DefaultDispatcher-worker-1 @coroutine#2
================================
================================
Emit: 3
Thread:DefaultDispatcher-worker-1 @coroutine#2
================================
================================
Collect 3
Thread:main @coroutine#1
================================
*/