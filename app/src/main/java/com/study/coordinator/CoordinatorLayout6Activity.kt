package com.study.coordinator

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.jthou.pro.crazy.databinding.ActivityCoordinatorLayout6Binding
import com.study.viewbinding.viewBinding

class CoordinatorLayout6Activity : AppCompatActivity() {

    private val binding by viewBinding(ActivityCoordinatorLayout6Binding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = ViewPagerAdapter()
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "Tab ${position + 1}"
        }.attach()
    }

    private inner class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val recyclerView = RecyclerView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                layoutManager = LinearLayoutManager(parent.context)
            }
            return ViewHolder(recyclerView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // Set up RecyclerView adapter for each page
            val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                    // Inflate your item layout here
                    return object : RecyclerView.ViewHolder(View(parent.context)) {}
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                    // Bind your data here
                }

                override fun getItemCount(): Int {
                    return 50 // Example item count
                }
            }
            holder.recyclerView.adapter = adapter
        }

        override fun getItemCount(): Int {
            return 3 // Number of tabs
        }

        inner class ViewHolder(val recyclerView: RecyclerView) : RecyclerView.ViewHolder(recyclerView)
    }
}
