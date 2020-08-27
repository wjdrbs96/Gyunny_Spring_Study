package com.example.demo.mapper;

import com.example.demo.dto.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // 유저 전체조회
    @Select("SELECT * FROM user")
    List<User> findAll();

    // 유저 한명 조회
    @Select("SELECT * FROM user WHERE userIdx = #{userIdx}")
    User findByUserIdx(@Param("userIdx") final int userIdx);

    // 유저 등록
    @Insert("INSERT INTO user (name, part) VALUES (#{name}, #{part})")
    @Options(useGeneratedKeys = true, keyColumn = "user.userIdx")
    int userAdd(final User user);

    // 유저 수정
    @Update("UPDATE user SET name = #{user.name}, part = #{user.part} WHERE userIdx = 1")
    void userUpdate(@Param("userIdx") int userIdx, final User user);

    // 유저 삭제
    @Delete("DELETE FROM user WHERE userIdx = #{userIdx}")
    void userDelete(@Param("userIdx") final int userIdx);

}
