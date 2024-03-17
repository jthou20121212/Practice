package com.study.since290

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.ActivityIndustrySituationBinding
import com.study.viewbinding.viewBinding
import jp.wasabeef.blurry.Blurry

class IndustrySituationActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityIndustrySituationBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_industry_situation)
        binding.root.postDelayed(500) {
            binding.industrySituationView.setIndustrySituation(100, 75)
        }
        binding.root.postDelayed(3500) {
            val bitmap = Blurry.with(this)
                .radius(10)
                .sampling(6)
                .capture(binding.container).get()
            binding.imageView.setImageDrawable(BitmapDrawable(resources, bitmap))
        }
    }

}