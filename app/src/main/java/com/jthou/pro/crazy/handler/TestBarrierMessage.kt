package com.jthou.pro.crazy.handler

import android.os.Looper
import android.os.MessageQueue

class TestBarrierMessage {

    fun addBarrier() {
        Looper.prepare()
        val method = MessageQueue::class.java.getMethod("postSyncBarrier")
        method.isAccessible = true
        val barrierToken = method.invoke(Looper.myLooper()?.queue)
        removeBarrier(barrierToken)
    }


    fun removeBarrier(barrierToken: Any?   ) {
        Looper.prepare()
        val method = MessageQueue::class.java.getMethod("removeSyncBarrier")
        method.isAccessible = true
        method.invoke(Looper.myLooper()?.queue, barrierToken)
    }

}