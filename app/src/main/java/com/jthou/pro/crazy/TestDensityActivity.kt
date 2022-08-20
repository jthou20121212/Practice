package com.jthou.pro.crazy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TestDensityActivity : AppCompatActivity() {
    companion object {
        fun launchActivity(context: Context) {
            val intent = Intent(context, TestDensityActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_density)
    }
}