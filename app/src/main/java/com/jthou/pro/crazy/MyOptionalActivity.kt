package com.jthou.pro.crazy

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jthou.fuckyou.Tab
import com.jthou.fuckyou.ViewHelper
import com.jthou.pro.crazy.databinding.ActivityMyOptionalBinding


class MyOptionalActivity : AppCompatActivity() {

    private var mLastPosition = 0

    companion object {
        const val TAG1 = "tag1"
        const val TAG2 = "tag2"
        const val TAG3 = "tag3"
        const val TAG4 = "tag4"
        const val VIEWPAGER_AUTO_SCROLL_DURATION = 600L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMyOptionalBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_my_optional)

        supportActionBar?.hide()

        val fragments = mutableListOf<Fragment>()
        fragments.add(TestFragment.newInstance("行情", TAG1))
        fragments.add(TestFragment.newInstance("投研", TAG2))
        fragments.add(TestFragment.newInstance("新闻", TAG3))
        fragments.add(TestFragment.newInstance("公告", TAG4))

        val mTabEntities = mutableListOf<CustomTabEntity>()
        mTabEntities.add(Tab("行情"))
        mTabEntities.add(Tab("投研"))
        mTabEntities.add(Tab("新闻"))
        mTabEntities.add(Tab("公告"))

//        viewPager.setSmoothScroll(false)
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return mTabEntities.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mTabEntities[position].tabTitle
            }

        }

        binding.viewPager.setPageTransformer(true, object : ViewPager.PageTransformer {

            override fun transformPage(page: View, position: Float) {
                val tag = page.tag
                Log.i(tag.toString(), "position : $position")

                val case1 = mLastPosition == 0 || mLastPosition == 1
                val case2 = binding.viewPager.currentItem == 0 || binding.viewPager.currentItem == 1

                if (TAG1 == tag && position <= 0 && position >= -1 && case1 && case2) {

                    Log.i("jthouLog", "position : $position")

                    // 处理行情 x 轴不偏移
                    val translationX = page.measuredWidth * position
                    ViewHelper.setTranslationX(-translationX, page)

                    // 处理行情 y 轴向下偏移
                    val translationY = page.measuredHeight * position
                    ViewHelper.setTranslationY(-translationY, page)
                }

                if (TAG2 == tag && position >= 0 && position <= 1 && case1 && case2) {
                    // 处理投研 x 轴不偏移
                    val translationX = page.measuredWidth * position
                    ViewHelper.setTranslationX(-translationX, page)

                    // 处理投研 y 轴向下偏移
                    val translationY = page.measuredHeight * position
                    ViewHelper.setTranslationY(translationY, page)
                }

//                if (TAG3 == tag && position > 0 && position < 2) {
//                    // 处理投研 x 轴不偏移
//                    val translationX = page.measuredWidth * position
//                    ViewHelper.setTranslationX(-translationX, page)
//
//                    // 处理投研 y 轴向下偏移
//                    val translationY = page.measuredHeight * position
//                    ViewHelper.setTranslationY(translationY, page)
//                }

//                val currentItem = viewPager.currentItem
//                Log.i("jthouLog", "currentItem : $currentItem")
            }

        })
        binding.tabLayout.setViewPager(binding.viewPager)
        binding.tabLayout.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                if (position != 0) {
                    return
                }

                binding.appBarLayout?.setExpanded(true, true)
            }

            override fun onTabReselect(position: Int) {

            }

        });
        binding.viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                mLastPosition = position
            }
        })
    }

}