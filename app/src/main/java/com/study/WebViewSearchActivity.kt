package com.study

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

/**
 * WebView 搜索词获取示例
 * 
 * 功能：通过 WebView 打开百度首页，获取用户在搜索框中输入的搜索词
 * 
 * 实现方案：
 * 1. JavaScript 注入：监听搜索框输入事件
 * 2. URL 拦截：捕获搜索请求的 URL 参数
 * 3. 混合方案：结合两种方式提高成功率
 * 
 * @author A同学
 * @date 2026-03-01
 */
class WebViewSearchActivity : AppCompatActivity() {
    
    private lateinit var webView: WebView
    private val searchWords = mutableListOf<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 创建 WebView
        webView = WebView(this)
        setContentView(webView)
        
        // 配置 WebView
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        
        // 方案一：注入 JavaScript 接口
        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        
        // 方案二：拦截 URL
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                request?.url?.let { url ->
                    // 检查是否是百度搜索请求
                    if (url.host == "www.baidu.com") {
                        val searchWord = extractSearchWord(url)
                        if (searchWord != null) {
                            handleSearchWord(searchWord)
                        }
                    }
                }
                return false
            }
            
            override fun onPageFinished(view: WebView?, url: String?) {
                // 页面加载完成后注入 JavaScript
                injectJavaScript()
            }
        }
        
        // 加载百度首页
        webView.loadUrl("https://www.baidu.com")
    }
    
    /**
     * 从 URL 中提取搜索词
     */
    private fun extractSearchWord(url: Uri): String? {
        // 百度搜索的参数通常是 wd、word 或 query
        return url.getQueryParameter("wd") ?: 
               url.getQueryParameter("word") ?: 
               url.getQueryParameter("query")
    }
    
    /**
     * 处理搜索词
     */
    private fun handleSearchWord(searchWord: String) {
        if (!searchWords.contains(searchWord)) {
            searchWords.add(searchWord)
            Log.d("WebViewSearch", "搜索词: $searchWord")
            Log.d("WebViewSearch", "历史记录: $searchWords")
            
            // 可以在这里处理搜索词，比如：
            // - 保存到数据库
            // - 发送到服务器
            // - 进行分析统计
            // - 显示在界面上
        }
    }
    
    /**
     * 注入 JavaScript 监听搜索框输入
     */
    private fun injectJavaScript() {
        webView.evaluateJavascript("""
            (function() {
                // 等待页面完全加载
                setTimeout(function() {
                    // 监听搜索框输入
                    var searchInput = document.querySelector('input[name="wd"]');
                    if (searchInput) {
                        searchInput.addEventListener('input', function(event) {
                            if (window.Android) {
                                window.Android.onSearchInput(event.target.value);
                            }
                        });
                        
                        // 监听回车提交
                        searchInput.addEventListener('keydown', function(event) {
                            if (event.key === 'Enter' && window.Android) {
                                window.Android.onSearchInput(event.target.value);
                            }
                        });
                    }
                    
                    // 监听表单提交（备用方案）
                    var searchForm = document.querySelector('form[action="/s"]');
                    if (searchForm) {
                        searchForm.addEventListener('submit', function(event) {
                            var searchWord = document.querySelector('input[name="wd"]')?.value;
                            if (searchWord && window.Android) {
                                window.Android.onSearchInput(searchWord);
                            }
                        });
                    }
                }, 1000);
            })();
        """, null)
    }
    
    /**
     * JavaScript 接口类
     * 用于接收来自 WebView 的搜索词
     */
    class WebAppInterface(private val activity: WebViewSearchActivity) {
        
        @JavascriptInterface
        fun onSearchInput(searchTerm: String) {
            activity.handleSearchWord(searchTerm)
        }
    }
    
    override fun onDestroy() {
        // 清理 WebView
        webView.destroy()
        super.onDestroy()
    }
}
