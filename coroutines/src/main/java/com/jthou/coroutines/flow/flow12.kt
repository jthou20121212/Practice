package com.jthou.coroutines.flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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