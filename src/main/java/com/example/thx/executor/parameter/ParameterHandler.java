package com.example.thx.executor.parameter;

import java.sql.PreparedStatement;

/**
 * @Classname ParameterHandler
 * @Description 参数处理器接口
 * @Date 2021/9/19 13:57
 * @Created by thx
 */
public interface ParameterHandler {
    void setParameters(PreparedStatement parameters);
}
