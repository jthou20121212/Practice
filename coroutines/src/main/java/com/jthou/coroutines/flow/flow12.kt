package com.jthou.coroutines.flow

import com.jthou.coroutines.logX
import com.jthou.coroutines.mySingleDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// 代码段23

fun main() = runBlocking {
    flow {
        withContext(Dispatchers.IO) {
            emit(1)
        }
    }.map { it * 2 }
        .collect()
}

/*
输出结果
IllegalStateException: Flow invariant is violated
*/