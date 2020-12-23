package com.example.querybysql;

import com.example.querybysql.controller.UserController;
import com.example.querybysql.entity.User;
import com.example.querybysql.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertNull;

@SpringBootTest
class QuerybysqlApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserService userService;

    @Test
    public  void testList(){
        List<User> users = userService.listAllUser();
        for(User u :users){
            System.out.println(u.getId()+" "+u.getName()+" "+u.getMobile()+" "+u.getEmail());
        }

    }

    @Test
    public void testAdd(){
        User user = new User();
        user.setName("李含");
        user.setMobile("1777773578");
        user.setEmail("29929@qq.com");
        User user1 = userService.createUser(user);

        assertNull(user1);
    }

    @Test
    public  void testDelete(){
        if(userService.getUser((long)2)!=null)
            userService.deleteUser((long) 2);
            System.out.println("ssssss");

        assertNull(userService.getUser((long)2));
    }


}
