package com.study.attrs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;


/**
 * @author HY
 * @date 2019-07-23
 * Desc:DarkModeUtils
 */
public class DarkModeUtils {

    public final static String DARK = "_dark";
    public final static String LIGHT = "_light";

    public final static String RESOURCE_NAME_COLOR = "color";
    public final static String RESOURCE_NAME_DRAWABLE = "drawable";

    private final static String TEXT_COLOR = "textColor";
    private final static String TEXT_COLOR_HINT = "textColorHint";
    private final static String BACKGROUND = "background";
    private final static String SRC = "src";

    private final static String DRAWABLE_LEFT = "drawableLeft";
    private final static String DRAWABLE_START = "drawableStart";
    private final static String DRAWABLE_RIGHT = "drawableRight";
    private final static String DRAWABLE_END = "drawableEnd";
    private final static String DRAWABLE_TOP = "drawableTop";
    private final static String DRAWABLE_BOTTOM = "drawableBottom";

    /**
     * loading三角刷新view
     */
    private final static String STROKE_COLOR = "strokeColor";

    /**
     * progressBar-indeterminate-drawable
     */
    private final static String INDETER_MINATER_DRAWABLE = "indeterminateDrawable";

    /**
     * progress-drawable
     */
    private final static String PROGRESS_DRAWABLE = "progressDrawable";

    /**
     * TitleBar-drawable
     */
    private final static String TITLE_BAR_DRAWABLE_LEFT = "tb_left_image_src";
    private final static String TITLE_BAR_DRAWABLE_RIGHT = "tb_right_image_src";

    /**
     * 设置背景资源
     *
     * @param view      view
     * @param isDarkMode 是否是深色模式
     * @param bgRes     资源id
     *                  bgRes[0] 日间模式资源
     *                  bgRes[1] 夜间模式资源
     *                  bgRes[2] 0:color 1:drawable
     */
    public static void setBackgroundRes(View view, boolean isDarkMode, int[] bgRes) {
        if (view == null || bgRes == null || bgRes.length < 3) {
            return;
        }
        if (isDarkMode) {
            if (isColorRes(bgRes)) {
                if (bgRes[0] != 0) {
                    view.setBackgroundColor(bgRes[0]);
                }
            } else {
                if (bgRes[0] != 0) {
                    view.setBackgroundResource(bgRes[0]);
                }
            }
        } else {
            if (isColorRes(bgRes)) {
                if (bgRes[1] != 0) {
                    view.setBackgroundColor(bgRes[1]);
                }
            } else {
                if (bgRes[1] != 0) {
                    view.setBackgroundResource(bgRes[1]);
                }
            }
        }
    }

