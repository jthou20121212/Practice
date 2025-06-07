package com.study.thread.order;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderedExecutionWithLock {
    public static void main(String[] args) {
        // 创建一个可重入锁
        Lock lock = new ReentrantLock();

        // 为线程间的顺序控制创建三个条件变量
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();

        // 创建一个共享变量来控制线程的执行顺序
        int[] turn = {1}; // 1 表示线程 1 先执行

        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                while (turn[0] != 1) {
                    condition1.await(); // 等待自己的执行机会
                }
                System.out.println("Thread 1 starts");
                Thread.sleep(1000); // 模拟任务执行
                System.out.println("Thread 1 ends");
                turn[0] = 2; // 交给线程 2 执行
                condition2.signal(); // 唤醒线程 2
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                while (turn[0] != 2) {
                    condition2.await(); // 等待自己的执行机会
                }
                System.out.println("Thread 2 starts");
                Thread.sleep(1000); // 模拟任务执行
                System.out.println("Thread 2 ends");
                turn[0] = 3; // 交给线程 3 执行
                condition3.signal(); // 唤醒线程 3
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        Thread thread3 = new Thread(() -> {
            lock.lock();
            try {
                while (turn[0] != 3) {
                    condition3.await(); // 等待自己的执行机会
                }
                System.out.println("Thread 3 starts");
                Thread.sleep(1000); // 模拟任务执行
                System.out.println("Thread 3 ends");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();
    }
}

