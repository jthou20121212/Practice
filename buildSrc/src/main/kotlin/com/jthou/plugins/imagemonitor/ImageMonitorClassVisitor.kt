package com.jthou.plugins.imagemonitor

import ImageMonitorConfig
import com.android.tools.r8.internal.mv
import com.jthou.plugins.hasAnnotation
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.LdcInsnNode
import org.objectweb.asm.tree.LineNumberNode
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.VarInsnNode


/**
 * 将所有继承 android.widget.ImageView 的子类替换为继承 com.study.asm.MonitorImageView
 */
class ImageMonitorClassVisitor(private val config: ImageMonitorConfig, private val nextClassVisitor: ClassVisitor) : ClassNode(Opcodes.ASM9) {

//    override fun visit(
//        version: Int,
//        access: Int,
//        name: String?,
//        signature: String?,
//        superName: String?,
//        interfaces: Array<out String>?
//    ) {
//        if (superName == config.src_class_name && name != config.dst_class_name) {
//            super.visit(version, access, name, signature, config.dst_class_name, interfaces)
//        } else {
//            super.visit(version, access, name, signature, superName, interfaces)
//        }
//    }

//    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
//        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
//
//        val newMv = object : MethodVisitor(Opcodes.ASM9, mv) {
//            // opcode 字节码指令
//            // owner 类名
//            // name 方法名
//            // descriptor 方法描述
//            override fun visitMethodInsn(opcode: Int, owner: String?, name: String, descriptor: String?, isInterface: Boolean) {
//                // 如果是 android.util.Log 中的函数替换为 LogFilter 中的函数（空实现）
//                val findHookPont = findHookPont(name, descriptor)
//                if (findHookPont) {
//                    mv.visitVarInsn(Opcodes.ALOAD, 0)
//                    mv.visitVarInsn(Opcodes.ALOAD, 1)
//                    super.visitMethodInsn(Opcodes.INVOKESTATIC, config.weave_class_name, name, config.weave_method_desc, isInterface)
//                } else {
//                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)
//                }
//            }
//
//        }
//        return newMv
//    }

    override fun visitEnd() {
        super.visitEnd()
        val shouldHookMethodList = mutableSetOf<MethodNode>()
        // 所有需要打印方法栈的方法
        methods.forEach { methodNode: MethodNode ->
            if (findHookPont(methodNode.name, methodNode.desc)) {
                shouldHookMethodList.add(methodNode)
            }
        }

        // 插入打印方法栈对应的代码
        shouldHookMethodList.forEach {
            if (it.instructions != null && it.instructions.size() > 0) {
                val list = InsnList()
                list.add(VarInsnNode(Opcodes.ALOAD, 1))
                list.add(
                    MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "com.study.asm.ImageMonitor".replace(".", "/"),
                        "load",
                        "(Ljava/lang/String;)V"
                    )
                )
                it.instructions.insert(list)
            }
        }

        accept(nextClassVisitor)
    }

    private fun findHookPont(name: String?, descriptor: String?): Boolean {
        return "load" == name && "(Ljava/lang/String;)Lcom/bumptech/glide/RequestBuilder;" == descriptor
    }

}