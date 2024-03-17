package com.study.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
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
class WechatPullNestedScrollView : NestedScrollView , IWechatPullChild {

    companion object {
        // 拦截事件的最大角度
        private const val MAX_ANGLE = 45
    }

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
                false
            }
            MotionEvent.ACTION_MOVE -> {
                val angle = getAngle(dx, dy)
                // 多指情况直接拦截事件
                val targetView = findTargetView(getChildAt(0))

                "angle : $angle".log()
                "targetView : $targetView".log()
                targetView is View && isContainView(targetView, ev) && angle > MAX_ANGLE
            }
            MotionEvent.ACTION_UP -> {
                false
            }
            else -> {
                false
            }
        }
        lastInterceptX = ev.x
        lastInterceptY = ev.y

        "WechatPullNestedScrollView intercepted : $intercepted".log()

        return intercepted
    }

    override fun scrollToTop(): Boolean {
        return scrollY == 0
    }

    override fun scrollToBottom(): Boolean {
        return getChildAt(0).measuredHeight <= scrollY + height
    }

    // 找到实现了 IWechatPullChild 接口的控件
    // 并且 visibility 是 View.VISIBLE 状态，因为可能会在 fragment 中使用并且 show/hide
    private fun findTargetView(view: View): IWechatPullChild? {
        when (view) {
            // 如果传入的就不可见这种情况不处理
            is IWechatPullChild -> {
                return view
            }
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    val childAt = view.getChildAt(i)
                    // 考虑在 ViewPager 中的情况
                    if (isShown(childAt)) {
                        val findChild = findTargetView(childAt)
                        if (findChild != null) {
                            return findChild
                        }
                    }
                }
            }
            else -> {
                return null
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