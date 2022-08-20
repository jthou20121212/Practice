package com.study

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.WindowManager.BadTokenException
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.jthou.pro.crazy.R
import java.util.concurrent.TimeUnit

/**
 * 测试 7.1.1 版本 Toast WindowManagerBadTokenException
 *
 * @author jthou
 * @since 1.0.0
 * @date 28-12-2021
 */
class ToastWindowManagerBadTokenExceptionActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ToastWindowManagerBadTo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast_window_manager_bad_token_exception)

        Toast.makeText(this, "测试 Toast Exception", Toast.LENGTH_SHORT).apply {
            fixWindowManagerBadTokenExceptionIn(this)
        }.show()

        TimeUnit.SECONDS.sleep(2)

        LogUtils.i(TAG, "休眠后打印")
    }

    // 修复 Android API 25 上的 android.view.WindowManager$BadTokenException
    private fun fixWindowManagerBadTokenExceptionIn(toast: Toast) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.N_MR1) {
            return
        }

        try {
            val mTnField = Toast::class.java.getDeclaredField("mTN")
            mTnField.isAccessible = true
            val mTn = mTnField.get(toast)

            val mHandlerField = mTn.javaClass.getDeclaredField("mHandler")
            mHandlerField.isAccessible = true
            val mHandler = mHandlerField.get(mTn) as? Handler ?: return

            mHandlerField.set(mTn, SafelyHandlerWrapper(mHandler))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private class SafelyHandlerWrapper(private val mDelegate: Handler) : Handler() {
        override fun dispatchMessage(msg: Message) {
            try {
                mDelegate.dispatchMessage(msg)
            } catch (e: BadTokenException) {
                e.printStackTrace()
            }
        }
    }

}