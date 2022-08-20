package com.jthou.generics

import java.util.*

// 泛型类
class Base<T> where T : CharSequence, T : Appendable {


}

// 泛型方法
fun <T> test(num: T) where T : CharSequence, T : Appendable {
    //do something
}

fun alcohol() {
    val items = listOf("one", 2, "three")
    println(items.filterIsInstance<String>())
}

fun <T> copy(dest: MutableList<in T>, src: List<T>) {
    val srcSize = src.size
    if (srcSize > dest.size) throw IndexOutOfBoundsException("Source does not fit in dest")
    if (srcSize < 10 ||
        src is RandomAccess && dest is RandomAccess
    ) {
        for (i in 0 until srcSize) dest[i] = src[i]
    } else {
        val di = dest.listIterator()
        val si = src.listIterator()
        for (i in 0 until srcSize) {
            di.next()
            di.set(si.next())
        }
    }
}
