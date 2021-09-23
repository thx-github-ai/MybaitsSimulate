package com.example.thx.cache;

import org.apache.ibatis.cache.CacheException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname PerpetualCache
 * @Description
 * @Date 2021/9/19 14:07
 * @Created by thx
 */
public class PerpetualCache implements Cache{
    private Map<Object, Object> cache = new HashMap<>();

    public int getSize() {
        return this.cache.size();
    }

    public Object putObject(Object key, Object value) {
        return cache.put(key, value);
    }

    public Object getObject(Object key) {
        return this.cache.get(key);
    }

    public Object removeObject(Object key) {
        return this.cache.remove(key);
    }

    public void clear() {
        this.cache.clear();
    }



}
