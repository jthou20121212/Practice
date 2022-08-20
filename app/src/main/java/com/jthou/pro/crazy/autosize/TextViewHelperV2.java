package com.jthou.pro.crazy.autosize;

import android.util.TypedValue;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 设置最大字号最小字号最大可显示范围控制显示内容
 * 设置了缓存，适合在 RecyclerView 中使用
 *
 * @author jthou
 * @version 1.0.0
 * @since 1.1.0
 * @date 15-04-2020
 */
public class TextViewHelperV2 {

    private static int putCount;
    private static int createCount;
    private static int evictionCount;
    private static int hitCount;
    private static int missCount;

    private static final int MAX_CAPACITY = 50;

    private static final TextViewParams KEY = TextViewParams.obtain(null, 0, 0, 0, 0);
    private static final Map<TextViewParams, TextViewResult> TEXT_COUNT_MAP = new LinkedHashMap<TextViewParams, TextViewResult>(0, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Entry<TextViewParams, TextViewResult> eldest) {
            TextViewParams key = eldest.getKey();
            TextViewParams.recycle(key);
            TextViewResult value = eldest.getValue();
            TextViewResult.recycle(value);
            evictionCount++;
            return size() > MAX_CAPACITY;
        }
    };

    private Builder mBuilder;

    private TextViewHelperV2(Builder builder) {
        this.mBuilder = builder;
    }

    private static TextViewHelperV2 newInstance(Builder builder) {
        return new TextViewHelperV2(builder);
    }

    public void showText(String text) {
        if (mBuilder == null || mBuilder.target == null || mBuilder.target.get() == null || ObjectUtils.isEmpty(text)) {
            return;
        }

        final TextView textView = mBuilder.target.get();
        final int availableRange = mBuilder.availableRange;
        final float maxFontSize = mBuilder.maxFontSize;
        final float minFontSize = mBuilder.minFontSize;
        final String tail = mBuilder.tail;
        assignmentKey(tail, minFontSize, maxFontSize, availableRange, text.length());
        TextViewResult textViewResult = get(KEY);
        if (textViewResult != null) {
            hitCount++;
            if (textViewResult.usedTail) {
                textView.setText(text.substring(0, textViewResult.length) + tail);
            } else if (text.length() <= textViewResult.length) {
                textView.setText(text);
            } else {
                textView.setText(text.substring(0, textViewResult.length));
            }

            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textViewResult.textSize);
            return;
        }
        try {
            missCount++;
            // 判断最大字号是否能展示的下
            float fontSize = maxFontSize;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);
            final int length = text.length();
            while (textView.getPaint().measureText(text.substring(0, length)) > availableRange) {
                fontSize -= 0.5f;
                if (fontSize < minFontSize) {
                    showTextWithTail(text, tail);
                    return;
                }
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fontSize);
            }
            textView.setText(text);
            createCount++;
            TextViewParams textViewParams = TextViewParams.obtain(tail, minFontSize, maxFontSize, availableRange, text.length());
            textViewResult = TextViewResult.obtain(length, false, fontSize);
            put(textViewParams, textViewResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 异常情况下为了保证文字不被切字把字号字数都设置为最小
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, minFontSize);
            textView.setText(text);
        }
    }

    private void showTextWithTail(final String text, String tail) {
        if (mBuilder == null || mBuilder.target == null || mBuilder.target.get() == null || ObjectUtils.isEmpty(text)) {
            return;
        }
        final TextView textView = mBuilder.target.get();
        final float minFontSize = mBuilder.minFontSize;
        final float maxFontSize = mBuilder.maxFontSize;
        final int availableRange = mBuilder.availableRange;
        try {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, minFontSize);
            // 因为完整的内容最小字号都展示不下，所以从 0 开始遍历
            int end = text.length();
            while (textView.getPaint().measureText(text.substring(0, end) + tail) > availableRange) {
                end--;
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, minFontSize);
            textView.setText(text.substring(0, end) + tail);
            createCount++;
            TextViewParams textViewParams = TextViewParams.obtain(tail, minFontSize, maxFontSize, availableRange, text.length());
            TextViewResult textViewResult = TextViewResult.obtain(end, true, minFontSize);
            put(textViewParams, textViewResult);
        } catch (Exception e) {
            e.printStackTrace();
            // 异常情况下为了保证文字不被切字把字号字数都设置为最小
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, minFontSize);
            textView.setText(text);
        }
    }

    private void assignmentKey(String tail, float minFontSize, float maxFontSize, int availableRange, int textCount) {
        KEY.tail = tail;
        KEY.minFontSize = minFontSize;
        KEY.maxFontSize = maxFontSize;
        KEY.availableRange = availableRange;
        KEY.textCount = textCount;
    }

    private synchronized TextViewResult get(TextViewParams textViewParams) {
        return TEXT_COUNT_MAP.get(textViewParams);
    }

    private synchronized void put(TextViewParams textViewParams, TextViewResult textViewResult) {
        putCount++;
        TEXT_COUNT_MAP.put(textViewParams, textViewResult);
    }

    public static class Builder {

        WeakReference<TextView> target;
        // 最大字号 dp
        float maxFontSize;
        // 最小字号 dp
        float minFontSize;
        // 最大可用范围 px
        int availableRange;
        // 尾巴，比如 ...
        public String tail;

        public Builder setTarget(TextView textView) {
            this.target = new WeakReference<>(textView);
            return this;
        }

        public Builder setMaxFontSize(float maxFontSize) {
            this.maxFontSize = maxFontSize;
            return this;
        }

        public Builder setMinFontSize(float minFontSize) {
            this.minFontSize = minFontSize;
            return this;
        }

        public Builder setAvailableRange(int availableRange) {
            this.availableRange = availableRange;
            return this;
        }

        public Builder setTail(String tail) {
            this.tail = tail;
            return this;
        }

        public TextViewHelperV2 build() {
            return TextViewHelperV2.newInstance(this);
        }

    }

    @Override public synchronized final String toString() {
        int accesses = hitCount + missCount;
        int hitPercent = accesses != 0 ? (100 * hitCount / accesses) : 0;
        return String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]",
                MAX_CAPACITY, hitCount, missCount, hitPercent);
    }

}



