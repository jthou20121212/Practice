package com.study.asm;

import java.util.Arrays;

public class LambdaSimple {

    private int j = 1;

    public void test() {
        //Lambda表达式1
        Runnable run = () -> System.out.println("hello world!");
        run.run();

        //Lambda表达式2
        Arrays.asList(1, 2, 3).forEach(i -> {
            System.out.println("result:" + (i + this.j));
        });
    }

}