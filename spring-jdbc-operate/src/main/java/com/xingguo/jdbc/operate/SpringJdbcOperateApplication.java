/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.jdbc.operate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

/**
 * SpringJdbcOperateApplication
 *
 * @author guoxing
 * @date 2020/12/15 2:06 PM
 * @since
 */
@SpringBootApplication
public class SpringJdbcOperateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringJdbcOperateApplication.class, args);
    }

    /**
     * jdbc写入工具类
     * 对于 {@link SimpleJdbcInsert}类进行自定义扩展
     *
     * @param dataSource 数据源
     * @return 自定义bean
     * @author guoxing
     * @date 2020-12-15 3:02 PM
     * @since 1.0.0
     */
    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(@Autowired DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("FOO") // 指定表名
                .usingGeneratedKeyColumns("ID"); // 使用自动生成ID字段数据
    }

    /**
     * 自定义 {@link NamedParameterJdbcTemplate} bean
     * 可以通过map指定名称实现相关jdbc操作
     *
     * @param dataSource
     * @return
     */
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Autowired DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * import org.h2.tools.Server;
     * 访问内置的  h2 sql
     *
     * @return
     * @throws SQLException
     */
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public Server h2WebServer() throws SQLException {
//        return Server.createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "8082");
//    }
}
