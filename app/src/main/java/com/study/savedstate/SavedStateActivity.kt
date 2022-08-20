package com.study.savedstate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jthou.pro.crazy.databinding.ActivitySavedStateBinding

class SavedStateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySavedStateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this)[SavedStateViewModel::class.java]
        binding.button.setOnClickListener {
            viewModel.setValue("邀酒摧肠三杯醉")
        }

        viewModel.getLiveData().observe(this) {
            binding.button.text = it
        }

        viewModel.getLiveData()
    }

}