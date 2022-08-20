package androidx.viewpager.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author jthou
 * @version ${BuildConfig.VERSION_CODE}
 * @date 01-07-2020
 */
public class SlowViewPager extends ViewPager {

    private static final int VELOCITY = 2000;

    public SlowViewPager(Context context) {
        super(context);
    }

    public SlowViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
        setCurrentItemInternal(item, false, always, VELOCITY);
    }

}
