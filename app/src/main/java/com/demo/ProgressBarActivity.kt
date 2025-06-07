package com.demo

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import com.jthou.pro.crazy.databinding.ActivityProgressBarBinding
import com.study.viewbinding.viewBinding
import com.widget.CircularProgressBar


/**
 * @author jthou
 * @date 2024/6/5 11:19
 * @since
 */
class ProgressBarActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityProgressBarBinding::inflate)

    private var progress = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val circularProgressBar = binding.circularProgressBar
        circularProgressBar.setProgress(50f) // 设置进度为 50%
        circularProgressBar.setColor(Color.RED) // 设置颜色
        circularProgressBar.setStrokeWidth(10) // 设置圆环宽度

        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                progress++
                circularProgressBar.setProgress(progress)
                if (progress < 100) {
                    handler.postDelayed(this, 16)
                }
            }
        }
        handler.postDelayed(runnable, 16)
    }

}