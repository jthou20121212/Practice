package com.jthou.pro.crazy.handler

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 15-03-2021
 */
class KLooper {

    var mQueue: KMessageQueue? = null

    init {
        mQueue = KMessageQueue()
    }

    companion object {
        private val THREAD_LOCAL = ThreadLocal<KLooper>()

        fun prepare() {
            if (THREAD_LOCAL.get() != null) {
                throw RuntimeException("Only one Looper may be created per thread")
            }
            THREAD_LOCAL.set(KLooper())
        }

        fun myLooper() : KLooper? {
            return THREAD_LOCAL.get()
        }

        fun loop() {
            val me = myLooper()
                ?: throw java.lang.RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.")
            val queue: KMessageQueue = me.mQueue ?: return
            while (true) {
                val msg: KMessage = queue.next() ?: return
                msg.target?.dispatchMessage(msg)
            }
        }
    }

    fun quit() {
        mQueue?.quit()
    }

}