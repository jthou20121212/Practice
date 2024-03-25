package com.jthou.plugins.methodstack

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

import com.jthou.plugins.utils.hasAnnotation
import org.objectweb.asm.Handle
import org.objectweb.asm.tree.*

class PrintMethodStackClassVisitor(private val config: PrintMethodStackConfig, private val nextClassVisitor: ClassVisitor) : ClassNode(Opcodes.ASM9) {

    override fun visitEnd() {
        super.visitEnd()
        val shouldHookMethodList = mutableSetOf<MethodNode>()
        // 所有需要打印方法栈的方法
        methods.forEach { methodNode: MethodNode ->
            // ButterKnife
            if (methodNode.hasAnnotation("Lbutterknife/OnClick;")) {
                shouldHookMethodList.add(methodNode)
            }

            // JavascriptInterface
            if (methodNode.hasAnnotation("Landroid/webkit/JavascriptInterface;")) {
                shouldHookMethodList.add(methodNode)
            }

            // 如果是匿名内部类
            if (methodNode.isAnonymousInnerClass()) {
                shouldHookMethodList.add(methodNode)
            }

            // 如果只有一个 android.view.View 参数并且是 public 方法
            // 如果不想打印可以使用不打印方法栈的注解
            // ViewHolder 的构造方法中是一个 View 所以屏蔽 <init> 方法
//            println("name : ${methodNode.name}")
//            println("desc : ${methodNode.desc}")
//            println("access : ${methodNode.access}")
//            println("Opcodes.ACC_PUBLIC : ${Opcodes.ACC_PUBLIC}")
//            println("and : ${methodNode.access.and(Opcodes.ACC_PUBLIC)}")
            if (methodNode.name != "<init>" && methodNode.desc == "(Landroid/view/View;)V" && methodNode.access.and(Opcodes.ACC_PUBLIC) != 0) {
                shouldHookMethodList.add(methodNode)
            }

            // 如果是 lambda 表达式
            methodNode.isLambdaExpressions()?.let {
                shouldHookMethodList.add(it)
            }
        }
        // 插入打印方法栈对应的代码
        shouldHookMethodList.forEach {
            if (it.instructions != null && it.instructions.size() > 0) {
                val line = (it.instructions.filterIsInstance<LineNumberNode>()[0] as? LineNumberNode)?.line ?: 0
                val list = InsnList()
                list.add(LdcInsnNode(it.name))
                list.add(LdcInsnNode(line.toString()))
                list.add(
                    MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "com.study.asm.PrintMethodStack".replace(".", "/"),
                        "printMethodStack",
                        "(Ljava/lang/String;Ljava/lang/String;)V"
                    )
                )
                it.instructions.insert(list)
            }
        }

        accept(nextClassVisitor)
    }

    private fun MethodNode.isAnonymousInnerClass(): Boolean {
        config.hookPointList.forEach { point->
            if (interfaces.find { it == point.interfaceName && name + desc == point.methodDesc } != null) {
                return true
            }
        }
        return false
    }

    private fun MethodNode.isLambdaExpressions(): MethodNode? {
        instructions.filterIsInstance(InvokeDynamicInsnNode::class.java).filter {
            it.isHookPoint()
        }.forEach {
            (it.bsmArgs[1] as? Handle)?.let {
                // 找到 lambda 指向的目标方法 这里判断是 lambda 匿名类并且有 android.view.View 参数应该也可以并且比较简单
                return methods.find { m -> m.name + m.desc == it.name + it.desc }
            }
        }
        return null
    }

    private fun InvokeDynamicInsnNode.isHookPoint(): Boolean {
        config.hookPointList.forEach {
            if (name == it.methodName && desc.endsWith(it.interfaceSignSuffix)) return true
        }
        return false
    }

}