package com.example.querybysql.dao;

import com.example.querybysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
/**
 *   创建UserJPA接口并且继承SpringDataJPA内的接口作为父类，
 *   UserJPA继承了JpaRepository接口（提供的简单数据操作接口）、
 *   JpaSpecificationExecutor（提供的复杂查询接口）、Serializable（序列化接口）。
 *
 *    我们并不需要做其他的任何操作了，因为SpringBoot以及SpringDataJPA会为我们全部搞定，
 *    SpringDataJPA内部使用了类代理的方式，让继承了它接口的子接口，都以spring管理的Bean的形式存在。
 *    */
public interface UserJPA extends JpaRepository<User,Long>, JpaSpecificationExecutor<User>, Serializable {
}
