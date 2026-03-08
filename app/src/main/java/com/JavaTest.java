package com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaTest {

    public static void main(String[] args) throws InterruptedException {
        // Thread threadA = new Thread(() -> {
        //     // ThreadA 的代码
        //     System.out.println("threadA");
        // });
        //
        // Thread threadB = new Thread(() -> {
        //     System.out.println("threadB");
        // });
        //
        // Thread threadC = new Thread(() -> {
        //     System.out.println("threadC");
        // });
        //
        // threadA.start();
        // threadA.join();
        //
        // threadB.start();
        // threadB.join();
        //
        // threadC.start();
        // threadC.join();

        // 创建一个单线程的线程池
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // 按顺序提交任务
        executor.submit(() -> {
            System.out.println("A is running");
        });

        executor.submit(() -> {
            System.out.println("B is running");
        });

        executor.submit(() -> {
            System.out.println("C is running");
        });

        // 关闭线程池
        executor.shutdown();
    }

}
