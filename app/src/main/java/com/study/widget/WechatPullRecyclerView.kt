package com.study.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.utils.isScrollToBottom
import com.utils.isScrollToTop

/**
 *
 *
 * @author jthou
 * @since ScrollView
 * @date 19-03-2023
 */
class WechatPullRecyclerView : RecyclerView , IWechatPullChild {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun scrollToTop(): Boolean {
        return !canScrollVertically(-1)
    }

    override fun scrollToBottom(): Boolean {
        return !canScrollVertically(1)
    }

}