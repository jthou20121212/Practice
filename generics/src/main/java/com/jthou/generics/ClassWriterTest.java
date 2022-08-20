package com.jthou.generics;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ClassWriterTest {

    public static void main(String[] args) throws Exception {
        Class clazz = C.class;
        String clazzFilePath = Utils.getClassFilePath(clazz);
        ClassReader classReader = new ClassReader(new FileInputStream(clazzFilePath));

        ClassWriter classWriter = new ClassWriter(0);

        AddTimerClassVisitor addTimerClassVisitor = new AddTimerClassVisitor(Opcodes.ASM5, classWriter);
        classReader.accept(addTimerClassVisitor, 0);

        // 写入文件
        byte[] bytes = classWriter.toByteArray();
        FileOutputStream fos = new FileOutputStream("/Users/huxiu/Desktop/copyed.class");
        fos.write(bytes);
        fos.flush();
        fos.close();

    }
}