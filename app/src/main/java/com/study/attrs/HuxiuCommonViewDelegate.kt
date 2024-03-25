package com.study.attrs

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.util.forEach
import com.jthou.pro.crazy.R
import com.utils.log
import kotlin.math.roundToInt


/**
 *
 *
 * @author jthou
 * @since 2.7.0
 * @date 10-08-2023
 */
class HuxiuCommonViewDelegate {

    companion object {

        const val TEXT_COLOR = "textColor"
        const val BACKGROUND = "background"
        const val DRAWABLE = "drawable"
        const val ANDROID = "android"

        const val RADIUS = "radius"
        const val OPACITY = "opacity"
        const val OPACITY_MODE = "opacity_mode"

        const val TOP_LEFT = 1
        const val TOP_RIGHT = 2
        const val BOTTOM_RIGHT = 4
        const val BOTTOM_LEFT = 8

        const val SUFFIX_DARK = "_dark"
        const val SUFFIX_LIGHT = "_light"

        private val map = mutableMapOf<String, Any>()

        @JvmStatic
        fun parse(view: View, context: Context, attrs: AttributeSet?) {
            val a = attrs ?: return
            val typedArray = context.obtainStyledAttributes(a, R.styleable.HuxiuCommonView)
            val radius = typedArray.getDimensionPixelSize(R.styleable.HuxiuCommonView_radius, 0)
            val corners = typedArray.getInt(R.styleable.HuxiuCommonView_radius_scope, 0)

            val radiusTopLeft = parseRadius(typedArray, radius, R.styleable.HuxiuCommonView_radius_top_left, corners, TOP_LEFT).toFloat()
            val radiusTopRight = parseRadius(typedArray, radius, R.styleable.HuxiuCommonView_radius_top_right, corners, TOP_RIGHT).toFloat()
            val radiusBottomRight = parseRadius(typedArray, radius, R.styleable.HuxiuCommonView_radius_bottom_right, corners, BOTTOM_RIGHT).toFloat()
            val radiusBottomLeft = parseRadius(typedArray, radius, R.styleable.HuxiuCommonView_radius_bottom_left, corners, BOTTOM_LEFT).toFloat()

            val opacity = typedArray.getFloat(R.styleable.HuxiuCommonView_opacity, 0f)
            typedArray.recycle()

            for (i in 0 until a.attributeCount) {
                val attributeName = a.getAttributeName(i)
                val attributeValue = a.getAttributeValue(i)
                "$attributeName : $attributeValue".log()

                if (!attributeValue.startsWith("@")) {
                    continue
                }

                val resId = attributeValue.substring(1).toInt()
                val resourcePackageName = context.resources.getResourcePackageName(resId)
                // @android: 不处理
                if (ANDROID == resourcePackageName) {
                    continue
                }

                val resourceTypeName = context.resources.getResourceTypeName(resId)
                // drawable 暂不处理
                if (DRAWABLE == resourceTypeName) {
                    continue
                }



                if (TEXT_COLOR == attributeName || view is TextView) {
                    // val resourceName = context.resources.getResourceName(resId)
                    val resourceEntryName = context.resources.getResourceEntryName(resId)
                    // 判断是否需要应用不透明度
                    if (SUFFIX_DARK == resourceEntryName) {
                        // 如果以 _dark 结尾需要判断 text_color text_color_dark text_color_light
                        // 需要考虑 dn 组件继承、重写
                    } else {
                        // 否则只需要判断 text_color
                        map[TEXT_COLOR] = ContextCompat.getColor(context, resId)
                    }
                } else if (BACKGROUND == attributeName) {
                    val resourceEntryName = context.resources.getResourceEntryName(resId)
                    if (SUFFIX_DARK == resourceEntryName) {
                        // 找到 _light 资源分别记录下来
                    } else {
                        map[BACKGROUND] = resId
                    }
                } else {
                    // ignore 其他属性不处理
                }

//                "resourceName : $resourceName".log()
//                "resourceEntryName : $resourceEntryName".log()
            }

            if (opacity != 0f && map[BACKGROUND] != null) {
                val backgroundId = map[BACKGROUND] as Int
                val backgroundColor = ContextCompat.getColor(context, backgroundId)
                //  // 圆角只对背景颜色生效
                val opacityColor = ColorUtils.setAlphaComponent(backgroundColor, (opacity * 255).roundToInt())
                view.background = ShapeUtils.createFilletDrawableUseColorInt(radiusTopLeft, radiusTopRight, radiusBottomRight, radiusBottomLeft, opacityColor)
            }
            // TODO 没有透明度还要处理圆角


            // textColor 只对 Textview 有效
            // 透明度不为空并且是 TextView 并且有设置 textColor 属性
            if (opacity != 0f && view is TextView && map[TEXT_COLOR] != null) {
                val textColor = ColorUtils.setAlphaComponent(map[TEXT_COLOR] as Int, (opacity * 255).roundToInt())
                view.setTextColor(textColor)
            }
        }

        private fun parseRadius(typedArray: TypedArray, radius: Int, index: Int, corners: Int, value: Int): Int {
            return if (typedArray.hasValue(index)) typedArray.getDimensionPixelSize(index, 0)
            else if (corners.and(value) != 0) radius
            else 0
        }

    }

}