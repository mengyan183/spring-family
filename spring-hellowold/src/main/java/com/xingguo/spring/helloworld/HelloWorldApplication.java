/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * springboot 启动类
 *
 *
 * @author guoxing
 * @date 2020-12-13 7:50 PM
 * @since 1.0.0
 */
@SpringBootApplication //标记当前类为springboot 启动类
@RestController // 表示当前类为 mvc中 一个 restful controller
public class HelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

    /**
     * 通过在控制台输入 curl http://localhost:8080/hello
     *
     * @return
     */
    @GetMapping("/hello")
    public String helloWorld() {
        return "hello spring";
    }
}