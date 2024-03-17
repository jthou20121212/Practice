package com.study.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import android.widget.OverScroller
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import com.blankj.utilcode.util.ObjectUtils
import com.utils.log
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.acos
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * 仿微信下拉小程序效果
 *
 * @author jthou
 * @date 10-06-2023
 * @since 1.0.0
 */
class WechatPullNewestFrameLayout : FrameLayout {

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

    private val scroller by lazy { OverScroller(context) }

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

                // not working
                // foregroundMarginView.offsetTopAndBottom(margin)
                foregroundMarginView.translationY = margin.toFloat()

                // 背景设置间距避免遮挡不能全部可见
                (backgroundMarginView.layoutParams as MarginLayoutParams).bottomMargin = background.measuredHeight - bottomHeight
                foregroundMarginView.requestLayout()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        "dispatchTouchEvent".log()
        if (inAnimation) return true
        velocityTracker.addMovement(ev)
        when(ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastInterceptY = ev.y
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                velocityTracker.computeCurrentVelocity(1000)
                val pair = calculateShouldScrollLocation(ev.y - lastInterceptY > 0, velocityTracker.yVelocity.absoluteValue)
                if (pair != null) {
                    val animator = ValueAnimator.ofFloat(foregroundMarginView.translationY, pair.second.toFloat())
                    animator.setDuration(100)
                    animator.addUpdateListener {
                        val floatValue = it.animatedValue as Float
                        foregroundMarginView.translationY = floatValue
                    }
                    animator.start()
                }
                velocityTracker.clear()
            }
        }

        lastX = ev.x
        lastY = ev.y
        return super.dispatchTouchEvent(ev)
    }

    override fun onStartNestedScroll(child: View?, target: View?, nestedScrollAxes: Int): Boolean {
        val result = ViewCompat.SCROLL_AXIS_VERTICAL == nestedScrollAxes
        "result : $result".log()
        return result
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray?) {
        // dy > 0 手指从下往上滑
        // dy < 0 手指从上往下滑
        val translationY = foregroundMarginView.translationY
        if (dy > 0) {
            "case1".log()
            if (translationY != topHeight.toFloat()) {
                "case11".log()
                // 如果消耗不了
                // 比如还差 3 就滑动到最顶部了 dy 是 5 的情况
                val used = min(translationY - topHeight, dy.toFloat())
                foregroundMarginView.translationY -= used
                consumed?.set(1, used.roundToInt())
            }
        } else if (dy < 0) {
            "case2".log()
            if (translationY != bottomHeight.toFloat() && !target.canScrollVertically(-1)) {
                "case22".log()
                val used = max(translationY - bottomHeight, dy.toFloat())
                foregroundMarginView.translationY -= used
                consumed?.set(1, used.roundToInt())
            }
        } else {
            "case3".log()
            consumed?.set(1, 0)
        }
    }

    override fun onNestedScroll(target: View?, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        // velocityY > 0 手指从下往上滑
        // velocityY < 0 手指从上往下滑
        "onNestedPreFling target : $target".log()
        "onNestedPreFling velocityX : $velocityX".log()
        "onNestedPreFling velocityY : $velocityY".log()
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {

        "onNestedFling".log()

        val pair = calculateShouldScrollLocation(velocityY < 0, velocityY)
        if (pair != null) {
            val animator = ValueAnimator.ofFloat(foregroundMarginView.translationY, pair.second.toFloat())
            animator.setDuration(100)
            animator.addUpdateListener {
                val floatValue = it.animatedValue as Float
                foregroundMarginView.translationY = floatValue
            }
            animator.start()
        }

        return true
    }

    override fun stopNestedScroll() {

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        background = getChildAt(0)
        foreground = getChildAt(1)

        // 如果在布局里写死的话可以这样取
        backgroundMarginView = findMarginView(background)
        foregroundMarginView = findMarginView(foreground)
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
    private fun calculateShouldScrollLocation(wantToDown : Boolean, yVelocity: Float) : Pair<Float, Int>? {
        "calculateShouldScrollLocation".log()
        val veryFast = abs(yVelocity) > MAX_PULL_VELOCITY
        val translationY = foregroundMarginView.translationY
        // 从上往下滑
        if (translationY > topHeight && translationY <= middleHeight) {
            // 在中间和底部位置

            val second = if (wantToDown) {
                if (veryFast || translationY > (middleHeight - topHeight) * RATIO_SWITCH) middleHeight else topHeight
            } else {
                if (veryFast || translationY < (middleHeight - topHeight) * (1 - RATIO_SWITCH)) topHeight else middleHeight
            }

            return translationY to second
        } else if (translationY >= middleHeight && translationY < bottomHeight) {
            // 在中间和底部位置

            val second = if (wantToDown) {
                if (veryFast || translationY > middleHeight + (bottomHeight - middleHeight) * RATIO_SWITCH) bottomHeight else middleHeight
            } else {
                if (veryFast || translationY < middleHeight + (bottomHeight - middleHeight) * (1 - RATIO_SWITCH)) middleHeight else bottomHeight
            }

            return translationY to second
        }
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