package com.example.thx.mapping;


import com.example.thx.config.Configuration;
import com.example.thx.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MapperRegistry
 * @Description 注册器
 * @Date 2021/9/19 14:02
 * @Created by thx
 */
public class MapperRegistry {
    private final Configuration config;
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap();

    public MapperRegistry(Configuration config) {
        this.config = config;
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession){
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>)this.knownMappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }

    public <T> boolean hasMapper(Class<T> type) {
        return this.knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (this.config.hasMapper(type)) {
                return;
            }
            this.knownMappers.put(type, new MapperProxyFactory<>(type));
            MapperAnnotationBuilder parse = new MapperAnnotationBuilder(this.config, type);
            parse.parse();
        }

    }
}
