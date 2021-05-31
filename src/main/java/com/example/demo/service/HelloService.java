package com.example.demo.service;

import com.example.demo.dto.Member;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.mapper.SpringMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by jg 2021/04/24
 */
@RequiredArgsConstructor
@Service
public class HelloService {

    private final SpringMapper springMapper;

    @Transactional
    public List<Member> helloService() {
//        GroupDto groupDto = new GroupDto("규니규니");
//        //helloMapper.getGroup();
//        helloMapper.insertGroup(groupDto);
//        System.out.println("===========");
//        System.out.println("===========");
//        System.out.println("===========");
//        System.out.println("===========");
//        System.out.println("===========");
//        helloMapper.insertGroupUsers(groupDto.getGroupId(), 1);
        //Member member = new Member("스프링", "서버파트", 1L);
        //springMapper.insert(member);
        return springMapper.getMembers();
    }
}

