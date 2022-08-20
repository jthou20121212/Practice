package com.study.delegate

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 06-03-2022
 */
interface Base {

    fun print()

}

class BaseImpl(val x: Int) : Base {
    override fun print() {
        print(x)
    }
}

class Derived(b: Base) : Base by b

fun main(args: Array<String>) {
    val b = BaseImpl(10)
    Derived(b).print()
}

