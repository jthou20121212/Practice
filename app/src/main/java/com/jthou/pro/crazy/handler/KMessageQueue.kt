package com.jthou.pro.crazy.handler

import java.util.concurrent.DelayQueue

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 15-03-2021
 */
class KMessageQueue {

    @Volatile private var mQuitting = false

    private val queue: DelayQueue<KMessage> = DelayQueue()

    fun enqueueMessage(msg: KMessage): Boolean {
        return if (mQuitting) {
            false
        } else {
            queue.add(msg)
        }

//        return queue.add(msg)
    }

    fun next(): KMessage? {
        return if (mQuitting) {
            null
        } else try {
            val msg = queue.take()
            if (msg == KMessage.quitMessage) {
                null
            } else {
                msg
            }
        } catch (e: InterruptedException) {
            null
        }
    }

    fun quit() {
        if (mQuitting) {
            return
        }
        mQuitting = true
        queue.clear()
        queue.add(KMessage.quitMessage)
    }

}