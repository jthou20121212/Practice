@file:JvmName("Coroutine1")
package com.jthou.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

// 代码段18

fun main() = runBlocking {
    val myExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Catch exception: $throwable")
    }

    // 注意这里
    val scope = CoroutineScope(coroutineContext + SupervisorJob() + myExceptionHandler)

    scope.launch {
        async {
            delay(100L)
        }

        launch {
            delay(100L)

        }
        launch {
            delay(100L)
            1 / 0 // 故意制造异常
        }

        delay(100L)
    }

    delay(1000L)
    println("End")
}

/*
Catch exception: ArithmeticException: / by zero
End
*/