package com.jthou.coroutines.flow

import com.jthou.coroutines.logX
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion


// 代码段11

fun main() {
    val scope = CoroutineScope(Job())
    scope.launch {
        flow {
            logX("上游")
            repeat(100) {
                emit(it)
            }
        }.filter {
            logX("中间")
            it > 2
        }
            .map { it * 2 }
            .onCompletion {
                logX(it)
            }
            .collect {
                logX(it)
                delay(1000L)
            }
    }

    Thread.sleep(2000L)

    scope.cancel()
    logX("结束")
}

/*
输出结果：
================================
上游
Thread:DefaultDispatcher-worker-1
================================
================================
中间
Thread:DefaultDispatcher-worker-1
================================
================================
中间
Thread:DefaultDispatcher-worker-1
================================
================================
中间
Thread:DefaultDispatcher-worker-1
================================
================================
中间
Thread:DefaultDispatcher-worker-1
================================
================================
6
Thread:DefaultDispatcher-worker-1
================================
================================
中间
Thread:DefaultDispatcher-worker-1
================================
================================
8
Thread:DefaultDispatcher-worker-1
================================
================================
结束
Thread:main
================================
================================
kotlinx.coroutines.JobCancellationException: Job was cancelled; job=JobImpl{Cancelling}@407d87d0
Thread:DefaultDispatcher-worker-1
================================
*/