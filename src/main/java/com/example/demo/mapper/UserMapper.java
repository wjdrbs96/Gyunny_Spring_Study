package com.example.demo.mapper;

import com.example.demo.dto.User;
import com.example.demo.model.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    // 유저전체조회
    @Select("SELECT * FROM user")
    List<Users> findALl();

    User findByNameAndPassword(@Param("name") final String name, @Param("password") final String password);

    // 유저 한명 조회
    User findByUserIdx(@Param("userIdx") int userIdx);

    // 유저등록
    int save(@Param("user") final User user);

    // 유저 삭제

}
