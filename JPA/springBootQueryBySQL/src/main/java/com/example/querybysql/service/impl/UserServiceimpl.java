package com.example.querybysql.service.impl;

import com.example.querybysql.dao.UserMapper;
import com.example.querybysql.entity.User;
import com.example.querybysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> listAllUser() {
        List<User> list = userMapper.findAll();
        return list;
    }

    @Override
    public User getUser(Long id) {
        User user = userMapper.findOne(id);
        return user;
    }
    @Override
    public int createUser(User user) {
        int cnt = userMapper.insert(user);
        return cnt;
    }

    @Override
    public int deleteUser(Long id) {
        int cnt = userMapper.delete(id);
        return cnt;
    }
}
