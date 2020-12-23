package com.example.querybysql.controller;

import com.example.querybysql.entity.User;
import com.example.querybysql.service.UserService;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object getAllUser() {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            List<User> list = this.userService.listAllUser();
            result.put("status", "ok");
            result.put("list", list);
        } catch (Exception ex) {
            result.put("status", "failure");
            result.put("errMsg", ex.getMessage());
            //logger.error(ex.getMessage(), ex);
        } finally {
        }
        return result;
    }


    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public Object getUserDetail(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            User user = this.userService.getUser(id);
            if (user != null) {
                result.put("status", "ok");
                result.put("user", user);
            } else {
                result.put("status", "failure");
                result.put("errMsg", "用户不存在");
            }
        } catch (Exception ex) {
            result.put("status", "failure");
            result.put("errMsg", ex.getMessage());
            // logger.error(ex.getMessage(), ex);
        } finally {
        }
        return result;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object createUser(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "email", required = false) String email) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setMobile(mobile);
            user.setEmail(email);
            int cnt = this.userService.createUser(user);
            if (cnt > 0) {
                result.put("status", "ok");
                result.put("user", user);
            } else {
                result.put("status", "failure");
                result.put("errMsg", "用户创建失败");
            }
        } catch (Exception ex) {
            result.put("status", "failure");
            result.put("errMsg", ex.getMessage());
            // logger.error(ex.getMessage(), ex);
        } finally {
        }
        return result;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Object deleteUser(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            int cnt = this.userService.deleteUser(id);
            if (cnt > 0) {
                result.put("status", "ok");
            } else {
                result.put("status", "failure");
                result.put("errMsg", "用户删除失败");
            }
        } catch (Exception ex) {
            result.put("status", "failure");
            result.put("errMsg", ex.getMessage());
            //logger.error(ex.getMessage(), ex);
        } finally {
        }
        return result;
    }


}
