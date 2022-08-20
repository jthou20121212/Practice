package com.study.lifecycle

import android.content.Intent
import androidx.lifecycle.LifecycleService

class TestLifecycleService : LifecycleService() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

}