package com.jthou.generics;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author jthou
 * @date 16-05-2022
 * @since 1.0.0
 */
class GenericTest {

    private static final String TABS = "\t";

    static class Person<T> {

        private Class<T> kind;

        public Person(Class<T> kind) {
            this.kind = kind;
        }

        void print(Object t) {
            System.out.println(kind.isInstance(t));
        }

    }

    static class Child extends Person<String> {

        public Child(Class<String> kind) {
            super(kind);
        }

        @Override
        void print(Object s) {
            super.print(s);
        }

    }

    public static void main(String[] args) {
        try {
            Method genericMethod = GenericTest.class.getMethod("genericMethod", Map.class);
            Type[] genericParameterTypes = genericMethod.getGenericParameterTypes();
            printGenericParam(genericParameterTypes);

            Type genericReturnType = genericMethod.getGenericReturnType();
            printGenericParam(genericReturnType);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

//        Child child = new Child(String.class);
//        child.print(4);
//        child.print("");
//        child.print(null);

//        Class<? extends Child> aClass = child.getClass();
//        Class<?> superclass = aClass.getSuperclass();
//        Type genericSuperclass = aClass.getGenericSuperclass();
//        printGenericParam(superclass);
//        printGenericParam(genericSuperclass);

        Person[] array = (Person[]) Array.newInstance(Person.class, 10);
        System.out.println(Arrays.deepToString(array));
    }

    public static List<String> genericMethod(Map<String, Person> map) {
        return null;
    }

    public static void printGenericParam(Type... types) {
        printGenericParam("", types);
    }

    public static void printGenericParam(String prefix, Type... types) {
        for (Type type : types) {
            if (prefix.length() == 0) {
                String typeName = type.getTypeName();
                System.out.println(prefix + "typeName : " + typeName);
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                System.out.println(prefix + "rawType : " + rawType);
                Type ownerType = parameterizedType.getOwnerType();
                System.out.println(prefix + "ownerType : " + ownerType);
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                printGenericParam(prefix + TABS, actualTypeArguments);
            } else if (type instanceof TypeVariable) {
                System.out.println(prefix + "TypeVariable");
                TypeVariable<?> typeVariable = (TypeVariable<?>) type;
                Type[] bounds = typeVariable.getBounds();
                printGenericParam(prefix + TABS, bounds);
            } else if (type instanceof WildcardType) {
                System.out.println(prefix + "WildcardType");
                WildcardType wildcardType = (WildcardType) type;
                Type[] lowerBounds = wildcardType.getLowerBounds();
                Type[] upperBounds = wildcardType.getUpperBounds();
                printGenericParam(prefix + TABS, lowerBounds);
                printGenericParam(prefix + TABS, upperBounds);
            } else if (type instanceof GenericArrayType) {
                System.out.println(prefix + "GenericArrayType");
                GenericArrayType genericArrayType = (GenericArrayType) type;
                Type genericComponentType = genericArrayType.getGenericComponentType();
                printGenericParam(prefix + TABS, genericComponentType);
            }
            if (prefix.length() != 0) {
                String typeName = type.getTypeName();
                System.out.println(prefix + "typeName : " + typeName);
            }
        }
        if (types.length > 0 ) {
            System.out.println();
        }
    }

    public static void print(List<? extends Number> list) {
        Number n = Double.valueOf("1.0");
        // list.add(n);
        Number tmp = list.get(0);
    }

}
