package com.jthou.coroutines.regex

const val VERSION_REGEX = """(\d\.\d{1,2}\.\d{1,2})"""

val MT_URL_MAP = mapOf(
    "360" to "https://app.so.com/detail/index?pname=com.huxiupro&id=4565084",
    "苹果" to "https://apps.apple.com/cn/app/%E5%A6%99%E6%8A%95-%E8%82%A1%E7%A5%A8%E8%B4%A2%E7%BB%8F%E6%96%B0%E9%97%BB%E7%83%AD%E7%82%B9%E8%B5%84%E8%AE%AF/id1521595325",
    "百度" to "https://mobile.baidu.com/search?w=妙投",
    "酷安" to "https://www.coolapk.com/apk/com.huxiupro",
    "华为" to "https://appgallery.huawei.com/app/C102708419",
    "魅族" to "https://app.meizu.com/apps/public/detail?package_name=com.huxiupro",
    "PP助手" to "https://m.pp.cn/detail.html?ch_src=pp_upgrade_wap&appid=8127133&cfg=&query=%E5%A6%99%E6%8A%95&order=1&ch=search_bz",
    "应用宝" to "https://a.app.qq.com/o/simple.jsp?pkgname=com.huxiupro",
    "Vivo" to "https://h5coml.vivo.com.cn/h5coml/appdetail_h5/browser_v2/index.html?appId=2953906&resource=301&source=7",
    "小米" to "https://m.app.mi.com/#page=detail&id=1273670",
    "应用汇" to "http://www.appchina.com/app/com.huxiupro",
    "机锋" to "http://www.gfanstore.com/android/8604.html",
)

val HX_URL_MAP = mapOf(
    "360" to "https://app.so.com/detail/index?pname=com.huxiu&id=792510",
    "苹果" to "https://apps.apple.com/cn/app/%E8%99%8E%E5%97%85-%E7%A7%91%E6%8A%80%E5%A4%B4%E6%9D%A1%E8%B4%A2%E7%BB%8F%E6%96%B0%E9%97%BB%E7%83%AD%E7%82%B9%E8%B5%84%E8%AE%AF/id557509598",
    "百度" to "https://mobile.baidu.com/search?w=虎嗅",
    "酷安" to "https://www.coolapk.com/apk/com.huxiu",
    "华为" to "https://appgallery.huawei.com/app/C10428259",
    "魅族" to "https://app.meizu.com/apps/public/detail?package_name=com.huxiu",
    "PP助手" to "https://m.pp.cn/detail.html?ch_src=pp_upgrade_wap&appid=2051077&cfg=&query=%E8%99%8E%E5%97%85&order=2&ch=search_bz",
    "应用宝" to "https://a.app.qq.com/o/simple.jsp?pkgname=com.huxiu",
    "Vivo" to "https://h5coml.vivo.com.cn/h5coml/appdetail_h5/browser_v2/index.html?appId=77361&resource=301&source=7",
    "小米" to "https://m.app.mi.com/#page=detail&id=49014",
    "应用汇" to "http://www.appchina.com/app/com.huxiu",
    "安智" to "http://m.anzhi.com/app_7296_com.huxiu.html",
)

val HANDLE_MAP = mapOf(
    "360" to QiHu360Handler(),
    "苹果" to AppleHandler(),
    "百度" to BaiduHandler(),
    "酷安" to KuAnHandler(),
    "华为" to HuaWeiHandler(),
    "魅族" to MeiZuHandler(),
    "PP助手" to PPAssistantHandler(),
    "应用宝" to YingYongBaoHandler(),
    "Vivo" to VivoHandler(),
    "小米" to XiaoMiHandler(),
    "应用汇" to YingYongHuiHandler(),
    "机锋" to JiFengHandler(),
    "安智" to AnZhiHandler(),
)