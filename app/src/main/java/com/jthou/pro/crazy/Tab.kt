package com.jthou.fuckyou

import com.flyco.tablayout.listener.CustomTabEntity

data class Tab(val title: String) : CustomTabEntity {

    override fun getTabUnselectedIcon(): Int {
        return 0
    }

    override fun getTabSelectedIcon(): Int {
        return 0
    }

    override fun getTabTitle(): String {
        return title
    }

}