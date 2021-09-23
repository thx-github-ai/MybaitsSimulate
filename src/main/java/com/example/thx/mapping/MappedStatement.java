package com.example.thx.mapping;

import com.example.thx.config.Configuration;

/**
 * @Classname MappedStatement
 * @Description mapper映射
 * @Date 2021/9/19 14:01
 * @Created by thx
 */
public class MappedStatement {
    private String namespace;
//    标签唯一标识符
    private String sqlId;
//    sql 语句
    private String sql;
//    返回类型
    private String resultType;

    private String sqlCommendType;

    private Configuration configuration;

    public MappedStatement() {
    }

    public MappedStatement(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSqlCommendType() {
        return sqlCommendType;
    }

    public void setSqlCommendType(String sqlCommendType) {
        this.sqlCommendType = sqlCommendType;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }


}
