package com.study.since290;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class RotateImageView extends AppCompatImageView {

    private float mRotateDegree = 0;

    public RotateImageView(@NonNull Context context) {
        super(context);
    }

    public RotateImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRotateDegree(float degree) {
        this.mRotateDegree = degree;
        invalidate();  // 更新视图，会触发 onDraw 方法的调用
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int px = getWidth() / 2;
        int py = getHeight();
        canvas.rotate(mRotateDegree, px, py);
        super.onDraw(canvas);
    }


}
