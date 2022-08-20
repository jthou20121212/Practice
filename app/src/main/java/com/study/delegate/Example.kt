package com.study.delegate;

//import kotlin.reflect.KProperty
//
//class Example {
//    // 被委托属性
//    var prop: String by Delegate() // 基础对象
//}
//
//// 基础类
//class Delegate {
//    private var _realValue: String = "彭"
//
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
//        println("getValue")
//        return _realValue
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
//        println("setValue")
//        _realValue = value
//    }
//}
//
//fun main(args: Array<String>) {
//    val e = Example()
//    println(e.prop)    // 最终调用 Delegate#getValue()
//    e.prop = "Peng"    // 最终调用 Delegate#setValue()
//    println(e.prop)    // 最终调用 Delegate#getValue()
//}

val lazyValue: String by lazy {
    println("Lazy Init Completed!")
    "Hello World."
}

fun main(args: Array<String>) {
    println(lazyValue) // 首次调用
    println(lazyValue) // 后续调用
}
