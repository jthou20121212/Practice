package com.study.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.utils.isScrollToBottom
import com.utils.isScrollToTop
import com.utils.log
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.hypot
import kotlin.math.roundToInt

/**
 *
 *
 * @author jthou
 * @since ScrollView
 * @date 19-03-2023
 */
class WechatPullLinearLayout : LinearLayout , IWechatPullChild {

    companion object {

        // 拦截事件的最大角度
        private const val MAX_ANGLE = 45

    }

    private var lastX = 0f
    private var lastY = 0f
    private var downX = 0f
    private var downY = 0f
    private var lastInterceptX = 0f
    private var lastInterceptY = 0f

    // 全局变量避免在事件处理中重复创建对象
    private val rect by lazy { Rect() }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val dx = ev.x - lastInterceptX
        val dy = ev.y - lastInterceptY
        val intercepted = when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                false
            }
            MotionEvent.ACTION_MOVE -> {
                val targetView = findTargetView(this)
                targetView is View && isContainView(targetView, ev) && getAngle(dx, dy) > MAX_ANGLE
            }
            MotionEvent.ACTION_UP -> {
                false
            }
            else -> {
                false
            }
        }
        lastX = ev.x
        lastY = ev.y
        lastInterceptX = ev.x
        lastInterceptY = ev.y

        "WechatPullNewFrameLayout intercepted : $intercepted".log()

        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val dy = ev.y - lastY
        val targetView = findTargetView(this)
        val top = targetView
//        if (ev.action == MotionEvent.ACTION_MOVE && (dy > 0 || ev.y > (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin) ) {
//            val topMargin = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
//            val distance = if (topMargin + dy > bottomHeight) {
//                bottomHeight
//            }  else if (topMargin + dy < topHeight) {
//                topHeight
//            } else {
//                topMargin + dy
//            }
//            (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin = distance.toInt()
//            foregroundMarginView.requestLayout()
//            doCallback(distance.toInt())
//        }
        lastX = ev.x
        lastY = ev.y
        return true
    }

    override fun scrollToTop(): Boolean {
        return findTargetView(this)?.scrollToTop() ?: true
    }

    override fun scrollToBottom(): Boolean {
        return findTargetView(this)?.scrollToBottom() ?: true
    }

    // 找到实现了 IWechatPullChild 接口的控件
    // 并且 visibility 是 View.VISIBLE 状态，因为可能会在 fragment 中使用并且 show/hide
    private fun findTargetView(viewGroup: ViewGroup): IWechatPullChild? {
        for (i in 0 until viewGroup.childCount) {
            val childAt = viewGroup.getChildAt(i)
            // 考虑在 ViewPager 中的情况
            if (isShown(childAt)) {
                if (childAt is IWechatPullChild) {
                    return childAt
                } else if (childAt is ViewGroup){
                    val findChild = findTargetView(childAt)
                    if (findChild != null) {
                        return findChild
                    }
                }
            }
        }
        return null
    }

    // 返回 view 是否可见
    private fun isShown(view : View) : Boolean {
        return view.hasWindowFocus()
                && view.getGlobalVisibleRect(rect)
                && view.visibility == VISIBLE
                && view.isShown
    }

    private fun isContainView(view: View, event: MotionEvent): Boolean {
        val x = event.rawX
        val y = event.rawY
        view.getGlobalVisibleRect(rect)
        return rect.contains(x.roundToInt(), y.roundToInt())
    }

    private fun getAngle(x: Float, y: Float): Double {
        return Math.toDegrees(acos(abs(x) / hypot(x.toDouble(), y.toDouble())))
    }

}