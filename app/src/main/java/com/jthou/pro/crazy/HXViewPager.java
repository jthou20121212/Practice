package com.jthou.pro.crazy;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import androidx.viewpager.widget.ViewPager;

import com.jthou.pro.crazy.SimpleAnimatorListener;

/**
 * <pre>
 *     @author : xyk
 *     e-mail : yaxiaoke@163.com
 *     time   : 2019/02/18
 *     desc   : 重写的目的是 禁止左右滑动 和 动画的
 *     version: 1.0
 * </pre>
 */
public class HXViewPager extends ViewPager {

    private static final int VIEWPAGER_AUTO_SCROLL_DURATION = 300;

    private boolean mSmoothScroll = false;

    private int mLastPosition;
    private int mTargetPosition;

    public HXViewPager(Context context) {
        super(context);
    }

    public HXViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener () {
            @Override
            public void onPageSelected(int position) {
                mLastPosition = position;
            }
        });
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.mSmoothScroll = smoothScroll;
    }

    // Stores the starting X position of the ACTION_DOWN event
    float downX;

    /**
     * Checks the X position value of the event and compares it to
     * previous MotionEvents. Returns a true/false value based on if the
     * event was an swipe to the right or a swipe to the left.
     *
     * @param event -   Motion Event triggered by the ViewPager
     * @return      -   True if the swipe was from left to right. False otherwise
     */
    private boolean wasSwipeToRightEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                return false;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                return downX - event.getX() > 0;

            default:
                return false;
        }
    }

    @Override
    public void setCurrentItem(int currentItem) {
        Log.i("jthouLog", "setCurrentItem");
//        animatePagerTransition(mTargetPosition = currentItem);
        super.setCurrentItem(currentItem);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    public int getLastPosition() {
        return mLastPosition;
    }

    public int getTargetItem() {
        return mTargetPosition;
    }

    private void animatePagerTransition(final int currentItem) {
        int width = getWidth();
        ValueAnimator animator = ValueAnimator.ofInt(0, (currentItem - mLastPosition) * width);
        animator.addListener(new SimpleAnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                setViewPagerOnAnimationEndOrCancel();
                // false 去除滚动效果
                 setCurrentItem(currentItem, false);
                Log.i("jthouLog", "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                setViewPagerOnAnimationEndOrCancel();
                Log.i("jthouLog", "onAnimationCancel");
            }

        });

        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private int oldDragPosition = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    int dragPosition = (Integer) animation.getAnimatedValue();
                    int dragOffset = dragPosition - oldDragPosition;
                    oldDragPosition = dragPosition;
                    if (!isFakeDragging()) {
                        return;
                    }
                    fakeDragBy(-dragOffset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });

//        int duration = VIEWPAGER_AUTO_SCROLL_DURATION + Math.abs(mLastPosition - currentItem) * VIEWPAGER_AUTO_SCROLL_DURATION;
        animator.setDuration(VIEWPAGER_AUTO_SCROLL_DURATION + Math.abs(mLastPosition - currentItem) * 100);
        beginFakeDrag();
        animator.start();
    }

    private void setViewPagerOnAnimationEndOrCancel() {
        try {
            endFakeDrag();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
