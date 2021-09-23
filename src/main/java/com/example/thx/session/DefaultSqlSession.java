package com.example.thx.session;

import com.example.thx.config.Configuration;
import com.example.thx.executor.Executor;
import com.example.thx.mapping.MappedStatement;

import java.util.List;

/**
 * @Classname DefaultSqlSession
 * @Description
 * @Date 2021/9/19 14:03
 * @Created by thx
 */
public class DefaultSqlSession implements SqlSession{
    private final Configuration configuration;


    private final Executor executor;

    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
        //创建一个执行器
        this.executor = this.configuration.newExecutor();
    }


    @Override
    public <T> T getMapper(Class<T> clazz) {
        return this.configuration.getMapper(clazz,this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }


    @Override
    public <E> E selectOne(String statementId) {
        return selectOne(statementId,null);
    }

    @Override
    public <E> E selectOne(String statementId, Object parameter) {
        List<E> list = this.selectList(statementId, parameter);
        if(list.size()==1){
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public <E> List<E> selectList(String statementId) {
        return selectList(statementId,null);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(statementId);
        return this.executor.query(ms,parameter);
    }

    @Override
    public int update(String statementId, Object parameter) {
        MappedStatement ms = this.configuration.getMappedStatement(statementId);
        return this.executor.update(ms,parameter);
    }

    @Override
    public int insert(String statementId, Object parameter) {
        return update(statementId,parameter);
    }

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public int delete(String statementId, Object[] args) {
        return 0;
    }
}
