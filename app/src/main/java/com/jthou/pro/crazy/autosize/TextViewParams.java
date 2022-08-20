package com.jthou.pro.crazy.autosize;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;

import java.util.Objects;

/**
 * @author jthou
 * @date 19-11-2020
 * @since 1.0.0
 */
class TextViewParams {

    private static final int MAX_POOL_SIZE = 40;
    private static final Pools.SynchronizedPool<TextViewParams> sPool = new Pools.SynchronizedPool<>(MAX_POOL_SIZE);

    // 尾巴，比如 ...
    public String tail;
    // 最小字号 dp
    public float minFontSize;
    // 最大字号 dp
    public float maxFontSize;
    // 最大可用范围 px
    public int availableRange;
    // 文本长度
    public int textCount;

    private TextViewParams() {
    }

    public static TextViewParams obtain(String tail, float minFontSize, float maxFontSize, int availableRange, int textCount) {
        TextViewParams params = sPool.acquire();
        if (params == null) {
            params = new TextViewParams();
        }
        params.tail = tail;
        params.minFontSize = minFontSize;
        params.maxFontSize = maxFontSize;
        params.availableRange = availableRange;
        params.textCount = textCount;
        return params;
    }

    public static void recycle(@NonNull TextViewParams params) {
        try {
            sPool.release(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextViewParams that = (TextViewParams) o;
        return Float.compare(that.minFontSize, minFontSize) == 0 &&
                Float.compare(that.maxFontSize, maxFontSize) == 0 &&
                availableRange == that.availableRange &&
                textCount == that.textCount &&
                Objects.equals(tail, that.tail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tail, minFontSize, maxFontSize, availableRange, textCount);
    }

    @Override
    public String toString() {
        return "TextViewParams{" +
                "tail='" + tail + '\'' +
                ", minFontSize=" + minFontSize +
                ", maxFontSize=" + maxFontSize +
                ", availableRange=" + availableRange +
                ", textCount=" + textCount +
                '}';
    }
}
