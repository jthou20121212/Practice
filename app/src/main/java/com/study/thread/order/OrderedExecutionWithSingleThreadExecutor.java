package com.study.thread.order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderedExecutionWithSingleThreadExecutor {
    public static void main(String[] args) {
        // 创建一个单线程的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 提交任务
        executorService.submit(() -> {
            System.out.println("Task 1 starts");
            try {
                Thread.sleep(1000); // 模拟任务执行
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task 1 ends");
        });

        executorService.submit(() -> {
            System.out.println("Task 2 starts");
            try {
                Thread.sleep(1000); // 模拟任务执行
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task 2 ends");
        });

        executorService.submit(() -> {
            System.out.println("Task 3 starts");
            try {
                Thread.sleep(1000); // 模拟任务执行
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task 3 ends");
        });

        // 关闭线程池
        executorService.shutdown();
    }
}
