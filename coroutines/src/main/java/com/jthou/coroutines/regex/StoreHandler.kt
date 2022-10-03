package com.jthou.coroutines.regex

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.internal.closeQuietly
import okio.IOException
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.time.Duration
import java.util.regex.Pattern
import kotlin.coroutines.resume

abstract class StoreHandler {

    abstract suspend fun connect(storeName: String, url: String): String

}

abstract class RegexStoreHandler : StoreHandler() {

    abstract val regex: String

    override suspend fun connect(storeName: String, url: String) : String {
        return suspendCancellableCoroutine {
            val request = Request.Builder().url(url)
                // 避免有些服务器的安全策略导致请求失败 如酷安
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Mobile Safari/537.36")
                .build()
            val okHttpClient = OkHttpClient.Builder().build()
            val newCall = okHttpClient.newCall(request)
            newCall.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resume("$storeName：${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    it.resume(handle(storeName, response))
                }
            })
            it.invokeOnCancellation { newCall.cancel() }
        }
    }

    private fun handle(storeName: String, response: Response): String {
        val input = response.body.string()
        val compile = Pattern.compile(regex)
        val matcher = compile.matcher(input)
        return if (matcher.find()) {
            val versionName = matcher.group(1)
            "$storeName：$versionName"
        } else {
            "$storeName：未匹配到版本信息"
        }
    }

}

abstract class WebDriverStoreHandler: StoreHandler() {

    override suspend fun connect(storeName: String, url: String): String {
        return suspendCancellableCoroutine {
            val driver: WebDriver = ChromeDriver(ChromeOptions().addArguments("headless"))
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15))
            driver[url]
            it.resume(handle(storeName, driver))
            driver.quit()
        }
    }

    abstract fun handle(storeName: String, driver: WebDriver): String

}

class QiHu360Handler(override val regex: String = """<p>版本：$VERSION_REGEX</p>""") : RegexStoreHandler()

class AppleHandler(override val regex: String = """<p class="l-column small-6 medium-12 whats-new__latest__version">版本\s$VERSION_REGEX<\/p>""") : RegexStoreHandler()

class BaiduHandler(override val regex: String = """<div\s?class="versionNum"\s?.+>\s*<span\s?.+>\s*版本：\s*<\/span>\s*<span\s?.+>\s*$VERSION_REGEX\s*<\/span>\s*<\/div>|"versionNum":"$VERSION_REGEX"""") : RegexStoreHandler() {
    override suspend fun connect(storeName: String, url: String) : String {
        return suspendCancellableCoroutine {
            val request = Request.Builder().url(url).build()
            val okHttpClient = OkHttpClient.Builder().build()
            val newCall = okHttpClient.newCall(request)
            newCall.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resume("$storeName：${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    val keyword = getSearchKeyword(url)
                    val input = response.body.string()
                    response.closeQuietly()
                    val pattern = Pattern.compile("""<a\s?href="([^"]+)"\s+.+?$keyword""")
                    val matcher = pattern.matcher(input)
                    if (matcher.find()) {
                        val group = matcher.group(1)
                        val baseUrl = url.substring(0, url.lastIndexOf("/"))
                        CoroutineScope(it.context).launch {
                            it.resume(super@BaiduHandler.connect(storeName, "$baseUrl$group"))
                        }
                    } else {
                        it.resume("$storeName：未匹配到版本信息")
                    }
                }
            })
            it.invokeOnCancellation { newCall.cancel() }
        }
    }

    fun getSearchKeyword(url: String): String {
        val pattern = Pattern.compile("""\?w=(.+)${'$'}""")
        val matcher = pattern.matcher(url)
        return if (matcher.find()) matcher.group(1) as String else ""
    }

}

class KuAnHandler(override val regex: String = """<span\s+class="list_app_info">\s*$VERSION_REGEX<\/span>""") : RegexStoreHandler()

class HuaWeiHandler : WebDriverStoreHandler () {

    override fun handle(storeName: String, driver: WebDriver): String {
        val list = driver.findElements(By.className("info_val"))
        val pattern = Pattern.compile(VERSION_REGEX)
        list.forEach {
            val matcher = pattern.matcher(it.text)
            if (matcher.find()) {
                val group = matcher.group(1)
                return "$storeName：$group"
            }
        }
        return "$storeName：未匹配到版本信息"
    }

}

class MeiZuHandler(override val regex: String = """<div class="app_content ellipsis noPointer">$VERSION_REGEX</div>""") : RegexStoreHandler()

class PPAssistantHandler : WebDriverStoreHandler() {
    override fun handle(storeName: String, driver: WebDriver): String {
        val list = driver.findElements(By.className("detail-update"))
        val pattern = Pattern.compile(VERSION_REGEX)
        list.forEach { webElement ->
            val findElements = webElement.findElements(By.tagName("p"))
            findElements.forEach {
                val matcher = pattern.matcher(it.text)
                if (matcher.find()) {
                    val group = matcher.group(1)
                    return "$storeName：$group"
                }
            }
        }
        return "$storeName：未匹配到版本信息"
    }
}

class YingYongBaoHandler(override val regex: String = """<p class="pp-comp-extra-p">版本：$VERSION_REGEX\s*<\/p>""") : RegexStoreHandler()

class VivoHandler : WebDriverStoreHandler() {
    override fun handle(storeName: String, driver: WebDriver): String {
        val list = driver.findElements(By.className("more-detail"))
        val pattern = Pattern.compile(VERSION_REGEX)
        list.forEach { webElement ->
            val findElements = webElement.findElements(By.tagName("span"))
            findElements.forEach {
                val matcher = pattern.matcher(it.text)
                if (matcher.find()) {
                    val group = matcher.group(1)
                    return "$storeName：$group"
                }
            }
        }
        return "$storeName：未匹配到版本信息"
    }
}

class XiaoMiHandler : WebDriverStoreHandler() {

    override fun handle(storeName: String, driver: WebDriver): String {
        val list = driver.findElements(By.className("detail-appinfo"))
        val pattern = Pattern.compile(VERSION_REGEX)
        list.forEach {
            val matcher = pattern.matcher(it.text)
            if (matcher.find()) {
                val group = matcher.group(1)
                return "$storeName：$group"
            }
        }
        return "$storeName：未匹配到版本信息"
    }

}

class YingYongHuiHandler(override val regex: String = """版本[\d\D]?$VERSION_REGEX""") : RegexStoreHandler()

class JiFengHandler(override val regex: String = """版本：\s?v$VERSION_REGEX""") : RegexStoreHandler()

class AnZhiHandler(override val regex: String = """<span>版本：$VERSION_REGEX<\/span>""") : RegexStoreHandler()