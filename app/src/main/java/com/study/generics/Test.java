package com.study.generics;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        ArrayList<String> list1 = new ArrayList<>();
        list1.add("abc");

        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(123);

        System.out.println(list1.getClass() == list2.getClass());
    }

}
