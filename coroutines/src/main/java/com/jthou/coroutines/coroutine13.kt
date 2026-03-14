package com.jthou.coroutines

// 代码段5
import kotlinx.coroutines.*

// 代码段6

// 代码段14

fun main() = runBlocking {
    val scope = CoroutineScope(SupervisorJob())
    val deferred = scope.async {
        delay(100L)
        1 / 0
    }
    try {
        deferred.await()
    } catch (e: ArithmeticException) {
        println("Catch $e")
    }

    delay(500L)
    println("End")
}

/*
输出结果
End
*/