package com.jthou.pro.crazy

import android.app.Activity

object MemberLeakManager {

    val activityList = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityList.remove(activity)
    }

}