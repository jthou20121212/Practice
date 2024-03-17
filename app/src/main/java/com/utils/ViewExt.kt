package com.utils

import android.widget.ScrollView

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 19-03-2023
 */
fun ScrollView.isScrollToTop(): Boolean {
    return scrollY == 0
}

fun ScrollView.isScrollToBottom(): Boolean {
    return getChildAt(0).measuredHeight <= scrollY + height
}