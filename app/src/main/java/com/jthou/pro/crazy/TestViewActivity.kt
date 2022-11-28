package com.jthou.pro.crazy

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ConvertUtils
import com.jthou.pro.crazy.databinding.ActivityTestViewBinding
import com.study.viewbinding.viewBinding

class TestViewActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityTestViewBinding::inflate)

    companion object {
        fun launchActivity(context: Context) {
            val intent = Intent(context, TestViewActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.roundCornerView.corner = ConvertUtils.dp2px(88f).toFloat()

        binding.textView.text = "第一次文本"
        binding.textView.text = "第二次文本"

        binding.roundCornerView.postDelayed({
            val animator = ValueAnimator.ofFloat(binding.roundCornerView.corner, 0f)
            animator.addUpdateListener {
                val animatedValue = it.animatedValue as Float
                binding.roundCornerView.corner = animatedValue
                binding.roundCornerView.invalidate()
            }
            animator.start()
        }, 1000)
    }

}