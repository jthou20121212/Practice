package com.study.shadow;

import android.content.Context;
import android.graphics.Outline;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.Nullable;

public class ShadowOutlineProviderView extends View {
    public ShadowOutlineProviderView(Context context) {
        super(context);
        init();
    }

    public ShadowOutlineProviderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null); // 使用软件层，允许自定义阴影
        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, getWidth(), getHeight(), 16f); // 设置阴影形状
            }
        });
        setClipToOutline(true); // 设置是否裁剪视图外的区域
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setTranslationZ(8f); // 设置阴影深度
    }
}
