package com.example.thx.executor.resultSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @Classname ResultSetHandler
 * @Description 结果集接口
 * @Date 2021/9/19 13:58
 * @Created by thx
 */
public interface ResultSetHandler {
    <E> List<E> handleResultSet(ResultSet resultSet);
}
