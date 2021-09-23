package com.example.thx.executor;

import com.example.thx.cache.PerpetualCache;
import com.example.thx.common.Constant;
import com.example.thx.config.Configuration;
import com.example.thx.executor.parameter.ParameterHandler;
import com.example.thx.executor.resultSet.ResultSetHandler;
import com.example.thx.executor.statement.StatementHandler;
import com.example.thx.mapping.MappedStatement;
import lombok.extern.slf4j.Slf4j;


import java.sql.*;
import java.util.List;

/**
 * @Classname SimpleExecutor
 * @Description
 * @Date 2021/9/19 14:00
 * @Created by thx
 */
@Slf4j
public class SimpleExecutor extends BaseExecutor{
    private static Connection connection;
    static {
        initConnection();
    }

    public SimpleExecutor(Configuration configuration) {
        super(configuration);
    }

    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
//            得到数据库连接
            connection = this.getConnection();
//            获取配置类
            Configuration configuration = ms.getConfiguration();
//            实例化 Statement 对象
            StatementHandler statementHandler = configuration.newStatementHandler(ms);
//            获取 PreparedStatement
            preparedStatement = statementHandler.prepare(connection);
//            获取参数
            ParameterHandler parameterHandler = configuration.newParameterHandler(parameter);
//           设置参数
            parameterHandler.setParameters(preparedStatement);
//            执行 sql 语句
            resultSet = statementHandler.query(preparedStatement);
//            获取结果集处理对象
            ResultSetHandler resultSetHandler = configuration.newDefaultResultSetHandler(ms);
//            返回处理结果
            return resultSetHandler.handleResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int doUpdate(MappedStatement ms, Object parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
//            得到数据库连接
            connection = this.getConnection();
//            获取配置类
            Configuration configuration = ms.getConfiguration();
//            实例化 Statement 对象
            StatementHandler statementHandler = configuration.newStatementHandler(ms);
//            获取 PreparedStatement
            preparedStatement = statementHandler.prepare(connection);
//            获取参数
            ParameterHandler parameterHandler = configuration.newParameterHandler(parameter);
//           设置参数
            parameterHandler.setParameters(preparedStatement);
//            返回结果即可
            return statementHandler.update(preparedStatement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //  初始化数据库连接
    private static void initConnection() {
        String url = Configuration.getProperty(Constant.DB_URL_CONF);
        String driver = Configuration.getProperty(Constant.DB_DRIVER_CONF);
        String username = Configuration.getProperty(Constant.DB_USERNAME_CONF);
        String password = Configuration.getProperty(Constant.db_PASSWORD);
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    获取数据库连接
    public Connection getConnection() throws SQLException {
        if (connection != null) {
            log.info("数据库连接成功！！");
            return connection;
        } else {
            throw new SQLException("数据库连接失败");
        }

    }

}
