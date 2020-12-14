/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.jdbc.withoutauto;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DataSourceDemo
 * 参考springboot 自动化配置 数据库相关,实现手动配置
 * {@link javax.sql.DataSource}
 * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager}
 * {@link org.springframework.jdbc.core.JdbcTemplate}
 *
 * @author guoxing
 * @date 2020/12/14 11:17 AM
 * @since
 */
@Configuration
@EnableTransactionManagement
public class DataSourceDemo {
    // 注入声明的bean datasource
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) throws SQLException {
        // 加载配置文件
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        // 初始化applicationContext
        classPathXmlApplicationContext.refresh();
        showDataSource(classPathXmlApplicationContext);
        // 关闭容器
        classPathXmlApplicationContext.close();
    }

    private static void showDataSource(ApplicationContext applicationContext) throws SQLException {
        // 从spring上下文中获取当前配置类
        DataSourceDemo dataSourceDemo = applicationContext.getBean("dataSourceDemo", DataSourceDemo.class);
        DataSource dataSource = dataSourceDemo.dataSource;
        // 打印datasource 相关,可以看到其默认使用的是 HikariPool 连接池
        System.out.printf("datasource相关%s\n", dataSource.toString());
        // 获取连接池中的连接
        Connection connection = dataSource.getConnection();
        System.out.printf("connection相关%s\n", connection);
        // 释放连接
        connection.close();
    }

    /**
     * 自定义声明{@link DataSource} bean
     *
     * @return
     * @throws Exception
     */
    @Bean
    public DataSource dataSource() throws Exception {
        Properties properties = new Properties();
        // 对应的是 spring.datasource.url=jdbc:h2:mem:testdb
        properties.setProperty("url", "jdbc:h2:mem:testdb");
        // spring.datasource.username=SA
        properties.setProperty("username", "SA");
        // spring.datasource.password=
        properties.setProperty("password", "");
        // 使用 commons-dbcp2 数据库连接池
        return BasicDataSourceFactory.createDataSource(properties);
    }

    /**
     * 自定义数据源事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 自定义 jdbcTemplate
     *
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
