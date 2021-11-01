/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xing.config;

import com.xing.dto.A;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AConfig
 *
 * @author guoxing
 * @date 2021/11/1 10:23 PM
 * @since
 */
@Configuration
public class AConfig {
    @Bean
    public A a() {
        return new A();
    }
}
