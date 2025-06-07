package com.work

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.jthou.pro.crazy.ViewHelper
import com.utils.log
import kotlin.math.abs
import kotlin.math.roundToInt

class VideoLiftFrameLayout : FrameLayout {

    private val videoView by lazy {
        ViewHelper.findChild(this, Button::class.java)
    }

    private val recyclerView by lazy {
        ViewHelper.findChild(this, RecyclerView::class.java)
    }

    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var lastInterceptX = 0f
    private var lastInterceptY = 0f

    internal var maxHeight = 0f
    internal var videoHeight = 0f
    internal var defaultHeight = 0f
    internal var aspectRatio = 1f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (videoView == null) return super.onInterceptTouchEvent(ev)
        if (recyclerView == null) return super.onInterceptTouchEvent(ev)
        // case1 向下滑并且视频高度小于 videoHeight
        // case2 向上滑并且视频高度大于 defaultHeight
        "onInterceptTouchEvent action : ${MotionEvent.actionToString(ev.action)}".log()
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.rawX - lastInterceptX
                val dy = ev.rawY - lastInterceptY
                if (abs(dy) > abs(dx)) {
                    // canScrollUp 为 true，表示 RecyclerView 可以向上滚动，还没到达顶部。
                    // canScrollUp 为 false，表示 RecyclerView 已经到达顶部，无法再向上滚动。
                    val canScrollUp1 = recyclerView?.canScrollVertically(1) == false
                    val canScrollUp2 = recyclerView?.canScrollVertically(-1) == false
                    val canScrollDown = recyclerView?.canScrollVertically(1) == true
                    "onInterceptTouchEvent canScrollUp1 : $canScrollUp1".log()
                    "onInterceptTouchEvent canScrollUp2 : $canScrollUp2".log()
                    "onInterceptTouchEvent canScrollDown : $canScrollDown".log()
                    val case1 = dy > 0 && videoView!!.measuredHeight < videoHeight.roundToInt() && canScrollUp2
                    val case2 = dy < 0 && videoView!!.measuredHeight > defaultHeight.roundToInt()
                    "onInterceptTouchEvent dy : $dy".log()
                    "onInterceptTouchEvent case1 : $case1".log()
                    "onInterceptTouchEvent case2 : $case2".log()
                    if (case1 || case2) {

                        "defaultHeight : $defaultHeight".log()
                        "videoView!!.measuredHeight : ${videoView!!.measuredHeight}".log()

                        lastTouchX = ev.rawX
                        lastTouchY = ev.rawY
                        return true
                    }
                }
            }
        }

        lastInterceptX = ev.rawX
        lastInterceptY = ev.rawY
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        "onTouchEvent action : ${MotionEvent.actionToString(ev.action)}".log()
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                val dy = ev.rawY - lastTouchY
                val case1 = dy > 0 && videoView!!.measuredHeight < videoHeight
                val case2 = dy < 0 && videoView!!.measuredHeight > defaultHeight
                if (case1 || case2) {
                    // 更新 VideoView 高度，更新 RecyclerView 的 topMargin
                    val minHeight = minOf(maxHeight, videoHeight)
                    val ratio = maxOf(videoHeight * 1.0f / minHeight, 1.0f)
                    "dy : $dy".log()
                    "ratio : $ratio".log()
                    recyclerView?.updateLayoutParams<MarginLayoutParams> {
                        topMargin = (topMargin + dy).roundToInt()
                        if (topMargin > minHeight.roundToInt()) {
                            topMargin = minHeight.roundToInt()
                        } else if (topMargin < defaultHeight.roundToInt()) {
                            topMargin = defaultHeight.roundToInt()
                        }
                    }
                    videoView!!.updateLayoutParams {
                        height = (height + dy * ratio).roundToInt()
                        if (height > videoHeight.roundToInt()) {
                            height = videoHeight.roundToInt()
                        } else if (height < defaultHeight.roundToInt()) {
                            height = defaultHeight.roundToInt()
                        }
                        width = (height * aspectRatio).roundToInt()
                    }
                } else {
                    val minHeight = minOf(maxHeight, videoHeight)
                    "minHeight : $minHeight".log()
                    "defaultHeight : $defaultHeight".log()
                    "videoView?.height : ${videoView?.height}".log()
                    "videoView?.measuredHeight : ${videoView?.measuredHeight}".log()
                    "topMargin : ${(recyclerView?.layoutParams as? MarginLayoutParams)?.topMargin}".log()
                }
            }
        }
        lastTouchX = ev.rawX
        lastTouchY = ev.rawY
        return true
    }

}