/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.annotationtx.service.impl;

import com.xingguo.spring.transaction.annotationtx.service.FooService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FooServiceImpl
 *
 * @author guoxing
 * @date 2020/12/20 6:12 PM
 * @since
 */
@Service
public class FooServiceImpl implements FooService, ApplicationContextAware {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 通过自己注入自己实现将 类方法调用方式 转为 spring bean 调用
     * 对于 自己注入自己 存在两种方式
     * 1: 通过  @Autowired 和 @Lazy 注入, @Lazy 的作用主要是为了延时注入
     * 2: 通过 实现 {@link ApplicationContextAware} 扩展接口 获取到当前spring 应用 context 来获取当前已注入的 {@link FooService} spring bean
     */
    // TODO : 方式1
    @Autowired
    @Lazy
    private FooService fooService;

    /**
     * 利用 {@link ApplicationContextAware} 来获取到 当前spring 容器上下文 来获取当前已注入的 FooService bean 实现fooService属性初始化
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO : 方式2 , 使用方式2,需要把 {@link Autowired} 和 {@link Lazy}
//        fooService = applicationContext.getBean(FooService.class);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    @Override
    public void insertFoo() {
        jdbcTemplate.execute("insert into FOO (BAR) values ('insertFoo')");
    }

    // 设置当前事务触发回滚条件为当抛出 {@link RuntimeException}时会触发事务回滚
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void insertFooWithRollBack() {
        jdbcTemplate.execute("insert into FOO (BAR) values ('insertFooWithRollBack')");
        throw new RuntimeException("自定义运行时异常,测试事务回滚");
    }

    @Override
    public void invokeInsertFooWithRollBack() {
        // 测试在当前操作下,事务情况
//        insertFooWithRollBack();

        //TODO  解决 调用 insertFooWithRollBack 事务不生效的问题
        fooService.insertFooWithRollBack();
    }

    @Override
    public void printListData() {
        List<String> strings = jdbcTemplate.queryForList("select `BAR` from FOO", String.class);
        strings.forEach(System.out::println);
    }
}
