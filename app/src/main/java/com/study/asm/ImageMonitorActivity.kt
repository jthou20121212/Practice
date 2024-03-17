package com.study.asm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jthou.pro.crazy.databinding.ActivityImageMonitorBinding
import com.study.viewbinding.viewBinding

/**
 * 大图检测
 *
 * @author jthou
 * @since 1.0.0
 * @date 06-08-2023
 */
class ImageMonitorActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_URL1 = "https://img.huxiucdn.com/test/article/share/202003/12/110300029339.gif"
        const val IMAGE_URL2 = "https://img.huxiucdn.com/article/content/202306/15/195742529583.png"
    }

    private val binding by viewBinding(ActivityImageMonitorBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(IMAGE_URL1).into(binding.imageView1)
        Glide.with(this).load(IMAGE_URL1).into(binding.appCompatImageView1)

        Glide.with(this).load(IMAGE_URL2).into(binding.imageView2)
        Glide.with(this).load(IMAGE_URL2).into(binding.appCompatImageView2)
    }

}