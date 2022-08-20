package com.jthou.pro.crazy

import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.jthou.pro.crazy.databinding.ActivityEventDispatchBinding
import kotlin.concurrent.thread

class EventDispatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_dispatch)
        val binding = ActivityEventDispatchBinding.inflate(layoutInflater)
        binding.button.setOnLongClickListener {
            LogUtils.i("jthou", "onLongClick")
            false
        }

        (window.decorView.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)

        Looper.getMainLooper().setMessageLogging {

        }

        thread {
            synchronized(this@EventDispatchActivity) {
                Thread.sleep(20000)
            }
        }.start()

        synchronized(this@EventDispatchActivity) {
            LogUtils.i("走你")
        }

    }

}