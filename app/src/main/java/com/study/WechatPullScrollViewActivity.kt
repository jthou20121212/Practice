package com.study

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityWechatPullScrollViewBinding
import com.study.viewbinding.viewBinding

class WechatPullScrollViewActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityWechatPullScrollViewBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 收起
        binding.foreTv1.setOnClickListener {
             binding.wechatPullLayout.collapse()
        }

        // 收起
        binding.foreTv1.setOnClickListener {
             binding.wechatPullLayout.expand()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}