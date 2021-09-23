package com.example.thx.annotation;

import java.lang.annotation.*;

/**
 * @Classname Update
 * @Description 修改
 * @Date 2021/9/19 13:52
 * @Created by thx
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Update {
    String value();
}
