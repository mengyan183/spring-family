/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.springdatasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class SpringDatasourceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringDatasourceApplication.class, args);
    }

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 通过实现 {@link CommandLineRunner}接口来设置应用启动后的相关操作,支持接收命令行参数
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        showDataSource();
        showAllData();
    }

    /**
     * 利用spring-boot sql自动初始化操作实现 schema生成和data写入
     */
    private void showAllData() {
        // 从指定的表中查询list数据,并循环打印
        jdbcTemplate.queryForList("select * from FOO")
                .forEach(System.out::println);
    }


    /**
     * 打印数据库连接池相关
     *
     * @throws SQLException
     * @author guoxing
     * @date 2020-12-14 10:20 AM
     * @since
     */
    private void showDataSource() throws SQLException {
        // 由于lombok plugin 尚不兼容 当前idea编辑器版本(2020.3),因此未使用lombok相关

        // 打印datasource 相关,可以看到其默认使用的是 HikariPool 连接池
        System.out.printf("datasource相关%s\n", dataSource.toString());
        // 获取连接池中的连接
        Connection connection = dataSource.getConnection();
        System.out.printf("connection相关%s\n", connection);
        // 释放连接
        connection.close();
    }

    /**
     * curl  当前web工程根访问路径/actuator/beans
     * 查看当前所有被ioc 管理的beans
     * 相关actuator 的配置操作 请查看 application.properties 文件
     *
     * 根据获取beans 可以看到存在很多 *AutoConfiguration相关配置类
     *
     * 数据源相关
     * {@link DataSource}
     *
     * 事务相关
     * {@link org.springframework.transaction.PlatformTransactionManager} 的子类 {@link org.springframework.jdbc.datasource.DataSourceTransactionManager}
     * {@link org.springframework.transaction.support.TransactionTemplate}
     *
     * 操作相关
     * {@link JdbcTemplate}
     */

}
