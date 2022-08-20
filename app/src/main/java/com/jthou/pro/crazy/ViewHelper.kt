package com.jthou.fuckyou

import android.view.View

class ViewHelper {

    companion object {

        fun setTranslationX(translationX: Float, vararg views: View?) {
            views.forEach {
                it?.translationX = translationX
            }
        }

        fun setTranslationY(translationY: Float, vararg views: View?) {
            views.forEach {
                it?.translationY = translationY
            }
        }

        fun setVisibility(visibility: Int, vararg views: View?) {
            views.forEach {
                it?.visibility = visibility
            }
        }

    }

}