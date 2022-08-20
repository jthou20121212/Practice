package com.utils

import android.util.Log
import kotlin.reflect.jvm.jvmName

const val TAG = "jthou"

fun Any.log() {
    Log.i(TAG, this.toString())

    this::class.members.forEach {
        Log.i(TAG, it.name)
    }
    Log.i(TAG, this::class.qualifiedName.toString())
    Log.i(TAG, this::class.jvmName)
    this::class.simpleName?.let { Log.i(TAG, it) }
}

