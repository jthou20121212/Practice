package com.study.widget

import android.graphics.Rect
import android.view.View
import com.jthou.pro.crazy.ViewHelper

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 19-03-2023
 */
interface IWechatPullChild {

    // 如果子控件可以滑动
    // return true 表示已经滑动到顶部
    // return false 表示还没有滑动到顶部
    fun scrollToTop() : Boolean

    // 如果子控件可以滑动
    // return true 表示已经滑动到底部
    // return false 表示还没有滑动到底部
    fun scrollToBottom() : Boolean

    fun isShown(rect: Rect): Boolean {
        val view = this as View
        return ViewHelper.isShown(view, rect)
    }

}