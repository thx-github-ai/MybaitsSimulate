package com.example.thx.cache;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Classname CacheKey
 * @Description
 * @Date 2021/9/19 14:06
 * @Created by thx
 */
public class CacheKey {
//    statementId = 全限定类名 + 方法名
    private String statementId;
//    sql 语句
    private String sql;
//    参数
    private Object[] params;

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CacheKey cacheKey = (CacheKey) o;
        return statementId.equals(cacheKey.statementId) &&
                sql.equals(cacheKey.sql) &&
                Arrays.equals(params, cacheKey.params);
    }
    public int hashCode() {
        int result = Objects.hash(statementId, sql);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }
}
