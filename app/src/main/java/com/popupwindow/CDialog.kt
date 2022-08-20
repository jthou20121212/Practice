package com.popupwindow

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.jthou.pro.crazy.R

class CDialog(context: Context) : AlertDialog(context), View.OnClickListener, DialogInterceptor {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.attributes?.width = 800
        window?.attributes?.height = 900

        setContentView(R.layout.dialog_c)
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

    override fun intercept() {
        // super.intercept(chain)
        val isShow = true // 这里可根据实际业务场景来定制dialog 显示条件。
        if (isShow) {
            this.show()
        } else { // 当自己不具备弹出条件的时候，可以立刻把请求转交给下一个拦截器。
            DialogChain.process()
        }
    }

}