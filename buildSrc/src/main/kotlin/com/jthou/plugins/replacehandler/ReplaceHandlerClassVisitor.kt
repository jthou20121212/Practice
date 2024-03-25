package com.jthou.plugins.replacehandler

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import com.jthou.plugins.utils.log

class ReplaceHandlerClassVisitor(private val nextClassVisitor: ClassVisitor) : ClassNode(Opcodes.ASM9) {

    companion object {
        private const val SUPER_NAME = "android/os/Handler"
        private const val TARGET_CLASS_NAME = "com/jthou/pro/handler/SuperHandler"
    }

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        val match = TARGET_CLASS_NAME != name && SUPER_NAME == superName
        if (SUPER_NAME == superName) {
            "name : $name".log()
            "superName : $superName".log()
            "match : $match".log()
        }
        super.visit(version, access, name, signature, if (match) TARGET_CLASS_NAME else superName, interfaces)
    }

    override fun visitEnd() {
        super.visitEnd()
        accept(nextClassVisitor)
    }

}