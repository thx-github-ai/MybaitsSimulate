package com.example.thx.executor;

import com.example.thx.cache.CacheKey;
import com.example.thx.cache.PerpetualCache;
import com.example.thx.config.Configuration;
import com.example.thx.mapping.MappedStatement;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * @Classname BaseExecutor
 * @Description
 * @Date 2021/9/19 13:59
 * @Created by thx
 */
public abstract class BaseExecutor implements Executor{
    protected Configuration configuration;
    protected PerpetualCache localCache;


    public BaseExecutor(Configuration configuration) {
        this.configuration = configuration;
        this.localCache = new PerpetualCache();
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        CacheKey cacheKey = createCacheKey(ms, parameter);
        return query(ms, parameter, cacheKey);
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter, CacheKey cacheKey) {
        List<E> list = (List<E>) localCache.getObject(cacheKey);
//        如果缓存中没找到，说明要去数据库里找到了
        if (list == null) {
            return queryFromDatabase(ms, parameter, cacheKey);
        }
        return list;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        localCache.clear();
        return doUpdate(ms, parameter);
    }

//    创建缓存
    @Override
    public CacheKey createCacheKey(MappedStatement ms, Object parameter) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.setSql(ms.getSql());
        cacheKey.setParams((Object[]) parameter);
        cacheKey.setStatementId(ms.getSqlId());
        return cacheKey;
    }
//      从数据库查询
@Override
    public <E> List<E> queryFromDatabase(MappedStatement ms, Object parameter, CacheKey cacheKey) {
        List<E> list = doQuery(ms, parameter);
//        查询数据库结果，放入缓存中
        localCache.putObject(cacheKey, list);
        return list;
    }

    public abstract <E> List<E> doQuery(MappedStatement ms, Object parameter);

    public abstract int doUpdate(MappedStatement ms,Object parameter);
}
