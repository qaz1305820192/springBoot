package com.example.demo;



import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class DemoApplication {
    static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {

        int num=0;
        String str ="测试";
        logger.info("Processing trade with id: {} and symbol : {} ", num, str);

        logger.info("info");

        SpringApplication.run(DemoApplication.class, args);
    }

}
