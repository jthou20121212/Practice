package com.jthou.generics;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.List;

public class MainTest<T> {

    List<? extends Comparable<?>> list;

    T[] param;

    public static void main(String[] args) throws Exception {
        Class<?> clazz = MainTest.class;
        Field field = clazz.getDeclaredField("list");
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) return;
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        String typeName1 = parameterizedType.getTypeName();
        System.out.println("typeName1 : " + typeName1);
        Type rawType = parameterizedType.getRawType();
        System.out.println("rawType : " + rawType);
        Type ownerType = parameterizedType.getOwnerType();
        System.out.println("ownerType : " + ownerType);
        System.out.println();
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length > 1) return;
        if (!(actualTypeArguments[0] instanceof WildcardType)) return;
        WildcardType wildcardType = (WildcardType) actualTypeArguments[0];
        String typeName2 = wildcardType.getTypeName();
        System.out.println("typeName2 : " + typeName2);
        System.out.println();

        Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length > 1) return;
        if (!(upperBounds[0] instanceof ParameterizedType)) return;
        ParameterizedType upper = (ParameterizedType) upperBounds[0];
        String typeName3 = upper.getTypeName();
        System.out.println("typeName3 : " + typeName3);
        System.out.println("rawType : " + upper.getRawType());
        System.out.println("ownerType : " + upper.getOwnerType());
        System.out.println();

        Type actualTypeArgument = upper.getActualTypeArguments()[0];
        System.out.println("typeName4 : " + actualTypeArgument.getTypeName());
        System.out.println();

        Type[] lowerBounds = wildcardType.getLowerBounds();
        System.out.println("lowerBounds is empty");

//        Class<?> clazz = MainTest.class;
//        Field field = clazz.getDeclaredField("param");
//        Type genericType = field.getGenericType();
//        if (!(genericType instanceof GenericArrayType)) return;
//        GenericArrayType genericArrayType = (GenericArrayType) genericType;
//        String typeName1 = genericArrayType.getTypeName();
//        System.out.println("typeName1 : " + typeName1);
//        System.out.println();
//        Type genericComponentType = genericArrayType.getGenericComponentType();
//        if (!(genericComponentType instanceof TypeVariable)) return;
//        TypeVariable<?> typeVariable = (TypeVariable<?>) genericComponentType;
//        String name = typeVariable.getName();
//        System.out.println("name : " + name);
//        String typeName2 = typeVariable.getTypeName();
//        System.out.println("typeName2 : " + typeName2);
//        Type[] bounds = typeVariable.getBounds();
//        System.out.println(Arrays.deepToString(bounds));
    }

}


