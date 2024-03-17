package com.study.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.utils.log

/**
 * 仿微信下拉小程序效果
 *
 * @author jthou
 * @date 19-03-2023
 * @since 1.0.0
 */
class WechatPullFrameLayout : FrameLayout {

    companion object {

        // 可以往下拉的最高大小
        private val FOREGROUND_VIEW_SCROLL_HEIGHT by lazy {
            (ScreenUtils.getScreenHeight() / 4f * 3).toInt()
        }

        // 中间状态
        private val MIDDLE_HEIGHT = FOREGROUND_VIEW_SCROLL_HEIGHT / 2

        private const val RATIO_PULL_DOWN = 0.33f

        private const val RATIO_PULL_UP = 0.9f
    }

    private val touchSlop by lazy {
        ViewConfiguration.get(context).scaledTouchSlop
    }

    // 正在执行动画
    private var inAnimation = false
    private var animator : ValueAnimator? = null

    private lateinit var background: View
    private lateinit var foreground: View
    private lateinit var foregroundMarginView: View
    private lateinit var backgroundMarginView: View

    private var lastX = 0f
    private var lastY = 0f
    private var lastInterceptX = 0f
    private var lastInterceptY = 0f

    private var middleLocationHeight : Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setMiddleLocationHeight(height: Int) {
        middleLocationHeight = height
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        background = getChildAt(0)
        foreground = getChildAt(1)
        backgroundMarginView = findMarginView(background)
        foregroundMarginView = findMarginView(foreground)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (inAnimation) return true
        val intercepted = when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                false
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = ev.y - lastInterceptY
                // case1 从上往下滑
                // case2 如果子控件可以滑动并且子控件已经滑动到顶部
                // case3 还可以往下拉
                val foregroundDownPull = dy > 0
                        && (findTargetView(foreground) == null || findTargetView(foreground)!!.scrollToTop())
                        && (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin < FOREGROUND_VIEW_SCROLL_HEIGHT

                // 从下往上滑
                // 手指落在背景上
                val backgroundCase = dy < 0
                        && ev.y < (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
                        && (findTargetView(background) == null || findTargetView(background)!!.scrollToBottom())
                        && (backgroundMarginView.layoutParams as MarginLayoutParams).bottomMargin < FOREGROUND_VIEW_SCROLL_HEIGHT

                // 从下往上滑
                // 手指落在前景上
                val foregroundUpPull = dy < 0
                        && ev.y > (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
                        && (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin == FOREGROUND_VIEW_SCROLL_HEIGHT

                "findTargetView(foreground)!!.scrollToTop() : ${findTargetView(foreground)!!.scrollToTop()}".log()

                foregroundDownPull || backgroundCase || foregroundUpPull
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
        return intercepted
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (inAnimation) return true
        if (ev.action == MotionEvent.ACTION_MOVE) {
            val dy = ev.y - lastY
            if (dy > 0 || ev.y > (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin) {
                val topMargin = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
                val distance = (topMargin + dy).coerceAtMost(FOREGROUND_VIEW_SCROLL_HEIGHT.toFloat())
                (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin = distance.toInt()
                foregroundMarginView.requestLayout()
            } else {
                val bottomMargin = (backgroundMarginView.layoutParams as MarginLayoutParams).bottomMargin
                val distance = (bottomMargin - dy).coerceAtMost(FOREGROUND_VIEW_SCROLL_HEIGHT.toFloat())
                (backgroundMarginView.layoutParams as MarginLayoutParams).bottomMargin = distance.toInt()
                // 可以实现效果但是为什么呢 ？？？
                backgroundMarginView.scrollBy(0, -dy.toInt())
                backgroundMarginView.requestLayout()
            }
        } else if ((ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL)) {
            // 前景展开背景显示状态
            val allExposed = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin == FOREGROUND_VIEW_SCROLL_HEIGHT
            if (allExposed) {
                // 滑动背景到一定阈值收起前景遮挡背景，收起状态
                if ((backgroundMarginView.layoutParams as MarginLayoutParams).bottomMargin > FOREGROUND_VIEW_SCROLL_HEIGHT * RATIO_PULL_DOWN) {
                    collapse()
                } else {
                    // 展示背景
                    inAnimation = true
                    val start = (backgroundMarginView.layoutParams as MarginLayoutParams).bottomMargin
                    val end = 0
                    animator = ObjectAnimator.ofInt(start, end)
                    animator?.addUpdateListener {
                        val intValue = it.animatedValue as Int
                        (backgroundMarginView.layoutParams as MarginLayoutParams).bottomMargin = intValue
                        backgroundMarginView.requestLayout()
                    }
                    animator?.doOnEnd {
                        inAnimation = false
                    }
                    animator?.start()
                }
            } else {
                if (lastInterceptY > FOREGROUND_VIEW_SCROLL_HEIGHT && (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin < FOREGROUND_VIEW_SCROLL_HEIGHT * RATIO_PULL_UP) {
                    collapse()
                } else {
                    val canReactEvent = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin > FOREGROUND_VIEW_SCROLL_HEIGHT * RATIO_PULL_DOWN
                    if (canReactEvent) {
                        expand((foregroundMarginView.layoutParams as MarginLayoutParams).topMargin)
                    } else {
                        collapse()
                    }
                }
            }
        }
        lastX = ev.x
        lastY = ev.y
        return true
    }

    // 展开
    // 展示背景
    fun expand(start: Int = 0) {
        inAnimation = true

        // 白色手柄处可点击，在最低高度点击回到中间高度，在中间高度点击到最高高度，在最高高度点击回中间高度。
        val end = /*if ((backgroundMarginView.layoutParams as MarginLayoutParams).topMargin == 0)
            MIDDLE_HEIGHT else */ FOREGROUND_VIEW_SCROLL_HEIGHT

        animator = ObjectAnimator.ofInt(start, end)
        animator?.interpolator = AccelerateInterpolator()
        animator?.addUpdateListener {
            val intValue = it.animatedValue as Int
            (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin = intValue
            foregroundMarginView.requestLayout()
        }
        animator?.doOnEnd {
            inAnimation = false
        }
        animator?.start()
    }

    // 收起
    // 收起前景遮挡背景
    fun collapse() {
        inAnimation = true
        val start = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
        val end = 0
        animator = ObjectAnimator.ofInt(start, end)
        animator?.addUpdateListener {
            val intValue = it.animatedValue as Int
            (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin = intValue
            foregroundMarginView.requestLayout()
        }
        animator?.doOnEnd {
            (backgroundMarginView.layoutParams as MarginLayoutParams).bottomMargin = 0
            if (backgroundMarginView is RecyclerView) {
                (((backgroundMarginView as RecyclerView).layoutManager) as? LinearLayoutManager)?.scrollToPositionWithOffset(0, 0)
            } else {
                backgroundMarginView.scrollTo(0, 0)
            }
            backgroundMarginView.requestLayout()
            inAnimation = false
        }
        animator?.start()
    }

    // 找到实现了 IWechatPullChild 接口的控件
    private fun findTargetView(view: View): IWechatPullChild? {
        when (view) {
            is IWechatPullChild -> {
                return view
            }
            is ViewGroup -> {
                for (i in 0 until view.childCount) {
                    val childAt = view.getChildAt(i)
                    val findChild = findTargetView(childAt)
                    if (findChild != null) {
                        return findChild
                    }
                }
            }
            else -> {
                return null
            }
        }
        return null
    }

    // 如果是 background 找到设置 bottomMargin 的控件，有实现 IWechatPullChild 则返回此控件，否则返回 background
    // 如果是 foreground 找到设置 topMargin 的控件，有实现 IWechatPullChild 则返回此控件，否则返回 foreground
    private fun findMarginView(view: View) : View {
        return findTargetView(view) as? View ?: view
    }

}
