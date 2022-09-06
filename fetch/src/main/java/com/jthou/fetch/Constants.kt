package com.jthou.fetch

/**
 *
 *
 * @author jthou
 * @since 1.0.o
 * @date 01-09-2022
 */
object Constants {

    const val BYTES = "bytes"

    const val ACCEPT_RANGES = "Accept-Ranges"

    const val CONTENT_LENGTH = "Content-Length"

    const val PARTIAL_CONTENT = 206

    // 一个下载任务使用的线程数
    const val THREAD_COUNT = 25

    // 请求头 Range 是 HTTP 范围请求的专用字段，格式是“bytes=x-y”，其中的 x 和 y 是以字节为单位的数据范围。
    const val RANGE = "Range"
    const val BOUNDARY = "bytes=%d-%d"

    // 临时文件后缀
    const val TEMP_FILE_SUFFIX = ".tmp"

}