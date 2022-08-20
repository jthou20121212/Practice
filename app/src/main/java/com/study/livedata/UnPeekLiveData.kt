package com.study.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.Objects
import java.util.concurrent.atomic.AtomicInteger

/**
 * 粘性事件的问题修复
 * 重写observer onChange判断版本号-推荐的版本
 */
class UnPeekLiveData<T> : MutableLiveData<T>() {

    private val currentVersion = AtomicInteger(START_VERSION)

    companion object {
        private const val START_VERSION = -1
    }

    /**
     * 非粘性事件
     */
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserveWrapper(observer, currentVersion.get()))
    }

    /**
     * 粘性事件
     */
    fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, ObserveWrapper(observer))
    }

    override fun setValue(value: T) {
        currentVersion.getAndIncrement()
        super.setValue(value)
    }

    inner class ObserveWrapper(
        private val mObserver: Observer<in T>,
        private val mVersion: Int = START_VERSION
    ) : Observer<T> {

        override fun onChanged(t: T) {
            if (currentVersion.get() > mVersion && t != null) {
                mObserver.onChanged(t)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other == null || javaClass != other::class) {
                return false
            }
            val that = other as UnPeekLiveData<*>.ObserveWrapper
            return mObserver == that.mObserver
        }

        override fun hashCode(): Int {
            return Objects.hash(mObserver)
        }

    }
}