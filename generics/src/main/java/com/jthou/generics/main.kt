package com.jthou.generics

import kotlin.reflect.KFunction1

fun greetings(message: String) {
    println("Hello, $message")
}

fun String.greetings2() {
    println("Hello, $this")
}
fun String?.greeting3() {
    println("Hello, $this")
}

fun main() {
//    val greeting1: KFunction1<String, Unit> = ::greetings
//    val greeting2: (message: String) -> Unit = ::greetings
//    val greeting3: (String) -> Unit = ::greetings

//    val greeting2 = String::greetings2
//    val greeting3 = String?::greeting3
//
//    greeting3("kotlin")
//    greeting3(null)

    val list : List<Any>? = null
    println(list.isNullOrEmpty())

}