package com.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ScreenUtils
import com.gyf.immersionbar.ImmersionBar
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.FragmentStockGuideChildBinding
import com.study.viewbinding.viewBinding
import kotlin.math.roundToInt

class StockGuideChildFragment : Fragment() {

    private val binding by viewBinding(FragmentStockGuideChildBinding::bind)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_guide_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupViews() {
        val screenMinSize = ScreenUtils.getScreenWidth().coerceAtMost(ScreenUtils.getScreenHeight())
        val targetWidth = (screenMinSize * NAME_CODE_WIDTH_RATIO).roundToInt()
        val params: ViewGroup.LayoutParams = binding.recyclerView1.layoutParams
        val layoutParams: ViewGroup.LayoutParams = binding.tvNameCode.layoutParams
        layoutParams.width = targetWidth
        params.width = layoutParams.width

        // 判断横屏进入
        if (ScreenUtils.getScreenWidth() > ScreenUtils.getScreenHeight()) {
            binding.ivCircleWitch.isGone = true
            binding.rlTitleBar.isGone = true
            hideStatusBar()
        }
        setMarginIfNotchScreen()
    }

    private fun hideStatusBar() {
        if (activity !is PlateActivity) {
            return
        }
        if (!ActivityUtils.isActivityAlive(activity)) {
            return
        }
        val activity: PlateActivity? = activity as PlateActivity?
        // activity.hideStatusBar()
    }

    private fun setMarginIfNotchScreen() {
        if (!ActivityUtils.isActivityAlive(activity)) {
            return
        }
        val hasNotchScreen: Boolean = ImmersionBar.hasNotchScreen(requireActivity())
        val horizontalScreen = ScreenUtils.getScreenWidth() > ScreenUtils.getScreenHeight()
        val leftMargin = if (hasNotchScreen && horizontalScreen) ImmersionBar.getStatusBarHeight(
            requireActivity()
        ) else 0
        val params1 = binding.tvNameCode.layoutParams as ViewGroup.MarginLayoutParams
        val params2 = binding.recyclerView1.layoutParams as ViewGroup.MarginLayoutParams
        if (leftMargin == params1.leftMargin && leftMargin == params2.leftMargin) {
            return
        }
        params2.leftMargin = leftMargin
        params1.leftMargin = params2.leftMargin
        binding.tvNameCode.requestLayout()
    }

    companion object {

        const val NAME_CODE_WIDTH_RATIO = 1 / 2f

        @JvmStatic
        fun newInstance() = StockGuideChildFragment()
    }
}