package com.study.jsoup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jthou.fetch.log
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.ActivityJsoupBinding
import com.study.viewbinding.viewBinding
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.FormElement
import kotlin.concurrent.thread
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class JsoupActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityJsoupBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jsoup)
        val url = "https://m.baidu.com"
        binding.webView.loadUrl(url)
        Utils.setDefaultWebSettings(binding.webView)

        lifecycleScope.launch(Dispatchers.IO) {
            delay(10.toDuration(DurationUnit.SECONDS))
            val element = Jsoup.connect(url).get().getElementById("index-kw")
            element.log()
            element?.`val`().log()
            (element is FormElement).log()
            element?.text().log()
        }
    }

}