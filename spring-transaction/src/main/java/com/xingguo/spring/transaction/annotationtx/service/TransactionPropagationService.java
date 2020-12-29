/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.annotationtx.service;

import com.xingguo.spring.transaction.annotationtx.exception.CustomTransactionException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * TransactionPropagationService
 * 不同事务隔离级别
 * 对于 requires_new 和 nested 的区别
 *
 * @author guoxing
 * @date 2020/12/29 9:40 AM
 * @since
 */
public interface TransactionPropagationService {

    /**
     * 父级没有事务
     *
     * @author guoxing
     * @date 2020-12-29 9:51 AM
     * @since
     */
    void parentWithNoneTransaction(boolean b,boolean childError);

    /**
     * 父级存在事务
     * 使用默认 的事务传播机制 required
     *
     * @author guoxing
     * @date 2020-12-29 9:52 AM
     * @since
     */
    @Transactional(isolation = Isolation.READ_COMMITTED)
    void parentWithTransaction(boolean b, boolean childError, Propagation propagation);


    /**
     * 设置子级事务传播机制为 requires_new
     *
     * @author guoxing
     * @date 2020-12-29 10:35 AM
     * @since
     * @param b
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = CustomTransactionException.class,isolation = Isolation.READ_COMMITTED)
    void childWithRequiresNew(boolean b);

    /**
     * 设置子级事务传播机制为 nested
     *
     * @author guoxing
     * @date 2020-12-29 10:38 AM
     * @since
     * @param b
     */
    @Transactional(propagation = Propagation.NESTED, rollbackFor = CustomTransactionException.class,isolation = Isolation.READ_COMMITTED)
    void childWithNested(boolean b);

    /**
     * 打印查询结果
     *
     * @author guoxing
     * @date 2020-12-29 11:21 AM
     * @since
     */
    void printSelectResult(int id);
}
