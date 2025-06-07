package com.demo

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.chad.library.adapter.base.BaseQuickAdapter
import com.utils.dp

class SimpleAdapter : BaseQuickAdapter<String, SimpleViewHolder>(android.R.layout.simple_list_item_1) {

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return super.onCreateDefViewHolder(parent, viewType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun convert(holder: SimpleViewHolder, item: String) {
        val textView: TextView = holder.itemView as TextView
        textView.textSize = 16f
        textView.text = item
        textView.updateLayoutParams {
            height = 50.dp
        }
        textView.gravity = Gravity.CENTER
        textView.setBackgroundColor(Color.TRANSPARENT)
    }

}