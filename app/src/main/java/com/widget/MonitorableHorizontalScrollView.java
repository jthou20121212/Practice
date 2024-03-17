package com.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * 可以监听滚动的事件的 HorizontalScrollView
 * android.view.View#setOnScrollChangeListener(android.view.View.OnScrollChangeListener) API 23 以上才可用
 *
 * @author jthou
 * @date 27-10-2020
 * @since 1.0.0
 */
public class MonitorableHorizontalScrollView extends HorizontalScrollView {

    private OnScrollChangeListener mOnScrollChangeListener;

    public MonitorableHorizontalScrollView(Context context) {
        this(context, null);
    }

    public MonitorableHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonitorableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MonitorableHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        } else {
            setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangeListener != null) {
            mOnScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    public void setCompatOnScrollChangeListener(OnScrollChangeListener l) {
        mOnScrollChangeListener = l;
    }

    public interface OnScrollChangeListener {
        /**
         * Called when the scroll position of a view changes.
         *
         * @param v The view whose scroll position has changed.
         * @param scrollX Current horizontal scroll origin.
         * @param scrollY Current vertical scroll origin.
         * @param oldScrollX Previous horizontal scroll origin.
         * @param oldScrollY Previous vertical scroll origin.
         */
        void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

}
