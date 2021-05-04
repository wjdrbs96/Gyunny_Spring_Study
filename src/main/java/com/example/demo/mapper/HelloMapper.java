package com.example.demo.mapper;

import com.example.demo.dto.GroupDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * created by jg 2021/05/04
 */
@Mapper
public interface HelloMapper {

    @Insert("INSERT INTO gyunny_group (title) VALUES(#{group.title})")
    @Options(useGeneratedKeys = true, keyProperty = "group.groupId")
    int insertGroup(@Param("group") GroupDto groupDto);

    @Insert("INSERT INTO user_group (users_id, gyunny_group_id) VALUES (#{userId}, #{groupId})")
    void insertGroupUsers(@Param("groupId") int groupId, @Param("userId") int userId);

    @Select("SELECT * FROM gyunny_group")
    List<GroupDto> getGroup();
}
