/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.datasource.druid;

import com.xingguo.spring.datasource.druid.service.FooService;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * SpringDatasourceDruidApplication
 * 使用druid作为数据库连接池使用
 *
 * @author guoxing
 * @date 2020/12/15 9:23 AM
 * @since 1.0.0
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement(proxyTargetClass = true)
public class SpringDatasourceDruidApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private FooService fooService;

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
        log.info("{}",dataSource);

        // 测试druid 慢查日志监控
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, new SynchronousQueue<>(),new NamedThreadFactory("slow-sql-druid"), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 2; i++) {
            threadPoolExecutor.execute(()->{
                // 在第二个线程执行时由于第一个线程首先开启了事务,并增加了行锁,且锁需要在事务提交后才可以释放
                // 由于设置druid 慢查标准为 1000 ms, 而 fooService.selectForUpdate 执行需要花费 1000ms以上,因此对于第二个线程执行由于被mysql互斥锁阻塞,此时这种情况就称为慢查
                // 可以看到日志中打印了慢查日志为
                // ERROR 85659 --- [slow-sql-druid] c.alibaba.druid.filter.stat.StatFilter   : slow sql 1030 millis. select BAR from FOO for update[]
                // 提示查询时间为 1030 毫秒
                fooService.selectForUpdate();
            });
        }
        threadPoolExecutor.shutdown();
    }
}
