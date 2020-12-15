/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.datasource.hikaricp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

/**
 * SpringDataSourceHikaricpApplication
 * 对于 {@link org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration.Hikari}
 * <a href="https://github.com/brettwooldridge/HikariCP"/>
 * 由于springboot 默认使用的是 hikaricp 作为数据源
 *
 * @author guoxing
 * @date 2020/12/14 9:18 PM
 * @since
 */
@SpringBootApplication
public class SpringDataSourceHikaricpApplication implements CommandLineRunner {
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataSourceHikaricpApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        //since springboot 2.0 对于 datasource 默认使用的是 Hikari datasource 作为数据库连接池
        System.out.println(dataSource);
    }
}
