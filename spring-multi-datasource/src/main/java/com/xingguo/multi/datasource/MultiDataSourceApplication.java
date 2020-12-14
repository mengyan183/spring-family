/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.multi.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

/**
 * MultiDataSourceApplication
 *
 * @author guoxing
 * @date 2020/12/14 3:07 PM
 * @since
 */
@SpringBootApplication(exclude = { // 由于在多数据源情况下需要自定义进行配置,则需要排除 spring boot jdbc 自动装配相关; 也可以通过 @Primary设置主要bean,springboot 就会自动装配
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class
})
public class MultiDataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiDataSourceApplication.class, args);
    }
}
