package com.example.thx.test;

import com.example.thx.entity.User;
import com.example.thx.session.SqlSession;
import com.example.thx.session.SqlSessionFactory;
import com.example.thx.session.SqlSessionFactoryBuilder;
import com.example.thx.test.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import java.io.IOException;
import java.util.List;

/**
 * @Classname Test
 * @Description 这是测试类
 * @Date 2021/9/17 14:48
 * @Created by thx
 */
@Slf4j
public class Test {

    private SqlSessionFactory factory;
    protected SqlSession sqlSession;

    @Before
    public void before() throws IOException {
        /**
         * 读取配置文件
         * 在读取的过程中，会获取相关配置并完成一些初始化，会读取xml配置文件和Mapper类去构建MappedStatement等等
         */
        factory = new SqlSessionFactoryBuilder().build("mybatis-config.properties");
        /**
         * 开启一个会话
         */
        sqlSession = factory.openSession();
    }

   @org.junit.Test
    public void text() {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
       List<User> users = userMapper.selectAll();
       for (User user : users) {
           log.info(user.getId() + " " + user.getUserName() + " " + user.getPassword());
       }
    }

}
