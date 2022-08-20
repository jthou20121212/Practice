package com.popupwindow

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.jthou.pro.crazy.R

class PopupWindowActivity : AppCompatActivity() {

//    private lateinit var dialogChain: DialogChain

    private val bDialog by lazy { BDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_window)
//        DialogChain.openLog(true)
        createDialogChain() //创建 DialogChain
        // 模拟延迟数据回调。
        Handler().postDelayed({
            bDialog.onDataCallback("延迟数据回来了！！")
        },10000)
    }

   //创建 DialogChain
    private fun createDialogChain() {
       DialogChain
            .addInterceptor(ADialog(this))
            .addInterceptor(bDialog)
            .addInterceptor(CDialog(this))
           .process()
    }

}