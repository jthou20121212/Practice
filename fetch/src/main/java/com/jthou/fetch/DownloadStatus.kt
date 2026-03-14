package com.jthou.fetch

import java.io.File

sealed class DownloadStatus {
    object Idle: DownloadStatus()
    data class Started(val supportRange: Boolean): DownloadStatus()
    data class Progress(val percent: Int): DownloadStatus()
    data class Completed(val file: File): DownloadStatus()
    data class Error(val throwable: Throwable): DownloadStatus()
}
