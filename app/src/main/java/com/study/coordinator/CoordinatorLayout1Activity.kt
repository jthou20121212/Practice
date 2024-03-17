package com.study.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityCoordinatorLayout1Binding
import com.study.viewbinding.viewBinding

class CoordinatorLayout1Activity : AppCompatActivity() {

    private val binding by viewBinding(ActivityCoordinatorLayout1Binding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}