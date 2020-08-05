package com.example.demo.mapper;

import com.example.demo.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Select("SELECT * FROM user WHERE name = ${name} AND password = ${password}")
    User findByNameAndPassword(@Param("name") final String name, @Param("password") final String password);

    @Select("SELECT * FROM user WHERE userIdx = ${userIdx}")
    User findByUserIdx(@Param("userIdx") int userIdx);
}
