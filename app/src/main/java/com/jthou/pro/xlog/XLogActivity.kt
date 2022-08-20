package com.jthou.pro.xlog

import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.os.Process
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.BuildConfig
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Log.setConsoleLogOpen
import com.tencent.mars.xlog.Xlog


/**
 *
 *
 * @author jthou
 * @since
 * @date 11-03-2022
 */
class XLogActivity : AppCompatActivity() {

    companion object {
        const val LOG_ALIVE_TIME: Long = 2 * 24 * 60 * 60 * 1000

        init {
            System.loadLibrary("c++_shared")
            System.loadLibrary("marsxlog")

            // Log.setLogImp(Xlog());

            android.util.Log.i("XLog", "init")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            text = "邀酒摧肠三杯醉"
            textSize = 20f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        setContentView(textView)

        val logPath = filesDir.path + "/xlog"
        val logFileName = "mars-xlog"

        val xLog = Xlog()
        xLog.setMaxAliveTime(0, LOG_ALIVE_TIME)
        Log.setLogImp(xLog)
        if (BuildConfig.DEBUG) {
            Log.setConsoleLogOpen(true)
            Log.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, logFileName, 0);
        } else {
            Log.setConsoleLogOpen(false);
            Log.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, "", logPath, logFileName, 0);
        }
        Log.getImpl().setMaxAliveTime(0, LOG_ALIVE_TIME)

        Log.f("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
        Log.e("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
        Log.w("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
        Log.i("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
        Log.d("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")

        Log.f("XLog", "月落乌啼霜满天")
        Log.f("XLog", "恰似一江春水向东流")

        Log.appenderFlushSync(false)
        // Log.appenderClose()

        android.util.Log.i("XLog", "同步写入结束")

//        Log.setLogImp(Xlog())
//        val xLog = Log.getImpl()
//        val xLogInstance = if (BuildConfig.DEBUG) {
//            setConsoleLogOpen(true)
//            xLog.openLogInstance(Xlog.LEVEL_DEBUG, Xlog.AppednerModeSync, "", logPath, logFileName, 0)
//        } else {
//            setConsoleLogOpen(false)
//            xLog.openLogInstance(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, "", logPath, logFileName, 0);
//        }
//        xLog.setMaxAliveTime(xLogInstance, LOG_ALIVE_TIME)
//        xLog.logE(xLogInstance,
//            "XLog",
//            "",
//            "",
//            0,
//            Process.myPid(),
//            Thread.currentThread().id,
//            Looper.getMainLooper().thread.id,
//            "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
//
////        Log.f("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
////        Log.e("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
////        Log.w("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
////        Log.i("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
////        Log.d("XLog", "Mars is a cross-platform infrastructure component developed by WeChat Mobile Team. It is proved to be effective by billions of WeChat users.")
////
////        Log.f("XLog", "月落乌啼霜满天")
////        Log.f("XLog", "恰似一江春水向东流")
//////        val string = "URL:https://t-log-api-ms.huxiu.com/userBehavior/heartbeat?appVersion=7.13.13&os=11&phoneModel=Mi%2010&udid=44F9AD294BA34515427526F4A35B1C62&udidNew=44F9AD294BA34515427526F4A35B1C62&source=yingyongbao&networkType=2&platform=Android&screenHeight=2340&screenWidth=1080&lat=&lon=&radius=&umengToken=AmXBmrYb6-2fer-hrLbDM7ptd7KUI9EQ317xRkEMH8snMethod:POSTProtocol:http/1.1User-Agent:huxiu/androidContent-Type:application/json; charset=utf-8Content-Length:191RequestStartTime:2022-02-24 15:29:37RequestEndTime:2022-02-24 15:29:37ResponseStartTime:2022-02-24 15:29:37ResposseEndTime:2022-02-24 15:29:37Duration:11msForm-Data:xxx=yyy&xxx=yyyCode:200Message:xxxxSuccess:falseErrorCode:xxxErrorMessage:xxx"
//////        Log.i("XLog", string)
//
//        Log.appenderFlushSync(true)
//        // Log.appenderClose()
//
//        android.util.Log.i("XLog", "同步写入结束")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.appenderClose()
    }

    private fun getDiskCacheDir(): String? {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            if (externalCacheDir == null) {
                cacheDir.path
            } else {
                externalCacheDir!!.path
            }
        } else {
            cacheDir.path
        }
    }

}