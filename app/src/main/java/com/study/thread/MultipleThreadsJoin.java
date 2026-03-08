package com.study.thread;

import java.util.ArrayList;
import java.util.List;

public class MultipleThreadsJoin {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            final int taskId = i;
            Thread thread = new Thread(() -> {
                System.out.println("任务 " + taskId + " 开始执行");
                try {
                    Thread.sleep(1000 + taskId * 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务 " + taskId + " 完成");
            });
            threads.add(thread);
            thread.start();
        }
        
        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("所有任务完成，主线程继续");
    }
}