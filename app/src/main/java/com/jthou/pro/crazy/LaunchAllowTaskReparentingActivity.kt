package com.jthou.pro.crazy

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Looper
import android.util.LruCache
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityLaunchAllowTaskReparentingBinding
import splitties.views.onClick

class LaunchAllowTaskReparentingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLaunchAllowTaskReparentingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tv1.onClick {
            val intent = Intent(Intent.ACTION_PROCESS_TEXT)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_PROCESS_TEXT, "京东")
            startActivity(intent)
        }

        // val activityManagerService = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManagerService

        val looper = Looper.myLooper()!!
        looper.setMessageLogging {
            // 打印的时候获取当前时间
            // 减去时间差就是消息执行时间
        }

//        val handler = Handler(looper)
//        val method = Handler::class.java.getMethod("runWithScissors", Runnable::class.java, Long::class.java)
//        method.isAccessible = true
//        method.invoke()

        val hashMap = LinkedHashMap<String, Bitmap>()
        val lruCache = LruCache<String, Bitmap>(100)
        val iterator = lruCache.snapshot().iterator()
    }
}