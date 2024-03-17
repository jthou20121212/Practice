package com.study.asm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Looper
import android.os.MessageQueue
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.load.resource.gif.GifDrawable

/**
 * 检测图片大小
 * 不能继承 AppCompatCustomView 需要继承 ImageView
 * 因为 AppCompatCustomView 会继承此类
 */
@SuppressLint("AppCompatCustomView")
open class MonitorImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr), MessageQueue.IdleHandler {

    companion object {

        private const val MAX_ALARM_IMAGE_SIZE = 1024 * 1024

    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        monitor()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        monitor()
    }

    private fun monitor() {
        Looper.myQueue().removeIdleHandler(this)
        Looper.myQueue().addIdleHandler(this)
    }

    override fun queueIdle(): Boolean {
        checkDrawable()
        return false
    }

    private fun checkDrawable() {
        val d = drawable ?: return
        val drawableWidth = d.intrinsicWidth
        val drawableHeight = d.intrinsicHeight
        val viewWidth = measuredWidth
        val viewHeight = measuredHeight
        val imageSize = calculateImageSize(d)
        val pageName = ActivityUtils.getActivityByContext(context).javaClass.name
        if (imageSize > MAX_ALARM_IMAGE_SIZE) {
            log(log = "$pageName ：图片大小超标 -> $imageSize")
        }
        if (drawableWidth > viewWidth || drawableHeight > viewHeight) {
            log(log = "$pageName ：图片尺寸超标 -> drawable：$drawableWidth x $drawableHeight  view：$viewWidth x $viewHeight")
        }


    }

    private fun calculateImageSize(drawable: Drawable): Int {
        return when (drawable) {
            is BitmapDrawable -> {
                drawable.bitmap.allocationByteCount
            }
            is GifDrawable -> {
                drawable.size
            }
            else -> {
                0
            }
        }
    }

    private fun log(log: String) {
        Log.e(javaClass.simpleName, log)
    }

}