package com.jthou.generics;

import kotlin.reflect.KParameter
import kotlin.reflect.full.memberFunctions

class Greeter {
    fun greet(param: String) {
        val c = Greeter::class;
        for (m in c.memberFunctions) {
            if (m.name == "greet") {
                val p: List<KParameter> = m.parameters
                p.forEach {
                    println(it.name)
                    println(it.kind.toString())
                    println(it.type.toString())
                }
            }
        }
//        println("Hello, $name");
    }
}


fun main(args: Array<String>) {

    val value = "UserName"
    Greeter().greet(value)

}