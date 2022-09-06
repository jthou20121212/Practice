package com.jthou.fetch

import java.io.File

/**
 * 下载监听
 *
 * @author jthou
 * @since 1.0.0
 * @date 01-09-2022
 */
interface DownloadListener {

    /**
     * @param supported 是否支持范围请求
     * 支持的话会多线程下载不支持单线程完整下载
     */
    fun supportRangeRequest(supported: Boolean)

    /**
     * @param file 文件下载完成对应的文件
     */
    fun downloadCompleted(file: File)

    /**
     * @param e 下载失败对应的异常
     */
    fun downloadFailure(e: Exception)

    /**
     * @param progress 下载进度百分比 0-100
     */
    fun downloadProgress(progress: Int)

    open class SimpleDownloadListener : DownloadListener {

        override fun supportRangeRequest(supported: Boolean) {

        }

        override fun downloadCompleted(file: File) {

        }

        override fun downloadFailure(e: Exception) {

        }

        override fun downloadProgress(progress: Int) {

        }

    }

}