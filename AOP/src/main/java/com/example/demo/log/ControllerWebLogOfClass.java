package com.example.demo.log;

import java.lang.annotation.*;

/**
 * 自定义注解，用于：
 * Web日志注解
* */
@Documented     //指明修饰的注解，可以被例如javadoc此类的工具文档化
@Retention(RetentionPolicy.RUNTIME)     //英文单词：保留  生命周期的控制  作用： 表示注解的生命周期。短到长 SOURCE<CLASS<RUNTIME
@Target(ElementType.TYPE)             //功能：指明了修饰的这个注解的使用范围，即被描述的注解可以用在哪里。  这里 TYPE代表只能用在 “Class”上，
public @interface ControllerWebLogOfClass {    //@interface Annotation{ } 定义一个注解 @Annotation，一个注解是一个类
    //这里是使用注解时的两个参数。
    String name();                      //所调用接口的名称
    boolean intoDb() default false;     //标识该条操作日志是否需要持久化存储
}
