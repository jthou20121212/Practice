package com.jthou.generics.algorithm

fun main() {
    val nums = mutableListOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4, 5, 6, 7, 8, 9, 9, 9, 9)
    var left = 0
    for (right in 0 until nums.size) {
        if (nums[left] != nums[right]) {
            // 如果左边与右边一样则左边不动右边向右移
            // 如果左边与右边不一样则 left 右移一步并且把 right 指向的值赋值给 left
            nums[++left] = nums[right]
        }
    }
    println(++left)
    println(nums.subList(0, left))
}