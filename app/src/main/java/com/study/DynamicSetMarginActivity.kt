package com.study

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jthou.pro.crazy.databinding.ActivityDynamicSetMarginBinding
import com.study.viewbinding.viewBinding
import com.study.widget.WechatPullNewestFrameLayout
import com.utils.dp

class DynamicSetMarginActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityDynamicSetMarginBinding::inflate)

    private class FragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return SimpleFragment()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.root.setTopLocationHeight(100.dp)
        binding.root.setMiddleLocationHeight(300.dp)
        binding.root.setBottomLocationHeight(500.dp)
        binding.root.setDefaultLocation(WechatPullNewestFrameLayout.Location.MIDDLE)

        binding.viewPager.adapter = FragmentAdapter(supportFragmentManager)
    }

}