package com.jthou.generics

import kotlin.reflect.KFunction1
import kotlin.reflect.full.*

fun Any?.log() {

    println(this?.toString() ?: "null")

    val c = Any::class;
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


//    // extension receiver parameter of fun kotlin.Any?.log(): kotlin.Unit
//    for (p in Any::log.parameters) {
//        println(p?.name)
//    }
//
//    val kFunction1: KFunction1<Any, Unit> = Any::log
//    for (p in kFunction1.typeParameters) {
//        println(p?.name)
//    }
//
//    for (p in kFunction1.valueParameters) {
//        println(p?.name)
//    }
//
//    println(kFunction1.instanceParameter?.name)
//    println(kFunction1.extensionReceiverParameter?.name)
//
//    this?.run {
//        val findParameterByName = kFunction1.findParameterByName(toString())
//        println(findParameterByName?.name)
//    }

}