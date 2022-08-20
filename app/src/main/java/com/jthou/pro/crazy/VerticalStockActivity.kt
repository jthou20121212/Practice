package com.jthou.pro.crazy

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ScreenUtils
import com.jthou.pro.crazy.databinding.ActivityVerticalStockBinding

/**
 * @author jthou
 * @date 21-10-2020
 * @since 1.0.0
 */
class VerticalStockActivity : AppCompatActivity() {

    private var mOrientationEventListenerCore: OrientationEventListenerCore? = null

    companion object {
        fun launchActivity(context: Context) {
            val intent = Intent(context, VerticalStockActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityVerticalStockBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_vertical_stock)
        val leftData = listOf("贵州茅台", "银科控股", "爱奇艺",
            "贵州茅台", "银科控股", "爱奇艺",
            "贵州茅台", "银科控股", "爱奇艺",
            "贵州茅台", "银科控股", "爱奇艺",
            "贵州茅台", "银科控股", "爱奇艺",
            "贵州茅台", "银科控股", "爱奇艺",
            "阿里巴巴", "京东")
        binding.recyclerView1.apply {
            this.layoutManager = LinearLayoutManager(this@VerticalStockActivity)
            this.adapter = LeftAdapter(this@VerticalStockActivity, leftData)
        }
        val rightData = listOf(
            null,
            null,
            null,
            Stock(),
            null,
            null,
            null,
            Stock(),
            null,
            null,
            null,
            Stock(),
            null,
            null,
            null,
            Stock(),
            null,
            null,
            null,
            Stock()
        )
        binding.recyclerView2.apply {
            this.layoutManager = LinearLayoutManager(this@VerticalStockActivity)
            this.adapter = RightAdapter(this@VerticalStockActivity, rightData)
        }
        binding.tvTitle.setOnClickListener {
            requestedOrientation = if (ScreenUtils.isPortrait()) {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            }
        }


        val scrollListeners =
            arrayOfNulls<RecyclerView.OnScrollListener>(2)
        scrollListeners[0] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.recyclerView2.removeOnScrollListener(scrollListeners[1]!!)
                binding.recyclerView2.scrollBy(dx, dy)
                binding.recyclerView2.addOnScrollListener(scrollListeners[1]!!)
            }
        }
        scrollListeners[1] = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.recyclerView1.removeOnScrollListener(scrollListeners[0]!!)
                binding.recyclerView1.scrollBy(dx, dy)
                binding.recyclerView1.addOnScrollListener(scrollListeners[0]!!)
            }
        }
        binding.recyclerView1.addOnScrollListener(scrollListeners[0]!!)
        binding.recyclerView2.addOnScrollListener(scrollListeners[1]!!)

        mOrientationEventListenerCore = OrientationEventListenerCore.newInstance()
        mOrientationEventListenerCore?.setListener {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        }?.register(this)

        val scrollListeners2 =
            arrayOfNulls<View.OnScrollChangeListener>(2)
        scrollListeners2[0] = object : View.OnScrollChangeListener {
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                binding.horizontalScrollView2.setOnScrollChangeListener(null)
                binding.horizontalScrollView2.scrollTo(scrollX, scrollY)
                binding.horizontalScrollView2.setOnScrollChangeListener(scrollListeners2[1]!!)
            }
        }
        scrollListeners2[1] = object : View.OnScrollChangeListener {
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                binding.horizontalScrollView1.setOnScrollChangeListener(null)
                binding.horizontalScrollView1.scrollTo(scrollX, scrollY)
                binding.horizontalScrollView1.setOnScrollChangeListener(scrollListeners2[0]!!)
            }
        }
        binding.horizontalScrollView1.setOnScrollChangeListener(scrollListeners2[0])
        binding.horizontalScrollView2.setOnScrollChangeListener(scrollListeners2[1])

    }

    class LeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class RightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

//    class LeftAdapter : RecyclerView.Adapter<LeftViewHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeftViewHolder {
//            val textView = TextView(this@VerticalStockActivity)
//            return LeftViewHolder(textView)
//        }
//
//        override fun getItemCount(): Int {
//            return leftData.size
//        }
//
//        override fun onBindViewHolder(holder: LeftViewHolder, position: Int) {
//            val textView = holder.itemView as? TextView ?: return
//            textView.text =
//        }
//
//    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            // 横屏
//            ViewHelper.setVisibility(View.GONE, tv_title)
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            // 竖屏
//            ViewHelper.setVisibility(View.VISIBLE, tv_title)
//        }
//    }

    override fun onBackPressed() {
        if (ScreenUtils.isLandscape()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        mOrientationEventListenerCore?.disable()
    }

}