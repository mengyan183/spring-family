/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * ProgramTransactionDemo
 *
 * @author guoxing
 * @date 2020/12/20 4:45 PM
 * @since
 */
@SpringBootApplication
public class ProgramTransactionDemo implements CommandLineRunner {
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ProgramTransactionDemo.class, args);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(@Autowired DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        /**
         * {@link org.springframework.transaction.TransactionDefinition}
         * 设置 事务相关配置
         */
        dataSourceTransactionManager.setDefaultTimeout(-1);
        return dataSourceTransactionManager;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.printf("事务开始之前:数据量为:{%d}\n", getCount());
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            jdbcTemplate.execute("insert into FOO (BAR) values ('aaa')");
            System.out.printf("事务未提交:数据量为:{%d}\n", getCount());
            // 回滚当前事务
//            transactionStatus.setRollbackOnly();
            // 默认自动提交当前事务
        });
        System.out.printf("事务完成(回滚或提交)后:数据量为:{%d}\n", getCount());
    }

    public Integer getCount() {
        return jdbcTemplate.queryForObject("select COUNT(ID) from FOO", Integer.class);
    }
}
