package com.study.livedata

import androidx.lifecycle.LiveData

class ChildLiveData<T> : LiveData<T>() {

    override fun onActive() {
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
    }

}