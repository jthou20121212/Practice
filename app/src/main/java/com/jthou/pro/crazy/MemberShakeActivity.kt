package com.jthou.pro.crazy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityMemberShakeBinding
import splitties.views.onClick

class MemberShakeActivity : AppCompatActivity() {

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val stringArray = Array(100000) { index ->
                index.toString()
            }
            sendEmptyMessage(0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMemberShakeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.onClick {
            mHandler.sendEmptyMessage(0)
        }
    }

}