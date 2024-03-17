package com.jthou.coroutines

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking


// 代码段1

fun main() = runBlocking {
    flow {                  // 上游，发源地
        emit(1)             // 挂起函数
        emit(2)
        emit(3)
        emit(4)
        emit(5)
    }.filter { it > 2 }     // 中转站1
        .map { it * 2 }     // 中转站2
        .take(2)            // 中转站3
        .collect{           // 下游
            println(it)
        }
}

/*
输出结果：
6
8
*/