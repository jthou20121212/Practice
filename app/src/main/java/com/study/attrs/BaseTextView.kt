package com.study.attrs

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.jthou.pro.crazy.R
import com.utils.log
import splitties.views.backgroundColor
import kotlin.math.roundToInt

/**
 * @author HY
 * @date 2019-07-23
 * Desc:BaseTextView
 */
open class BaseTextView : AppCompatTextView {

    companion object {
        const val ATTR_TEXT_COLOR = "textColor"
        const val ATTR_BACKGROUND = "background"
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

        const val TEXT_COLOR = 1
        const val TEXT_COLOR_DARK = 2
        const val TEXT_COLOR_LIGHT = 4
        const val BACKGROUND = 8
        const val BACKGROUND_DARK = 16
        const val BACKGROUND_LIGHT = 32
    }

    private val map = mutableMapOf<String, Any>()

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    protected open fun init(context: Context, attrs: AttributeSet?) {
        val a = attrs ?: return
        val typedArray = context.obtainStyledAttributes(a, R.styleable.HuxiuCommonView)
        val radius = typedArray.getDimensionPixelSize(R.styleable.HuxiuCommonView_radius, 0)
        val radiusScope = typedArray.getInt(R.styleable.HuxiuCommonView_radius_scope, 0)

        val radiusTopLeft = parseRadius(typedArray, radius, R.styleable.HuxiuCommonView_radius_top_left, radiusScope, TOP_LEFT).toFloat()
        val radiusTopRight = parseRadius(typedArray, radius, R.styleable.HuxiuCommonView_radius_top_right, radiusScope, TOP_RIGHT).toFloat()
        val radiusBottomRight = parseRadius(typedArray, radius, R.styleable.HuxiuCommonView_radius_bottom_right, radiusScope, BOTTOM_RIGHT).toFloat()
        val radiusBottomLeft = parseRadius(typedArray, radius, R.styleable.HuxiuCommonView_radius_bottom_left, radiusScope, BOTTOM_LEFT).toFloat()

        val opacity = typedArray.getFloat(R.styleable.HuxiuCommonView_opacity, 0f)

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

            val opacityScope = typedArray.getInt(R.styleable.HuxiuCommonView_opacity_scope, 0)
            val resourceEntryName = context.resources.getResourceEntryName(resId)
            "resourceEntryName : $resourceEntryName".log()
            if (ATTR_TEXT_COLOR == attributeName) {
                @ColorInt
                val textColor = ContextCompat.getColor(context, resId)
                val textColorOpacity = parseOpacity(typedArray, opacity, R.styleable.HuxiuCommonView_opacity_text_color, opacityScope, TEXT_COLOR)
                // 判断是否需要应用不透明度
                if (resourceEntryName.endsWith(SUFFIX_DARK)) {
                    // 如果以 _dark 结尾需要判断 text_color text_color_dark text_color_light
                    // 需要考虑 dn 组件继承、重写
                    val lightResName: String = resourceEntryName.substring(0, resourceEntryName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT
                    val lightResId = context.resources.getIdentifier(lightResName, "color", context.packageName)

                    val textColorDarkOpacity = typedArray.getFloat(R.styleable.HuxiuCommonView_opacity_text_color_dark, textColorOpacity)
                    val textColorDark = getColorWithOpacity(textColor, textColorDarkOpacity)
                    if (lightResId == 0) {
                        recordTextColor(textColorDark, Global.NO_VALUE)
                    } else {
                        val textColorLight = ContextCompat.getColor(context, lightResId)
                        val textColorLightOpacity = typedArray.getFloat(R.styleable.HuxiuCommonView_opacity_text_color_light, textColorOpacity)
                        recordTextColor(textColorDark, getColorWithOpacity(textColorLight, textColorLightOpacity))
                    }
                } else {
                    recordTextColor(getColorWithOpacity(textColor, textColorOpacity), Global.NO_VALUE)
                }
            } else if (ATTR_BACKGROUND == attributeName) {
                @ColorInt
                val backgroundColor = ContextCompat.getColor(context, resId)
                val backgroundOpacity = parseOpacity(typedArray, opacity, R.styleable.HuxiuCommonView_opacity_background, opacityScope, BACKGROUND)
                if (resourceEntryName.endsWith(SUFFIX_DARK)) {
                    val lightResName: String = resourceEntryName.substring(0, resourceEntryName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT
                    val lightResId = context.resources.getIdentifier(lightResName, "color", context.packageName)

                    val backgroundColorDarkOpacity = typedArray.getFloat(R.styleable.HuxiuCommonView_opacity_background_dark, backgroundOpacity)
                    val backgroundColorDark = getColorWithOpacity(backgroundColor, backgroundColorDarkOpacity)
                    if (lightResId == 0) {
                        val background = getDrawableWithRadiusAndOpacity(context, resId, backgroundColorDark, Global.NO_VALUE, radiusTopLeft, radiusTopRight, radiusBottomRight, radiusBottomLeft)
                        recordBackGround(background, backgroundColorDark, Global.NO_VALUE)
                    } else {
                        val backgroundColorLight = ContextCompat.getColor(context, lightResId)
                        val backgroundColorLightOpacity = typedArray.getFloat(R.styleable.HuxiuCommonView_opacity_background_light, backgroundOpacity)
                        val background = getDrawableWithRadiusAndOpacity(context, resId, backgroundColorDark, getColorWithOpacity(backgroundColorLight, backgroundColorLightOpacity), radiusTopLeft, radiusTopRight, radiusBottomRight, radiusBottomLeft)
                        recordBackGround(background, backgroundColor, getColorWithOpacity(backgroundColorLight, backgroundColorLightOpacity))
                    }
                } else {
                    val background = getDrawableWithRadiusAndOpacity(context, resId, getColorWithOpacity(backgroundColor, backgroundOpacity), Global.NO_VALUE, radiusTopLeft, radiusTopRight, radiusBottomRight, radiusBottomLeft)
                    recordBackGround(background, Global.NO_VALUE, Global.NO_VALUE)
                }
            } else {
                // ignore 其他属性不处理
            }
        }

        typedArray.recycle()
    }

    private fun parseRadius(typedArray: TypedArray, radius: Int, index: Int, radiusScope: Int, corner: Int): Int {
        return if (typedArray.hasValue(index)) typedArray.getDimensionPixelSize(index, 0)
        else if (radiusScope.and(corner) != 0) radius
        else 0
    }

    private fun parseOpacity(typedArray: TypedArray, opacity: Float, index: Int, opacityScope: Int, value: Int): Float {
        return if (typedArray.hasValue(index)) typedArray.getFloat(index, 0f)
        else if (opacityScope.and(value) != 0) opacity
        else 0f
    }

    @ColorInt
    private fun getColorWithOpacity(@ColorInt color: Int, opacity: Float): Int {
        return if (opacity == 0f) color
        else ColorUtils.setAlphaComponent(color, (opacity * 255).roundToInt())
    }

    private fun getDrawableWithRadiusAndOpacity(
        context: Context,
        @ColorRes resId: Int,
        @ColorInt darkColor: Int,
        @ColorInt lightColor: Int,
        radiusTopLeft: Float,
        radiusTopRight: Float,
        radiusBottomRight: Float,
        radiusBottomLeft: Float
    ): Drawable {
        return if (radiusTopLeft == 0f && radiusTopRight == 0f && radiusBottomRight == 0f && radiusBottomLeft == 0f) {
            val drawable = ContextCompat.getDrawable(context, resId)
            if (!Global.DARK_MODE && lightColor != Global.NO_VALUE) {
                drawable?.setTint(lightColor)
            }
            drawable!!
        } else {
            if (Global.DARK_MODE) {
                ShapeUtils.createFilletDrawableUseColorInt(radiusTopLeft, radiusTopRight, radiusBottomRight, radiusBottomLeft, darkColor)
            } else {
                ShapeUtils.createFilletDrawableUseColorInt(radiusTopLeft, radiusTopRight, radiusBottomRight, radiusBottomLeft, if (Global.NO_VALUE == lightColor) darkColor else lightColor)

            }
        }
    }

    @CallSuper
    open fun recordTextColor(@ColorInt textColorDark: Int, @ColorInt textColorLight: Int) {
        setTextColor(if (Global.DARK_MODE) textColorDark else textColorLight)
    }

    @CallSuper
    open fun recordBackGround(drawable: Drawable, @ColorInt darkColor: Int, @ColorInt lightColor : Int) {
        background = drawable
    }

}