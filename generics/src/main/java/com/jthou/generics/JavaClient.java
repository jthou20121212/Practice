package com.jthou.generics;

public class JavaClient {

    public static void main(String[] args) {
        int[] numbers = {3719,
                4248,
                8551,
                9295,
                8715,
                9208,
                5097,
                3816,
                6152,
                6330
        };

        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        System.out.println(total);
    }

}