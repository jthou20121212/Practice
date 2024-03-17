package com.jthou.jvm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ClassWriterTest {


    public static void main(String[] args) throws Exception {
        Class clazz = User.class;
        String clazzFilePath = Utils.getClassFilePath(clazz);
        ClassReader classReader = new ClassReader(new FileInputStream(clazzFilePath));

        ClassWriter classWriter = new ClassWriter(0);
        classReader.accept(classWriter, 0);

        // 写入文件
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream fos = new FileOutputStream("/Users/zhy/Desktop/copyed.class");
        fos.write(bytes);
        fos.flush();
        fos.close();

    }
}
