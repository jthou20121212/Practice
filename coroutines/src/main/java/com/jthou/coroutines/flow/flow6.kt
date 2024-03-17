package com.jthou.coroutines.flow

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

// 代码段9
fun main() = runBlocking {
    val flow = flow {
        emit(1)
        emit(2)
        emit(3)
    }

    flow.map { it * 2 }
        .catch { println("catch: $it") }
        .filter { it / 0 > 1}  // 故意制造异常
        .collect {
            println(it)
        }
}

/*
输出结果
Exception in thread "main" ArithmeticException: / by zero
*/