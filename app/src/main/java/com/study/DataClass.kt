package com.study

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.study.delegate.name
import java.io.Serializable
import java.util.*

/**
 * since 1.7.6 data class
 *
 * @author jthou
 * @since 1.0.0
 * @date 26-07-2021
 */

// since 1.7.6 搜索页面的数据
class SearchArticleWrapper : Serializable {
    var label: String? = null
    val data: List<SearchArticle>? = null
}

data class SearchArticle(
    val sub_type: Int,
    val sub_type_name: String,
    val publish_time: String,
    val route_url: String,
    val ftime: String,
)

// since 1.7.6 接口返回的展示文案
data class GuideConfig(
    val app_search: String,
    val app_login: String?,
    val app_mine: String
) : Serializable

// since 1.8.0 热点解读-非会员文案
class Mask(
    val text: String,
    val bottom_text: String,
    val is_reg_experience: Boolean
) : Serializable

// since 1.8.0 热点解读-分享信息
data class ShareInfo(
    val is_today_share: Boolean,
    val is_month_limit: Boolean,
    val login_share_title: String?,
    val logout_share_title: String?,
    val share_category: String?,

    // since 1.8.6 热点解读分享信息
    @SerializedName("share_title")
    val shareTitle: String?,
    @SerializedName("share_desc")
    val shareDesc: String?,
    @SerializedName("share_url")
    val shareUrl: String?,
    @SerializedName("share_img")
    val shareImg: String?
) : Serializable

data class Company(val id: String, val name: String)

// since 1.8.0 热点解读
data class HotSpotInterpretation(
    val id: String,
    val title: String,
    val summary: String,
    @SerializedName("bg_img_url")
    val imageUrl: String,
    val share_info: ShareInfo?,
    val companies: ArrayList<Company>?,
    val view_points: ArrayList<InterpretationView>?,
    val mask: Mask?,
    @SerializedName("is_favorite")
    var isFavorite: Boolean?,

    // 以下字段为列表中使用的字段
    val publish_time: Long,
    val publish_date: String?,
    var dateTitle: String?,
    var isShowDate: Boolean,

    // 以下字段为分享卡使用的观点
    var sharedViewPoint: InterpretationView?
) : Serializable

// since 1.8.0 观点
data class InterpretationView(
    val id: String,
    val content: String,
    @SerializedName("relevant_url")
    val originUrl: String?,
    val dateline: Long,
    var is_agree: Boolean,
    @SerializedName("agree_num")
    var agreeNum: Int,
    var isExpand: Boolean
)

// since 1.8.0 分享结果
class ShareResult(
    val code: Int,              // 1 没有获得奖励 2获得奖励
    val total: Int,             // 每月最多获得X天
    val every: String,          // 当前获得X天
    val message: String,        // 给用户的提示信息
    val remaining_day: String   // 剩余获得X天
) : Serializable

// since 1.8.2
class MemberRecommendRead(
    val message: String,
    // 1首次购买 2重复购买
    val code: Int,
    // 小虎哥二维码（已经在会员信息接口里返回使用了这里没有使用）
    val kefu_qrcode: String,
    // 文章列表
    val article_list: List<SearchArticle>
) : Serializable {

    companion object {
        // 1首次购买 2重复购买
        const val FIRST_PURCHASE = 1
        const val IGNORE = 2
    }
}

// since 1.8.2 index/setting 接口返回的付费引导浮窗文案
data class VipButtonCopyWriter(
    val logout: String?,
    val login: String?,
    val default: String?
) : Serializable

// since 1.8.6 用户购买引导
// 有付费历史 & 无付费历史

// 体验有礼&会员特价
class BuyGuideSkuInfo(
    val log_id: String,
    var count_down: Long,
    val end_time: Long,
    val sku_id: String,
    val original_price: String,
    val sku_price: String,
    val title: String,
    val describe: String
) : Serializable

// 福利邀请&用户回访
class BuyGuideOther(
    val title: String,
    val url: String?,
    val describe: String
) : Serializable

class UserBuyGuideEntity(
    val sku_info: BuyGuideSkuInfo?,
    val other: BuyGuideOther?
)

class User(

) {


    var userName: String? = ""
    var age: Int = 0
    var marriage: Boolean = true

    override fun toString(): String {
        return "User(userName='$userName', age=$age, marriage=$marriage)"
    }
}