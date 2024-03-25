package com.jthou.pro.handler

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityMessageMechanismBinding
import com.utils.log
import splitties.views.onClick

class MessageMechanismActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMessageMechanismBinding.inflate(layoutInflater) }

    private class MyHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            "MyHandler handleMessage".log()
        }
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            "handler handleMessage".log()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.button1.onClick {
            handler.sendMessage(Message.obtain())
        }

        binding.button2.onClick {
            MyHandler().sendMessage(Message.obtain())
        }
    }

}