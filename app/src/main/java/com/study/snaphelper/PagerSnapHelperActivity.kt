package com.study.snaphelper

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.jthou.pro.crazy.databinding.ActivityPagerSnapHelperBinding

class PagerSnapHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPagerSnapHelperBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.recyclerView.run {
            adapter = PagerSnapHelperAdapter()
            layoutManager =
                LinearLayoutManager(context)
            PagerSnapHelper().attachToRecyclerView(this)
        }
    }

}