package com.study.attrs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.blankj.utilcode.util.ColorUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * <pre>
 *     @author : xyk
 *     e-mail : yaxiaoke@163.com
 *     desc   : 用于动态创建 Drawable
 * </pre>
 */
public class ShapeUtils {

    private static final float PRESSED_COLOR_ALPHA = 0.8f;

    public static Drawable createPressedDrawableUseColorRes(@NonNull Context context, float radiusLeftTop, float radiusRightTop, float radiusRightBottom, float radiusLeftBottom, @ColorRes int colorRes) {
        return createPressedDrawableUseColorInt(radiusLeftTop, radiusRightTop, radiusRightBottom, radiusLeftBottom, ContextCompat.getColor(context, colorRes));
    }

    public static Drawable createPressedDrawableUseColorInt(float radiusLeftTop, float radiusRightTop, float radiusRightBottom, float radiusLeftBottom, @ColorInt int colorInt) {
        StateListDrawable states = new StateListDrawable();
        int pressedColor = ColorUtils.setAlphaComponent(colorInt, PRESSED_COLOR_ALPHA);
        states.addState(new int[]{android.R.attr.state_pressed}, createDrawableUseColorInt(radiusLeftTop, radiusRightTop, radiusRightBottom, radiusLeftBottom, pressedColor));
        states.addState(new int[]{}, createDrawableUseColorInt(radiusLeftTop, radiusRightTop, radiusRightBottom, radiusLeftBottom, colorInt));
        return states;
    }

    public static Drawable createDrawableUseColorRes(@NonNull Context context, float radius, @ColorRes int colorRes) {
        return createDrawableUseColorInt(radius, radius, radius, radius, ContextCompat.getColor(context, colorRes));
    }

    public static Drawable createDrawableUseColorRes(@NonNull Context context, float radiusLeftTop, float radiusRightTop, float radiusRightBottom, float radiusLeftBottom, @ColorRes int colorRes) {
        return createDrawableUseColorInt(radiusLeftTop, radiusRightTop, radiusRightBottom, radiusLeftBottom, ContextCompat.getColor(context, colorRes));
    }

    public static Drawable createDrawableUseColorInt(float radius, @ColorInt int colorInt) {
        return createDrawableUseColorInt(radius, radius, radius, radius, colorInt);
    }

    public static Drawable createDrawableUseColorInt(float radiusLeftTop, float radiusRightTop, float radiusRightBottom, float radiusLeftBottom, @ColorInt int colorInt) {
        float[] floats = new float[]{radiusLeftTop, radiusLeftTop, radiusRightTop, radiusRightTop, radiusRightBottom, radiusRightBottom, radiusLeftBottom, radiusLeftBottom};
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setCornerRadii(floats);
        drawable.setColor(colorInt);
        return drawable;
    }

    public static Drawable createCircleDrawableUseColorRes(@NonNull Context context, int size, @ColorRes int colorRes) {
        return createCircleDrawableUseColorInt(size, ContextCompat.getColor(context, colorRes));
    }

    public static Drawable createCircleDrawableUseColorInt(int size, @ColorInt int colorInt) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setSize(size, size);
        drawable.setColor(colorInt);
        return drawable;
    }

    // 创建圆角矩形 Drawable
    public static Drawable createFilletDrawableUseColorRes(@NonNull Context context, int size, @ColorRes int colorRes) {
        return createFilletDrawableUseColorInt(size, ContextCompat.getColor(context, colorRes));
    }

    // 创建圆角矩形 Drawable
    public static Drawable createFilletDrawableUseColorInt(int size, @ColorInt int colorInt) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setSize(size, size);
        drawable.setCornerRadius(size);
        drawable.setColor(colorInt);
        return drawable;
    }

    // 创建圆角矩形 Drawable
    public static Drawable createFilletDrawableUseColorInt(float radiusLeftTop, float radiusRightTop, float radiusRightBottom, float radiusLeftBottom, @ColorInt int colorInt) {
        final float[] radii = new float[]{radiusLeftTop, radiusLeftTop, radiusRightTop, radiusRightTop, radiusRightBottom, radiusRightBottom, radiusLeftBottom, radiusLeftBottom};
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setCornerRadii(radii);
        drawable.setColor(colorInt);
        return drawable;
    }

    public static Drawable createEnableDrawableUseColorRes(@NonNull Context context, @DrawableRes int enableDrawableRes, @DrawableRes int disableDrawableRes) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_enabled}, ContextCompat.getDrawable(context, enableDrawableRes));
        states.addState(new int[]{}, ContextCompat.getDrawable(context, disableDrawableRes));
        return states;
    }

    public static Drawable createEnableDrawableUseColorInt(@NonNull Drawable enableDrawable, @NonNull Drawable disableDrawable) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_enabled}, enableDrawable);
        states.addState(new int[]{}, disableDrawable);
        return states;
    }

}
