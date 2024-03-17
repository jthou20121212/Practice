package com.jthou.jvm

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class HelloMethodVisitor(methodVisitor: MethodVisitor, access: Int, name: String?, descriptor: String?) :
    AdviceAdapter(Opcodes.ASM7, methodVisitor, access, name, descriptor) {

    private var isInjectHello = false

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        println("访问方法:$name -注解:$descriptor")
        if (descriptor!! == Type.getDescriptor(InjectHello::class.java)) {
            println("标记了注解:$descriptor, 需要处理")
            isInjectHello = true
        }
        return super.visitAnnotation(descriptor, visible)
    }

    override fun onMethodEnter() {
        super.onMethodEnter()
        methodAccess
        // 此处为方法开头
        if (isInjectHello) {
            println("开始插入代码: [ System.out.println(\"Hello Asm\"); ]")
            // **********方法1************
            // 对应->GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
            getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"))
            // 对应->LDC "Hello Asm"
            visitLdcInsn("Hello Asm")
            // 对应->INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"), Method("println", "(Ljava/lang/String;)V"))
            // **********方法2************
            // 直接从ASMified中复制代码，和方法1是等价的:
            // mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            // mv.visitLdcInsn("Hello Asm");
            //mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
    }

}