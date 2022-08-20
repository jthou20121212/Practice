package com.study.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatImageView;

public class ZoomImageView extends AppCompatImageView implements OnScaleGestureListener,
        OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {

    private static final String TAG = ZoomImageView.class.getSimpleName();

    public static final float SCALE_MAX = 4.0f;
    public static final float SCALE_MID = 2.0f;

    /**
     * 初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
     */
    private float initScale = 1.0f;

    /**
     * 用于存放矩阵的9个值
     */
    private final float[] matrixValues = new float[9];

    /**
     * 缩放的手势检测
     */
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;

    private float mLastX;
    private float mLastY;
    private boolean isCanDrag;
    private final float mTouchSlop;
    private int mLastPointerCount;
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    private boolean isAutoScale;

    private final Matrix mScaleMatrix = new Matrix();

    public ZoomImageView(Context context) {
        this(context, null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale) return true;
                float x = e.getX();
                float y = e.getY();

                float scale = getScale();
                if (scale < SCALE_MID) {
                    ZoomImageView.this.post(new AutoScaleRunnable(x, y, SCALE_MID));
                    isAutoScale = true;
                } else if (scale < SCALE_MAX) {
                    ZoomImageView.this.post(new AutoScaleRunnable(x, y, SCALE_MAX));
                    isAutoScale = true;
                } else {
                    ZoomImageView.this.post(new AutoScaleRunnable(x, y, initScale));
                    isAutoScale = true;
                }

                return super.onDoubleTap(e);
            }
        });
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.setOnTouchListener(this);
    }

    class AutoScaleRunnable implements Runnable {

        static final float BIGGER = 1.07f;
        static final float SMALLER = 0.93f;
        private float tmpScale;

        private float x;
        private float y;
        private float targetScale;

        public AutoScaleRunnable(float x, float y, float targetScale) {
            this.x = x;
            this.y = y;
            this.targetScale = targetScale;
            if (getScale() < targetScale) {
                tmpScale = BIGGER;
            } else {
                tmpScale = SMALLER;
            }
        }

        @Override
        public void run() {
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            // 如果值在合法范围内，继续缩放
            final float currentScale = getScale();
            if (((tmpScale > 1f) && (currentScale < targetScale))
                    || ((tmpScale < 1f) && (targetScale < currentScale))) {
                ZoomImageView.this.post(this);
            } else {
                // 设置为目标的缩放比例
                final float deltaScale = targetScale / currentScale;
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }
        }

    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null)
            return true;

        /**
         * 缩放的范围控制
         */
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                || (scale > initScale && scaleFactor < 1.0f)) {
            /**
             * 最大值最小值判断
             */
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale;
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }
            /**
             * 设置缩放比例
             */
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

            // 检查边界
            checkBorderAndCenterWhenScale();

            setImageMatrix(mScaleMatrix);
        }
        return true;

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;

        mScaleGestureDetector.onTouchEvent(event);
        float tX = 0;
        float tY = 0;

        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            tX += event.getX(i);
            tY = event.getY(i);
        }

        float x = tX / pointerCount;
        float y = tY / pointerCount;

        if (pointerCount != mLastPointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy);
                }

                if (isCanDrag) {
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null) {
                        // 如果宽度小于控件宽度则不能左右滑动
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        } else {
                            isCheckLeftAndRight = true;
                        }

                        // 如果高度小于控件高度则不能上下滑动
                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        } else {
                            isCheckTopAndBottom = true;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkMatrixBounds();
                        setImageMatrix(mScaleMatrix);
                    }
                }

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
        }
        return true;
    }

    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }

    private void checkMatrixBounds() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0, deltaY = 0;

        final float viewWidth = getWidth();
        final float viewHeight = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rectF.bottom;
        }

        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rectF.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }


    /**
     * 在缩放时，进行图片显示范围的控制
     */
    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.e(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);

        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rect = new RectF();
        Drawable d = getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rect);
        }
        return rect;
    }

    /**
     * 获得当前的缩放比例
     *
     * @return
     */
    public final float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        Drawable d = getDrawable();
        if (d == null) return;
        Log.e(TAG, d.getIntrinsicWidth() + " , " + d.getIntrinsicHeight());
        int width = getWidth();
        int height = getHeight();
        // 拿到图片的宽和高
        int dw = d.getIntrinsicWidth();
        int dh = d.getIntrinsicHeight();
        float scale = 1.0f;
        // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        if (dh > height && dw <= width) {
            scale = height * 1.0f / dh;
        }
        // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
        if (dw > width && dh > height) {
            scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
        }
        initScale = scale;
        // 图片移动至屏幕中心
        mScaleMatrix.postTranslate((width - dw) / 2f, (height - dh) / 2f);
        mScaleMatrix.postScale(scale, scale, getWidth() / 2f, getHeight() / 2f);
        setImageMatrix(mScaleMatrix);
    }

}