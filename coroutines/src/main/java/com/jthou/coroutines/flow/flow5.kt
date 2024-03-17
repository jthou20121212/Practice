package com.jthou.coroutines.flow

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


// 代码段8
fun main() = runBlocking {
    val flow = flow {
        emit(1)
        emit(2)
        throw IllegalStateException()
        emit(3)
    }

    flow.map { it * 2 }
        .catch { println("catch: $it") } // 注意这里
        .collect {
            println(it)
        }
}
/*
输出结果：
2
4
catch: java.lang.IllegalStateException
*/