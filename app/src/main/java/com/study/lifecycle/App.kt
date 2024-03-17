package com.study.lifecycle

import android.app.Application
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.github.anrwatchdog.ANRWatchDog

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 07-03-2022
 */
class App : Application() {

    companion object {
        const val TAG = "Application"
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {

            override fun onCreate(owner: LifecycleOwner) {
                super.onCreate(owner)
                Log.i(TAG, "onCreate")
            }

            override fun onStart(owner: LifecycleOwner) {
                super.onStart(owner)
                Log.i(TAG, "onStart")
            }

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                Log.i(TAG, "onResume")
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                Log.i(TAG, "onPause")
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                Log.i(TAG, "onStop")
            }

            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                Log.i(TAG, "onDestroy")
            }

        })

        ANRWatchDog().start()
    }

}