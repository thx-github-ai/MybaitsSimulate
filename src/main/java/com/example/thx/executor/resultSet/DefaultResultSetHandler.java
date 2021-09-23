package com.example.thx.executor.resultSet;

import com.example.thx.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname DefaultResultSetHandler
 * @Description 默认的结果集映射
 * @Date 2021/9/19 13:58
 * @Created by thx
 */
public class DefaultResultSetHandler implements ResultSetHandler{

    private MappedStatement mappedStatement;

    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }

    @Override
    public <E> List<E> handleResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                String returnType = mappedStatement.getResultType();
                if (returnType.equals("Integer") || returnType.equals("Long")) {
                    Class<?> entityClass = Class.forName("java.lang.Integer");
                    int count = 0;
                    while (resultSet.next()){
                        count = resultSet.getInt("count(*)");
                    }
                    E entity = (E) new Integer(count);
                    List<E> countList = new ArrayList<>();
                    countList.add((E) entity);
                    return countList;

                }
                List<E> resultList = new ArrayList<>();
                while(resultSet.next()){
                    Class<?> entityClass = Class.forName(mappedStatement.getResultType());
                    E entity = (E) entityClass.newInstance();
                    Field[] declaredFields = entityClass.getDeclaredFields();
                    for (Field field : declaredFields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
//                      如果是 String 或者 int 类型，设置返回结果集
                        if(String.class.equals(type)){
                            field.set(entity,resultSet.getString(field.getName()));
                        }
                        else if(int.class.equals(type)){
                            field.set(entity,resultSet.getInt(field.getName()));
                        }
                        //其他类型
                        else{
                            field.set(entity,resultSet.getObject(field.getName()));
                        }
                    }
                    resultList.add(entity);
                }
                return resultList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
