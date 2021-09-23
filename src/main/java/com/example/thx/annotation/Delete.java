package com.example.thx.annotation;

import java.lang.annotation.*;

/**
 * @Classname Delete
 * @Description 删除
 * @Date 2021/9/19 13:52
 * @Created by thx
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Delete {
    String value();
}
