package com.jthou.generics.kotlin;

import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty

class StringDelegate(private var s: String = "Hello") : ReadWriteProperty<Owner, String> {
    //     ①                           ②                              ③
//     ↓                            ↓                               ↓
    override operator fun getValue(thisRef: Owner, property: KProperty<*>): String {
        return s
    }
    //      ①                          ②                                     ③
//      ↓                           ↓                                      ↓
    override operator fun setValue(thisRef: Owner, property: KProperty<*>, value: String) {
        s = value
    }
}

class SmartDelegator {

    operator fun provideDelegate(
        thisRef: Owner,
        prop: KProperty<*>
    ): ReadWriteProperty<Owner, String> {

        return if (prop.name.contains("log")) {
            StringDelegate("log")
        } else {
            StringDelegate("normal")
        }
    }
}

class Owner {
    var normalText: String by SmartDelegator()
    var logText: String by SmartDelegator()
}

fun main() {
    val owner = Owner()
    println(owner.normalText)
    println(owner.logText)
}
