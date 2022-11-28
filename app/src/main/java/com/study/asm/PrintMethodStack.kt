package com.study.asm

import android.util.Log

object PrintMethodStack {

    private val lineSeparator: String = System.getProperty("line.separator") ?: "\n"
    private const val UNKNOWN_SOURCE = "Unknown Source"

    @JvmStatic
    fun printMethodStack(methodName: String, lineNumber: String) {
        val stackTraceString = Log.getStackTraceString(Throwable())
        val lineSplit = stackTraceString.split(lineSeparator).toMutableList()
        // 第一行是 java.lang.Throwable
        // 第二行是 com.study.asm.PrintMethodStack.printMethodStack 这一行
        // .kt:\d+\)
        val secondLine = lineSplit[1]
        val fileFormat = if(Regex(".kt:\\d+\\)").find(secondLine) != null) ".kt" else ".java"
        // 要处理第三行 不包含就直接输出了
        val thirdLine = lineSplit[2]
        if (thirdLine.contains(UNKNOWN_SOURCE)) {
            val dotSplit = thirdLine.split(".")
            val penultimate = dotSplit[dotSplit.size - 2]
            // 从最后一个 . 往前找如果找到的内容不包含 $ 符号则直接使用否则再使用 $ 符分割
            val contains = penultimate.contains("$")
            val target = if (contains) {
                penultimate.substring(0, penultimate.indexOfFirst { c -> c == '$' })
            } else {
                penultimate
            }
            lineSplit[2] = thirdLine.substring(0, thirdLine.indexOfFirst { c -> c == '(' } + 1)  + "$target$fileFormat:$lineNumber)"
            Log.i("---jthou---", lineSplit.joinToString(separator = lineSeparator) { it })
        } else {
            Log.i("---jthou---", stackTraceString)
        }
    }

}