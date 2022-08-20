package com.jthou.pro.crazy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class KotlinTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val userLiveData1 = MutableLiveData<String>()
        val userLiveData2 = MutableLiveData<String>()
        val userMediatorLiveData = MediatorLiveData<String>()
        userMediatorLiveData.addSource(userLiveData1) { user -> userMediatorLiveData.value = user }
        userMediatorLiveData.addSource(userLiveData2) { user -> userMediatorLiveData.value = user }
        userMediatorLiveData.observe(this) {

        }


    }
}