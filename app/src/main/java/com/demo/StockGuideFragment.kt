package com.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.FragmentIndustryDataBinding
import com.jthou.pro.crazy.databinding.FragmentStockGuideBinding
import com.study.viewbinding.viewBinding

class StockGuideFragment : Fragment() {

    private val binding by viewBinding(FragmentStockGuideBinding::bind)

    private class FragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return StockGuideChildFragment.newInstance()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = FragmentAdapter(this)
        // TabLayout 和 Viewpager2 关联
        val array = arrayOf(
            "硅料",
            "硅片",
            "电池片"
        )
        val tab = TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = array[position]
        }
        tab.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = StockGuideFragment()
    }

}