package com.jthou.pro.crazy

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 13-03-2022
 */
class AnnotationLifecycleTest : LifecycleObserver {

    companion object {
        private const val TAG = "AnnotationLifecycleTest"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(lifecycleOwner: LifecycleOwner, onEvent: Lifecycle.Event) {
        Thread.currentThread().stackTrace.forEach {
            Log.i(TAG, it.className + "." + it.methodName)
        }
    }

}