package com.study

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ShadowUtils
import com.google.android.material.shadow.ShadowDrawableWrapper
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.ActivityShadowBinding

class ShadowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShadowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bitmapDrawable: Drawable = BitmapDrawable(
            resources,
            BitmapFactory.decodeResource(resources, R.drawable.fuck_my_life)
        )
        val drawable = ShadowDrawableWrapper(this, bitmapDrawable, 10f, 20f, 20f)
        binding.button4.background = drawable

        val config = ShadowUtils.Config().setShadowColor(Color.RED)
            .setShadowSize(10)
            .setShadowRadius(5f)
        ShadowUtils.apply(binding.button5, config)
    }
}