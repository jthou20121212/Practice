package com.jthou.pro.crazy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


/**
 * @author jthou
 * @date 05-11-2020
 * @since 1.0.0
 */
public class RoundCornerView extends View {

    private static final String STATE_CORNER = "state_corner";
    private static final String STATE_INSTANCE = "state_instance";

    private float mCorner;

    private int mWidth, mHeight;

    private Paint mPaint;
    private Xfermode xfermode;

    public RoundCornerView(Context context) {
        this(context, null);
    }

    public RoundCornerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RoundCornerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
    }

    public float getCorner() {
        return mCorner;
    }

    public void setCorner(float corner) {
        this.mCorner = corner;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc = canvas.saveLayer(0, 0, mWidth, mHeight, null);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
        mPaint.setXfermode(xfermode);
        mPaint.setColor(Color.TRANSPARENT);
        canvas.drawRoundRect(0, 0, mWidth, mHeight, mCorner, mCorner, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putFloat(STATE_CORNER, mCorner);
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getParcelable(STATE_INSTANCE));
            this.mCorner = bundle.getFloat(STATE_CORNER);
        } else {
            super.onRestoreInstanceState(state);
        }
    }

}
