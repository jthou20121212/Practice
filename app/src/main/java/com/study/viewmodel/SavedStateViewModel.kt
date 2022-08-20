package com.study.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jthou.pro.crazy.R

/**
 * @Author: leavesCZY
 * @Github：https://github.com/leavesCZY
 */
class SavedStateViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {

        private const val KEY_NAME = "keyName"

    }

    val nameLiveData = savedStateHandle.getLiveData<String>(KEY_NAME)

    val blogLiveData = MutableLiveData<String>()

}