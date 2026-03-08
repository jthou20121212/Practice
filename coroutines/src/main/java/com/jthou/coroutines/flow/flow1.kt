package com.jthou.coroutines

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking

// 代码段2

fun main() = runBlocking {
    flowOf(1, 2, 3, 4, 5).filter { it > 2 }
        .map { it * 2 }
        .take(2)
        .collect {
            println(it)
        }

    listOf(1, 2, 3, 4, 5).filter { it > 2 }
        .map { it * 2 }
        .take(2)
        .forEach {
            println(it)
        }
}

/*
输出结果
6
8
6
8
*/