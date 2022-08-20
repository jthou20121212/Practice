package com.jthou.fuckyou;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;

/**
 * @author jthou
 * @date 21-10-2020
 * @since 1.0.0
 */
class OverSizeScrollView extends ScrollView {

    private static final String TAG = "OverSizeScrollView";

    public OverSizeScrollView(Context context) {
        super(context);
    }

    public OverSizeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverSizeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OverSizeScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int screenWidth = ScreenUtils.getScreenWidth();
        int measuredHeight = getMeasuredHeight();
        LogUtils.i(TAG, "screenWidth : " + screenWidth);
        LogUtils.i(TAG, "measuredHeight : " + measuredHeight);
        setMeasuredDimension(screenWidth * 3 , measuredHeight);
    }

}
