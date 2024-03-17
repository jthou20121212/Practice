package com.study.coordinator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.ViewCompat

class DependentView @JvmOverloads constructor(
    val mContext: Context,
    val attributeSet: AttributeSet? = null,
    val flag: Int = 0
) : View(mContext, attributeSet, flag) {

    private var paint: Paint
    private var mStartX = 0
    private var mStartY = 0

    init {
        paint = Paint()
        paint.color = Color.parseColor("#000000")
        paint.style = Paint.Style.FILL
        isClickable = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawRect(
                Rect().apply {
                    left = 200
                    top = 200
                    right = 400
                    bottom = 400
                },
                paint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("TAG", "ACTION_DOWN")
                mStartX = event.rawX.toInt()
                mStartY = event.rawY.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                Log.e("TAG", "ACTION_MOVE")

                val endX = event.rawX.toInt()
                val endY = event.rawY.toInt()
                val dx = endX - mStartX
                val dy = endY - mStartY

                ViewCompat.offsetTopAndBottom(this, dy)
                ViewCompat.offsetLeftAndRight(this, dx)
                postInvalidate()

                mStartX = endX
                mStartY = endY
            }
        }

        return super.onTouchEvent(event)
    }

}
