package com.study.gson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun main() {
    val json = """
        [{"store":"360","version":"8.6.2"},{"store":"华为","version":"8.6.2"},{"store":"魅族","version":"8.6.2"},{"store":"PP助手","version":"8.6.2"},{"store":"应用宝","version":"8.6.2"},{"store":"Vivo","version":"8.6.2"},{"store":"应用汇","version":"8.6.2"},{"store":"联想","version":"8.6.2"},{"store":"百度","version":"8.6.0"},{"store":"机锋","version":"8.3.8"}]
    """.trimIndent()

    val list: List<AppVersion> =
        Gson().fromJson(json, object : TypeToken<List<AppVersion>>() {}.type)

    println("className : ${list.javaClass.name}")
}