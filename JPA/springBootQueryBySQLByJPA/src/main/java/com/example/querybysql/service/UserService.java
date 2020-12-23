package com.example.querybysql.service;

import com.example.querybysql.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> listAllUser();

    Optional<User> getUser(Long id);

    User createUser(User user);

    void deleteUser(Long id);

}
