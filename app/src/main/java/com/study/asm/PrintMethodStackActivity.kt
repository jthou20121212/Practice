package com.study.asm

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.databinding.ActivityPrintMethodStackBinding
import com.study.viewbinding.viewBinding

class PrintMethodStackActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityPrintMethodStackBinding::inflate)

    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tv1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.i("PrintMethodStackActivity", "匿名内部类点击事件")
            }
        })
        binding.tv2.setOnClickListener {
            Log.i("PrintMethodStackActivity", "lambda 点击事件")
        }

        handler.postDelayed({ testAnnotation() }, 5000)
    }

    fun onClick(view: View) {
        Log.i("PrintMethodStackActivity", "xml 文件配置 onclick")
    }

    @JavascriptInterface
    fun testAnnotation() {
        Log.i("PrintMethodStackActivity", "JavascriptInterface 注解")
    }



    private fun privateMethod() {

    }

    internal fun internalMethod() {

    }

    protected fun protectedMethod() {

    }

}