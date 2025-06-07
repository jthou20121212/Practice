package com.study.thread.order;

import java.util.concurrent.Semaphore;

/**
 * 许可：Semaphore 管理一个许可的数量，线程通过获取许可（acquire()）来访问共享资源。许可的数量限制了同时能够执行的线程数。
 * 控制并发：Semaphore 的常见用途是控制对资源（如数据库连接、文件、网络带宽等）的并发访问数。
 * 计数器：Semaphore 内部维护一个计数器，每次线程获取许可时，计数器减 1；每次线程释放许可时，计数器加 1。计数器值为 0 时，后续线程必须等待，直到有许可被释放。
 */
public class OrderedExecutionWithSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore1 = new Semaphore(1); // 线程1可以立即开始
        Semaphore semaphore2 = new Semaphore(0); // 线程2等待线程1完成
        Semaphore semaphore3 = new Semaphore(0); // 线程3等待线程2完成

        // 线程1
        Thread thread1 = new Thread(() -> {
            try {
                semaphore1.acquire(); // 获取许可，开始执行
                System.out.println("Thread 1 starts");
                Thread.sleep(1000); // 模拟任务
                System.out.println("Thread 1 ends");
                semaphore2.release(); // 释放许可，通知线程2
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 线程2
        Thread thread2 = new Thread(() -> {
            try {
                semaphore2.acquire(); // 等待线程1释放许可
                System.out.println("Thread 2 starts");
                Thread.sleep(1000); // 模拟任务
                System.out.println("Thread 2 ends");
                semaphore3.release(); // 释放许可，通知线程3
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 线程3
        Thread thread3 = new Thread(() -> {
            try {
                semaphore3.acquire(); // 等待线程2释放许可
                System.out.println("Thread 3 starts");
                Thread.sleep(1000); // 模拟任务
                System.out.println("Thread 3 ends");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
