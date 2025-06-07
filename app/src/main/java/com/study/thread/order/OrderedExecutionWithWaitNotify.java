package com.study.thread.order;

public class OrderedExecutionWithWaitNotify {
    private static final Object lock = new Object();
    private static int counter = 1;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                while (counter != 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Thread 1 starts");
                // 执行线程1的任务
                System.out.println("Thread 1 ends");
                counter++;
                lock.notifyAll();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                while (counter != 2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Thread 2 starts");
                // 执行线程2的任务
                System.out.println("Thread 2 ends");
                counter++;
                lock.notifyAll();
            }
        });

        Thread thread3 = new Thread(() -> {
            synchronized (lock) {
                while (counter != 3) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Thread 3 starts");
                // 执行线程3的任务
                System.out.println("Thread 3 ends");
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
