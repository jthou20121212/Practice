package com.study;

import android.util.SparseArray;

/**
 * @author jthou
 * @date 30-09-2021
 * @since 1.0.0
 */
public class SparseArrayTest {

    public static void main(String[] args) {
        SparseArray<String> sparseArray = new SparseArray<>();
        sparseArray.put(1, "1");
        sparseArray.put(2, "2");
        sparseArray.put(3, "3");
        sparseArray.put(3, "3.3");
        System.out.println("sparseArray : " + sparseArray);
    }

}
