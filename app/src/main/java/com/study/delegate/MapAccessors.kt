package com.study.delegate

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MapAccessors(private val map: MutableMap<String, Any?>) {

    operator fun <V> getValue(thisRef: Any?, property: KProperty<*>): V = @Suppress("UNCHECKED_CAST") (map[property.name] as V)

    operator fun <V> setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        map[property.name] = value
    }
}

// 使用方法（其实用扩展函数语法更简洁，但考虑到编辑器不会帮我们导致自定义实现，所以故意套在 MapAccessors 内）：

private val _data = MapAccessors(HashMap<String, Any?>())

private var count: Int? by _data

val name by object : ReadOnlyProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "Peng"
    }
}

var name2 by object : ReadWriteProperty<Any?, String> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "Peng"
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    }
}

