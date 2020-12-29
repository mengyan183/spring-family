/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.annotationtx.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

/**
 * TransactionPropagationServiceTest
 *
 * @author guoxing
 * @date 2020/12/29 11:46 AM
 * @since
 */
@SpringBootTest
@Slf4j
public class TransactionPropagationServiceTest {
    @Autowired
    private TransactionPropagationService transactionPropagationService;

    /**
     * 在 父级无事务且无异常情况下 , 子级存在事务且无异常情况下
     * 打印结果为
     * id : 2,version:3
     * id : 2,version:4
     * id : 1,version:2
     * 其所有的事务都正常提交,对于父级而言其并不接受spring事务管理,使用的是数据库的默认事务
     */
    @Test
    public void parentAndChildNoThrow() {
        try {
            transactionPropagationService.parentWithNoneTransaction(false, false);
        } catch (Exception e) {
            log.error("parentWithNoneTransaction : {}", e.getMessage());
        }
        transactionPropagationService.printSelectResult(1);
    }

    /**
     * 在 父级无事务且存在异常情况下 , 子级存在事务且无异常情况下
     * 结果为
     * id : 2,version:3
     * id : 2,version:4
     * id : 1,version:2
     * <p>
     * 在父级无事务情况下 "id : 1,version : 2" 表示 父级的sql使用数据库的默认事务正常提交了,即使发生抛出异常也不会回滚;
     * <p>
     * id : 2,version:3
     * id : 2,version:4
     * 表明子级事务属于单独的新的事务,因此其正常提交
     */
    @Test
    public void parentWithThrowAndChildNoThrow() {
        try {
            transactionPropagationService.parentWithNoneTransaction(true, false);
        } catch (Exception e) {
            log.error("parentWithNoneTransaction : {}", e.getMessage());
        }
        transactionPropagationService.printSelectResult(1);
    }

    /**
     * 父级无事务 且抛出异常, 子级存在事务且抛出异常
     * 父级打印结果
     * id : 1,version:2
     * <p>
     * 子级打印结果为
     * id : 2,version:2
     * id : 2,version:2
     * <p>
     * 可以得出结论为 父级无事务管理,即使抛出异常,也不会影响到父级以及子级的事务状态;
     * 而对于子级 在接受事务管理的情况下, 对于 requires_new 或 requires 或 nested 都会创建一个新的事务,并自己管理自身当前的事务
     * 因此对于子级而言无论是提交还是回滚操作都是由自己开启的事务进行管理,父级的任何操作都不会影响到子级
     */
    @Test
    public void parentAndChildWithThrow() {
        try {
            transactionPropagationService.parentWithNoneTransaction(true, true);
        } catch (Exception e) {
            log.error("parentWithNoneTransaction : {}", e.getMessage());
        }
        transactionPropagationService.printSelectResult(1);
    }


    /**
     * ============================================================
     * 以下测试都是 父级开启事务 情况下
     */

    /**
     * 父级存在事务情况下
     * 父级无异常,子级也无异常
     * 子级事务提交时机:
     *  通过将事务的隔离级别设置为 read_committed情况下 可以看到 子级事务的提交是单独提交的,并非在父级事务提交的情况下才执行提交操作
     */
    @Test
    public void ptAndChildNoThrow() {
        {
            // 父级存在事务,子级事务传播行为 为nested
            try {
                transactionPropagationService.parentWithTransaction(false, false, Propagation.NESTED);
            } catch (Exception e) {
                log.error("parentWithTransaction : {}", e.getMessage());
            }
            transactionPropagationService.printSelectResult(1);
        }
        log.info("=============================================");
        {
            // 父级存在事务,子级事务传播行为 为 requires_new
            try {
                transactionPropagationService.parentWithTransaction(false, false, Propagation.REQUIRES_NEW);
            } catch (Exception e) {
                log.error("parentWithTransaction : {}", e.getMessage());
            }
            transactionPropagationService.printSelectResult(1);
        }
    }


