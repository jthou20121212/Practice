package com.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadOrderWithAtomicInteger {
    private static final AtomicInteger counter = new AtomicInteger(1);

    public static void main(String[] args) {
        Thread a = new Thread(() -> {
            while (counter.get() != 1) {
                // 忙等待，直到轮到自己
                Thread.yield();
            }
            System.out.println("A is running");
            counter.incrementAndGet(); // counter = 2
        });

        Thread b = new Thread(() -> {
            while (counter.get() != 2) {
                Thread.yield();
            }
            System.out.println("B is running");
            counter.incrementAndGet(); // counter = 3
        });

        Thread c = new Thread(() -> {
            while (counter.get() != 3) {
                Thread.yield();
            }
            System.out.println("C is running");
            counter.incrementAndGet(); // counter = 4
        });

        a.start();
        b.start();
        c.start();
    }
}
