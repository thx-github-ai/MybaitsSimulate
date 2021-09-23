package com.example.thx.mapping;



import com.example.thx.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname MapperProxyFactory
 * @Description
 * @Date 2021/9/19 14:01
 * @Created by thx
 */
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;
    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession) {
        MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession,this.mapperInterface);
        return this.newInstance(mapperProxy);
    }

    public <T> T newInstance(MapperProxy<T> mapperProxy){
        return (T) Proxy.newProxyInstance(this.mapperInterface.getClassLoader(),new Class[]{this.mapperInterface},mapperProxy);
    }
}
