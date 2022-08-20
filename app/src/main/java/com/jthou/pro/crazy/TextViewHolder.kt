package com.jthou.pro.crazy

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.jthou.pro.crazy.autosize.TextViewHelperV2

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 24-11-2020
 */
class TextViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        private const val MAX_FONT_SIZE_USERNAME = 30f
        private const val MIN_FONT_SIZE_USERNAME = 20f
        private val width = lazy { ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(160f) }
    }

    fun bind(item: String) {
        val subTextView = itemView.findViewById<TextView>(R.id.tv_sub)
        val contentTextView = itemView.findViewById<TextView>(R.id.tv_content)
        val mTextViewHelper: TextViewHelperV2 = TextViewHelperV2.Builder()
            .setMaxFontSize(MAX_FONT_SIZE_USERNAME)
            .setMinFontSize(MIN_FONT_SIZE_USERNAME)
            .setAvailableRange(width.value)
            .setTarget(subTextView)
            .setTail("...")
            .build()
        mTextViewHelper.showText(item)
        contentTextView.text = item

        Log.i("jthou", "mTextViewHelper : $mTextViewHelper")
    }

}