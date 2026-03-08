package com.study.thread;

import java.util.concurrent.Semaphore;

public class ThreadOrderWithSemaphore {
    public static void main(String[] args) throws InterruptedException {
        // 初始：A有1个许可，B和C有0个许可
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(0);
        Semaphore semaphoreC = new Semaphore(0);

        Thread a = new Thread(() -> {
            try {
                semaphoreA.acquire(); // A获取许可
                System.out.println("A is running");
                semaphoreB.release(); // 释放B的许可
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread b = new Thread(() -> {
            try {
                semaphoreB.acquire(); // B等待许可
                System.out.println("B is running");
                semaphoreC.release(); // 释放C的许可
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread c = new Thread(() -> {
            try {
                semaphoreC.acquire(); // C等待许可
                System.out.println("C is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        a.start();
        b.start();
        c.start();
    }
}
