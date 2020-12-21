/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.annotationtx;

import com.xingguo.spring.transaction.annotationtx.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * AnnotationTransactionDemo
 * 使用注解的方式来实现事务管理
 *
 * @author guoxing
 * @date 2020/12/20 5:49 PM
 * @since
 */
@SpringBootApplication
/**
 *  对于 aop 代理方式存在 多种, 根据 mode 和 proxyTargetClass 的不同组成 实现不同的代理增强方式
 *  mode        proxyTargetClass  代理模式
 *  proxy           true          cglib 代理 基于接口和实现类提供增强
 *  proxy           false         jdk Proxy代理 只针对接口增强
 *  aspectJ         true/false    无论proxyTargetClass true/false 其结果都是直接使用 aspectJ代理
 */
@EnableTransactionManagement(
        proxyTargetClass = true,
        mode = AdviceMode.PROXY
)
public class AnnotationTransactionDemo implements CommandLineRunner {
    @Autowired
    private FooService fooService;

    public static void main(String[] args) {
        SpringApplication.run(AnnotationTransactionDemo.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=============insertFoo start====================");

        fooService.insertFoo();
        fooService.printListData();
        System.out.println("=============insertFoo end====================");

        System.out.println("=============insertFooWithRollBack start====================");

        try {
            fooService.insertFooWithRollBack();
        } catch (Exception e) {
            System.out.println("错误信息:" + e.getMessage());
        } finally {
            fooService.printListData();
        }
        System.out.println("=============insertFooWithRollBack end====================");


        System.out.println("=============invokeInsertFooWithRollBack start====================");
        try {
            fooService.invokeInsertFooWithRollBack();
        } catch (Exception e) {
            System.out.println("错误信息:" + e.getMessage());
        } finally {
            /**
             * todo :对于当前问题的结果方案就是使用 spring bean 调用来实现
             * 解决方案 为 : 在 FooServiceImpl实现类中 注入 FooService bean
             *
             *
             * =============invokeInsertFooWithRollBack start====================
             * 错误信息:自定义运行时异常,测试事务回滚
             * insertFoo
             * insertFooWithRollBack
             * =============invokeInsertFooWithRollBack end====================
             *
             * 通过以上打印结果可以 看出 spring 事务未生效
             *
             * 调用线路如下
             *  fooService.invokeInsertFooWithRollBack() ->
             *  fooServiceImpl.insertFooWithRollBack()
             *
             *  对于 fooService.invokeInsertFooWithRollBack() 并不接受spring事务管理,并不会开启一个新的spring托管事务,
             *  在 调用insertFooWithRollBack方法时其直接通过内部方法调用的方式,并未走 spring bean 进行调用
             *
             *  由于 @Transactional 注解生效 是利用 的 AOP 的 around 切面控制, 通过 {@link TransactionInterceptor} 拦截生效
             *
             *  对于spring 事务统一管理 实际 就是对于 当前整个事务所有包裹的方法 实际都是使用 当前事务开启时获取到的sql连接, 通过事务包裹内所有的请求都公用同一个
             *  sqlsession,基于 事务 原子性的特点实现统一管理
             */
            fooService.printListData();
        }
        System.out.println("=============invokeInsertFooWithRollBack end====================");

    }
}


