package com.demo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.google.android.material.datepicker.MaterialDatePicker
import com.jthou.pro.crazy.databinding.ActivityProgressBarBinding
import com.study.viewbinding.viewBinding
import com.widget.CircularProgressBar


/**
 * @author jthou
 * @date 2024/6/5 11:19
 * @since
 */
class MaterialDateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create the MaterialDatePicker instance
        val datePicker = MaterialDatePicker.Builder.datePicker().build()

        // Show the MaterialDatePicker
        datePicker.show(supportFragmentManager, "MaterialDatePicker")
    }

}