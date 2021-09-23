package com.example.thx.executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Classname StatementHandler
 * @Description statement的处理器
 * @Date 2021/9/19 13:59
 * @Created by thx
 */
public interface StatementHandler {
//    获取 JDBC 连接
    PreparedStatement prepare(Connection connection);
//    处理 返回结果
    ResultSet query(PreparedStatement preparedStatement);
//    解析 sql 语句
    String parseSql(String originSql);
//    执行 更新操作
    int update(PreparedStatement preparedStatement);

}
