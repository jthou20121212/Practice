package com.jthou.jvm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface InjectHello {
}