package com.study.asm;

import com.utils.AnyExtKt;

/**
 * 打印 glide 加载图片时传入的图片地址
 *
 * @author jthou
 * @date 08-08-2023
 * @since 1.0.0
 */
public class ImageMonitor {

    public static void load(String string) {
        AnyExtKt.log("图片地址：" + string);
    }

}