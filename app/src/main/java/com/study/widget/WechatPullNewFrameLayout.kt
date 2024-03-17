package com.study.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import com.blankj.utilcode.util.ObjectUtils
import com.utils.log
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.hypot

/**
 * 仿微信下拉小程序效果
 *
 * @author jthou
 * @date 19-03-2023
 * @since 1.0.0
 */
class WechatPullNewFrameLayout : FrameLayout {

    companion object {

        private const val OFFSET_ANIMATION = 20

        // 滑动到下一级的比率
        private const val RATIO_SWITCH = 0.20f

        // 拦截事件的最大角度
        private const val MAX_ANGLE = 45

        // 最大拉动速度，超出此速度忽视高度直接回到手势方向上一位置
        private const val MAX_PULL_VELOCITY = 600

    }

    private val touchSlop by lazy {
        ViewConfiguration.get(context).scaledTouchSlop
    }

    // 正在执行动画
    var inAnimation = false

    private lateinit var background: View
    private lateinit var foreground: View
    private lateinit var foregroundMarginView: View
    private lateinit var backgroundMarginView: View

    private var lastX = 0f
    private var lastY = 0f
    private var downX = 0f
    private var downY = 0f
    private var lastInterceptX = 0f
    private var lastInterceptY = 0f

    private var topHeight: Int = 0
    private var middleHeight: Int = 0
    private var bottomHeight: Int = 0
    private var callback: Function1<Location, Unit>? = null
    private var location: Location? = null

    // 全局变量避免在事件处理中重复创建对象
    private val rect by lazy { Rect() }

    private val velocityTracker by lazy {
        VelocityTracker.obtain()
    }

    enum class Location {
        TOP, MIDDLE, BOTTOM
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setLocationCallback(callback : Function1<Location, Unit>) {
        this.callback = callback
    }

    fun setTopLocationHeight(height: Int) {
        topHeight = height
    }

    fun setMiddleLocationHeight(height: Int) {
        middleHeight = height
    }

    fun setBottomLocationHeight(height: Int) {
        bottomHeight = height
    }

