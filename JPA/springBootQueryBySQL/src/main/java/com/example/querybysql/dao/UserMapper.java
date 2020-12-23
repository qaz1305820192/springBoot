package com.example.querybysql.dao;

import com.example.querybysql.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 查询指定ID的数据
     */
    public User findOne(Long id);

    public List<User> findAll();

    public int insert(User data);

    public int delete(Long id);


}
