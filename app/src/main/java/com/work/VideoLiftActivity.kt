package com.work

import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import com.demo.SimpleAdapter
import com.jthou.pro.crazy.databinding.ActivityVideoLiftBinding
import com.study.viewbinding.viewBinding
import com.utils.log
import kotlin.math.roundToInt
import kotlin.random.Random

class VideoLiftActivity : AppCompatActivity() {

    private val adapter by lazy { SimpleAdapter() }

    private val maxHeight by lazy {
        MAX_HEIGHT_RATIO * ScreenUtils.getScreenHeight()
    }

    private val videoDefaultWidth by lazy {
        ScreenUtils.getScreenWidth()
    }
    private val videoDefaultHeight by lazy {
        videoDefaultWidth / STANDARD_ASPECT_RATIO
    }

    companion object {
        const val STANDARD_ASPECT_RATIO = 375f / 210f
        const val MAX_HEIGHT_RATIO = 0.618f
    }

    private val binding by viewBinding(ActivityVideoLiftBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 横向视频
        val wide = Random(System.currentTimeMillis()).nextBoolean()
        "wide = $wide".log()
        // 竖向
        if (wide) {
            "videoDefaultWidth = $videoDefaultWidth".log()
            "videoDefaultHeight = $videoDefaultHeight".log()
            binding.videoArea.updateLayoutParams {
                width = videoDefaultWidth
                height = videoDefaultHeight.roundToInt()
            }
            binding.recyclerView.updateLayoutParams<MarginLayoutParams> {
                topMargin = videoDefaultHeight.roundToInt()
            }
        } else {
            // 生成随机宽度和高度值
            val randomWidth = Random(System.currentTimeMillis()).nextInt(600, 900)
            val randomHeight = Random(System.currentTimeMillis()).nextInt(900, 1200)
            "randomWidth = $randomWidth".log()
            "randomHeight = $randomHeight".log()

            val videoWidth = videoDefaultWidth
            val videoHeight = videoWidth * 1.0f * randomHeight / randomWidth
            "videoWidth = $videoWidth".log()
            "videoHeight = $videoHeight".log()

            binding.root.maxHeight = maxHeight
            binding.root.videoHeight = videoHeight
            binding.root.defaultHeight = videoDefaultHeight
            binding.root.aspectRatio = randomWidth * 1.0f / randomHeight
            binding.videoArea.updateLayoutParams {
                width = videoWidth
                height = videoHeight.roundToInt()
            }
            binding.recyclerView.updateLayoutParams<MarginLayoutParams> {
                // 如果超出最大高度，则设置为最大高度，即覆盖视频
                topMargin = minOf(videoHeight, maxHeight).roundToInt()
            }
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setNewInstance((1..100).map { it.toString() }.toMutableList())
    }

}