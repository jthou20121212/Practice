package com.utils

import com.blankj.utilcode.util.ConvertUtils
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Number 扩展
 *
 * @author jthou
 * @since 1.8.0
 * @date 13-08-2021
 */
val Number.px : Int by Delegate()
val Number.dp : Int by Delegate()

class Delegate : ReadOnlyProperty<Number, Int> {
    override fun getValue(thisRef: Number, property: KProperty<*>): Int {
        when (property.name) {
            "px" -> return ConvertUtils.px2dp(thisRef.toFloat())
            "dp" -> return ConvertUtils.dp2px(thisRef.toFloat())
        }
        throw IllegalArgumentException("invalid property: ${property.name}")
    }
}


fun <T : Number> T.toDp() = ConvertUtils.px2dp(this.toFloat())

fun <T : Number> T.toPx() = ConvertUtils.dp2px(this.toFloat())