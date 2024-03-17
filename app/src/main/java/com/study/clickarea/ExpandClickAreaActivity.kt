package com.study.clickarea

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.ViewHelper
import com.jthou.pro.crazy.databinding.ActivityExpandClickAreaBinding
import com.jthou.pro.crazy.databinding.ActivityNestedBinding
import com.study.viewbinding.viewBinding
import com.utils.dp
import splitties.views.onClick

class ExpandClickAreaActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityExpandClickAreaBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvFirst.onClick {
            Toast.makeText(this, "first", Toast.LENGTH_SHORT).show()
        }

        binding.tvSecond.onClick {
            Toast.makeText(this, "second", Toast.LENGTH_SHORT).show()
        }

        binding.tvThird.onClick {
            Toast.makeText(this, "third", Toast.LENGTH_SHORT).show()
        }

        ViewHelper.expandTouchArea(binding.tvSecond, binding.tvFirst, 150.dp)
    }

}