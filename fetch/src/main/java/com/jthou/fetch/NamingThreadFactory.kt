package com.jthou.fetch

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicLong

/**
 * 给线程命名
 *
 * @author jthou
 * @since 1.0.0
 * @date 05-09-2022
 */
class NamingThreadFactory constructor(private val name: String, private val delegate: ThreadFactory) : ThreadFactory {

    private val threadCount: AtomicLong = AtomicLong()

    override fun newThread(p0: Runnable): Thread {
        val newThread = delegate.newThread(p0)
        newThread.name = "[#$name-${threadCount.incrementAndGet()}#]"
        return newThread
    }

}