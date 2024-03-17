package com.study;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.study.gson.User;

/**
 * @author jthou
 * @date 22-09-2021
 * @since 1.0.0
 */
class GsonTest {

    public static void main(String[] args) {
        String json = "{\"name\":\"haha\"}";
        Gson gson = new GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        User user = gson.fromJson(json, User.class);
        System.out.println(user);
        System.out.println(user.name);
        System.out.println(gson.toJson(user));
    }

}
