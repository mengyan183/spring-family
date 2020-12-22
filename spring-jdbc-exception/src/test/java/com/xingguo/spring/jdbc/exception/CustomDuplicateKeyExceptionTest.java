/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.jdbc.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * com.xingguo.spring.jdbc.exception.CustomDuplicateKeyExceptionTest
 *
 * @author guoxing
 * @date 2020/12/22 3:00 PM
 * @since
 */
@SpringBootTest
public class CustomDuplicateKeyExceptionTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 自定义异常测试
     * {@link CustomDuplicateKeyException}
     * {@code spring-jdbc-exception/src/main/resources/sql-error-codes.xml} // 自定义异常配置
     */
    @Test
    public void testDuplicateKey() {
        try {
            for (int i = 0; i < 2; i++) {
                // 写入两条相同
                jdbcTemplate.execute("insert into FOO (ID) values (1)");
            }
        } catch (CustomDuplicateKeyException customDuplicateKeyException) {
            System.out.println("捕获到了自定义异常");
        }

    }
}
