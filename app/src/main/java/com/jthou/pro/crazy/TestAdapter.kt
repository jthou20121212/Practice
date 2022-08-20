package com.jthou.pro.crazy

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import splitties.systemservices.layoutInflater

class TestAdapter(private val pageTitle: String, private val data: MutableList<String>) :
    RecyclerView.Adapter<TestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(
            parent.context.layoutInflater.inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val textView = holder.itemView as? TextView ?: return
        (pageTitle + data[position]).also { textView.text = it }
    }

}