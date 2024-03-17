package com.study

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.jthou.pro.crazy.databinding.ActivityWechatPullRecyclerViewBinding
import com.study.viewbinding.viewBinding
import com.utils.dp

private class SimpleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    init {
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300.dp)
        view.setOnClickListener {
            Toast.makeText(view.context, (view as TextView).text, Toast.LENGTH_SHORT).show()
        }
        val textView = view as? TextView
        textView?.setTextColor(Color.RED)
        textView?.gravity = Gravity.CENTER
        textView?.textSize = 20.dp.toFloat()
    }
}

private class SimpleAdapter(val data : List<String>) : RecyclerView.Adapter<SimpleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return SimpleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val textView : TextView = holder.itemView as TextView
        textView.text = data[position]
    }

}

class WechatPullRecyclerViewActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityWechatPullRecyclerViewBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val data1 = mutableListOf<String>()
        val data2 = mutableListOf<String>()
        for (i in 1..10) {
            data1.add("背景 item ：$i")
            data2.add("前景 item ：$i")
        }
        binding.recyclerView1.adapter = SimpleAdapter(data1)
        binding.recyclerView2.adapter = SimpleAdapter(data2)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}