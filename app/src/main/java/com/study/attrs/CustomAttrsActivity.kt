package com.study.attrs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityCustomAttrsBinding
import com.study.viewbinding.viewBinding
import splitties.views.onClick

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 11-08-2023
 */
class CustomAttrsActivity : AppCompatActivity() {

    private var darkMode = Global.DARK_MODE

    private val binding by viewBinding(ActivityCustomAttrsBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.onClick {
            darkMode = !darkMode
            binding.textView.darkModeChange(darkMode)
        }
    }

}