package com.jthou.fuckyou;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * @author jthou
 * @date 21-10-2020
 * @since 1.0.0
 */
public class OverSizeRecyclerView extends RecyclerView {

    public OverSizeRecyclerView(@NonNull Context context) {
        super(context);
    }

    public OverSizeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OverSizeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int screenWidth = ScreenUtils.getScreenWidth();
        int measuredHeight = getMeasuredHeight();
        setMeasuredDimension(screenWidth * 3 , measuredHeight);
    }

}
