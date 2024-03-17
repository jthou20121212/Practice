package com.jthou.generics.whatever

import com.jthou.generics.log
import java.util.*
import kotlin.math.abs

fun main() {
//    class1.filter { it.score < 60 }.log()
//    class1.map { it.copy(name = "小某某") }.log()
//    listOf(class1, class2).flatten().filter { it.score < 60 }.log()
//    class1.groupBy { "${it.score / 10}0分组" }.log()
    class1.sortedByDescending { it.score }.drop(3).dropLast(3).log()

//    testFlatten()
//    takeStudent()
}

fun compareVersion1(version1: String, version2: String): Int {
    val v1 = version1.split(".").map { it.toInt() }
    val v2 = version2.split(".").map { it.toInt() }

    for (i in 0 until maxOf(v1.size, v2.size)) {
        val diff = v1.getOrElse(i) { 0 } - v2.getOrElse(i) { 0 }
        if (diff != 0) return if (diff > 0) 1 else -1
    }
    return 0;
}

fun compareVersion2(version1: String, version2: String): Int {
    return (version1.split(".").map { it.toInt() }
            to version2.split(".").map { it.toInt() })
        .run {
            (0 until maxOf(first.size, second.size))
                .fold(0) { acc, i ->
                    if (acc != 0) acc
                    else first.getOrElse(i) { 0 }.compareTo(second.getOrElse(i) { 0 })
                }
        }
}


fun compareVersion3(version1: String, version2: String): Int {
    // ① 分割
    val list1 = version1.split(".")
    val list2 = version2.split(".")

    var i = 0
    while (i < list1.size || i < list2.size) {
        // ② 遍历元素
        val v1 = list1.getOrNull(i)?.toInt() ?: 0
        val v2 = list2.getOrNull(i)?.toInt() ?: 0

        // ③ 对比
        if (v1 != v2) {
            return v1.compareTo(v2)
        }
        i++
    }

    // ④ 相等
    return 0
}

fun compareVersion4(version1: String, version2: String): Int {
    val list1 = version1.split(".")
    val list2 = version2.split(".")
    return list1.zip(list2) { v1, v2 -> Pair(v1.toInt(), v2.toInt()) }
        .onEach {
            if (it.first != it.second) {
                it.first.compareTo(it.second)
            }
        }.run { list1.size - list2.size }
}

fun compareVersion(version1: String, version2: String): Int =
    version1.split(".")
        .zipLongest(version2.split("."), "0")
        .onEachWithReceiver {
            if (first != second) {
                return first.compareTo(second)
            }
        }.run { return 0 }

private inline fun <T, C : Iterable<T>> C.onEachWithReceiver(action: T.() -> Unit): C {
    return apply { for (element in this) action(element) }
}

private fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int =
    if (this is Collection<*>) this.size else default

private fun <T> Iterator<T>.nextOrNull(): T? = if (hasNext()) next() else null

private fun Iterable<String>.zipLongest(
    other: Iterable<String>,
    default: String
): List<Pair<Int, Int>> {
    val first = iterator()
    val second = other.iterator()
    val list = ArrayList<Pair<Int, Int>>(minOf(collectionSizeOrDefault(10), other.collectionSizeOrDefault(10)))
    while (first.hasNext() || second.hasNext()) {
        val v1 = (first.nextOrNull() ?: default).toInt()
        val v2 = (second.nextOrNull() ?: default).toInt()
        list.add(Pair(v1, v2))
    }
    return list
}

fun mostCommonWord1(paragraph: String, banned: Array<String>) =
    paragraph.toLowerCase(Locale.ROOT)
        .replace("[^a-zA-Z ]".toRegex(), " ")
        .split("\\s+".toRegex())
        .filter { it !in banned.toSet() }
        .groupBy { it }
        .mapValues { it.value.size }
        .maxByOrNull { it.value }
        ?.key ?: throw IllegalArgumentException()


fun solveEquation(equation: String): String {
    // ① 分割等号
    val list = equation.split("=")

    var leftSum = 0
    var rightSum = 0

    val leftList = splitByOperator(list[0])
    val rightList = splitByOperator(list[1])

    // ② 遍历左边的等式，移项，合并同类项
    leftList.forEach {
        if (it.contains("x")) {
            leftSum += xToInt(it)
        } else {
            rightSum -= it.toInt()
        }
    }

    // ③ 遍历右边的等式，移项，合并同类项
    rightList.forEach {
        if (it.contains("x")) {
            leftSum -= xToInt(it)
        } else {
            rightSum += it.toInt()
        }
    }

    // ④ 系数化为一,返回结果
    return when {
        leftSum == 0 && rightSum == 0 -> "Infinite solutions"
        leftSum == 0 && rightSum != 0 -> "No solution"
        else -> "x=${rightSum / leftSum}"
    }
}

// 根据“+”、“-”分割式子
private fun splitByOperator(list: String): List<String> {
    val result = mutableListOf<String>()
    var temp = ""
    list.forEach {
        if (it == '+' || it == '-') {
            if (temp.isNotEmpty()) {
                result.add(temp)
            }
            temp = it.toString()
        } else {
            temp += it
        }
    }

    result.add(temp)
    return result
}

// 提取x的系数：“-2x” ->“-2”
private fun xToInt(x: String) =
    when (x) {
        "x",
        "+x" -> 1
        "-x" -> -1
        else -> x.replace("x", "").toInt()
    }

fun fractionAddition(expression: String): String {
    // Split the expression to several pairs of numerator and denominator
    val numbers = expression
        .replace("-", "+-")
        .split("+")
        .filter { it.isNotEmpty() }
        .map { it.split("/").take(2).map(String::toInt) }

    // Calculate the lcm of all denominators
    val rawDenominator = numbers.map { it[1] }.fold(1) { x, y -> lcm(x, y) }
    // Calculate the sum of all numerators
    val rawNumerator = numbers.sumOf { it[0] * rawDenominator / it[1] }

    // Reformat numerator and denominator through their gcd
    val gcd = abs(gcd(rawNumerator, rawDenominator))
    val denominator = rawDenominator / gcd
    val numerator = rawNumerator / gcd
    return "$numerator/$denominator"
}

// 最大公约数
fun gcd(x: Int, y: Int): Int = if (y == 0) x else gcd(y, x % y)
// 最小公倍数
fun lcm(x: Int, y: Int): Int = x * y / gcd(x, y)


private fun testFlatten() {
    val list = listOf(listOf(1, 2, 3), listOf(4, 5, 6))
    val result = list.flatten()
    println(result)
}

private fun takeStudent() {
    val first3 = class1
        .sortedByDescending { it.score }
        .take(3)

    val last3 = class1
        .sortedByDescending { it.score }
        .takeLast(3)

    println(first3)
    println(last3)
}

private fun joinScore() {
    val sum2 = class1
        .map { it.score.toString() }
        .reduce { acc, score -> acc + score }

    val sum3 = class1
        .map { it.score.toString() }
        .fold("Prefix=") { acc, score -> acc + score }

    println(sum2)
    println(sum3)
}

data class Student(
    val name: String = "",
    val score: Int = 0
)

val class1 = listOf(
    Student("小明", 83),
    Student("小红", 92),
    Student("小李", 50),
    Student("小白", 67),
    Student("小琳", 72),
    Student("小刚", 97),
    Student("小强", 57),
    Student("小林", 86)
)

val class2 = listOf(
    Student("大明", 80),
    Student("大红", 97),
    Student("大李", 53),
    Student("大白", 64),
    Student("大琳", 76),
    Student("大刚", 92),
    Student("大强", 58),
    Student("大林", 88)
)