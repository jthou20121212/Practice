package com.jthou.coroutines

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

// 代码段4
fun main() = runBlocking {
    flowOf(1, 2, 3, 4, 5)
        .filter {
            println("filter: $it")
            it > 2
        }
        .map {
            println("map: $it")
            it * 2
        }
        .take(2)
        .onStart { println("onStart") } // 注意这里
        .collect {
            println("collect: $it")
        }
}

/*
输出结果
onStart
filter: 1
filter: 2
filter: 3
map: 3
collect: 6
filter: 4
map: 4
collect: 8
*/