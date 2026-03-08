package com.study.thread;

import java.util.concurrent.CyclicBarrier;

public class ThreadOrderWithCyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier barrier1 = new CyclicBarrier(2); // A和main线程
        CyclicBarrier barrier2 = new CyclicBarrier(2); // B和main线程

        Thread a = new Thread(() -> {
            try {
                System.out.println("A is running");
                barrier1.await(); // 等待main线程
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread b = new Thread(() -> {
            try {
                barrier1.await(); // 等待A完成
                System.out.println("B is running");
                barrier2.await(); // 等待main线程
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread c = new Thread(() -> {
            try {
                barrier2.await(); // 等待B完成
                System.out.println("C is running");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        a.start();
        b.start();
        c.start();
    }
}
