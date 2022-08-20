package com.jthou.generics;

import java.math.BigDecimal;

/**
 * @author jthou
 * @date 14-05-2022
 * @since 1.0.0
 */
class Person {

    public <T> Person(T t) {
        System.out.println(t);
    }

    int age;
    String name;

    Job job;

   static class Job {

        BigDecimal salary;

       public Job(BigDecimal salary) {
           this.salary = salary;
       }

       @Override
       public String toString() {
           return "Job{" +
                   "salary=" + salary +
                   '}';
       }
   }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", job=" + job +
                '}';
    }

}
