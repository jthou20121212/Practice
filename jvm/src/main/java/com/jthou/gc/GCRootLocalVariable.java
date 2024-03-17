package com.jthou.gc;

/**
 * 验证虚拟机栈（栈帧中的局部变量）中引用的对象作为 GC Root
 *
 * 开始时:
 * free is 242 M, total is 245 M,
 * 第一次GC完成
 * free is 163 M, total is 245 M,
 * 第二次GC完成
 * free is 243 M, total is 245 M,
 *
 * 当第一次 GC 时，g 作为局部变量，引用了 new 出的对象（80M），并且它作为 GC Roots，在 GC 后并不会被 GC 回收。
 * 当第二次 GC：method() 方法执行完后，局部变量 g 跟随方法消失，不再有引用类型指向该 80M 对象，所以第二次 GC 后此 80M 也会被回收。
 */
public class GCRootLocalVariable {
    private int _10MB = 10 * 1024 * 1024;
    private byte[] memory = new byte[8 * _10MB];

    public static void main(String[] args) {
        System.out.println("开始时:");
        printMemory();
        method();
        System.gc();
        System.out.println("第二次GC完成");
        printMemory();
    }

    public static void method() {
        GCRootLocalVariable g = new GCRootLocalVariable();
        System.gc();
        System.out.println("第一次GC完成");
        printMemory();
    }

    public static void printMemory() {
        System.out.print("free is " + Runtime.getRuntime().freeMemory() / 1024 / 1024 + " M, ");
        System.out.println("total is " + Runtime.getRuntime().totalMemory() / 1024 / 1024 + " M, ");
    }
}
