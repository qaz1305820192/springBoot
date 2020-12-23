/*
package com.example.querybysql.dao;

import com.example.querybysql.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    */
/**
     * 查询指定ID的数据
     *//*

    @Select("select * from user where id=#{id}")
    public User findOne(Long id);
    @Select("select * from user ")
    public List<User> findAll();
    @Insert("Insert into user values (#{id},#{name},#{mobile},#{email})")
    public int insert(User data);
    @Delete("delete from user where id=#{id}")
    public int delete(Long id);


}
*/
