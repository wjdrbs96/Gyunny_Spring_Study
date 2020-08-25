package com.example.demo.mapper;

import com.example.demo.dto.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {

    @Select("SELECT * FROM post")
    List<Post> findAll();
}
