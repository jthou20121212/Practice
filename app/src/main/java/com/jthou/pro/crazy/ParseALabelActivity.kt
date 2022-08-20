package com.jthou.pro.crazy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jthou.pro.crazy.databinding.ActivityParseAlabelBinding

class ParseALabelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityParseAlabelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView.text = "新眸曾在《中国商超进化史：隐藏在并购潮里的大江大河》一文中提到，测试<a href=\"https://t-pro.huxiu.com/company/main/1\" style=\"text-decoration:none;\">京东</a>中国商超的进化史，实质上是一部各大玩家的并购史，扩大规模、发展新业态、补足短板、规划战略则成为资本游戏的一部分，随着外资品牌进一步萎缩，国内商超将出现由全国性商超企业与区域性商超企业共存、相互竞争的市场格局，并且，商超的小型化、社区化及生鲜化将更加明显。于此之下，山姆会员店的生存法则就变得更加有趣。"
    }

}