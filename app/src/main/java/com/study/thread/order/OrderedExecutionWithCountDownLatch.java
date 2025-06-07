package com.study.thread.order;

import java.util.concurrent.CountDownLatch;

public class OrderedExecutionWithCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        // 创建 3 个 CountDownLatch 对象
        CountDownLatch latch1 = new CountDownLatch(1); // 用于线程2等待线程1完成
        CountDownLatch latch2 = new CountDownLatch(1); // 用于线程3等待线程2完成

        // 创建并启动线程1
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("Thread 1 starts");
                // 执行线程1的任务
                Thread.sleep(1000);
                System.out.println("Thread 1 ends");
                latch1.countDown(); // 线程1完成后释放 latch1
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 创建并启动线程2
        Thread thread2 = new Thread(() -> {
            try {
                latch1.await(); // 等待线程1完成
                System.out.println("Thread 2 starts");
                // 执行线程2的任务
                Thread.sleep(1000);
                System.out.println("Thread 2 ends");
                latch2.countDown(); // 线程2完成后释放 latch2
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 创建并启动线程3
        Thread thread3 = new Thread(() -> {
            try {
                latch2.await(); // 等待线程2完成
                System.out.println("Thread 3 starts");
                // 执行线程3的任务
                Thread.sleep(1000);
                System.out.println("Thread 3 ends");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();

        // 等待所有线程完成
        thread1.join();
        thread2.join();
        thread3.join();
    }
}
