package com.study

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat.setTint
import com.blankj.utilcode.util.ScreenUtils
import com.google.android.material.shape.*
import com.jthou.pro.crazy.databinding.ActivityShadowBinding
import com.utils.dp
import com.utils.log

class ShadowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShadowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            //        val drawable = ShadowDrawableWrapper(this, ColorDrawable(Color.RED), 5f, 20f, 50f)
//        binding.button4.background = drawable
//
//        val config = ShadowUtils.Config().setShadowColor(Color.RED)
//            .setShadowSize(20)
//            .setShadowMaxSize(50)
//            .setShadowRadius(5f)
//        ShadowUtils.apply(binding.button5, config)

            // button6 MaterialShapeDrawable
            val offset = (ScreenUtils.getScreenWidth() - 32.dp) / 2 - 18.dp
            val shapeModel = ShapeAppearanceModel.builder()
                .setAllCornerSizes(4.dp.toFloat())
                .setTopEdge(
                    OffsetEdgeTreatment(
                        TriangleEdgeTreatment(6.dp.toFloat(), false),
                        // x 中心点往左的偏移量
                        offset.toFloat()
                    )
                )
                .build()
            val backgroundDrawable = MaterialShapeDrawable(shapeModel).apply {
                setTint(Color.BLUE)
                paintStyle = Paint.Style.FILL
            }
            // 需要设置 parent 的 clipChildren 属性
            binding.button6.background = backgroundDrawable

            // button7 ViewOutlineProvider
            val viewOutline = CommonOutlineProvider(100.dp, 100.dp, 10.dp)
            button7.outlineProvider = viewOutline
            button7.clipToOutline = true
        }
    }
}