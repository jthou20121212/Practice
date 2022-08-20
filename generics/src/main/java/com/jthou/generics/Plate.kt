package com.jthou.generics

class Plate<T>(private val t: T) {

    fun cut() {
        println(t.toString())
    }

}

class Apple

class Banana

fun main() {
    val plateApple = Plate(Apple())
    // 泛型类型自动推导
    val plateBanana = Plate(Banana())
    plateApple.cut()
    plateBanana.cut()
}
