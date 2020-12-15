/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.datasource.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

/**
 * SpringDatasourceDruidApplication
 * 使用druid作为数据库连接池使用
 *
 * @author guoxing
 * @date 2020/12/15 9:23 AM
 * @since 1.0.0
 */
@SpringBootApplication
public class SpringDatasourceDruidApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SpringDatasourceDruidApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        /**
         *  可以查看默认输出的连接数量
         *   和
         * spring.datasource.druid.initial-size=5
         * spring.datasource.druid.max-active=5
         * spring.datasource.druid.min-idle=5
         * 配置有关
         *
         * 打印的结果如下
         * {
         * 	CreateTime:"2020-12-15 11:46:57",
         * 	ActiveCount:0,
         * 	PoolingCount:5,
         * 	CreateCount:5,
         * 	DestroyCount:0,
         * 	CloseCount:1,
         * 	ConnectCount:1,
         * 	Connections:[ //输出当前已建立的连接, 由于 spring.datasource.druid.initial-size 为 5,因此默认会创建5个连接
         *         {ID:45320991, ConnectTime:"2020-12-15 11:46:58", UseCount:0, LastActiveTime:"2020-12-15 11:46:58"},
         *        {ID:491825098, ConnectTime:"2020-12-15 11:46:58", UseCount:0, LastActiveTime:"2020-12-15 11:46:58"},
         *        {ID:1370169059, ConnectTime:"2020-12-15 11:46:58", UseCount:0, LastActiveTime:"2020-12-15 11:46:58"},
         *        {ID:779511842, ConnectTime:"2020-12-15 11:46:58", UseCount:0, LastActiveTime:"2020-12-15 11:46:58"},
         *        {ID:805561728, ConnectTime:"2020-12-15 11:46:58", UseCount:1, LastActiveTime:"2020-12-15 11:46:58"}
         * 	]
         * }
         *
         */
        System.out.println(dataSource);
    }
}
