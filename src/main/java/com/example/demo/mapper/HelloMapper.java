package com.example.demo.mapper;

import com.example.demo.dto.GroupDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * created by jg 2021/05/04
 */
@Mapper
public interface HelloMapper {

    @Insert("INSERT INTO gyunny_group (title) VALUES(#{title})")
    @Options(useGeneratedKeys = true, keyProperty = "groupId")
    int insertGroup(GroupDto groupDto);

    @Insert("INSERT INTO user_group (users_id, gyunny_group_id) VALUES (#{userId}, #{groupId})")
    void insertGroupUsers(int groupId, int userId);

    @Select("SELECT * FROM gyunny_group")
    List<GroupDto> getGroup();
}

