package com.example.thx.annotation;

import java.lang.annotation.*;

/**
 * @Classname Insert
 * @Description 插入
 * @Date 2021/9/19 13:52
 * @Created by thx
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Insert {
    String value();
}
