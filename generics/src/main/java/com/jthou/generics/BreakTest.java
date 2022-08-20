package com.jthou.generics;

/**
 * @author jthou
 * @date 29-05-2022
 * @since 1.1.1
 */
class BreakTest {

    public static void main(String[] args) {
        boolean breakValue = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 20; k++) {
                    if (k == 10) {
                        breakValue = true;
                        break;
                    }
                    System.out.println("3");
                }
                if (breakValue) System.out.println("2");
            }
            if (breakValue) System.out.println("1");
        }
    }

}
