/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xing.config;

import com.xing.dto.A;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * WebConfig
 *
 * @author guoxing
 * @date 2021/11/1 10:13 PM
 * @since
 */
@Configuration
public class WebConfig {
    @Bean
    public RouterFunction<ServerResponse> helloWorld() {
        return route(GET("/hello-world"),
                request -> ServerResponse.ok().body(Mono.just("Hello,World"), String.class)
        );
    }


    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerReady(WebServerInitializedEvent event) {
        System.out.println("当前 WebServer 实现类为：" + event.getWebServer().getClass().getName());
    }

    @Bean
    public ApplicationRunner runner(BeanFactory beanFactory) {
        return args -> {
            System.out.println("当前 helloWorld Bean 实现类为："
                    + beanFactory.getBean("helloWorld").getClass().getName());

            System.out.println("当前 WebConfiguration Bean 实现类为："
                    + beanFactory.getBean(WebConfig.class).getClass().getName());
            System.out.println(beanFactory.getBean(A.class).getClass().getName());
        };
    }
}
