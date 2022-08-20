package com.jthou.generics

import org.objectweb.asm.Opcodes
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor

class AddTimerClassVisitor : ClassVisitor {

    constructor(api: Int) : super(api)

    constructor(api: Int, classVisitor: ClassVisitor) : super(api, classVisitor)

    override fun visitEnd() {
        val fv = cv?.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "timer", "J", null, null)
        fv?.visitEnd()
        cv?.visitEnd()
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val vm = super.visitMethod(access, name, descriptor, signature, exceptions)
        return object : MethodVisitor(api, vm) {}
    }

}