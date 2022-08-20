package com.jthou.pro.crazy.handler

import java.util.concurrent.Delayed
import java.util.concurrent.TimeUnit

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 15-03-2021
 */
class KMessage : Delayed {

    companion object {
        val quitMessage = KMessage()
    }

    var target: KHandler? = null
    var what = 0
    var arg1 = 0
    var arg2 = 0
    var obj: Any? = null
    var `when`: Long = 0
    var callback: Runnable? = null
    var isAsynchronous : Boolean = false

    override fun compareTo(other: Delayed?): Int {
        return if (other is KMessage) (`when` - other.`when`).toInt()
        else (getDelay(TimeUnit.MILLISECONDS) - other?.getDelay(TimeUnit.MILLISECONDS)!!).toInt()
    }

    override fun getDelay(unit: TimeUnit): Long {
        return unit.convert(`when` - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    }

    override fun toString(): String {
        return "${super.toString()} : when = $`when` obj = $obj"
    }

}