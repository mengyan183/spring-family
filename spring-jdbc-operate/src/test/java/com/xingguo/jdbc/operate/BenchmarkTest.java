/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.jdbc.operate;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BenchmarkTest
 *
 * @author guoxing
 * @date 2020/12/15 8:06 PM
 * @since
 */
public class BenchmarkTest {

    /**
     * 测试 {@link org.springframework.jdbc.core.namedparam.NamedParameterUtils#parseSqlStatement(String)}  }
     */
    @Test
    public void testParseSqlStatement() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            String sql = "insert into FOO (BAR) values(:bar)";
            int i = 0;
            long l = System.currentTimeMillis();
            while (i < 100000) {
                NamedParameterUtils.parseSqlStatement(sql);
                i++;
            }
            System.out.println(":属性名模式花费时间为:" + (System.currentTimeMillis() - l));
        });
        executorService.submit(() -> {
            String sql = "insert into FOO (BAR) values(:{bar})";
            int i = 0;
            long l = System.currentTimeMillis();
            while (i < 100000) {
                NamedParameterUtils.parseSqlStatement(sql);
                i++;
            }
            System.out.println(":{属性名}模式花费时间为:" + (System.currentTimeMillis() - l));
        });

        executorService.shutdown();
        Thread.sleep(100);
    }
}
