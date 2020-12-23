package com.example.querybysql.service;

import com.example.querybysql.entity.User;

import java.util.List;

public interface UserService {

    List<User> listAllUser();

    User getUser(Long id);

    int createUser(User user);

    int deleteUser(Long id);

}
