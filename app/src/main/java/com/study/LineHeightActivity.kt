package com.study

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ConvertUtils
import com.jthou.pro.crazy.R

/**
 * 测试 setLineHeight 方法
 *
 * @author jthou
 * @since 1.0.0
 * @date 28-12-2021
 */
class LineHeightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_height)
        val textView = findViewById<TextView>(R.id.tv1)
        textView.lineHeight = ConvertUtils.dp2px(100f)
    }

}