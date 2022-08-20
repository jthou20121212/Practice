package com.study.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.ActivityConstraintLayoutBinding
import com.jthou.pro.crazy.databinding.ActivitySavedStateBinding
import com.study.viewbinding.viewBinding
import com.utils.log

class ConstraintLayoutActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityConstraintLayoutBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            "what".log()
        }
    }

}
