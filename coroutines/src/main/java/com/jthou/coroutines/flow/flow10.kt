package com.jthou.coroutines.flow

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

// 代码段20

fun main() = runBlocking {
    flow {
        println("emit: 3")
        emit(3)
        println("emit: 4")
        emit(4)
        println("emit: 5")
        emit(5)
    }.filter {
        println("filter: $it")
        it > 2
    }.map {
        println("map: $it")
        it * 2
    }.collect {
        println("collect: $it")
    }
}
/*
输出结果：
emit: 3
filter: 3
map: 3
collect: 6
emit: 4
filter: 4
map: 4
collect: 8
emit: 5
filter: 5
map: 5
collect: 10
*/