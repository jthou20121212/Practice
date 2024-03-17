package com.study.since290

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.annotation.IntRange
import com.blankj.utilcode.util.ActivityUtils
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.animator.addLifecycleObserver
import com.utils.dp
import com.utils.log

// 2024年3月1日
class IndustrySituationView : View {

    private val width = 20.dp
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectF by lazy { RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat()) }
    private val drawRectF =
        RectF(
            rectF.left + width / 2,
            rectF.top + width / 2,
            rectF.right - width / 2,
            rectF.bottom - width / 2
        )
    private val bitmap by lazy { BitmapFactory.decodeResource(resources, R.drawable.pointer) }
    private val matrix = Matrix()

    private var sweepAngle = 0
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val desiredWidth = 116.dp
        val desiredHeight = 68.dp

        // Measure Width
        val measuredWidth: Int = when (widthMode) {
            MeasureSpec.EXACTLY -> {
                // Must be this size
                widthSize
            }

            MeasureSpec.AT_MOST -> {
                // Can't be bigger than...
                desiredWidth.coerceAtMost(widthSize)
            }

            else -> {
                // Be whatever you want
                desiredWidth
            }
        }

        // Measure Height
        val measuredHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> {
                // Must be this size
                heightSize;
            }

            MeasureSpec.AT_MOST -> {
                // Can't be bigger than...
                desiredHeight.coerceAtMost(heightSize);
            }

            else -> {
                // Be whatever you want
                desiredHeight;
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 刚开始不绘制
        if (sweepAngle == 0) return
        // canvas?.drawColor(Color.GREEN)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = width.toFloat()
        // paint.color = Color.BLUE
        rectF.set(0f, 0f, measuredWidth.toFloat(), (measuredHeight.toFloat() - 10.dp) * 2)
        // canvas?.drawRect(rectF, paint)
        paint.style = Paint.Style.STROKE
        // 绘制区域要考虑画笔宽度
        drawRectF.set(
            rectF.left + width / 2,
            rectF.top + width / 2,
            rectF.right - width / 2,
            rectF.bottom - width / 2
        )
        paint.color = Color.WHITE
        canvas?.drawArc(drawRectF, -180f, 180f, false, paint)
        paint.color = Color.RED
        // 开始角度是 3 点钟，正值顺时针负值逆时针方向绘制
        canvas?.drawArc(drawRectF, -180f, sweepAngle * 180f / 100, false, paint)

        val centerX = rectF.left + (rectF.right - rectF.left) / 2f
        val centerY = rectF.top + (rectF.bottom - rectF.top) / 2f
        val left = centerX - bitmap.width / 2
        val top = centerY - bitmap.height
        matrix.setTranslate(left, top)
        val angle = sweepAngle * 180f / 100
        "angle : $angle".log()
        matrix.postRotate(angle - 90, centerX, centerY)
        canvas?.drawBitmap(bitmap, matrix, paint)
    }

    fun setIndustrySituation(
        @IntRange(from = 0, to = 100) lastSituation: Int,
        @IntRange(from = 0, to = 100) currentSituation: Int
    ) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            setIndustrySituationInternal(lastSituation, currentSituation)
        } else {
            post {
                setIndustrySituationInternal(lastSituation, currentSituation)
            }
        }
    }

    private fun setIndustrySituationInternal(
        @IntRange(from = 0, to = 100) lastSituation: Int,
        @IntRange(from = 0, to = 100) currentSituation: Int
    ) {
        val animator = ObjectAnimator.ofInt(this, "sweepAngle", lastSituation, currentSituation)
        animator.duration = 3000
        animator.interpolator = DecelerateInterpolator()
        (ActivityUtils.getActivityByContext(context) as? ComponentActivity)?.let {
            animator.addLifecycleObserver(it)
        }
        animator.start()
    }

}