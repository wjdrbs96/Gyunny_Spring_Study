package com.example.demo.service;

import com.example.demo.dto.GroupDto;
import com.example.demo.mapper.HelloMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.IIOException;

/**
 * created by jg 2021/04/24
 */
@RequiredArgsConstructor
@Service
public class HelloService {

    private final HelloMapper helloMapper;

    @Transactional(readOnly = true)
    public void helloService() {
        GroupDto groupDto = new GroupDto("제목22");
        helloMapper.getGroup();
//        helloMapper.insertGroup(groupDto);
//        helloMapper.insertGroupUsers(groupDto.getGroupId(), 1);
    }
}

