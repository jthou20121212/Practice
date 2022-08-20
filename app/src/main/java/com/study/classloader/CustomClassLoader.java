package com.study.classloader;

/**
 * @author jthou
 * @date 23-03-2022
 * @since 1.0.0
 */
class CustomClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        return super.findClass(name);
    }

}
