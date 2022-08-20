package com.study.savedstate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class SavedStateViewModel constructor(private val handle: SavedStateHandle) : ViewModel() {

    companion object {
        const val KEY = "com.study.savedstate.SavedStateViewModel"
    }

    fun setValue(value: String) {
        handle[KEY] = value
    }

    fun getValue(): String? {
        return handle.get<String>(KEY)
    }

    fun getLiveData(): MutableLiveData<String> {
        return handle.getLiveData(KEY)
    }

    fun getStateFlow(): StateFlow<Nothing?> {
        return handle.getStateFlow(KEY, null)
    }

}