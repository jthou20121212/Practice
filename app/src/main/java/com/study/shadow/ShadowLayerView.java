package com.study.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class ShadowLayerView extends View {
    private Paint paint;
    private Path path;

    public ShadowLayerView(Context context) {
        super(context);
        init();
    }

    public ShadowLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setShadowLayer(8f, 0f, 0f, Color.BLACK);
        path = new Path();
        path.addRect(0, 0, 200, 200, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }
}
