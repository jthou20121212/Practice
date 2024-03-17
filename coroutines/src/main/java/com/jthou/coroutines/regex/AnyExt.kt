package com.jthou.coroutines.regex

import android.util.Log

const val TAG = "---jthou---"

fun Any?.log() {
    Log.i(TAG, this?.toString() ?: "null")
}

fun printMethodStack() {
    val stack = Thread.currentThread().stackTrace
    for (element in stack) {
        Log.i(TAG, "element.getMethodName() : " + element.methodName)
    }
}