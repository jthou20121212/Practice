package com.study.thread;

import java.util.concurrent.CompletableFuture;

public class ThreadOrderWithCompletableFuture {
    public static void main(String[] args) {
        CompletableFuture<Void> future = CompletableFuture
            .runAsync(() -> System.out.println("A is running"))
            .thenRun(() -> System.out.println("B is running"))
            .thenRun(() -> System.out.println("C is running"));

        future.join(); // 等待所有任务完成
    }
}
