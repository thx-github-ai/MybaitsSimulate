package com.example.thx.test.mapper;

import com.example.thx.annotation.Select;
import com.example.thx.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Classname UserMapper
 * @Description 用户接口
 * @Date 2021/9/19 14:02
 * @Created by thx
 */
public interface UserMapper {
    @Select("select * form user")
    List<User> selectAll();
}
