package com.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jthou.pro.crazy.databinding.ActivityPlateBinding
import com.study.viewbinding.viewBinding


class PlateActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityPlateBinding::inflate)

    private class FragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return if (position == 0) IndustryDataFragment.newInstance() else StockGuideFragment.newInstance()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewPager.adapter = FragmentAdapter(this)
        // TabLayout 和 Viewpager2 关联
        val array = arrayOf(
            "行业数据",
            "个股指南"
        )
        val tab = TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = array[position]
        }
        tab.attach()

    }

}