package com.jthou.pro.crazy.animator

import android.animation.ValueAnimator
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

fun ValueAnimator.addLifecycleObserver(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycle.addObserver(AnimatorLifecycleObserver(this))
}

class AnimatorLifecycleObserver constructor(private val animator: ValueAnimator) : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.i("jthou", "destroy")
            animator.removeAllUpdateListeners()
            source.lifecycle.removeObserver(this)
        }
    }

}