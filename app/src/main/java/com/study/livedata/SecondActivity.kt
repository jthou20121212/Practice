package com.study.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.jthou.pro.crazy.R
import com.utils.log

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        ListViewModel.navigateToDetails.observe(this) {
            javaClass.name.log()
        }
        findViewById<Button>(R.id.smwy).setOnClickListener {
            ListViewModel.userClicksOnButton()
        }
    }

}