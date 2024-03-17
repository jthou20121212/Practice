package com.jthou.pro.crazy.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blankj.utilcode.util.ActivityUtils

class Test {

    fun launchService() {
        val activity = ActivityUtils.getTopActivity()
        val intent = Intent(activity, TestService::class.java)
        activity.startService(intent)
    }

}

class TestService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

}