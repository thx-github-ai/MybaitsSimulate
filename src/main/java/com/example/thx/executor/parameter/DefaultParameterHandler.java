package com.example.thx.executor.parameter;

import com.example.thx.annotation.Select;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Classname DefaultParameterHandler
 * @Description 默认的参数处理
 * @Date 2021/9/19 13:57
 * @Created by thx
 */
@Slf4j
public class DefaultParameterHandler implements ParameterHandler{

    private Object parameter;

    public DefaultParameterHandler(Object parameter) {
        this.parameter = parameter;
    }

    @Override
    public void setParameters(PreparedStatement preparedStatement) {
        try {
            if (parameter != null) {
                if (parameter.getClass().isArray()) {
                    Object[] params = (Object[]) parameter;
                    log.info("进入 参数处理");
                    for (int i = 0; i < params.length; i++) {
                        if (params[i] instanceof String) {
                            log.info("此入参是 String 类型");
                        }
                        if (params[i] instanceof Integer) {
                            log.info("此入参是 Integer 类型");
                        }
//                        将 入参类型放入 preparedStatement 中
//                        左边是从 1 开始的索引，右边是参数
                        preparedStatement.setObject(i + 1, params[i]);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
