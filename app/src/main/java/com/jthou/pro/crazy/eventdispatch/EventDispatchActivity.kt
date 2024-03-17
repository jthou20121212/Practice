package com.jthou.pro.crazy.eventdispatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityEventDispatchBinding
import com.study.viewbinding.viewBinding
import com.utils.log
import splitties.views.onClick

class EventDispatchActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityEventDispatchBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button1.onClick {
            "button1".log()
        }

        binding.button2.onClick {
            "button2".log()
        }

    }

}