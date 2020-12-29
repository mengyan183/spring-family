/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.datasource.druid.service.impl;

import com.xingguo.spring.datasource.druid.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FooServiceImpl
 *
 * @author guoxing
 * @date 2020/12/29 8:29 AM
 * @since
 */
@Service
public class FooServiceImpl implements FooService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    // 保证锁的释放需要等待事务提交后才能释放,模拟慢查情况
    @Transactional
    public void selectForUpdate() {
        jdbcTemplate.queryForList("select BAR from FOO for update");
        try {
            Thread.sleep(1000);
        }catch (Exception e){

        }
    }
}
