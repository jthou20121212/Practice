package com.study

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.jthou.pro.crazy.R
import com.utils.dp


class ScrollerTextViewActivity : AppCompatActivity() {

    //    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var total: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroller_text_view)
        val textView = findViewById<TextView>(R.id.textView)

        

        val viewHeight = 300.dp
        // val textHeight = textView.paint.measureText(textView.text.toString())
        val staticLayout = StaticLayout(
            textView.text,
            textView.paint,
            ScreenUtils.getScreenWidth(),
            Layout.Alignment.ALIGN_NORMAL,
            1.2f,
            3.dp.toFloat(),
            true
        )
        val textHeight = staticLayout.height + textView.lineHeight
        val vary = textHeight - viewHeight
        textView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    var dy = lastY - event.rawY
                    lastY = event.rawY
                    // 刚进入就往上滑
                    if (total <= 0f && dy < 0) {
                        return@setOnTouchListener true
                    }

                    // 滑到底还往下滑
                    if (total >= vary && dy > 0) {
                        return@setOnTouchListener true
                    }

                    if (dy > vary - total) {
                        dy = vary - total
                    }

                    if (dy == 0f) {
                        return@setOnTouchListener true
                    }

                    total += dy
                    textView.scrollBy(0, dy.toInt())
                }
            }
            true
        }
    }
}