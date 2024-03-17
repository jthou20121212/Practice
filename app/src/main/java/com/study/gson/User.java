package com.study.gson;

public class User {

    public String name;
    public int age;
    public boolean marriage = true;

    public User(String name, int age, boolean marriage) {
        this.name = name;
        this.age = age;
        this.marriage = marriage;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", marriage=" + marriage +
                '}';
    }
}