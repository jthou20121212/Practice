package com.work

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.ActivityTextMagnifierBinding
import com.study.viewbinding.viewBinding

// 自定义 ImageSpan 类，用于调整图标的绘制位置
class TopAlignedImageSpan(drawable: Drawable) : ImageSpan(drawable) {
    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val drawable = drawable
        val fontMetrics = paint.fontMetricsInt
        // 计算文本基线到顶部的距离
        val textTopOffset = y + fontMetrics.top
        // 确保图标底部不超过 TextView 内容区域
        val maxBottom = bottom - drawable.bounds.height()
        val transY = textTopOffset.coerceAtMost(maxBottom).toFloat()

        canvas.save()
        canvas.translate(x, transY)
        drawable.draw(canvas)
        canvas.restore()
    }
}

class TextMagnifierActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityTextMagnifierBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val htmlText = "月落乌啼霜满天，江枫渔火对愁眠。岁月长，衣衫薄<em>星图智能</em>完成Pre-A轮融资"
        // 解析 HTML 内容
        val spannable = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY) as Spannable
        val builder = SpannableStringBuilder(spannable)

        // 为 em 标签添加点击事件
        val emStartTag = "<em>"
        val emEndTag = "</em>"
        var startIndex = 0
        while (true) {
            val start = htmlText.indexOf(emStartTag, startIndex)
            if (start == -1) break
            val end = htmlText.indexOf(emEndTag, start)
            if (end == -1) break

            // 计算实际文本中的起始和结束位置
            // 修正计算逻辑
            val actualStart = start + emStartTag.length
            val actualEnd = end
            val spannableStart = builder.toString().indexOf(htmlText.substring(actualStart, actualEnd))
            val spannableEnd = spannableStart + (actualEnd - actualStart)

            if (spannableStart != -1 && spannableEnd != -1) {
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        // 获取点击文本内容
                        val text = builder.subSequence(spannableStart, spannableEnd)
                        Toast.makeText(this@TextMagnifierActivity, text, Toast.LENGTH_SHORT).show()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = false
                        ds.color = Color.GREEN
                    }
                }
                builder.setSpan(clickableSpan, spannableStart, spannableEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                // 在 '星图智能' 右上角添加图标
                val icon = ContextCompat.getDrawable(this, R.drawable.ic_magnifier)
                icon?.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
                if (icon != null) {
                    // 使用自定义的 TopAlignedImageSpan
                    val imageSpan = TopAlignedImageSpan(icon)
                    builder.insert(spannableEnd, " ") // 添加一些空白空间
                    builder.setSpan(imageSpan, spannableEnd, spannableEnd + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            startIndex = end + emEndTag.length
        }

        binding.textView.text = builder
        binding.textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }
}