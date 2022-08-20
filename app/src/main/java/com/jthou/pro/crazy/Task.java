package com.jthou.fuckyou;

/**
 * @author jthou
 * @date 30-07-2020
 * @since 1.0.0
 */
public interface Task<T> {

    void execute();

    void showDialog(T t);

}
