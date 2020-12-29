/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.datasource.druid.service;

/**
 * FooService
 *
 * @author guoxing
 * @date 2020/12/29 8:29 AM
 * @since
 */
public interface FooService {

    /**
     * 使用selectForUpdate增加互斥锁模拟并发情况下的慢查
     *
     * @author guoxing
     * @date 2020-12-29 8:30 AM
     * @since
     */
    void selectForUpdate();
}
