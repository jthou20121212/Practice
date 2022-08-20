package com.study.snaphelper

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
import androidx.recyclerview.widget.SnapHelper
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.utils.dp

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 28-02-2022
 */
class ZhihuSnapHelper : SnapHelper() {

    private var mVerticalHelper : OrientationHelper? = null

    companion object {
        val MAX_HEIGHT = lazy { 300.dp }.value
    }

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        val intArray = IntArray(2)
        val position = layoutManager.getPosition(targetView)
        if (position > 0 && layoutManager.canScrollVertically()) {
            val decoratedStart = getVerticalHelper(layoutManager)!!.getDecoratedStart(targetView)
            LogUtils.i("jthou", "decoratedStart : $decoratedStart")
            val visibleAreaHeight = ScreenUtils.getScreenHeight() - decoratedStart
            if (visibleAreaHeight > MAX_HEIGHT) {
                intArray[1] = MAX_HEIGHT - visibleAreaHeight
                LogUtils.i("jthou", "intArray[1] : ${intArray[1]}")
            }
        }
        return intArray
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        if (layoutManager !is LinearLayoutManager) return null
        val position = layoutManager.findLastVisibleItemPosition()
        return layoutManager.findViewByPosition(position)
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val currentPosition = layoutManager.getPosition(currentView)
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        val view = layoutManager.findViewByPosition(currentPosition) ?: return RecyclerView.NO_POSITION
        val decoratedStart = getVerticalHelper(layoutManager)!!.getDecoratedStart(view)
        val calculateScrollDistance = calculateScrollDistance(velocityX, velocityY)
        return if (calculateScrollDistance[0] > view.measuredHeight - (ScreenUtils.getScreenHeight() - decoratedStart)) {
            currentPosition + 1
        } else {
            currentPosition
        }
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper
    }

}