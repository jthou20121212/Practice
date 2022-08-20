package com.study.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 不要使用这个事件
object ListViewModel : ViewModel() {
    private val _navigateToDetails = MutableLiveData<Boolean>()

    val navigateToDetails : LiveData<Boolean>
        get() = _navigateToDetails


    fun userClicksOnButton() {
        _navigateToDetails.value = true
    }
}
