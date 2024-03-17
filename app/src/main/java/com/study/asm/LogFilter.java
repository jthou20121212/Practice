package com.study.asm;

/**
 * 保持虚拟机指令一致，必须使用静态方法
 *
 * @author jthou
 * @since 1.0.0
 * @date 01-08-2023
 */
public class LogFilter {

    // android.util.Log 相关函数，签名必须一致
    public static int d(String tag, String msg) {
        return 0;
    }

    public static int e(String tag, String msg) {
        return 0;
    }

    public static int i(String tag, String msg) {
        return 0;
    }

    public static int v(String tag, String msg) {
        return 0;
    }

    public static int w(String tag, String msg) {
        return 0;
    }

    public static int println(int priority, String tag, String msg) {
        return 0;
    }

    public static void print(Object object) {

    }

    public static void println(Object object) {

    }

}