package com.study.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.utils.log
import kotlin.math.roundToInt

/**
 * 可以滚动的 TextView
 *
 * @author jthou
 * @since 1.0.0
 * @date 29-04-2023
 */
class ScrollableTextView : AppCompatTextView {

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.rawX
                lastY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.rawX - lastX
                val dy = ev.rawY - lastY
                scrollBy(0, -dy.roundToInt())
                lastX = ev.rawX
                lastY = ev.rawY
            }
        }
        return true
    }

}