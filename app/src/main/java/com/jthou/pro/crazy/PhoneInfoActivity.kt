package com.jthou.pro.crazy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ScreenUtils
import com.jthou.pro.crazy.databinding.ActivityPhoneInfoBinding

class PhoneInfoActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(context: Context) {
            val intent = Intent(context, PhoneInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPhoneInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tv1.text = "宽度：${ScreenUtils.getScreenWidth()}"
        binding.tv2.text = "高度：${ScreenUtils.getAppScreenHeight()}"
        binding.tv3.text = "密度 DPI：${ScreenUtils.getScreenDensityDpi()}"
        binding.tv4.text = "dp / px：${ScreenUtils.getScreenDensity()}"
    }

}