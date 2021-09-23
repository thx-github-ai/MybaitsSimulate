package com.example.thx.session;

/**
 * @Classname SqlSessionFactory
 * @Description
 * @Date 2021/9/19 14:04
 * @Created by thx
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
