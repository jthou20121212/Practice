//package com.jthou.generics;
//
//import org.objectweb.asm.ClassVisitor;
//import org.objectweb.asm.MethodVisitor;
//
//public class AddTimerClassVisitor extends ClassVisitor {
//
//    public AddTimerClassVisitor(int api, ClassVisitor classVisitor) {
//        super(api, classVisitor);
//    }
//
//    @Override
//    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//
//        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
//
//        MethodVisitor newMethodVisitor = new MethodVisitor(api, methodVisitor) {
//
//        };
//
//        return newMethodVisitor;
//    }
//}