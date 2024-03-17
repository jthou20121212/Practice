package com.jthou.plugins.logfilter

import LogFilterConfig
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class LogFilterClassVisitor(private val config: LogFilterConfig, private val classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM9, classVisitor) {

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (config.enable) {
            val newMv = object : MethodVisitor(Opcodes.ASM9, mv) {
                // opcode 字节码指令
                // owner 类名
                // name 方法名
                // descriptor 方法描述
                override fun visitMethodInsn(opcode: Int, owner: String?, name: String, descriptor: String?, isInterface: Boolean) {
                    // 如果是 android.util.Log 中的函数替换为 LogFilter 中的函数（空实现）
                    val hookPointOpCode = findHookPont(owner, name, descriptor)
                    if (hookPointOpCode != null) {
                        super.visitMethodInsn(Opcodes.INVOKESTATIC, config.className, name, descriptor, isInterface)
                    } else {
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
                    }
                }

                override fun visitLocalVariable(name: String?, descriptor: String?, signature: String?, start: Label?, end: Label?, index: Int) {
                    println("visitLocalVariable name : " + name)
                    println("visitLocalVariable descriptor : " + descriptor)
                    println("visitLocalVariable signature : " + signature)
                    super.visitLocalVariable(name, descriptor, signature, start, end, index)
                }

            }
            return newMv
        } else {
            return mv
        }
    }

    private fun findHookPont(owner: String?, name: String, descriptor: String?): Int? {
        config.listHookPoint.forEach {
            if (it.className == owner) {
                it.methodNameArray.forEachIndexed { index, methodName ->
                    if (name == methodName && (it.methodDescArray.isNullOrEmpty() || it.methodDescArray[index] == descriptor)) {
                        return it.methodType.opcode
                    }
                }
            }
        }
        return null
    }

}