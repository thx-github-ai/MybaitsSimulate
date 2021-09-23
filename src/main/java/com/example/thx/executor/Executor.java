package com.example.thx.executor;

import com.example.thx.cache.CacheKey;
import com.example.thx.mapping.MappedStatement;

import java.util.List;

/**
 * @Classname Executor
 * @Description 执行器，执行 sql
 * @Date 2021/9/19 13:59
 * @Created by thx
 */
public interface Executor {

    <E> List<E> query(MappedStatement ms, Object parameter);

    <E> List<E> query(MappedStatement ms, Object parameter, CacheKey cacheKey);

    int update(MappedStatement ms, Object parameter);
//    创建缓存
    CacheKey createCacheKey(MappedStatement ms,Object parameter);
//    缓存没找到 从数据库查询
    <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, CacheKey cacheKey);
}
