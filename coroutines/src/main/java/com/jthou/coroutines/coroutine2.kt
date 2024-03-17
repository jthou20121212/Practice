package com.jthou.coroutines

import kotlinx.coroutines.*

// 代码段18

@OptIn(ExperimentalStdlibApi::class)
fun main() = runBlocking {
    val scope = CoroutineScope(Job() + mySingleDispatcher)
    // 注意这里
    scope.launch(CoroutineName("MyFirstCoroutine!")) {
        logX(coroutineContext[CoroutineDispatcher] == mySingleDispatcher)
        delay(1000L)
        logX("First end!")
    }

    delay(500L)
    scope.cancel()
    delay(1000L)
}

/*
输出结果：

================================
true
Thread:MySingleThread @MyFirstCoroutine!#2  // 注意这里
================================
*/