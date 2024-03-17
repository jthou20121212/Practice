package com.jthou.pro.crazy

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup

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

        // 获取指定类型的子控件
        fun findChild(view: View, viewClass: Class<out View?>): View? {
            if (viewClass.isAssignableFrom(view.javaClass)) {
                return view
            } else if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val childAt = view.getChildAt(i)
                    val findChild = findChild(childAt, viewClass)
                    if (findChild != null) {
                        return findChild
                    }
                }
            }
            return null
        }

        // 获取指定类型的父控件
        fun <T : View?> findParent(view: View, viewClass: Class<T>): T {
            var parent = view.parent
            while (parent is View) {
                if (viewClass.isAssignableFrom(parent.javaClass)) {
                    return viewClass.cast(parent)
                }
                parent = parent.getParent()
            }
            return viewClass.cast(view)
        }

        fun expandTouchArea(bigView: View, smallView: View, extraPadding: Int) {
            bigView.post {
                val rect = Rect()
                smallView.getHitRect(rect)
                rect.top -= extraPadding
                rect.left -= extraPadding
                rect.right += extraPadding
                rect.bottom += extraPadding
                bigView.touchDelegate = TouchDelegate(rect, smallView)
            }
        }


        // 返回 view 是否可见
        // 因为可能是在事件分发中频繁调用所以让外部传入避免重复创建对象
        fun isShown(view: View, rect: Rect?): Boolean {
            return view.hasWindowFocus()
                    && view.getGlobalVisibleRect(rect)
                    && view.visibility == View.VISIBLE && view.isShown
        }

    }

}