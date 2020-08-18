package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UseDaoService {

    // 유저를 담는 List
    private static List<User> users = new ArrayList<>();

    // 유저 인원
    private static int userCount = 3;

    static {
        users.add(new User(1, "A", new Date()));
        users.add(new User(2, "B", new Date()));
        users.add(new User(3, "C", new Date()));
    }

    // 사용자 전체조회
    public List<User> findALl() {
        return users;
    }

    // 사용자 추가
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }

        users.add(user);
        return user;
    }

    // 사용자 한명 조회
    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        // 찾지 못함
        return null;
    }

}
