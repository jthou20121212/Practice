package com.study.blur

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.blankj.utilcode.util.ScreenUtils
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.ActivityImageBlurBinding
import com.study.viewbinding.viewBinding
import com.utils.bitmap
import com.utils.dp
import com.utils.drawable
import com.utils.log
import kotlin.math.roundToInt

class ImageBlurActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityImageBlurBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val size = ScreenUtils.getScreenWidth()
        // 纯颜色
        val bitmap = createBitmapFromColor(Color.BLACK, size, size)
        // shape drawable 带圆角
        // val bitmap = R.drawable.shape_black.drawable()?.toBitmap(size - 100.dp, size - 100.dp) ?: return
        // val bitmap = R.drawable.luffy.drawable()?.toBitmap() ?: return
        if (Build.VERSION.SDK_INT >= 31) {
//            // 一种方式，无法模糊纯颜色
//             binding.imageView.setImageBitmap(bitmap)
//             binding.imageView.setRenderEffect(RenderEffect.createBlurEffect(10.dp.toFloat(), 10.dp.toFloat(), Shader.TileMode.CLAMP))

            // 另一种方式
            val radius = size / 2f - 50.dp
            val blurBitmap = blurBitmapUsingCanvas(bitmap, 10.dp.toFloat())
            binding.imageView.setImageBitmap(blurBitmap)
        } else {
            val blurBitmap = getBlurBitmap(3.dp, bitmap)
            binding.imageView.setImageBitmap(blurBitmap)
        }
    }

    private fun getBlurBitmap(radius: Int, bitmap: Bitmap): Bitmap {
        val renderScript = RenderScript.create(this)
        val input: Allocation = Allocation.createFromBitmap(renderScript, bitmap)
        val output: Allocation = Allocation.createTyped(renderScript, input.type)
        val scriptIntrinsicBlur: ScriptIntrinsicBlur =
            ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        scriptIntrinsicBlur.setRadius(radius.toFloat())
        scriptIntrinsicBlur.setInput(input)
        scriptIntrinsicBlur.forEach(output)
        output.copyTo(bitmap)
        return bitmap
    }

    /**
     * 根据颜色生成 Bitmap
     * @param color 颜色值，可以使用 Color 类的常量或十六进制值
     * @param width Bitmap 的宽度
     * @param height Bitmap 的高度
     * @return 创建的 Bitmap 对象
     */
    fun createBitmapFromColor(color: Int, width: Int, height: Int): Bitmap {
        // 创建一个空的 Bitmap
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // 创建一个画布，将 Bitmap 作为背景
        val canvas = Canvas(bitmap)

        // 设置画笔颜色
        val paint = Paint()
        paint.color = color

        // 填充整个画布
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        return bitmap
    }

    fun blurBitmapUsingCanvas(bitmap: Bitmap, radius: Float): Bitmap {
        "radius = $radius".log()
        val width = bitmap.width
        val height = bitmap.height

        // 创建一个新的 Bitmap
        val blurredBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // 创建 Canvas 对象
        val canvas = Canvas(blurredBitmap)
        val paint = Paint()

        // 这里使用的 Paint 可以设定模糊效果
        paint.isAntiAlias = true
        paint.setMaskFilter(BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL))

        // 使用 Canvas 绘制模糊效果
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        return blurredBitmap
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 使用 Drawable 的 toBitmap() 方法 (API 29+)
            drawable.toBitmap()
        } else {
            // 对于 API 29 以下的设备，使用其他方法（见下方的示例）
            drawableToBitmapLegacy(drawable)
        }
    }

    fun drawableToBitmapLegacy(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // 设置 drawable 的边界
        drawable.setBounds(0, 0, canvas.width, canvas.height)

        // 将 drawable 绘制到 Canvas 上
        drawable.draw(canvas)

        return bitmap
    }


}