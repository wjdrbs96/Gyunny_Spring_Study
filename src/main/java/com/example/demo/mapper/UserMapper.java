package com.example.demo.mapper;

import com.example.demo.dto.User;
import com.example.demo.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<Users> findALl();

    User findByNameAndPassword(@Param("name") final String name, @Param("password") final String password);

    User findByUserIdx(@Param("userIdx") int userIdx);

    int save(@Param("user") final User user);

}
