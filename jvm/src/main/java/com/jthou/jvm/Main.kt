package com.jthou.jvm

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    val targetFile = "${System.getProperty("user.dir")}/jvm/src/main/java/com/jthou/jvm/Target.class"
    println("目标文件:$targetFile")
    val startTime = System.currentTimeMillis()
    val fis = FileInputStream(targetFile)
    val reader = ClassReader(fis)// 读取文件
    val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES)// 写文件
    println("开始修改文件")
    // HelloClassVisitor为自定义的类访问器
    reader.accept(HelloClassVisitor(writer), ClassReader.EXPAND_FRAMES)// 访问class文件并修改
    val bytes = writer.toByteArray()// 获取修改后的流
    val fos = FileOutputStream(targetFile)
    println("写入文件:$targetFile")
    fos.write(bytes)// 将修改后字节流写入文件
    fos.flush()
    println("关闭文件流")
    fis.close()
    fos.close()
    println("本次插桩耗时:${System.currentTimeMillis() - startTime} ms")
}