    /**
     * 在父级存在事务抛出异常, 子级也存在事务情况下
     *
     * 结论 :
     * 1: 父级在存在事务管理情况下,当抛出异常时,父级的事务会进行回滚操作
     * 2: 当子级事务传播机制为nested 情况下,当父级事务回滚时,子级事务也会被回滚
     * 3: 当子级事务传播机制为requires_new 情况下,当父级事务回滚时,子级事务不会被回滚
     *
     */
    @Test
    public void ptExceptionAndChildNoThrow(){
        {
            // 父级存在事务,子级事务传播行为 为nested
            try {
                transactionPropagationService.parentWithTransaction(true, false, Propagation.NESTED);
            } catch (Exception e) {
                log.error("parentWithTransaction : {}", e.getMessage());
            }
            //输出结果为 id : 1,version:1
            // 当父级事务抛出异常时,父级事务也被回滚了
            transactionPropagationService.printSelectResult(1);
            // 输出结果为 id : 2,version:2
            // 以上输出结果表示 在父级存在事务 ,且抛出异常,子级事务传播机制为 nested 情况下, 当父级回滚时,子级也会回滚
            transactionPropagationService.printSelectResult(2);
        }
        log.info("=============================================");
        {
            // 父级存在事务,子级事务传播行为 为 requires_new
            try {
                transactionPropagationService.parentWithTransaction(true, false, Propagation.REQUIRES_NEW);
            } catch (Exception e) {
                log.error("parentWithTransaction : {}", e.getMessage());
            }
            // 输出结果为 id : 1,version:1
            // 在父级事务存在异常情况下,父级事务回滚了
            transactionPropagationService.printSelectResult(1);
            // 输出结果为 id : 2,version:3
            // 在父级事务存在异常情况下,子级事务没有回滚
            transactionPropagationService.printSelectResult(2);
        }
    }


    /**
     * 在父级存在事务, 子级也存在事务且抛出异常(子级抛出的异常不会直接上抛给父级,只测试子级事务回滚对父级事务的影响)
     *
     * 结论 :
     * 不论是子级事务传播机制是 nested/requires_new , 子级事务的回滚操作并不会导致父级事务回滚
     */
    @Test
    public void ptNoThrowAndChildThrow(){
        {
            // 父级存在事务(无异常),子级事务传播行为 为nested(并抛出异常回滚)
            try {
                transactionPropagationService.parentWithTransaction(false, true, Propagation.NESTED);
            } catch (Exception e) {
                log.error("parentWithTransaction : {}", e.getMessage());
            }
            //打印结果为: id : 1,version:2
            //在子级事务回滚情况下,父级事务并不会进行回滚操作,父级事务仍然正常提交
            transactionPropagationService.printSelectResult(1);
            //结果为: id : 2,version:2
            //子级事务正常回滚
            transactionPropagationService.printSelectResult(2);
        }
        log.info("=============================================");
        {
            // 父级存在事务(无异常),子级事务传播行为 为 requires_new(抛出异常回滚)
            try {
                transactionPropagationService.parentWithTransaction(false, true, Propagation.REQUIRES_NEW);
            } catch (Exception e) {
                log.error("parentWithTransaction : {}", e.getMessage());
            }
            // 打印结果为: id : 1,version:3
            //在子级事务回滚情况下,父级事务并不会进行回滚操作,父级事务仍然正常提交
            transactionPropagationService.printSelectResult(1);
            // 打印结果为: id : 2,version:2
            // 子级事务正常回滚
            transactionPropagationService.printSelectResult(2);
        }
    }

    /**
     * TODO : 综上测试,得出以下结论
     * 1:当父级不存在事务情况下,对于requires_new 或 nested 其都是创建的一个新的独立的事务,父级的任何操作(异常上抛)都不会影响到子级事务的操作(回滚或提交)
     * 2:当父级存在事务情况下
     *      2.1:父级事务回滚情况下
     *          2.1.1:当子级事务传播机制为nested情况下,即使子级事务不发生异常触发回滚,子级事务也会被父级事务回滚
     *          2.1.2:当子级事务传播机制为requires_new 情况下,除非子级事务发生异常触发回滚,否则子级事务不会回滚
     *      2.2:父级事务正常提交情况下
     *          2.2.1:当子级事务传播机制为nested情况下,子级事务的回滚操作不会影响到父级事务,提交操作也不会受到父级事务的影响
     *          2.2.2:当子级事务传播机制为requires_new 情况下,子级事务的回滚操作都不会影响到父级事务,提交操作也不会受到父级事务的影响
     *
     *
     * TODO : 对于父级存在事务情况下,父级回滚时,nested 传播机制下,子级被迫回滚的操作;
     * 其核心主要是利用 在nested 事务开启时,其会创建一个回滚点,当父级发生回滚操作时,如果判断回滚点存在,则会将子级事务操作回滚到当前回滚点
     * 具体处理逻辑如下
     * @see AbstractPlatformTransactionManager#handleExistingTransaction(org.springframework.transaction.TransactionDefinition, java.lang.Object, boolean)
     */
}
