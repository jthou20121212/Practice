package com.widget.sortable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ObjectUtils;

/**
 * @author jthou
 * @date 28-10-2020
 * @since 1.0.0
 */
public class SortableTextView extends AppCompatTextView implements ISortable {

    private static final int DRAWABLE_RIGHT = 2;

    private Sorter mSorter;

    public SortableTextView(Context context) {
        super(context);
    }

    public SortableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SortableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void sort(Sorter sorter) {
//        mSorter = sorter;
//        ViewHelper.setTextColor(ViewUtils.getColor(getContext(), sorter.colorRes), this);
//        Drawable[] compoundDrawables = getCompoundDrawables();
//        if (ObjectUtils.isEmpty(compoundDrawables)) {
//            return;
//        }
//        if (compoundDrawables.length <= DRAWABLE_RIGHT) {
//            return;
//        }
//        if (compoundDrawables[DRAWABLE_RIGHT] == null) {
//            return;
//        }
//        ViewHelper.setRightDrawable(ViewUtils.getResource(getContext(), sorter.drawableRes), this);
    }

    @Override
    public Sorter getSorter() {
        return mSorter;
    }

}
