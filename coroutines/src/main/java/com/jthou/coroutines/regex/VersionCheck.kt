package com.jthou.coroutines.regex

import io.github.bonigarcia.wdm.WebDriverManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.regex.Pattern

suspend fun main(args: Array<String>) {
     WebDriverManager.chromedriver().setup()
    val userDir = System.getProperty("user.dir")
    System.setProperty("webdriver.chrome.driver", "$userDir/coroutines/driver/chromedriver")

    if (args.isEmpty() || args.size < 2) throw IllegalArgumentException("请确定要查询的 App 版本信息和要发送的机器人 key\n 妙投：m\n虎嗅：h")

    val appFlag = args[0]
    if (!"m".equals(appFlag, true) && !"h".equals(appFlag, true))  throw IllegalArgumentException("请确定要查询的 App 版本信息和要发送的机器人 key\n 妙投：m\n虎嗅：h")

    val m = "m".equals(appFlag, true)
    val urlMap = if (m) MT_URL_MAP else HX_URL_MAP
    val handlerList = arrayListOf<String>()
    urlMap.flatMap { (storeName, url) ->
        val handler = HANDLE_MAP[storeName] ?: return
        handlerList.apply { add((handler.connect(storeName, url))) }
    }

    val pattern = Pattern.compile(VERSION_REGEX)
    handlerList.sortBy {
        val matcher = pattern.matcher(it)
        if (matcher.find()) {
            matcher.group(1)
        } else {
            "0.0.0"
        }
    }

    val maxVersion = handlerList.last().split("：")[1]
    val versionString = StringBuilder()
    handlerList.forEach {
        val split = it.split("：")
        versionString.appendLine().append("\t\t").append("    ")
        if (it.endsWith(maxVersion)) {
            versionString.append(""">${split[0]}：<font color='info'>${split[1]}</font>""")
        } else {
            versionString.append(""">${split[0]}：<font color='warning'>${split[1]}</font>""")
        }
    }
    val icon = if (m) """💰""" else """🐯"""
    val name = if (m) "妙投" else "虎嗅"
    val result =
    """
    {
        "msgtype": "markdown",
        "markdown": {
            "content": "$icon <font color='info'>$name App</font> 版本更新$versionString"
        }
    }
    """.trim()
    result.log()
    val requestBody = result.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    val webhookUrlKeys = args.copyOfRange(1, args.size)
    webhookUrlKeys.forEach {
        val request: Request = Request.Builder()
            .url("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=$it")
            .post(requestBody)
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader("cache-control", "no-cache")
            .addHeader("postman-token", "b855601a-720c-a5b1-fb25-409820086a00")
            .build()
        val client = OkHttpClient.Builder().build()
        val response = client.newCall(request).execute()
        if (response.code == 200) {
            println("send success")
        }
    }

}