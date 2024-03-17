package com.study;

import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

public class CommonOutlineProvider extends ViewOutlineProvider {
    private final int width;
    private final int height;
    private final int radius;

    public CommonOutlineProvider(int width, int height, int radius) {
        this.width = width;
        this.height = height;
        this.radius = radius;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, width, height, radius);
    }

}