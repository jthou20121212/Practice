package com.study.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityCoordinatorLayout4Binding
import com.study.viewbinding.viewBinding

class CoordinatorLayout4Activity : AppCompatActivity() {

    private val binding by viewBinding(ActivityCoordinatorLayout4Binding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}