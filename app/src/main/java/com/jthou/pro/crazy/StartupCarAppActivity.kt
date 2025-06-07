package com.jthou.pro.crazy

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jthou.pro.crazy.databinding.ActivityStartupCarAppBinding
import com.study.viewbinding.viewBinding
import splitties.views.onClick


class StartupCarAppActivity : AppCompatActivity() {

    companion object {
        const val PKG = "com.huxiu.incar"
        const val CLS = "com.huxiu.incar.splash.SplashActivity"
    }

    private val binding by viewBinding(ActivityStartupCarAppBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textView.onClick {
//            val launchIntent = packageManager.getLaunchIntentForPackage(PKG)
//            launchIntent?.let {
//                startActivity(launchIntent)
//            } ?: run {
//                // 可能未安装，可以提示用户或进行其他操作
//                Toast.makeText(this, "虎嗅车机项目没有安装.", Toast.LENGTH_SHORT).show()
//            }

            val intent = Intent()
            intent.setComponent(ComponentName(PKG, CLS))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Activity 没有找到，可能是因为 App 未安装或者该 Activity 未在 App 中声明
                Toast.makeText(this, "虎嗅车机项目没有安装.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}