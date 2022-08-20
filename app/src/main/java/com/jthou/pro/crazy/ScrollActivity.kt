package com.jthou.pro.crazy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jthou.pro.crazy.databinding.ActivityScrollBinding

class ScrollActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(context: Context) {
            val intent = Intent(context, ScrollActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityScrollBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = ScrollAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    class ScrollViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class ScrollAdapter : RecyclerView.Adapter<ScrollViewHolder>() {

        val array = Array<String>(100){it ->
            "测试数据 $it"
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrollViewHolder {
            return ScrollViewHolder(LayoutInflater.from(this@ScrollActivity).inflate(R.layout.item_right, parent, false))
        }

        override fun getItemCount(): Int {
           return 1
        }

        override fun onBindViewHolder(holder: ScrollViewHolder, position: Int) {

        }

    }

}