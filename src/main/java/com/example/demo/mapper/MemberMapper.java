package com.example.demo.mapper;

import com.example.demo.dto.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * created by jg 2021/05/26
 */
@Mapper
public interface MemberMapper {

    int insertMember(Member member, int userIdx);
}
