package com.jthou.coroutines.regex

suspend fun main() {
    BaiduHandler().connect("百度", "https://mobile.baidu.com/search?w=妙投").log()
    YingYongHuiHandler().connect("百度", "https://mobile.baidu.com/search?w=妙投").log()
    AnZhiHandler().connect("百度", "https://mobile.baidu.com/search?w=妙投").log()
}