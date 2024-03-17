package com.jthou.pro.crazy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs


class RightScrollActivity : AppCompatActivity() {
    
    companion object {
        // 最小距离 
        private const val FLING_MIN_DISTANCE = 50
        // 最小速度
        private const val FLING_MIN_VELOCITY = 0

        fun launchActivity(context: Context) {
            val intent = Intent(context, RightScrollActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var mGestureDetector : GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_right_scroll)
        mGestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val x = e1.x - e2.x
                val x2 = e2.x - e1.x
                if (x > FLING_MIN_DISTANCE && abs(velocityX) > FLING_MIN_VELOCITY) {
                    Toast.makeText(this@RightScrollActivity, "向左手势", Toast.LENGTH_SHORT).show()
                } else if (x2 > FLING_MIN_DISTANCE && abs(velocityX) > FLING_MIN_VELOCITY) {
                    Toast.makeText(this@RightScrollActivity, "向右手势", Toast.LENGTH_SHORT).show()
                }
                return false
            }
        })
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }

}