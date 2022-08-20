package com.popupwindow

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.jthou.pro.crazy.R

class BDialog(context: Context) : AlertDialog(context), View.OnClickListener, DialogInterceptor {
    private var data: String? = null

    //  注释1：这里注意：intercept(chain: DialogChain)方法与 onDataCallback(data: String)方法被调用的先后顺序是不确定的
    fun onDataCallback(data: String) {
        this.data = data
        tryToShow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.attributes?.width = 800
        window?.attributes?.height = 900

        setContentView(R.layout.dialog_b)
        findViewById<View>(R.id.tv_confirm)?.setOnClickListener(this)
        findViewById<View>(R.id.tv_cancel)?.setOnClickListener(this)
        // 弹窗消失时把请求移交给下一个拦截器。
        setOnDismissListener {
            DialogChain.process()
        }
    }

    override fun onClick(p0: View?) {
        dismiss()
    }
   // 注释2 这里注意：intercept(chain: DialogChain)方法与 onDataCallback(data: String)方法被调用的先后顺序是不确定的
    override fun intercept() {
        // super.intercept(chain)
        tryToShow()
    }

    private fun tryToShow() {
        // 只有同时满足这俩条件才能弹出来。
        if (data != null) {
            this.show()
        }
    }
}