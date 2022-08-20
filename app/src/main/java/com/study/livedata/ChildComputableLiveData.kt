package com.study.livedata

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.lifecycle.ComputableLiveData

@SuppressLint("RestrictedApi")
class ChildComputableLiveData : ComputableLiveData<Bitmap>() {
    override fun compute(): Bitmap {
        TODO("Not yet implemented")
    }

}