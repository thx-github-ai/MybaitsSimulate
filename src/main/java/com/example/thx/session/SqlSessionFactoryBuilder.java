package com.example.thx.session;

import com.example.thx.config.Configuration;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Classname SqlSessionFactoryBuilder
 * @Description
 * @Date 2021/9/19 14:03
 * @Created by thx
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(String propFileName) throws IOException {
        InputStream inputStream = SqlSessionFactoryBuilder.class.getClassLoader().getResourceAsStream(propFileName);
        return this.build(inputStream);
    }

    public SqlSessionFactory build(InputStream inputStream) throws IOException {
        Configuration.configProp.load(inputStream);
        //DefaultSqlSessionFactory的构造函数会加载配置文件，可以进去看看
        return new DefaultSqlSessionFactory(new Configuration());
    }
}
