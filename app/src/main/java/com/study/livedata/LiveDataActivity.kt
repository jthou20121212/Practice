package com.study.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jthou.pro.crazy.databinding.ActivityLiveDataBinding
import com.utils.log
import splitties.activities.start

class LiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ListViewModel.navigateToDetails.observe(this) {
            javaClass.name.log()
        }

        binding.button.setOnClickListener {
            start<SecondActivity>()
        }

        val factory: ViewModelProvider.Factory = ViewModelProvider.NewInstanceFactory()
        val viewModel = ViewModelProvider(this, factory)[TestViewModel::class.java]
        // factory.create(List)

        lastNonConfigurationInstance

    }

}