package com.study.snaphelper

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ColorUtils
import com.utils.dp
import splitties.views.backgroundColor

class LinearSnapHelperAdapter : RecyclerView.Adapter<LinearSnapHelperAdapter.LinearSnapHelperViewHolder>() {

    class LinearSnapHelperViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinearSnapHelperViewHolder {
        return LinearSnapHelperViewHolder(TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(100.dp, ViewGroup.LayoutParams.MATCH_PARENT)
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            textSize = 20f
            backgroundColor = ColorUtils.getRandomColor()
        })
    }

    override fun onBindViewHolder(holder: LinearSnapHelperViewHolder, position: Int) {
        (holder.itemView as TextView).text = position.toString()
    }

    override fun getItemCount() = 100

}