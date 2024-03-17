package com.widget.sortable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.jthou.pro.crazy.R;

/**
 * 可排序接口，用于股票列表按条件排序
 *
 * @author jthou
 * @date 28-10-2020
 * @since 1.0.0
 */
public interface ISortable {

    enum Sorter {

        /**
         * 默认状态
         */
        NONE(R.drawable.pro_sort_default_dark, null, R.color.pro_standard_gray_747b89_dark),

        /**
         * 升序
         */
        ASC(R.drawable.pro_sort_up_dark, "asc", R.color.pro_standard_white_ffffff_dark),

        /**
         * 降序
         */
        DESC(R.drawable.pro_sort_down_dark, "desc", R.color.pro_standard_white_ffffff_dark);

        @DrawableRes int drawableRes;
        String groupBy;
        @ColorRes int colorRes;

        Sorter(@DrawableRes int drawableRes, String groupBy, int colorRes) {
            this.drawableRes = drawableRes;
            this.groupBy = groupBy;
            this.colorRes = colorRes;
        }
    }

    /**
     * 排序
     * @param sorter 排序方式
     */
    void sort(Sorter sorter);

    /**
     * 返回当前排序用于根据排序显示 UI
     * @return 当前排序
     */
    Sorter getSorter();

}
