package com.study.attrs

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import com.study.attrs.Global.NO_VALUE

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 12-08-2023
 */
class NewDnTextView : BaseTextView, IDarkMode {

    @ColorInt
    private var textColorLight = NO_VALUE

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr)

    // 记录 textColor
    override fun darkModeChange(isDarkMode: Boolean) {
    }

}