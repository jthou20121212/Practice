package com.study.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.utils.isScrollToBottom
import com.utils.isScrollToTop
import com.utils.log
import kotlin.math.roundToInt

/**
 *
 *
 * @author jthou
 * @since ScrollView
 * @date 19-03-2023
 */
class WechatPullScrollView : ScrollView , IWechatPullChild {

    // 全局变量避免在事件处理中重复创建对象
    private val rect by lazy { Rect() }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val targetView = findTargetView(getChildAt(0))
        targetView.log()
        if (targetView is View && isContainView(targetView, ev)) {
            "拦截事件".log()
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun scrollToTop(): Boolean {
        return isScrollToTop()
    }

    override fun scrollToBottom(): Boolean {
        return isScrollToBottom()
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

}