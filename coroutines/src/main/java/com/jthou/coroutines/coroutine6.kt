package com.jthou.coroutines


// 代码段21

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

//                        挂起函数能可以访问协程上下文吗？
//                                 ↓
suspend fun testContext() = coroutineContext

fun main() = runBlocking {
    println(testContext())
}