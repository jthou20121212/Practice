package com.study.snaphelper

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jthou.pro.crazy.databinding.ActivityZhihuBinding

class ZhihuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityZhihuBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.recyclerView.run {
            adapter = ZhihuSnapHelperAdapter()
            layoutManager =
                LinearLayoutManager(this@ZhihuActivity, RecyclerView.VERTICAL, false)
            ZhihuSnapHelper().attachToRecyclerView(this)
        }
    }

}