    // 如果实现了 com.huxiu.widget.club.IWechatPullChild 接口的布局是通过类似 replace fragment 的方式添加的
    // 请保证在 replace 之后再调用此方法才可以取到指定的控件
    // 设置默认展示的位置
    fun setDefaultLocation(location: Location = Location.MIDDLE) {
        post {
            // com.huxiu.widget.club.WechatPullNewFrameLayout.onFinishInflate 取到的可能不准确在这里再取一次
            if (ObjectUtils.isNotEmpty(background) && ObjectUtils.isNotEmpty(foreground)) {
                // 严格来说这里也需要考虑在 Fragment 中使用的情况
                // 现在没有这种场景暂不考虑
                backgroundMarginView = findMarginView(background)
                // 给最外层控件设置间距
                foregroundMarginView = foreground

                this.location = location
                val margin = when (location) {
                    Location.TOP -> topHeight
                    Location.MIDDLE -> middleHeight
                    Location.BOTTOM -> bottomHeight
                }

                (foregroundMarginView.layoutParams as ViewGroup.MarginLayoutParams).topMargin = margin
                // 背景设置间距避免遮挡不能全部可见
                (backgroundMarginView.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = background.measuredHeight - bottomHeight
                foregroundMarginView.requestLayout()
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        background = getChildAt(0)
        foreground = getChildAt(1)

        // 如果在布局里写死的话可以这样取
        backgroundMarginView = findMarginView(background)
        foregroundMarginView = findMarginView(foreground)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (inAnimation) return true
        val dx = ev.x - lastInterceptX
        val dy = ev.y - lastInterceptY
        val targetView = findTargetView(foreground)
        val topMargin = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
        val intercepted = when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                false
            }
            MotionEvent.ACTION_MOVE -> {
                val angle = getAngle(dx, dy)

                // case1 从上往下滑
                // case2 如果子控件可以滑动并且子控件已经滑动到顶部
                // case3 手指在前景上
                // case4 还可以往下拉
                // case5 满足角度要求（方向）
                val foregroundDownPull = dy > 0
                        && (targetView == null || targetView.scrollToTop())
                        && ev.y > topMargin
                        && topMargin < bottomHeight
                        && angle > MAX_ANGLE

                // 从下往上滑
                // 手指落在前景上
                // 还可以往上滑
                // 满足角度要求（方向）
                val foregroundUpPull = dy < 0 && ev.y > topMargin && topMargin > topHeight && angle > MAX_ANGLE

                // 增加一个手势，在中间位置时，后面深色区域可上下挪动，下拉为刷新，上拉时白色区域自动下滑到最低高度
                //
                val backgroundUpPull = dy < 0 && ev.y < topMargin && topMargin == middleHeight && abs(ev.y - downY) > touchSlop && angle > MAX_ANGLE

                // 只是收起不拦截事件
                // 这里判断距离避免与子控件长按同时响应
                if (backgroundUpPull) {
                    // 这里需要延迟下否则子控件不能响应事件
                    postDelayed({executeAnimator(topMargin, bottomHeight)}, 100)
                }

                // 多指情况直接拦截事件
                ev.pointerCount > 1 || foregroundDownPull || foregroundUpPull
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
        velocityTracker.addMovement(ev)
        val dy = ev.y - lastY
        if (ev.action == MotionEvent.ACTION_MOVE && (dy > 0 || ev.y > (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin) ) {
            val topMargin = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
            val distance = if (topMargin + dy > bottomHeight) {
                bottomHeight
            }  else if (topMargin + dy < topHeight) {
                topHeight
            } else {
                topMargin + dy
            }
            (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin = distance.toInt()
            foregroundMarginView.requestLayout()
            doCallback(distance.toInt())
        } else if ((ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL)) {
            val scrollLocation = calculateShouldScrollLocation(ev.y - lastInterceptY)
            scrollLocation?.let { executeAnimator(it.first, it.second) }
        }
        lastX = ev.x
        lastY = ev.y
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        foregroundMarginView.clearAnimation()
        velocityTracker.recycle()
    }

    // 白色手柄处可点击，在最低高度点击回到中间高度，在中间高度点击到最高高度，在最高高度点击回中间高度
    fun toggle() {
        val topMargin = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
        val second = if (topMargin == middleHeight) topHeight else middleHeight
        executeAnimator(topMargin, second)
    }

    // 计算应该滑动到的位置
    // 返回值是用来做动画的起止点
    // 等于 topHeight middleHeight bottomHeight 三个值则什么都不做
    // 最后距离谁近则滑到谁的位置，距离顶近则到顶部，距离中近则到中部，距离底近则到底部
    // 规则大概率要调整
    // direction 的作用是判断方向，更偏向于滑动的方向
    private fun calculateShouldScrollLocation(direction: Float) : Pair<Int, Int>? {
        velocityTracker.computeCurrentVelocity(1000)
        val wantToDown = direction > 0
        val veryFast = abs(velocityTracker.yVelocity) > MAX_PULL_VELOCITY
        val topMargin = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
        // 从上往下滑
        if (topMargin in topHeight until middleHeight) {
            // 在中间和底部位置

            val second = if (wantToDown) {
                if (veryFast || topMargin > (middleHeight - topHeight) * RATIO_SWITCH) middleHeight else topHeight
            } else {
                if (veryFast || topMargin < (middleHeight - topHeight) * (1 - RATIO_SWITCH)) topHeight else middleHeight
            }

            return topMargin to second
        } else if (topMargin in middleHeight until bottomHeight) {
            // 在中间和底部位置

            val second = if (wantToDown) {
                if (veryFast || topMargin > middleHeight + (bottomHeight - middleHeight) * RATIO_SWITCH) bottomHeight else middleHeight
            } else {
                if (veryFast || topMargin < middleHeight + (bottomHeight - middleHeight) * (1 - RATIO_SWITCH)) middleHeight else bottomHeight
            }

            return topMargin to second
        }
        velocityTracker.clear()
        return null
    }

    private fun executeAnimator(start: Int, end: Int) {
        // inAnimation = true
        // post runnable 方式
        // post(AnimatorRunnable(start, end))

        if (ObjectUtils.isEmpty(foregroundMarginView)) return
        val layoutParams = foregroundMarginView.layoutParams as? MarginLayoutParams ?: return
        inAnimation = true
        // animator 方式
        val animator = ObjectAnimator.ofInt(start, end).setDuration(200)
        animator.addUpdateListener {
            val intValue = it.animatedValue as Int
            layoutParams.topMargin = intValue
            foregroundMarginView.requestLayout()
        }
        animator.doOnEnd {
            inAnimation = false

            doCallback(end)
        }
        animator.start()
    }

    private fun doCallback(margin: Int) {
        val location = when(margin) {
            topHeight-> Location.TOP
            middleHeight-> Location.MIDDLE
            bottomHeight -> Location.BOTTOM
            else -> return
        }

        if (location == this.location) return
        this.location = location

        callback?.invoke(location)
    }

    inner class AnimatorRunnable(private val start: Int, private val end: Int) : Runnable {
        override fun run() {
            if (ObjectUtils.isEmpty(foregroundMarginView)) return
            var value = if (end > start) OFFSET_ANIMATION else -OFFSET_ANIMATION
            val topMargin = (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin
            if (topMargin != end) {
                val margin = abs(abs(end) - topMargin)
                if (margin < OFFSET_ANIMATION) {
                    value = if (value > 0) margin else -margin
                }
                (foregroundMarginView.layoutParams as MarginLayoutParams).topMargin += value
                foregroundMarginView.requestLayout()
                post(this)
            } else {
                inAnimation = false
            }
        }
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
                    // 判断可见性
                    val findChild = findTargetView(childAt)
                    if (findChild != null && findChild.isShown(rect)) {
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

    private fun getAngle(x: Float, y: Float): Double {
        return Math.toDegrees(acos(abs(x) / hypot(x.toDouble(), y.toDouble())))
    }

}