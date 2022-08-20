package com;

public class VolatileDemo {

    private static boolean ready;

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread is running...");
            while (!ready) ; // 如果ready为false，则死循环
            System.out.println("MyThread is end");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new MyThread().start();
        Thread.sleep(1000);
        ready = true;
        System.out.println("ready = " + ready);
        Thread.sleep(5000);
        System.out.println("main thread is end.");
    }

}
