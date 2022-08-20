package com.jthou.pro.crazy.autosize;

import androidx.annotation.NonNull;
import androidx.core.util.Pools;

/**
 * @author jthou
 * @date 19-11-2020
 * @since 1.0.0
 */
class TextViewResult {

    private static final int MAX_POOL_SIZE = 40;
    private static final Pools.SynchronizedPool<TextViewResult> sPool = new Pools.SynchronizedPool<>(MAX_POOL_SIZE);

    public int length;

    /**
     * 标记是否使用了 tail
     * 如果最小字号还是显示不下并且尾巴不为空则为 true
     */
    public boolean usedTail;

    /**
     * 最终使用的字体大小
     */
    public float textSize;

    public static TextViewResult obtain(int length, boolean usedTail, float textSize) {
        TextViewResult result = sPool.acquire();
        if (result == null) {
            result = new TextViewResult();
        }
        result.length = length;
        result.usedTail = usedTail;
        result.textSize = textSize;
        return result;
    }

    public static void recycle(@NonNull TextViewResult result) {
        try {
            sPool.release(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "TextViewResult{" +
                "length=" + length +
                ", usedTail=" + usedTail +
                ", textSize=" + textSize +
                '}';
    }

}
