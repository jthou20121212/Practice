package com.study.thread;

public class JoinExample {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("子线程执行: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start(); // 启动子线程
        
        // 主线程等待子线程执行完毕
        thread.join();
        
        System.out.println("主线程继续执行");
    }
}