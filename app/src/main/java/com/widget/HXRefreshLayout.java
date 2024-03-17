package com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.jthou.pro.crazy.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

/**
 * Created on 2018/11/9 11:07
 *
 * @author andy
 */
public class HXRefreshLayout extends SmartRefreshLayout {

    public static final int REBOUND_DURATION = 300;
    public static final float AUTO_REFRESH_RATE = 1.0F;

    /**
     * 是否拦截RefreshLayout的事件
     * 处理进入二楼
     */
    private boolean mInterceptEvent;
    /**
     * 是否进入二楼
     */
    private boolean mHoleEnter;
    /**
     * TabBarOverlay
     */
    private View mTabBarOverlay;

    public HoleEnterListener mHoleEnterListener;

    public interface HoleEnterListener {
        /**
         * 进入二楼
         */
        void holeEnter();
    }

    public HXRefreshLayout(Context context) {
        this(context, null);
    }

    public HXRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //下面示例中的值等于默认值
        RefreshLayout refreshLayout = this;
        refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        //显示下拉高度/手指真实下拉高度=阻尼效果
        refreshLayout.setDragRate(1F);
        //回弹动画时长（毫秒）
        refreshLayout.setReboundDuration(REBOUND_DURATION);
        refreshLayout.setReboundInterpolator(new DecelerateInterpolator());

        //Header标准高度（显示下拉高度>=标准高度 触发刷新）
        refreshLayout.setHeaderHeight(60);
        //同上-像素为单位 （V1.1.0删除）
//        refreshLayout.setHeaderHeightPx(100);
        //Footer标准高度（显示上拉高度>=标准高度 触发加载）
        refreshLayout.setFooterHeight(60);
        //同上-像素为单位 （V1.1.0删除）
//        refreshLayout.setFooterHeightPx(100);

        //设置 Header 起始位置偏移量 1.0.5
//        refreshLayout.setFooterHeaderInsetStart(0);
        //同上-像素为单位 1.0.5 （V1.1.0删除）
//        refreshLayout.setFooterHeaderInsetStartPx(0);
        //设置 Footer 起始位置偏移量 1.0.5
//        refreshLayout.setFooterFooterInsetStart(0);
        //同上-像素为单位 1.0.5 （V1.1.0删除）
//        refreshLayout.setFooterFooterInsetStartPx(0);

        //最大显示下拉高度/Header标准高度
        refreshLayout.setHeaderMaxDragRate(2.4F);
        //最大显示下拉高度/Footer标准高度
        refreshLayout.setFooterMaxDragRate(2F);
        //触发刷新距离 与 HeaderHeight 的比率1.0.4
        refreshLayout.setHeaderTriggerRate(1F);
        //触发加载距离 与 FooterHeight 的比率1.0.4
        refreshLayout.setFooterTriggerRate(1F);

        //是否启用下拉刷新功能
        refreshLayout.setEnableRefresh(true);
        //是否启用上拉加载功能
        refreshLayout.setEnableLoadMore(false);
        //是否启用列表惯性滑动到底部时自动加载更多
        refreshLayout.setEnableAutoLoadMore(true);
        //是否启用纯滚动模式
        refreshLayout.setEnablePureScrollMode(false);
        //是否启用嵌套滚动
        refreshLayout.setEnableNestedScroll(false);
        //是否启用越界回弹
        refreshLayout.setEnableOverScrollBounce(false);
        //是否在加载完成时滚动列表显示新的内容
        refreshLayout.setEnableScrollContentWhenLoaded(true);
        //是否下拉Header的时候向下平移列表或者内容
        refreshLayout.setEnableHeaderTranslationContent(true);
        //是否上拉Footer的时候向上平移列表或者内容
        refreshLayout.setEnableFooterTranslationContent(true);
        //是否在列表不满一页时候开启上拉加载功能
        refreshLayout.setEnableLoadMoreWhenContentNotFull(true);
        //是否在全部加载结束之后Footer跟随内容1.0.4
        // refreshLayout.setEnableFooterFollowWhenLoadFinished(false);
        //是否启用越界拖动（仿苹果效果）1.0.4
        refreshLayout.setEnableOverScrollDrag(false);

        //是否在刷新完成时滚动列表显示新的内容 1.0.5
        refreshLayout.setEnableScrollContentWhenRefreshed(true);
        //是否剪裁Header当时样式为FixedBehind时1.0.5
//        refreshLayout.srlEnableClipHeaderWhenFixedBehind(true);
        //是否剪裁Footer当时样式为FixedBehind时1.0.5
//        refreshLayout.srlEnableClipFooterWhenFixedBehind(true);

        //是否在刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenRefresh(false);
        //是否在加载的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(false);
    }

    @Override
    public boolean autoRefresh() {
        return super.autoRefresh(0, REBOUND_DURATION, AUTO_REFRESH_RATE, false);
    }

    public boolean isRefreshing() {
        return getState() == RefreshState.Refreshing;
    }

    public void setInterceptEvent(boolean interceptEvent) {
        mInterceptEvent = interceptEvent;
    }

    public boolean isHoleEnter() {
        return mHoleEnter;
    }

    public void setHoleEnter(boolean holeEnter) {
        mHoleEnter = holeEnter;
    }

    public void setHoleEnterListener(HoleEnterListener holeEnterListener) {
        mHoleEnterListener = holeEnterListener;
    }

    public void setTabBarOverlay(View tabBarOverlay) {
        mTabBarOverlay = tabBarOverlay;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        try {
            if (mTabBarOverlay != null && mTabBarOverlay.getVisibility() != VISIBLE) {
                mTabBarOverlay.setVisibility(VISIBLE);
            }
            final int action = e.getActionMasked();
            if (!(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE)) {
                if (mTabBarOverlay != null && mTabBarOverlay.getVisibility() != GONE) {
                    mTabBarOverlay.setVisibility(GONE);
                }
            }
            switch (action) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (mInterceptEvent) {
                        if (mHoleEnterListener != null) {
                            mHoleEnterListener.holeEnter();
                            setHoleEnter(true);
                        }
                        mInterceptEvent = false;
                        return true;
                    }
                    break;
                default:
                    break;
            }
            return super.dispatchTouchEvent(e);
        } catch (Exception ee) {
            ee.printStackTrace();
            return false;
        }
    }

}
