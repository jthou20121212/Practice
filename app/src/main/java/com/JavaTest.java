package com;

public class JavaTest {

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            // ThreadA 的代码
            System.out.println("threadA");
        });

        Thread threadB = new Thread(() -> {
            try {
                threadA.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("threadB");
        });

        Thread threadC = new Thread(() -> {
            try {
                threadB.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("threadC");
        });
        threadA.start();
        threadB.start();
        threadC.start();
    }

}
