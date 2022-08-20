package com.study.snaphelper

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jthou.pro.crazy.databinding.ActivityLinearSnapHelperBinding

class LinearSnapHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLinearSnapHelperBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.recyclerView.run {
            adapter = LinearSnapHelperAdapter()
            layoutManager =
                LinearLayoutManager(this@LinearSnapHelperActivity, RecyclerView.HORIZONTAL, true)
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

}