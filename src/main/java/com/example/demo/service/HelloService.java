package com.example.demo.service;

import com.example.demo.dto.GroupDto;
import com.example.demo.dto.Member;
import com.example.demo.mapper.HelloMapper;
import com.example.demo.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by jg 2021/04/24
 */
@RequiredArgsConstructor
@Service
public class HelloService {

    private final HelloMapper helloMapper;
    private final MemberMapper memberMapper;

    @Transactional
    public void helloService() {
//        GroupDto groupDto = new GroupDto("규니규니");
//        //helloMapper.getGroup();
//        helloMapper.insertGroup(groupDto);
//        System.out.println("===========");
//        System.out.println("===========");
//        System.out.println("===========");
//        System.out.println("===========");
//        System.out.println("===========");
//        helloMapper.insertGroupUsers(groupDto.getGroupId(), 1);
        memberMapper.insertMember(new Member("이름", "파트"), 1);
    }
}

