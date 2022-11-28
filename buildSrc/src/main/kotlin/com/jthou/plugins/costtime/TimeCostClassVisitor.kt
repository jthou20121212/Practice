package com.jthou.plugins.costtime

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class TimeCostClassVisitor(nextVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM9, nextVisitor) {

    var className: String? = null

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        className = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        val newMethodVisitor =
            object : AdviceAdapter(Opcodes.ASM9, methodVisitor, access, name, descriptor) {

                @Override
                override fun onMethodEnter() {
                    // 方法开始
                    if (isNeedVisitMethod(name)) {
                        mv.visitLdcInsn(name)
                        mv.visitLdcInsn(className)
                        mv.visitMethodInsn(
                            INVOKESTATIC, "com.study.asm.TimeCache".replace(".", "/"), "putStartTime", "(Ljava/lang/String;Ljava/lang/String;)V", false
                        )
                    }
                    super.onMethodEnter();
                }

                @Override
                override fun onMethodExit(opcode: Int) {
                    // 方法结束
                    if (isNeedVisitMethod(name)) {
                        mv.visitLdcInsn(name)
                        mv.visitLdcInsn(className)
                        mv.visitMethodInsn(
                            INVOKESTATIC, "com.study.asm.TimeCache".replace(".", "/"), "putEndTime", "(Ljava/lang/String;Ljava/lang/String;)V", false
                        )
                    }
                    super.onMethodExit(opcode);
                }
            }
        return newMethodVisitor
    }

    private fun isNeedVisitMethod(name: String?): Boolean {
        return name != "putStartTime" && name != "putEndTime" && name != "<clinit>" && name != "printlnTime" && name != "<init>"
    }

}