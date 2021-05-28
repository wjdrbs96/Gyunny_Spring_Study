package com.example.demo.mapper;

import com.example.demo.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by jg 2021/05/26
 */
@Mapper
public interface SpringMapper {

    int insert(Member member);
}
