package com.utils

import android.util.Log

const val TAG = "---jthou---"

fun Any?.log() {
    Log.i(TAG, this?.toString() ?: "null")
}

fun printMethodStack2() {
    Log.i(TAG, Log.getStackTraceString(Throwable()))
}