package com.study.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadOrderWithBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        Thread a = new Thread(() -> {
            System.out.println("A is running");
            queue.offer("A_DONE");
        });

        Thread b = new Thread(() -> {
            try {
                queue.take(); // 等待A的信号
                System.out.println("B is running");
                queue.offer("B_DONE");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread c = new Thread(() -> {
            try {
                queue.take(); // 等待B的信号
                System.out.println("C is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 先启动B和C，它们会等待队列中的信号
        b.start();
        c.start();
        Thread.sleep(100); // 确保B和C先开始等待
        a.start(); // 最后启动A
    }
}