    /**
     * 设置Text资源
     *
     * @param textView  textView
     * @param isDarkMode 是否是深色模式
     * @param textColor 资源id
     *                  textColorId[0] 日间模式资源
     *                  textColorId[1] 夜间模式资源
     *                  textColorId[2] drawableLeft日间模式资源
     *                  textColorId[3] drawableLeft夜间模式资源
     *                  textColorId[4] drawableTop日间模式资源
     *                  textColorId[5] drawableTop夜间模式资源
     *                  textColorId[6] drawableRight日间模式资源
     *                  textColorId[7] drawableRight夜间模式资源
     *                  textColorId[8] drawableBottom日间模式资源
     *                  textColorId[9] drawableBottom夜间模式资源
     *                  textColorId[10] textColorHint日间模式资源
     *                  textColorId[11] textColorHint夜间模式资源
     */
    public static void setTextRes(TextView textView, boolean isDarkMode, int[] textColor) {
        if (textView == null || textColor == null || textColor.length < 12) {
            return;
        }
        Context context = textView.getContext();
        if (context == null) {
            return;
        }
        if (isDarkMode) {
            if (textColor[0] != 0) {
                ColorStateList colorStateList = context.getResources().getColorStateList(textColor[0], null);
                textView.setTextColor(colorStateList);
            }
            if (textColor[2] != 0) {
                Drawable drawable = ContextCompat.getDrawable(context, textColor[2]);
                textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }
            if (textColor[4] != 0) {
                Drawable drawable = ContextCompat.getDrawable(context, textColor[4]);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            }
            if (textColor[6] != 0) {
                Drawable drawable = ContextCompat.getDrawable(context, textColor[6]);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
            if (textColor[8] != 0) {
                Drawable drawable = ContextCompat.getDrawable(context, textColor[8]);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
            }
            if (textColor[10] != 0) {
                ColorStateList colorStateList = context.getResources().getColorStateList(textColor[10], null);
                textView.setHintTextColor(colorStateList);
            }
        } else {
            if (textColor[1] != 0) {
                ColorStateList colorStateList = context.getResources().getColorStateList(textColor[1], null);
                textView.setTextColor(colorStateList);
            }
            if (textColor[3] != 0) {
                Drawable drawable = ContextCompat.getDrawable(context, textColor[3]);
                textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }
            if (textColor[5] != 0) {
                Drawable drawable = ContextCompat.getDrawable(context, textColor[5]);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            }
            if (textColor[7] != 0) {
                Drawable drawable = ContextCompat.getDrawable(context, textColor[7]);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
            if (textColor[9] != 0) {
                Drawable drawable = ContextCompat.getDrawable(context, textColor[9]);
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
            }
            if (textColor[11] != 0) {
                ColorStateList colorStateList = context.getResources().getColorStateList(textColor[11], null);
                textView.setHintTextColor(colorStateList);
            }
        }
    }

    /**
     * 是否是color资源
     *
     * @return true:是 false:为drawable
     */
    private static boolean isColorRes(int[] bgRes) {
        return bgRes[2] == 0;
    }

    /**
     * 获取textColor
     * textColorId[0] 日间模式资源
     * textColorId[1] 夜间模式资源
     * textColorId[2] drawableLeft日间模式资源
     * textColorId[3] drawableLeft夜间模式资源
     * textColorId[4] drawableTop日间模式资源
     * textColorId[5] drawableTop夜间模式资源
     * textColorId[6] drawableRight日间模式资源
     * textColorId[7] drawableRight夜间模式资源
     * textColorId[8] drawableBottom日间模式资源
     * textColorId[9] drawableBottom夜间模式资源
     * textColorId[10] textColorHint日间模式资源
     * textColorId[11] textColorHint夜间模式资源
     */
    @ColorInt
    public static int[] getTextColor(Context context, AttributeSet attrs) {
        @ColorInt int[] textColorId = new int[12];
        try {
            if (context == null || attrs == null) {
                return textColorId;
            }
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                String attributeValue = attrs.getAttributeValue(i);
                if (!attributeValue.startsWith("@")) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.parseInt(attributeValue.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == 0) {
                    continue;
                }
                String attrName = attrs.getAttributeName(i);

                String resName = context.getResources().getResourceEntryName(id);
                if (!resName.endsWith(DARK)) {
                    continue;
                }
                String lightResName = resName.substring(0, resName.indexOf(DARK)) + LIGHT;

                if (TEXT_COLOR.equals(attrName)) {
                    // textColor
                    textColorId[0] = id;
                    String resourceName = context.getResources().getResourceTypeName(id);
                    if (resourceName.equals(RESOURCE_NAME_COLOR)) {
                        textColorId[1] = context.getResources().getIdentifier(
                                lightResName, "color", context.getPackageName());
                    } else if (resourceName.equals(RESOURCE_NAME_DRAWABLE)) {
                        textColorId[1] = context.getResources().getIdentifier(
                                lightResName, "drawable", context.getPackageName());
                    }
                } else if (DRAWABLE_LEFT.equals(attrName) || DRAWABLE_START.equals(attrName)) {
                    // drawableLeft
                    textColorId[2] = id;
                    textColorId[3] = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                } else if (DRAWABLE_TOP.equals(attrName)) {
                    // drawableTop
                    textColorId[4] = id;
                    textColorId[5] = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                } else if (DRAWABLE_RIGHT.equals(attrName) || DRAWABLE_END.equals(attrName)) {
                    // drawableRight
                    textColorId[6] = id;
                    textColorId[7] = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                } else if (DRAWABLE_BOTTOM.equals(attrName)) {
                    // drawableBottom
                    textColorId[8] = id;
                    textColorId[9] = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                } else if (TEXT_COLOR_HINT.equals(attrName)) {
                    // textColorHint
                    textColorId[10] = id;
                    textColorId[11] = context.getResources().getIdentifier(
                            lightResName, "color", context.getPackageName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textColorId;
    }

    /**
     * 获取background
     * bgResId[0] 日间模式资源
     * bgResId[1] 夜间模式资源
     * bgResId[2] 0:color 1:drawable
     */
    public static int[] getBgColor(Context context, AttributeSet attrs) {
        int[] bgResId = new int[3];
        try {
            if (context == null || attrs == null) {
                return bgResId;
            }
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                String attributeValue = attrs.getAttributeValue(i);
                if (!attributeValue.startsWith("@")) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.parseInt(attributeValue.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == 0) {
                    continue;
                }
                String attrName = attrs.getAttributeName(i);
                if (!BACKGROUND.equals(attrName)) {
                    continue;
                }

                String resName = context.getResources().getResourceEntryName(id);
                if (!resName.endsWith(DARK)) {
                    continue;
                }
                String lightResName = resName.substring(0, resName.indexOf(DARK)) + LIGHT;

                // 资源类型--是color还是drawable
                String resourceName = context.getResources().getResourceTypeName(id);
                if (resourceName.equals(RESOURCE_NAME_COLOR)) {
                    // background-color
                    bgResId[0] = ContextCompat.getColor(context, id);
                    bgResId[1] = ContextCompat.getColor(context, context.getResources().getIdentifier(
                            lightResName, "color", context.getPackageName()));
                    bgResId[2] = 0;
                } else if (resourceName.equals(RESOURCE_NAME_DRAWABLE)) {
                    // background-drawable
                    bgResId[0] = id;
                    bgResId[1] = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                    bgResId[2] = 1;
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bgResId;
    }

    /**
     * 获取imageView-src
     * srcResId[0] 日间模式src资源
     * srcResId[1] 夜间模式src资源
     * srcResId[2] 日间模式background资源
     * srcResId[3] 夜间模式background资源
     */
    public static int[] getSrcDrawable(Context context, AttributeSet attrs) {
        int[] srcResId = new int[4];
        try {
            if (context == null || attrs == null) {
                return srcResId;
            }
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                String attributeValue = attrs.getAttributeValue(i);
                if (!attributeValue.startsWith("@")) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.parseInt(attributeValue.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == 0) {
                    continue;
                }
                String attrName = attrs.getAttributeName(i);

                String resName = context.getResources().getResourceEntryName(id);
                if (!resName.endsWith(DARK)) {
                    continue;
                }
                String lightResName = resName.substring(0, resName.indexOf(DARK)) + LIGHT;

                if (SRC.equals(attrName)) {
                    // src
                    // 资源类型--是color还是drawable
                    String resourceName = context.getResources().getResourceTypeName(id);
                    if (resourceName.equals(RESOURCE_NAME_COLOR)) {
                        // src-color
                        srcResId[0] = id;
                        srcResId[1] = context.getResources().getIdentifier(
                                lightResName, "color", context.getPackageName());
                    } else if (resourceName.equals(RESOURCE_NAME_DRAWABLE)) {
                        // src-drawable
                        srcResId[0] = id;
                        srcResId[1] = context.getResources().getIdentifier(
                                lightResName, "drawable", context.getPackageName());
                    }
                } else if (BACKGROUND.equals(attrName)) {
                    // background
                    // 资源类型--是color还是drawable
                    String resourceName = context.getResources().getResourceTypeName(id);
                    if (resourceName.equals(RESOURCE_NAME_COLOR)) {
                        // background-color
                        srcResId[2] = id;
                        srcResId[3] = context.getResources().getIdentifier(
                                lightResName, "color", context.getPackageName());
                    } else if (resourceName.equals(RESOURCE_NAME_DRAWABLE)) {
                        // background-drawable
                        srcResId[2] = id;
                        srcResId[3] = context.getResources().getIdentifier(
                                lightResName, "drawable", context.getPackageName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return srcResId;
    }

    /**
     * 获取loading view color
     * colorId[0] 日间模式资源
     * colorId[1] 夜间模式资源
     */
    public static int[] getLoadingStrokeColor(Context context, AttributeSet attrs) {
        int[] colorId = new int[2];
        try {
            if (context == null || attrs == null) {
                return colorId;
            }
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                String attributeValue = attrs.getAttributeValue(i);
                if (!attributeValue.startsWith("@")) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.parseInt(attributeValue.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == 0) {
                    continue;
                }
                String attrName = attrs.getAttributeName(i);
                if (!STROKE_COLOR.equals(attrName)) {
                    continue;
                }

                String resName = context.getResources().getResourceEntryName(id);
                if (!resName.endsWith(DARK)) {
                    continue;
                }
                String lightResName = resName.substring(0, resName.indexOf(DARK)) + LIGHT;

                // color
                colorId[0] = ContextCompat.getColor(context, id);
                colorId[1] = ContextCompat.getColor(context, context.getResources().getIdentifier(
                        lightResName, "color", context.getPackageName()));
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colorId;
    }

    /**
     * 获取progress-indeterminate-drawable
     * drawableId[0] 日间模式资源
     * drawableId[1] 夜间模式资源
     */
    public static int[] getIdentifierDrawable(Context context, AttributeSet attrs) {
        int[] drawableId = new int[2];
        try {
            if (context == null || attrs == null) {
                return drawableId;
            }
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                String attributeValue = attrs.getAttributeValue(i);
                if (!attributeValue.startsWith("@")) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.parseInt(attributeValue.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == 0) {
                    continue;
                }
                String attrName = attrs.getAttributeName(i);
                if (!INDETER_MINATER_DRAWABLE.equals(attrName)) {
                    continue;
                }

                String resName = context.getResources().getResourceEntryName(id);
                if (!resName.endsWith(DARK)) {
                    continue;
                }
                String lightResName = resName.substring(0, resName.indexOf(DARK)) + LIGHT;

                // drawable
                drawableId[0] = id;
                drawableId[1] = context.getResources().getIdentifier(
                        lightResName, "drawable", context.getPackageName());
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawableId;
    }

    /**
     * 获取TitleBar left right drawable
     * drawableId[0] left 深色资源
     * drawableId[1] left 浅色资源
     * drawableId[2] right 深色资源
     * drawableId[3] right 浅色资源
     */
    public static int[] getTitleBarDrawable(Context context, AttributeSet attrs) {
        int[] drawableId = new int[4];
        try {
            if (context == null || attrs == null) {
                return drawableId;
            }
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                String attributeValue = attrs.getAttributeValue(i);
                if (!attributeValue.startsWith("@")) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.parseInt(attributeValue.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == 0) {
                    continue;
                }
                String attrName = attrs.getAttributeName(i);

                String resName = context.getResources().getResourceEntryName(id);
                if (!resName.endsWith(DARK)) {
                    continue;
                }
                String lightResName = resName.substring(0, resName.indexOf(DARK)) + LIGHT;

                if (TITLE_BAR_DRAWABLE_LEFT.equals(attrName)) {
                    // left-drawable
                    drawableId[0] = id;
                    drawableId[1] = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                } else if (TITLE_BAR_DRAWABLE_RIGHT.equals(attrName)) {
                    // right-drawable
                    drawableId[2] = id;
                    drawableId[3] = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawableId;
    }

    /**
     * 获取progressDrawable
     * drawableId[0] 日间模式资源
     * drawableId[1] 夜间模式资源
     */
    public static int[] getProgressDrawable(Context context, AttributeSet attrs) {
        int[] drawableId = new int[2];
        try {
            if (context == null || attrs == null) {
                return drawableId;
            }
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                String attributeValue = attrs.getAttributeValue(i);
                if (!attributeValue.startsWith("@")) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.parseInt(attributeValue.substring(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id == 0) {
                    continue;
                }
                String attrName = attrs.getAttributeName(i);
                if (!PROGRESS_DRAWABLE.equals(attrName)) {
                    continue;
                }

                String resName = context.getResources().getResourceEntryName(id);
                if (!resName.endsWith(DARK)) {
                    continue;
                }
                String lightResName = resName.substring(0, resName.indexOf(DARK)) + LIGHT;

                // drawable
                drawableId[0] = id;
                drawableId[1] = context.getResources().getIdentifier(
                        lightResName, "drawable", context.getPackageName());
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawableId;
    }
}
