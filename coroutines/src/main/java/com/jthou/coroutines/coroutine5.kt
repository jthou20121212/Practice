@file:JvmName("Coroutine5")
package com.jthou.coroutines

import kotlinx.coroutines.*



// 代码段8

fun main() = runBlocking {
    println("Before launch.")  // 1
//               变化在这里
//                  ↓
    launch(Dispatchers.Unconfined) {
        println("In launch.")  // 2
        delay(1000L)
        println("End launch.") // 3
    }
    println("After launch")    // 4
}

/*
输出结果：
================================
Before launch.
Thread:main @coroutine#1
================================
================================
In launch.
Thread:main @coroutine#2
================================
================================
After launch
Thread:main @coroutine#1
================================
================================
End launch.
Thread:kotlinx.coroutines.DefaultExecutor @coroutine#2
================================
*/