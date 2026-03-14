package com.jthou.coroutines

// 代码段3

import kotlinx.coroutines.*
import java.util.concurrent.Executors

val fixedDispatcher = Executors.newFixedThreadPool(2) {
    Thread(it, "MyFixedThread").apply { isDaemon = false }
}.asCoroutineDispatcher()

fun main() = runBlocking {
    // val otherJob = Job()
    // 父协程
    val parentJob = launch(fixedDispatcher) {

        // 1，注意这里
        // launch(otherJob) { // 子协程1
        launch() { // 子协程1
            var i = 0
            while (isActive) {
                Thread.sleep(500L)
                i ++
                println("First i = $i")
            }
        }

        launch { // 子协程2
            var i = 0
            while (isActive) {
                Thread.sleep(500L)
                i ++
                println("Second i = $i")
            }
        }
    }

    delay(2000L)

    // otherJob.cancel()

    parentJob.cancel()
    parentJob.join()

    println("End")
}

/*
输出结果
First i = 1
Second i = 1
First i = 2
Second i = 2
Second i = 3
First i = 3
First i = 4
Second i = 4
End
First i = 5
First i = 6
// 子协程1永远不会停下来
*/