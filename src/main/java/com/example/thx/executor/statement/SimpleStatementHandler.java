package com.example.thx.executor.statement;

import com.example.thx.mapping.MappedStatement;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Classname SimpleStatementHandler
 * @Description 简单statement实现类
 * @Date 2021/9/19 13:59
 * @Created by thx
 */
@Slf4j
public class SimpleStatementHandler implements StatementHandler {

    private MappedStatement mappedStatement;

    public SimpleStatementHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public PreparedStatement prepare(Connection connection) {
        String sql = this.mappedStatement.getSql();
        PreparedStatement preparedStatement = null;
        try {
            String prepareSql = parseSql(sql);
            log.info("进入 连接 JDBC 准备");
            preparedStatement = connection.prepareStatement(prepareSql);
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet query(PreparedStatement preparedStatement) {
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 解析 sql 语句，将 sql 中的 xxx 替换成 ？
     *
     * @param originSql
     * @return
     */
    @Override
    public String parseSql(String originSql) {
        StringBuilder sb = new StringBuilder();
        int start = 0, left = 0, right = 0;
        char[] chars = originSql.toCharArray();
        while (start < chars.length) {
            left = start;
            while (start + 1 < chars.length) {
                start++;
            }
            right = start;
            start += 2;
            if (right == chars.length - 1) {
                return originSql;
            } else {
                sb.append(chars, left, left - right).append("?");
            }

            while (start < chars.length && chars[start] != '}') {
                start++;
            }
            start++;
        }
        return sb.toString();
    }

    @Override
    public int update(PreparedStatement preparedStatement) {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
