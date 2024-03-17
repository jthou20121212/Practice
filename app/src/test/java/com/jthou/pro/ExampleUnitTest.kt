package com.jthou.pro

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.study.gson.User
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun javaModelDefaultValue() {
        val json = "{\"name\":\"haha\"}"
        val gson = GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val user = gson.fromJson(json, User::class.java)
        println(user)
        println(user.name)
        println(gson.toJson(user))
    }

}
