package com.study.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.FileInputStream;
import java.util.List;

public class TreeApiTest {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = User.class;
        // 首先我们拿到 class 文件的路径；
        String clazzFilePath = Utils.getClassFilePath(clazz);
        // 然后交给 ClassReader
        ClassReader classReader = new ClassReader(new FileInputStream(clazzFilePath));
        // 再构造一个 ClassNode 对象
        ClassNode classNode = new ClassNode(Opcodes.ASM5);
        // 调用 ClassReader.accept()方法完成对 class 遍历，并把相关信息记录到 ClassNode 对象中；
        classReader.accept(classNode, 0);

        List<MethodNode> methods = classNode.methods;
        List<FieldNode> fields = classNode.fields;

        System.out.println("methods:");
        for (MethodNode methodNode : methods) {
            System.out.println(methodNode.name + ", " + methodNode.desc);
        }

        System.out.println("fields:");
        for (FieldNode fieldNode : fields) {
            System.out.println(fieldNode.name + ", " + fieldNode.desc);
        }

    }

}
