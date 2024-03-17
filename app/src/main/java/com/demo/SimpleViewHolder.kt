package com.demo

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.utils.dp

class SimpleViewHolder(val view: View) : BaseViewHolder(view) {
    init {
        view.setOnClickListener {
            Toast.makeText(view.context, (view as TextView).text, Toast.LENGTH_SHORT).show()
        }
        val textView = view as? TextView
        textView?.setTextColor(Color.RED)
        textView?.gravity = Gravity.CENTER
        textView?.textSize = 20.dp.toFloat()
    }
}
