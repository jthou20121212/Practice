package com.study.thread;

public class JoinTimeoutExample {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000); // 子线程睡眠5秒
                System.out.println("子线程完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        
        // 主线程最多等待2秒
        thread.join(2000);
        
        System.out.println("主线程继续 - 可能子线程还未完成");
    }
}