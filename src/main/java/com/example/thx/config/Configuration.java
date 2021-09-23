package com.example.thx.config;

import com.example.thx.common.ExecutorType;
import com.example.thx.executor.Executor;
import com.example.thx.executor.SimpleExecutor;
import com.example.thx.executor.parameter.DefaultParameterHandler;
import com.example.thx.executor.parameter.ParameterHandler;
import com.example.thx.executor.resultSet.DefaultResultSetHandler;
import com.example.thx.executor.resultSet.ResultSetHandler;
import com.example.thx.executor.statement.SimpleStatementHandler;
import com.example.thx.executor.statement.StatementHandler;
import com.example.thx.mapping.MappedStatement;
import com.example.thx.mapping.MapperRegistry;
import com.example.thx.session.SqlSession;

import java.util.*;

/**
 * @Classname Configuration
 * @Description 配置类
 * @Date 2021/9/19 13:55
 * @Created by thx
 */
public class Configuration {
//    设置默认执行器类型：
    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;
//    设置 properties 配置文件
    public static Properties configProp = new Properties();
//     mapper 注册器
    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);
//  每一个标签，对应一个唯一的 mappedStatement
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
//  已加载资源
    protected final Set<String> loadedResources = new HashSet<>();

    public static String getProperty(String key){
        return getProperty(key,"mapperLocation");
    }


    public static String getProperty(String key,String defaultValue) {
        return configProp.containsKey(key)?configProp.getProperty(key):defaultValue;
    }

    public void addMappedStatement(String key,MappedStatement mappedStatement){
        this.mappedStatements.put(key, mappedStatement);
    }

    public MappedStatement getMappedStatement(String statementId){
        return this.mappedStatements.get(statementId);
    }

    public <T> void addMapper(Class<T> type){
        this.mapperRegistry.addMapper(type);
    }

    public <T> boolean hasMapper(Class<T> type){
        return this.mapperRegistry.hasMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession){
        return this.mapperRegistry.getMapper(type, sqlSession);
    }

    public void addLoadedResource(String resource){
        this.loadedResources.add(resource);
    }

    public boolean isLoadedResource(String resource){
        return this.loadedResources.contains(resource);
    }

    public Executor newExecutor(){
        return this.newExecutor(this.defaultExecutorType);
    }

    public Executor newExecutor(ExecutorType executorType){
        Executor executor = null;
        if(ExecutorType.REUSE==executorType){

        }
        else if(ExecutorType.BATCH==executorType){

        }
        else if(ExecutorType.SIMPLE==executorType){
            executor =  new SimpleExecutor(this);

        }

        return executor;
    }


    public StatementHandler newStatementHandler(MappedStatement ms) {
        StatementHandler statementHandler  = new SimpleStatementHandler(ms);
        return statementHandler;
    }

    public ParameterHandler newParameterHandler(Object parameter){
        ParameterHandler parameterHandler = new DefaultParameterHandler(parameter);
        return parameterHandler;
    }

    public ResultSetHandler newDefaultResultSetHandler(MappedStatement ms){
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(ms);
        return resultSetHandler;
    }
}
