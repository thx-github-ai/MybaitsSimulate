package com.example.thx.cache;

/**
 * @Classname Cache
 * @Description 缓存接口
 * @Date 2021/9/19 14:06
 * @Created by thx
 */
public interface Cache {


    Object getObject(Object var1);

    Object removeObject(Object var1);

    void clear();

    int getSize();

    Object putObject(Object key,Object value);
}
