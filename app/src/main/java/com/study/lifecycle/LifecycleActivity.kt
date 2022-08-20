package com.study.lifecycle

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.jthou.pro.crazy.AnnotationLifecycleTest
import com.jthou.pro.crazy.databinding.ActivityLifecycleBinding

class LifecycleActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "LifecycleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLifecycleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lifecycleOwner = binding.textView.findViewTreeLifecycleOwner()
        Log.i(TAG, "lifecycleOwner : $lifecycleOwner")

        val textView = TextView(this)
        val lifecycleOwner2 = textView.findViewTreeLifecycleOwner()
        Log.i(TAG, "lifecycleOwner2 : $lifecycleOwner2")

        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                lifecycle.addObserver(object : LifecycleEventObserver {
                    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {

                    }
                })
            }
        })

        lifecycle.addObserver(AnnotationLifecycleTest())

        Log.i(TAG, lifecycleOwner?.toString() ?: "null")
        Log.i(TAG, (lifecycleOwner == this).toString())
        Log.i(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }


}