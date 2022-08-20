package com.jthou.fuckyou;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;

/**
 * @author jthou
 * @date 21-10-2020
 * @since 1.0.0
 */
class OverSizeLinearLayout extends LinearLayout {

    private static final String TAG = "OverSizeLinearLayout";

    public OverSizeLinearLayout(Context context) {
        super(context);
    }

    public OverSizeLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OverSizeLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OverSizeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int screenWidth = ScreenUtils.getScreenWidth();
        int measuredHeight = getMeasuredHeight();
        setMeasuredDimension(screenWidth * 3 , measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LogUtils.i(TAG, "measuredWidth : " + getMeasuredWidth());
        LogUtils.i(TAG, "measuredHeight : " + getMeasuredHeight());
    }

}
