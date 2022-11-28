package com.study.jsoup;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

class Utils {

    public static void setDefaultWebSettings(WebView webview) {
        webview.getSettings().setDefaultTextEncodingName("UTF-8");
        //设置加载进来的页面自适应手机屏幕
        webview.getSettings().setUseWideViewPort(false);//可以任意缩放比例
        // 设置禁止缩放工具
        webview.getSettings().setBuiltInZoomControls(false);
        //        webview.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setTextZoom(100);
        webview.getSettings().setDisplayZoomControls(false);


         /*
        setLayoutAlgorithm设定了WebView的HTML布局方式，其中包含了下面的三个参数值
        NORMAL：正常显示，没有渲染变化。
        SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。   //这个是强制的，把网页都挤变形了
        NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。 //好像是默认的
        * */
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //5.0以后兼容https和http
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//
        }

        webview.getSettings().setJavaScriptEnabled(true);//支持javascript
        //        webview.getSettings().setLoadWithOverviewMode(true);//HTML5的地理位置服务,设置为true,启用地理定位

        //
        //        //--------------从网上找到的webview功能---------------------------------
        //        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        //        webview.getSettings().setLoadsImagesAutomatically(true);  //支持自动加载图片

        // //1.提高渲染等级
        // webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //
        //
        // // 开启 DOM storage API 功能
        // webview.getSettings().setDomStorageEnabled(true);
        // //开启 database storage API 功能
        // webview.getSettings().setDatabaseEnabled(true);
        //
        // //开启 Application Caches 功能
        // webview.getSettings().setAppCacheEnabled(true);
        // webview.getSettings().setAppCacheMaxSize(100 * 1024 * 1024);   //缓存最多可以有8M
        // webview.getSettings().setAllowFileAccess(true);   // 可以读取文件缓存(manifest生效)
    }

}
