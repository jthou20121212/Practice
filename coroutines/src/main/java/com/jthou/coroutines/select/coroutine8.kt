package com.jthou.coroutines.select

// 代码段19
import kotlinx.coroutines.*

fun main() = runBlocking {
    val myExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Catch exception: $throwable")
    }

    // 不再传入myExceptionHandler
    val scope = CoroutineScope(coroutineContext)
    scope.launch {
        async {
            delay(100L)
        }
        launch {
            delay(100L)
            // 变化在这里
            launch(myExceptionHandler) {
                delay(100L)
                1 / 0
            }
        }
        delay(100L)
    }
    delay(1000L)
    println("End")
}
/*
输出结果
崩溃：
Exception in thread "main" ArithmeticException: / by zero
*/