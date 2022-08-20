package com.jthou.pro.crazy.handler


/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 15-03-2021
 */
open class KHandler(
    private var looper: KLooper? = null,
    private var callback: Callback? = null,
    private var async: Boolean = false
) {

    var mQueue: KMessageQueue? = null

    init {
        mQueue = looper?.mQueue ?: KLooper.myLooper()?.mQueue
    }

    interface Callback {
        fun handleMessage(msg: KMessage): Boolean
    }

    fun dispatchMessage(msg: KMessage) {
        if (msg.callback != null) {
            msg.callback?.run()
        } else {
            if (callback != null) {
                if (callback!!.handleMessage(msg)) {
                    return
                }
            }
            handleMessage(msg)
        }
    }

    open fun handleMessage(msg: KMessage) {

    }

    fun sendMessage(msg: KMessage): Boolean {
        msg.target = this
        return sendMessageDelayed(msg, 0)
    }

    fun getLooper(): KLooper? = looper

    fun sendMessageDelayed(msg: KMessage, delayMillis: Long): Boolean {
        return sendMessageAtTime(msg, System.currentTimeMillis() + delayMillis)
    }

    private fun sendMessageAtTime(msg: KMessage, uptimeMillis: Long): Boolean {
        val queue: KMessageQueue? = mQueue
        if (queue == null) {
            val e =
                RuntimeException("$this sendMessageAtTime() called with no mQueue")
            println(e)
            return false
        }
        return enqueueMessage(queue, msg, uptimeMillis)
    }

    private fun enqueueMessage(
        queue: KMessageQueue, msg: KMessage,
        uptimeMillis: Long
    ): Boolean {
        msg.target = this
        msg.`when` = uptimeMillis
        if (async) {
            msg.isAsynchronous = true
        }
        return queue.enqueueMessage(msg)
    }

}