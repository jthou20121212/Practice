package com.jthou.coroutines

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


// 代码段6
fun main() = runBlocking {
    flowOf(1, 2, 3, 4, 5)
        .onCompletion { println("onCompletion") } // 注意这里
        .filter {
            println("filter: $it")
            it > 2
        }
        .take(2)
        .collect {
            println("collect: $it")
        }
}

/*
输出结果
filter: 1
filter: 2
filter: 3
collect: 3
filter: 4
collect: 4
onCompletion
*/