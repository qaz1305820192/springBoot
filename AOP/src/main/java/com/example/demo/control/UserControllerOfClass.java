package com.example.demo.control;

//5. 测试接口



import com.example.demo.log.ControllerWebLogOfClass;
import com.example.demo.log.ControllerWebLogOfMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 运行测试
 *
 * 	浏览器请求：http://localhost:8080/user/getOne?id=1&name=Savage
 * ，可以看到后台日志输出：*/
@RestController
@RequestMapping("/user")
@ControllerWebLogOfClass(name = "(基于类的)查询", intoDb = true)//使用自定义的注解 基于类的
public class UserControllerOfClass {

    @GetMapping("/getTwo")
    public String getTwo(Long id, String name) {

        return "基于类的";
    }


}