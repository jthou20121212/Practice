package com.study.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityCoordinatorLayout2Binding
import com.jthou.pro.crazy.databinding.ActivityCoordinatorLayout3Binding
import com.study.viewbinding.viewBinding

class CoordinatorLayout3Activity : AppCompatActivity() {

    private val binding by viewBinding(ActivityCoordinatorLayout3Binding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

}