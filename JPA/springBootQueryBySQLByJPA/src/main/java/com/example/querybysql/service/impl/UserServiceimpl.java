package com.example.querybysql.service.impl;

import com.example.querybysql.dao.UserJPA;
import com.example.querybysql.entity.User;
import com.example.querybysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserJPA userJPA;
    @Override
    public List<User> listAllUser() {
        List<User> list = userJPA.findAll();
        return list;
    }

    @Override
    public Optional<User> getUser(Long id) {
        Optional<User> byId = userJPA.findById(id);
        return byId;
    }
    @Override
    public User createUser(User user) {
        return userJPA.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userJPA.deleteById(id);

    }
}
