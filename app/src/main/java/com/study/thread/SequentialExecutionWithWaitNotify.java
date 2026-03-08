package com.study.thread;

public class SequentialExecutionWithWaitNotify {
    private static final Object lock = new Object();
    private static int currentThread = 1; // 1: Thread1, 2: Thread2, 3: Thread3
    
    public static void main(String[] args) {
        Thread t1 = new Thread(new Task(1, 2), "Thread-1");
        Thread t2 = new Thread(new Task(2, 3), "Thread-2");
        Thread t3 = new Thread(new Task(3, 1), "Thread-3");
        
        t1.start();
        t2.start();
        t3.start();
    }
    
    static class Task implements Runnable {
        private final int threadId;
        private final int nextThreadId;
        
        public Task(int threadId, int nextThreadId) {
            this.threadId = threadId;
            this.nextThreadId = nextThreadId;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) { // 每个线程执行3次
                synchronized (lock) {
                    // 如果不是当前轮到的线程，就等待
                    while (currentThread != threadId) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    
                    // 执行任务
                    System.out.println(Thread.currentThread().getName() + " 执行 - 第 " + (i + 1) + " 次");
                    
                    // 设置下一个要执行的线程
                    currentThread = nextThreadId;
                    
                    // 通知所有等待的线程
                    lock.notifyAll();
                }
                
                // 模拟一些处理时间
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}