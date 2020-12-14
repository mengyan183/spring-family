/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.multi.datasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * MultiDataSourceConfig
 * 自定义多数据源
 *
 * @author guoxing
 * @date 2020/12/14 3:53 PM
 * @since
 */
@Configuration
public class MultiDataSourceConfig {

    /**
     * 自定义声明 foo.datasource 配置相关
     * {@link ConfigurationProperties}注解使用支持解析properties文件中指定前缀的key
     *
     * @return
     */
    @Bean("fooDataSourceProperties")
    @ConfigurationProperties("foo.datasource")
    public DataSourceProperties fooDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 自定义声明 foo datasource
     *
     * @param dataSourceProperties
     * @return
     */
    @Bean("fooDataSource")
    public DataSource fooDataSource(@Qualifier("fooDataSourceProperties") DataSourceProperties dataSourceProperties) {
        System.out.printf("当前fooDataSourceUrl为:%s\n", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 自定义 foo 相关事务管理器
     */
    @Bean("fooTransactionManager")
    public PlatformTransactionManager fooTransactionManager(@Qualifier("fooDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 自定义声明 bar.datasource 配置相关
     *
     * @return
     */
    @Bean("barDataSourceProperties")
    @ConfigurationProperties("bar.datasource")
    public DataSourceProperties barDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 自定义声明 bar datasource
     *
     * @param dataSourceProperties
     * @return
     */
    @Bean("barDataSource")
    public DataSource barDataSource(@Qualifier("barDataSourceProperties") DataSourceProperties dataSourceProperties) {
        System.out.printf("当前barDataSourceUrl为:%s\n", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 自定义 bar 相关事务管理器
     */
    @Bean("barTransactionManager")
    public PlatformTransactionManager barTransactionManager(@Qualifier("barDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
