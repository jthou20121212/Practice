package com.study.thread;

public class SequentialExecution {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("第一阶段任务");
        });
        
        Thread t2 = new Thread(() -> {
            System.out.println("第二阶段任务");
        });
        
        Thread t3 = new Thread(() -> {
            System.out.println("第三阶段任务");
        });
        
        t1.start();
        t1.join();  // 等待t1完成
        
        t2.start();
        t2.join();  // 等待t2完成
        
        t3.start();
        t3.join();  // 等待t3完成
        
        System.out.println("所有阶段完成");
    }
}