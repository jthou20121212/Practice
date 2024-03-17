package com.study.attrs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @author HY
 * @date 2019-07-23
 * Desc:DrkMode-TextView
 */
public class DnTextView extends BaseTextView implements IDarkMode {

    private int[] mTextColor = new int[12];
    /**
     * mBgColor[0] 日间模式资源
     * mBgColor[1] 夜间模式资源
     * mBgColor[2] 0:color 1:drawable
     */
    private int[] mBgColor = new int[3];

    private int mTextColorDark;
    private int mTextColorLight;
    private Drawable mDrawable;
    private int mBackgroundColorDark;
    private int mBackgroundColorLight;

    public DnTextView(Context context) {
        super(context);
    }

    public DnTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DnTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        mTextColor = DarkModeUtils.getTextColor(context, attrs);
        mBgColor = DarkModeUtils.getBgColor(context, attrs);
        changeDarkModeUi(Global.DARK_MODE);
    }

    @Override
    public void darkModeChange(boolean isDarkMode) {
        changeDarkModeUi(isDarkMode);
        if (Global.NO_VALUE != mTextColorLight) {
            setTextColor(isDarkMode ? mTextColorDark : mTextColorLight);
        }
        if (Global.NO_VALUE != mBackgroundColorLight) {
            mDrawable.setTint(isDarkMode ? mBackgroundColorDark : mBackgroundColorLight);
            mDrawable.invalidateSelf();
        }
    }

    @Override
    public void recordTextColor(int textColorDark, int textColorLight) {
        super.recordTextColor(textColorDark, textColorLight);
        mTextColorDark = textColorDark;
        mTextColorLight = textColorLight;
    }

    @Override
    public void recordBackGround(@NonNull Drawable drawable, int darkColor, int lightColor) {
        super.recordBackGround(drawable, darkColor, lightColor);
        mDrawable = drawable;
        mBackgroundColorDark = darkColor;
        mBackgroundColorLight = lightColor;
    }

    private void changeDarkModeUi(boolean isDayMode) {
        DarkModeUtils.setTextRes(this, isDayMode, mTextColor);
        DarkModeUtils.setBackgroundRes(this, isDayMode, mBgColor);
    }

    /**
     * 设置textView颜色，支持DarkMode自动变色
     *
     * @param colorId 日间正常colorId
     */
    public void setTextColorSupport(@ColorRes int colorId) {
        try {
            Context context = getContext();
            if (context == null) {
                return;
            }
            if (Global.DARK_MODE) {
                setTextColor(ContextCompat.getColor(context, colorId));
            } else {
                String resName = context.getResources().getResourceEntryName(colorId);
                if (!resName.endsWith(DarkModeUtils.DARK)) {
                    setTextColor(ContextCompat.getColor(context, colorId));
                    return;
                }
                String lightResName = resName.substring(0, resName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT;
                @ColorRes int nightColorId = context.getResources().getIdentifier(
                        lightResName, "color", context.getPackageName());
                setTextColor(ContextCompat.getColor(context, nightColorId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置View背景颜色，支持DarkMode自动变色
     *
     * @param colorId 日间正常colorId
     */
    public void setBackgroundColorSupport(@ColorRes int colorId) {
        ViewUtils.setBackgroundColor(this, colorId);
    }

    /**
     * 设置View背景Resource，支持DarkMode自动变色
     *
     * @param resId 日间正常resId
     */
    public void setBackgroundResourceSupport(@DrawableRes int resId) {
        ViewUtils.setBackgroundResource(this, resId);
    }

}
