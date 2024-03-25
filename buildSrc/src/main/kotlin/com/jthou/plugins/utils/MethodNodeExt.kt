package com.jthou.plugins.utils

import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode

fun MethodNode.hasAnnotation(annotationDesc: String): Boolean {
    return visibleAnnotations?.find { annotationNode ->
        annotationNode.desc == annotationDesc
    } != null
}

fun MethodNode.isPublicMethod() : Boolean {
    return access.and(Opcodes.ACC_PUBLIC) != 0
}
