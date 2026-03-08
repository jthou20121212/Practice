package com.jthou.coroutines

// 代码段17
import com.jthou.coroutines.regex.log
import kotlinx.coroutines.*

//fun main() = runBlocking {
//
//    val scope = CoroutineScope(coroutineContext)
//
//    scope.launch {
//        async {
//            delay(100L)
//        }
//
//        launch {
//            delay(100L)
//
//            launch {
//                delay(100L)
//                1 / 0 // 故意制造异常
//            }
//        }
//
//        delay(100L)
//    }
//
//    delay(1000L)
//    println("End")
//}
//
///*
//输出结果
//Exception in thread "main" ArithmeticException: / by zero
//*/

// 代码段18

fun main() = runBlocking {
    val myExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Catch exception: $throwable")
    }

    // 注意这里
    val scope = CoroutineScope(coroutineContext + Job() + myExceptionHandler)
    // "scope.coroutineContext is ${scope.coroutineContext}".log()

    scope.launch {
        async {
            delay(100L)
        }

        launch {
            delay(100L)

            launch {
                delay(100L)
                1 / 0 // 故意制造异常
            }
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