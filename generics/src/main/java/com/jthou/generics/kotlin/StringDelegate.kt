package com.jthou.generics.kotlin

import kotlin.reflect.KProperty

//class StringDelegate(private var s: String = "Hello") {
////     ①                           ②                              ③
////     ↓                            ↓                               ↓
//    operator fun getValue(thisRef: Owner, property: KProperty<*>): String {
//        return s
//    }
////      ①                          ②                                     ③
////      ↓                           ↓                                      ↓
//    operator fun setValue(thisRef: Owner, property: KProperty<*>, value: String) {
//            s = value
//    }
//}
//
////      ②
////      ↓
//class Owner {
////               ③
////               ↓
//    var text: String by StringDelegate()
//}