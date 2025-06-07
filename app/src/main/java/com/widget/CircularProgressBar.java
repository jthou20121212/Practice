package com.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

    public class CircularProgressBar extends View {
    private Paint paint;
    private RectF rectF;
    private float progress = 0;
    private int strokeWidth = 20;
    private int color = Color.BLUE;

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height);
        rectF.set(strokeWidth / 2f, strokeWidth / 2f, size - strokeWidth / 2f, size - strokeWidth / 2f);

        paint.setColor(Color.BLUE);
        canvas.drawOval(rectF, paint);

        paint.setColor(color);
        canvas.drawArc(rectF, -90, 360 * progress / 100, false, paint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        paint.setStrokeWidth(strokeWidth);
        invalidate();
    }
}
