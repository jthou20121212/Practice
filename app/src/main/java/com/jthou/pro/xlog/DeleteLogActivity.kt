package com.jthou.pro.xlog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.TimeUtils
import com.jthou.pro.crazy.R
import com.jthou.pro.xlog.XLogActivity.Companion.LOG_ALIVE_TIME
import java.util.*
import kotlin.concurrent.thread

class DeleteLogActivity : AppCompatActivity() {

    private val TAG = "DeleteLogActivity"

    val XLOG_PATH by lazy { filesDir.path + "/" + "xlog" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_log)

        deleteFile()
    }

    fun deleteFile() {
        thread {
            Log.i(TAG, ThreadUtils.isMainThread().toString())
            val instance = Calendar.getInstance()
            instance.set(Calendar.HOUR_OF_DAY, 0)
            instance.set(Calendar.MINUTE, 0)
            instance.set(Calendar.SECOND, 0)
            instance.set(Calendar.MILLISECOND, 0)
            val timeInMillis = instance.timeInMillis
            Log.i(TAG, TimeUtils.millis2String(timeInMillis, "yyyy-MM-dd HH:mm:ss"))
            val listFilesInDirWithFilter = FileUtils.listFilesInDirWithFilter(XLOG_PATH) { pathname ->
                Log.i(TAG, "文件：" + pathname.absolutePath)
                "xlog" == pathname?.extension && timeInMillis - pathname.lastModified() > LOG_ALIVE_TIME
            }
            listFilesInDirWithFilter.forEach {
                Log.i(TAG, "文件：" + it.absolutePath + " 删除结果：" + it.delete())
            }
        }
    }

}