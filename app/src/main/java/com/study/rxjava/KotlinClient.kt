package com.study.rxjava

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.study.User

fun main() {
    val json = "{\"userName\":\"haha\"}"

    // gson
    val gson: Gson = GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    val user = gson.fromJson(json, User::class.java)
    println(user)
    println(user.userName)
    println(gson.toJson(user))

//    // Moshi
//    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//    val jsonAdapter: JsonAdapter<User> = moshi.adapter(User::class.java)
//    val user = jsonAdapter.fromJson(json)
//    println(user)

}