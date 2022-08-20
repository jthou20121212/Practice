package com.jthou.pro.crazy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 嵌套在垂直RecyclerView中的水平RecyclerView
 *
 * @author jthou
 */
public class HorizontalRecyclerView extends RecyclerView {

    private float mLastX = 0;
    private float mLastY = 0;

    /**
     * 最小滑动距离
     */
    private int mTouchSlop;

    /**
     * 需要拦截事件的最大角度
     */
    private static final int MAX_ANGLE = 75;

    public HorizontalRecyclerView(Context context) {
        this(context, null);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = ev.getRawX();
                mLastY = ev.getRawY();
                requestDisallowInterceptTouchEventInternal(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float rawX = ev.getRawX();
                float rawY = ev.getRawY();

                float dealtX = rawX - mLastX;
                float dealtY = rawY - mLastY;

                boolean scrollToStart = false;
                RecyclerView.LayoutManager layoutManager = getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    scrollToStart = firstCompletelyVisibleItemPosition == 0;
                }

                if (scrollToStart && dealtX > mTouchSlop) {
                    requestDisallowInterceptTouchEventInternal(false);
                } else {
                    boolean horizontal = Math.abs(dealtX) > mTouchSlop;
                    boolean maxAngle =  getAngle(dealtX, dealtY) < MAX_ANGLE;
                    boolean distance = Math.abs(dealtX) > Math.abs(dealtY);
                    boolean touchSlop = Math.abs(dealtX) < mTouchSlop && Math.abs(dealtY) < mTouchSlop;
                    requestDisallowInterceptTouchEventInternal(horizontal || maxAngle || distance || touchSlop);
                }

                mLastX = rawX;
                mLastY = rawY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
            default:
        }
        return super.dispatchTouchEvent(ev);
    }

    private double getAngle(float x, float y) {
        return Math.toDegrees(Math.acos(Math.abs(x) / Math.hypot(x, y)));
    }

    private void requestDisallowInterceptTouchEventInternal(boolean disallowIntercept) {
        ViewParent parent = getParent();
        while (parent != null) {
            if (parent instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) parent;
                recyclerView.requestDisallowInterceptTouchEvent(disallowIntercept);
                break;
            }
            parent = parent.getParent();
        }
    }

}