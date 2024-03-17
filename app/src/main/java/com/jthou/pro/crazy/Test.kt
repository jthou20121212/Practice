package com.jthou.pro.crazy

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.pow
import kotlin.properties.Delegates
import kotlin.random.Random.Default.nextInt


data class Value(val title: String, val sort: Int)

// var value : Long by Delegate()

private var helloMessage : String = "Hello"


fun main() {

    val randomInt1 = nextInt(1000000000)
    val randomInt2 = nextInt(10000)

    println(randomInt1 % randomInt2)
    println(randomInt1 and randomInt2 - 1)
    println(randomInt1.and(randomInt2 - 1))


//    val list = arrayListOf<Int>(1, 2, 3, 4, 5)
//    list.forEach {
//        if (it == 6) {
//            println("6")
//            return
//        }
//    }
//    println("end")
//
////    val handler = Handler(null, null, true)
//
//    val json = """{
//    "banner":{
//        "title":"banner图",
//        "sort":"1"
//    },
//    "category":{
//        "title":"深度分类",
//        "sort":"2"
//    }
//}"""
//
//    val map: Map<String, Value> = GsonUtils.fromJson<Map<String, Value>>(json, object : TypeToken<Map<String?, Value?>?>() {}.type)
//    println(map)
//
//    val a = 5
//    val b = 8
//    val result1 = a or b
//    val result2 = a and b
////    println(result)





    println(Integer.toBinaryString(2.toFloat().pow(30).toInt()).length)




}
