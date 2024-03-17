package com.study.attrs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author HY
 * @date 2019-07-26
 * Desc:ViewUtils
 */
public class ViewUtils {
    
    /**
     * 获取Color资源Res，根据DarkMode返回相应颜色
     *
     * @param colorId 日间正常colorId
     */
    @ColorRes
    public static int getColorRes(Context context, @ColorRes int colorId) {
        try {
            if (Global.DARK_MODE) {
                return colorId;
            } else {
                String resName = context.getResources().getResourceEntryName(colorId);
                if (!resName.endsWith(DarkModeUtils.DARK)) {
                    return colorId;
                }
                String lightResName = resName.substring(0, resName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT;
                @ColorRes int nightColorId = context.getResources().getIdentifier(
                        lightResName, "color", context.getPackageName());
                return nightColorId == 0 ? colorId : nightColorId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colorId;
    }

    /**
     * 获取Color颜色，根据DarkMode返回相应颜色
     *
     * @param colorId 日间正常colorId
     */
    @ColorInt
    public static int getColor(Context context, @ColorRes int colorId) {
        try {
            if (Global.DARK_MODE) {
                return ContextCompat.getColor(context, colorId);
            } else {
                String resName = context.getResources().getResourceEntryName(colorId);
                if (!resName.endsWith(DarkModeUtils.DARK)) {
                    return ContextCompat.getColor(context, colorId);
                }
                String lightResName = resName.substring(0, resName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT;
                @ColorRes int nightColorId = context.getResources().getIdentifier(
                        lightResName, "color", context.getPackageName());
                return nightColorId == 0 ? ContextCompat.getColor(context, colorId) :
                        ContextCompat.getColor(context, nightColorId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ContextCompat.getColor(context, colorId);
    }

    /**
     * 获取Resource，根据DarkMode返回相应Resource
     *
     * @param resId 日间正常resId
     */
    @DrawableRes
    public static int getResource(Context context, @DrawableRes int resId) {
        try {
            if (Global.DARK_MODE) {
                return resId;
            } else {
                String resName = context.getResources().getResourceEntryName(resId);
                if (!resName.endsWith(DarkModeUtils.DARK)) {
                    return resId;
                }
                String lightResName = resName.substring(0, resName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT;
                int nightResId = 0;
                // 资源类型--是color还是drawable
                String resourceName = context.getResources().getResourceTypeName(resId);
                if (resourceName.equals(DarkModeUtils.RESOURCE_NAME_COLOR)) {
                    nightResId = context.getResources().getIdentifier(
                            lightResName, "color", context.getPackageName());
                } else if (resourceName.equals(DarkModeUtils.RESOURCE_NAME_DRAWABLE)) {
                    nightResId = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                }
                return nightResId == 0 ? resId : nightResId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;
    }

    /**
     * 获取默认蒙版透明度
     */
    public static float getMaskAlpha() {
        return Global.DARK_MODE ? 0.7F : 0.5F;
    }

    /**
     * 设置textView颜色，支持DarkMode自动变色
     *
     * @param textView textView
     * @param colorId  日间正常colorId
     */
    public static void setTextColor(TextView textView, @ColorRes int colorId) {
        try {
            if (textView == null) {
                return;
            }
            Context context = textView.getContext();
            if (context == null) {
                return;
            }
            if (Global.DARK_MODE) {
                textView.setTextColor(ContextCompat.getColor(context, colorId));
            } else {
                String resName = context.getResources().getResourceEntryName(colorId);
                if (!resName.endsWith(DarkModeUtils.DARK)) {
                    textView.setTextColor(ContextCompat.getColor(context, colorId));
                    return;
                }
                String lightResName = resName.substring(0, resName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT;
                @ColorRes int nightColorId = context.getResources().getIdentifier(
                        lightResName, "color", textView.getContext().getPackageName());
                textView.setTextColor(nightColorId == 0 ? ContextCompat.getColor(context, colorId) :
                        ContextCompat.getColor(context, nightColorId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置View背景颜色，支持DarkMode自动变色
     *
     * @param view    view
     * @param colorId 日间正常colorId
     */
    public static void setBackgroundColor(View view, @ColorRes int colorId) {
        try {
            if (view == null) {
                return;
            }
            Context context = view.getContext();
            if (context == null) {
                return;
            }
            if (Global.DARK_MODE) {
                view.setBackgroundColor(ContextCompat.getColor(context, colorId));
            } else {
                String resName = context.getResources().getResourceEntryName(colorId);
                if (!resName.endsWith(DarkModeUtils.DARK)) {
                    view.setBackgroundColor(ContextCompat.getColor(context, colorId));
                    return;
                }
                String lightResName = resName.substring(0, resName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT;
                @ColorRes int nightColorId = context.getResources().getIdentifier(
                        lightResName, "color", view.getContext().getPackageName());
                view.setBackgroundColor(nightColorId == 0 ? ContextCompat.getColor(context, colorId) :
                        ContextCompat.getColor(context, nightColorId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置View背景Resource，支持DarkMode自动变色
     *
     * @param view  view
     * @param resId 日间正常resId
     */
    public static void setBackgroundResource(View view, @DrawableRes int resId) {
        try {
            if (view == null) {
                return;
            }
            Context context = view.getContext();
            if (context == null) {
                return;
            }
            if (Global.DARK_MODE) {
                view.setBackgroundResource(resId);
            } else {
                String resName = context.getResources().getResourceEntryName(resId);
                if (!resName.endsWith(DarkModeUtils.DARK)) {
                    view.setBackgroundResource(resId);
                    return;
                }
                String lightResName = resName.substring(0, resName.indexOf(DarkModeUtils.DARK)) + DarkModeUtils.LIGHT;
                // 资源类型--是color还是drawable
                String resourceName = context.getResources().getResourceTypeName(resId);
                int nightResourceId = 0;
                if (resourceName.equals(DarkModeUtils.RESOURCE_NAME_COLOR)) {
                    nightResourceId = context.getResources().getIdentifier(
                            lightResName, "color", context.getPackageName());
                } else if (resourceName.equals(DarkModeUtils.RESOURCE_NAME_DRAWABLE)) {
                    nightResourceId = context.getResources().getIdentifier(
                            lightResName, "drawable", context.getPackageName());
                }
                view.setBackgroundResource(nightResourceId == 0 ? resId : nightResourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 让RecyclerView缓存在Pool中的item失效
     *
     * @param recyclerView recyclerView
     */
    public static void clearRecycledViewPool(RecyclerView recyclerView) {
        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(recyclerView), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = recyclerView.getRecycledViewPool();
            recycledViewPool.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新adapter
     *
     * @param adapter adapter
     */
    public static void notifyDataSetChanged(RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public static void traversingViewHolder(RecyclerView recyclerView, boolean isDayMode) {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(itemView);
            if (!(viewHolder instanceof IDarkMode)){
                continue;
            }
            IDarkMode iDarkMode = (IDarkMode) viewHolder;
            iDarkMode.darkModeChange(isDayMode);
        }
    }

    /**
     * RecyclerView--removeItemDecoration
     *
     * @param recyclerView recyclerView
     */
    public static void removeItemDecoration(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        for (int i = 0; i < recyclerView.getItemDecorationCount(); i++) {
            recyclerView.removeItemDecoration(recyclerView.getItemDecorationAt(i));
        }
    }

    public static void traversalApplyDarkMode(View view, boolean isDayMode) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                traversalApplyDarkMode(childAt, isDayMode);
            }
        } else if (view instanceof IDarkMode) {
            IDarkMode darkMode = (IDarkMode) view;
            darkMode.darkModeChange(isDayMode);
        }
    }

    public static void applyDarkMode(View view, boolean isDayMode) {
        if (view instanceof IDarkMode) {
            IDarkMode darkMode = (IDarkMode) view;
            darkMode.darkModeChange(isDayMode);
        }
    }

}
