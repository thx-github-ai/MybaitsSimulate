package com.example.thx.session;

import com.example.thx.config.Configuration;
import com.example.thx.executor.Executor;

import java.util.List;

/**
 * @Classname SqlSession
 * @Description
 * @Date 2021/9/19 14:04
 * @Created by thx
 */
public interface SqlSession {
    <T> T getMapper(Class<T> clazz);

    Configuration getConfiguration();

    <E> E selectOne(String statementId);

    <E> E selectOne(String statementId,Object parameter);


    <E> List<E> selectList(String statementId);

    <E> List<E> selectList(String statementId,Object parameter);

    int update(String statementId,Object parameter);

    int insert(String statementId,Object parameter);

    Executor getExecutor();


    int delete(String statementId, Object[] args);
}
