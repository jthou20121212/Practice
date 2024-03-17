package com.study.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityCoordinatorLayout2Binding
import com.study.viewbinding.viewBinding

class CoordinatorLayout2Activity : AppCompatActivity() {

    private val binding by viewBinding(ActivityCoordinatorLayout2Binding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}