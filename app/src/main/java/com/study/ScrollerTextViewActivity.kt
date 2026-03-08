package com.study

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import com.blankj.utilcode.util.ScreenUtils
import com.jthou.pro.crazy.R
import com.utils.dp


class ScrollerTextViewActivity : AppCompatActivity() {



    //    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var total: Float = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroller_text_view)
        val textView = findViewById<TextView>(R.id.textView)
        val content = "紫金矿业涨幅并不突出，市盈率垫底，主要原因有两个：\n" +
                "\n" +
                "\n" +
                "\n" +
                "一是大A酷爱小盘，市值8200亿的紫金矿业盘子太大；\n" +
                "\n" +
                "\n" +
                "\n" +
                "二是紫金矿业被“黄周期”压制。市场担心需求、顾虑库存，对铜价走势看不准，不肯给高估值。\n" +
                "\n" +
                "\n" +
                "\n" +
                "目前，铜的供需形势趋于明朗——电力、新能源车、AI算力对铜的需求旺盛。如：特高压工程每千公里用铜2万吨，英伟达GB200系列单柜用铜200公斤，纯电动车每台用铜约80-120公斤，直流快充桩每台用铜60公斤……高盛预测，到2030年电网升级将推动全球铜需求增长60%，相当于新增一个美国的需求。\n" +
                "\n" +
                "\n" +
                "\n" +
                "此外，铜也成为大国博弈的重要工具。美国玩不转稀土，但金融资本高度发达。" +
                "2021年，矿产金售价、成本分别为349元/克、176元/克，毛利润率49.5%；冶炼金售价、成本分别为368元/克、367.6元克，毛利润率仅为0.12%；\n" +
                "\n" +
                "2024年，矿产金售价、成本分别增至521元/克、231元/克，毛利润55.7%；冶炼金售价、成本分别增至550元/克、547元克，毛利润率也才0.45%；\n" +
                "\n" +
                "2025年H1，矿产金售价进一步提高到692元/克，成本亦增至262克/元，毛利润率62.2%；冶炼金售价、成本分别为713元/克、707元/克，毛利润率0.84%；"
        textView.text = content
        // textView.maxHeight = 300.dp
//        textView.updateLayoutParams {
//            height = 300.dp
//        }
        // textView.movementMethod = ScrollingMovementMethod()
    }
}