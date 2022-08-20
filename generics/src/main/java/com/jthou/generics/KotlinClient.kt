package com.jthou.generics

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import kotlin.reflect.full.*

fun main() {

    val encode = URLEncoder.encode("夸父追日", "UTF-8")
    val decode = URLDecoder.decode(encode, "UTF-8")
    println(encode)
    println(decode)

    val c = Any::class
    for (m in c.functions) {
        println(m.name)
    }

    for (m in c.staticFunctions) {
        println(m.name)
    }

    for (m in c.memberFunctions) {
        println(m.name)
    }

    for (m in c.memberExtensionFunctions) {
        println(m.name)
    }

    for (m in c.declaredFunctions) {
        println(m.name)
    }

    for (m in c.declaredMemberFunctions) {
        println(m.name)
    }

    for (m in c.declaredMemberExtensionFunctions) {
        println(m.name)
    }

    val taskList = mutableListOf<() -> Unit>()

//    "邀酒摧肠三杯醉".log()
}