package com.popupwindow

/**
 *
 *
 * @author jthou
 * @since 1.9.6
 * @date 28-12-2021
 */
object DialogChain {

    private var index: Int = 0

    private var isOpenLog: Boolean = false

    private var interceptors: MutableList<DialogInterceptor> = mutableListOf()

    // 执行拦截器。
    fun process() {
        if (interceptors.isEmpty()) return
        when (index) {
            in interceptors.indices -> {
                val interceptor = interceptors[index]
                index++
                interceptor.intercept()
            }
            // 最后一个弹窗关闭的时候，我们希望释放所有弹窗引用。
            interceptors.size -> {
                clearAllInterceptors()
            }
        }
    }

    private fun clearAllInterceptors() {
        interceptors.clear()
    }

    // 添加一个拦截器。
    fun addInterceptor(interceptor: DialogInterceptor): DialogChain {
        if (!interceptors.contains(interceptor)) {
            interceptors.add(interceptor)
        }
        return this
    }

}