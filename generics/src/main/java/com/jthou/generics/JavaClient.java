package com.jthou.generics;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaClient {

    ArrayList<? extends Animal> list4 = new ArrayList<Cat>(){};

    static class Animal{}//父类

    static class Dog extends Animal{}//子类

    static class Cat extends Animal{}//子类

    public static void main(String[] args) {
        List<Number> numberList = new ArrayList<>();

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);

//        JavaClient.copyAll(numberList, integerList); // 报错

        AnyExtKt.log("邀酒摧肠三杯醉");

        AnyExtKt.log("e08f7c6899abaf56ee4da3ea0fbddb38".length());


    }

    private static <T> void copyAll(List<T> to, List<? extends T> from) {
        to.addAll(from);
    }

}