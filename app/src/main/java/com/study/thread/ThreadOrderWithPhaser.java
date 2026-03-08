package com.study.thread;

import java.util.concurrent.Phaser;

public class ThreadOrderWithPhaser {
     public static void main(String[] args) {
        Phaser phaser = new Phaser(1); // 注册主线程

        // 线程A
        Thread threadA = new Thread(() -> {
            System.out.println("A is running");
            phaser.arriveAndDeregister(); // A完成，注销
        });

        // 线程B
        Thread threadB = new Thread(() -> {
            phaser.register(); // 注册到Phaser
            phaser.arriveAndAwaitAdvance(); // 等待前一阶段完成
            System.out.println("B is running");
            phaser.arriveAndDeregister(); // B完成，注销
        });

        // 线程C
        Thread threadC = new Thread(() -> {
            phaser.register(); // 注册到Phaser
            phaser.arriveAndAwaitAdvance(); // 等待前一阶段完成
            phaser.arriveAndAwaitAdvance(); // 等待前一阶段完成

            System.out.println("C is running");
            phaser.arriveAndDeregister(); // C完成，注销
        });

        // 启动线程（启动顺序无关）
        threadC.start();
        threadB.start();
        threadA.start();
    }
}
