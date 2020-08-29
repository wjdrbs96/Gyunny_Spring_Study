package com.example.demo.mapper;

import com.example.demo.dto.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {

    // 게시글 전체조회
    @Select("SELECT * FROM post")
    List<Post> findAll();

    // 게시글 하나조회
    @Select("SELECT * FROM post WHERE postIdx = #{postIdx}")
    Post findByPostIdx(@Param("postIdx") int postIdx);

    // 게시글 등록


    // 게시글 수정

    // 게시글 삭제

}
