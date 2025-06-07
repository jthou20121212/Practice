package com.study.thread.order;

public class OrderedExecutionWithJoin {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1 starts");
            try {
                Thread.sleep(1000); // 模拟任务
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread 1 ends");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2 starts");
            try {
                Thread.sleep(1000); // 模拟任务
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread 2 ends");
        });

        Thread thread3 = new Thread(() -> {
            System.out.println("Thread 3 starts");
            try {
                Thread.sleep(1000); // 模拟任务
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread 3 ends");
        });

        // 启动线程并使用 join 保证顺序
        try {
            thread1.start();
            thread1.join(); // 等待线程1执行完成
            thread2.start();
            thread2.join(); // 等待线程2执行完成
            thread3.start();
            thread3.join(); // 等待线程3执行完成
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
