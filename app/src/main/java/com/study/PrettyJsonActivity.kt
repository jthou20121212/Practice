package com.study

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jthou.pro.crazy.Tab
import com.jthou.pro.crazy.databinding.ActivityPrettyJsonBinding
import com.study.viewbinding.viewBinding
import com.utils.log

class PrettyJsonActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityPrettyJsonBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val binding = ActivityPrettyJsonBinding.bind(inflate(R.layout.activity_pretty_json))
        setContentView(binding.root)

        val tab = Gson().fromJson("", Tab::class.java)
        // Caused by: java.lang.NullPointerException: Gson().fromJson("", obje…n<List<Tab?>?>() {}.type) must not be null
        // val tabList1: List<Tab> = Gson().fromJson("", object : TypeToken<List<Tab?>?>() {}.type)
        val tabList2 = Gson().fromJson<List<Tab>>("[]", List::class.java)

        "tab : $tab".log()
        "tabList2 : $tabList2".log()

        val json = """
            {
    "id": 31,
    "title": "F啊测试很多字显示验证m测试很多字显示验证，测试很多字显示验",
    "summary": "今天，北京市朝阳区人民检察院经依法审查，对犯罪嫌疑人吴某凡以涉嫌强奸罪批准逮捕。自吴亦凡事件在网络爆发，呈现在公众面前的是一个罔顾法纪、恣意妄为、金钱至上、随心所欲的“明星”。呵呵呵呵呵",
    "bg_img_url": "https://img.huxiucdn.com/miaotou/hot_spot_bg.png",
    "companies": [],
    "share_info": {
      "is_today_share": false,
      "is_month_limit": false,
      "share_category": "每日分享",
      "login_share_title": "解锁1天妙投会员，每月最多7天",
      "logout_share_title": "登录后再分享可获得会员权益哦"
    },
    "mask": {
      "text": "还有13位用户的观点等你解锁",
      "is_reg_experience": false,
      "bottom_text": "立即开通妙投会员"
    },
    "view_points": [
      {
        "id": 133,
        "user": {
          "uid": 1528191,
          "username": "哈佛商业评论©",
          "avatar": "https://s0-img.huxiucdn.com/avatar/default.jpg",
          "user_title": "妙投高级研究员"
        },
        "content": "据河南省气象部门预报，8月19日下午到夜间，安阳、鹤壁、新乡、濮阳、三门峡、洛阳降水量25～45毫米、局部70～100毫米，局地伴有7-8级短时大风；22日黄河以北和三门峡、洛阳、郑州、开封、许昌、商丘等地部分县市有暴雨或大暴雨，局地特大暴雨，大部地区降水量70～100毫米，部分县市150～180毫米，局部可达200～300毫米，最大小时雨强70～90毫米；24到26日，降水主雨带将位于江淮一带，河南省淮河流域多降水天气。",
        "agree_num": 0,
        "is_agree": false,
        "dateline": 1629356940,
        "img_urls": null,
        "relevant_url": "https://baijiahao.baidu.com/s?id=1708489619034546690&wfr=spider&for=pc"
      },
      {
        "id": 118,
        "user": {
          "uid": 1648821,
          "username": "嗅友网哈哈哈哈哈哈哈哈哈哈哈哈",
          "avatar": "https://s0-img.huxiucdn.com/test/auth/data/avatar/001/64/88/21_1605842150.jpeg",
          "user_title": "妙投高级研究员"
        },
        "content": "百度世界2021大会于2021年8月18日在百度APP、央视新闻客户端等多个平台同步直播。\n百度创始人、董事长兼首席执行官李彦宏与央视主持人一同在线上与观众互动，让用户与最前沿的技术近距离接触。",
        "agree_num": 1,
        "is_agree": false,
        "dateline": 1629254940,
        "img_urls": null,
        "relevant_url": "https://baike.baidu.com/item/%E7%99%BE%E5%BA%A6%E4%B8%96%E7%95%8C2021%E5%A4%A7%E4%BC%9A/58264679?fromtitle=%E7%99%BE%E5%BA%A6%E4%B8%96%E7%95%8C%E5%A4%A7%E4%BC%9A2021&fromid=58307235&fr=aladdin"
      },
      {
        "id": 102,
        "user": {
          "uid": 1524891,
          "username": "哈罗闪",
          "avatar": "https://s0-img.huxiucdn.com/auth/data/avatar/3.jpg",
          "user_title": "妙投普通研究员"
        },
        "content": "8 月 17 日，市场监管总局发布《禁止网络不正当竞争行为规定(公开征求意见稿)》，反馈截止日期为 2021 年 9 月 15 日。\n全国人大常委会会议第三次审议的个人信息保护法草案，对“大数据杀熟”等问题作出。-12345",
        "agree_num": 5,
        "is_agree": false,
        "dateline": 1629190980,
        "img_urls": null,
        "relevant_url": "https://www.chinaz.com/2021/0817/1292283.shtml"
      },
      {
        "id": 104,
        "user": {
          "uid": 1523548,
          "username": "哈尔滨永利皇宫",
          "avatar": "https://s0-img.huxiucdn.com/auth/data/avatar/001/52/35/48_1473214729.jpg",
          "user_title": "妙投普通研究员"
        },
        "content": "此外，草案三审稿还对大型互联网平台和小型个人信息处理者进行了区分，规定大型互联网平台应当遵循公开、公平、公正的原则，制定有关个人信息保护的平台规则；授权国家网信部门针对小型个人信息处理者制定相关规则。\n诟病已久的互联网问题，正在逐步去解决。",
        "agree_num": 2,
        "is_agree": false,
        "dateline": 1629190980,
        "img_urls": [
          {
            "uuid": "e1bd967f-ff39-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/170242591685.jpg?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/170242591685.jpg",
            "origin_width": 300,
            "origin_height": 260,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/170242591685.jpg",
            "is_gif": false,
            "tailored_width": 399,
            "tailored_height": 345
          }
        ],
        "relevant_url": "https://www.baidu.com/s?cl=3&tn=baidutop10&fr=top1000&wd=%E7%A6%81%E6%AD%A2%E5%A4%A7%E6%95%B0%E6%8D%AE%E6%9D%80%E7%86%9F%E6%8B%9F%E5%85%A5%E6%B3%95&rsv_idx=2&rsv_dl=fyb_n_homepage&hisfilter=1"
      },
      {
        "id": 101,
        "user": {
          "uid": 1648444,
          "username": "你努力之后才发现智商的鸿沟是无",
          "avatar": "https://s0-img.huxiucdn.com/test/auth/data/avatar/001/64/84/44_1622429341.jpeg",
          "user_title": "妙投中级研究员"
        },
        "content": "每个人都在自己的手机上购物，一些商家通过收集、分析个人信息并进行“大数据杀熟”，给不同人展现不同的价格，同样的东西，别人买来 1 元，你要1. 5 元，而你就这么被割了，却很难发现。",
        "agree_num": 2,
        "is_agree": false,
        "dateline": 1629190920,
        "img_urls": null,
        "relevant_url": ""
      },
      {
        "id": 100,
        "user": {
          "uid": 1648309,
          "username": "虎小小",
          "avatar": "https://s0-img.huxiucdn.com/test/auth/data/avatar/001/64/83/09_1626344480.png",
          "user_title": "妙投中级研究员"
        },
        "content": "个人信息保护法草案今天提请十三届全国人大常委会第三十次会议三审。草案进一步完善个人信息处理规则，特别是对应用程序（APP）过度收集个人信息、“大数据杀熟”等作出有针对性规范。同时，草案将不满十四周岁未成年人的个人信息作为敏感个人信息，并要求个人信息处理者对此制定专门的个人信息处理规则。",
        "agree_num": 1,
        "is_agree": false,
        "dateline": 1629190860,
        "img_urls": null,
        "relevant_url": ""
      },
      {
        "id": 94,
        "user": {
          "uid": 2953479,
          "username": "喵小猫",
          "avatar": "https://s0-img.huxiucdn.com/auth/data/avatar/8.png",
          "user_title": "妙投高级研究员"
        },
        "content": "自吴亦凡事件在网络爆发，呈现在公众面前的是一个罔顾法纪、恣意妄为、金钱至上、随心所欲的“明星”。作为文艺工作者，以艺术作品弘扬中国文化的基本职业作用无从谈起；<a href=\"https://t-pro.huxiu.com/company/main/23\" style=\"text-decoration: none;\">阿里哈哈</a>、<a href=\"https://t-pro.huxiu.com/company/main/1\" style=\"text-decoration: none;\">jingdong</a>作为公众人物，传递核心价值观的社会职责置若罔闻；作为拥有庞大粉丝群体的“顶流”，触犯法律底线，造成极其恶劣、难以挽回的社会影响，行业难容。目前吴亦凡影视、音乐、综艺等相关作品均已下架，其本人也将接受法律制裁。测试",
        "agree_num": 5,
        "is_agree": false,
        "dateline": 1629184620,
        "img_urls": [
          {
            "uuid": "f480f3a8-ff2a-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/151552413260.gif?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/151552413260.gif",
            "origin_width": 500,
            "origin_height": 500,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/151552413260.gif?imageMogr2/thumbnail/800x800>",
            "is_gif": true,
            "tailored_width": 800,
            "tailored_height": 800
          },
          {
            "uuid": "82893a5d-ff30-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155537482102.gif?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155537482102.gif",
            "origin_width": 500,
            "origin_height": 500,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/155537482102.gif?imageMogr2/thumbnail/800x800>",
            "is_gif": true,
            "tailored_width": 800,
            "tailored_height": 800
          },
          {
            "uuid": "77a2b177-ff30-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155519890362.jpg?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155519890362.jpg",
            "origin_width": 700,
            "origin_height": 1243,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/155519890362.jpg?imageMogr2/thumbnail/450x800>",
            "is_gif": false,
            "tailored_width": 450,
            "tailored_height": 800
          },
          {
            "uuid": "7a5e7ed2-ff30-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155524162492.jpg?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155524162492.jpg",
            "origin_width": 1792,
            "origin_height": 828,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/155524162492.jpg?imageMogr2/thumbnail/800x368>",
            "is_gif": false,
            "tailored_width": 800,
            "tailored_height": 368
          },
          {
            "uuid": "7d1c2942-ff30-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155528620731.jpg?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155528620731.jpg",
            "origin_width": 720,
            "origin_height": 1280,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/155528620731.jpg?imageMogr2/thumbnail/450x800>",
            "is_gif": false,
            "tailored_width": 450,
            "tailored_height": 800
          },
          {
            "uuid": "7fa600bb-ff30-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155532446160.jpeg?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155532446160.jpeg",
            "origin_width": 1920,
            "origin_height": 1080,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/155532446160.jpeg?imageMogr2/thumbnail/800x450>",
            "is_gif": false,
            "tailored_width": 800,
            "tailored_height": 450
          },
          {
            "uuid": "854d717c-ff30-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155542366700.jpg?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155542366700.jpg",
            "origin_width": 720,
            "origin_height": 1281,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/155542366700.jpg?imageMogr2/thumbnail/448x800>",
            "is_gif": false,
            "tailored_width": 448,
            "tailored_height": 800
          },
          {
            "uuid": "8a27bd8c-ff30-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155550365385.jpg?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155550365385.jpg",
            "origin_width": 480,
            "origin_height": 321,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/155550365385.jpg?imageMogr2/thumbnail/800x534>",
            "is_gif": false,
            "tailored_width": 800,
            "tailored_height": 534
          },
          {
            "uuid": "8d05115d-ff30-11eb-a30c-00163e2cc2ab",
            "origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155555312716.jpg?watermark/3/image/aHR0cHM6Ly9pbWcuaHV4aXVjZG4uY29tL3NoYXJlL210LXdhdGVybWFyay5wbmc=/dx/0/dy/0/gravity/SouthEast/ws/0.09/wst/2",
            "none_water_origin_pic": "https://img.huxiucdn.com/test/moment/202108/17/155555312716.jpg",
            "origin_width": 1792,
            "origin_height": 828,
            "tailored_pic": "https://img.huxiucdn.com/test/moment/202108/17/155555312716.jpg?imageMogr2/thumbnail/800x368>",
            "is_gif": false,
            "tailored_width": 800,
            "tailored_height": 368
          }
        ],
        "relevant_url": "https://t-www.huxiu.com/article/398247.html"
      }
    ]
  }
        """.trimIndent()
        val entity = Gson().fromJson(json, HotSpotInterpretation::class.java)
        val result = GsonBuilder().create().toJson(entity)
        Log.i("jthou", "result : $result")
        binding.textView.text = result
    }

}