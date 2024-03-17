package com.widget.sortable;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author jthou
 * @date 28-10-2020
 * @since 1.0.0
 */
public class SortableImageView extends AppCompatImageView implements ISortable {

    private Sorter mSorter;

    public SortableImageView(Context context) {
        super(context);
    }

    public SortableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SortableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void sort(Sorter sorter) {
        mSorter = sorter;
        // ViewHelper.setImageResource(ViewUtils.getResource(getContext(), sorter.drawableRes), this);
    }

    @Override
    public Sorter getSorter() {
        return mSorter;
    }

}
