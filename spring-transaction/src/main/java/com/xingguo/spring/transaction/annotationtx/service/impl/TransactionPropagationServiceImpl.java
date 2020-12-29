/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.annotationtx.service.impl;

import com.xingguo.spring.transaction.annotationtx.exception.CustomTransactionException;
import com.xingguo.spring.transaction.annotationtx.service.TransactionPropagationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

/**
 * TransactionPropagationServiceImpl
 *
 * @author guoxing
 * @date 2020/12/29 10:58 AM
 * @since
 */
@Service
@Slf4j
public class TransactionPropagationServiceImpl implements TransactionPropagationService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Lazy
    private TransactionPropagationService transactionPropagationService;

    @Override
    public void parentWithNoneTransaction(boolean b, boolean childError) {
        jdbcTemplate.update("update FOO set version=version+1 where id = 1");

        try {
            transactionPropagationService.childWithNested(childError);
        } catch (Exception e) {
            log.error("childWithNested exception:{}", e.getMessage());
        } finally {
            printSelectResult(2);
        }

        try {
            transactionPropagationService.childWithRequiresNew(childError);
        } catch (Exception e) {
            log.error("childWithRequiresNew exception:{}", e.getMessage());
        } finally {
            printSelectResult(2);
        }

        if (b) {
            throw new RuntimeException("父级无事务情况下抛出异常");
        }
    }

    @Override
    public void parentWithTransaction(boolean b, boolean childError, Propagation propagation) {
        jdbcTemplate.update("update FOO set version=version+1 where id = 1");
        log.info("子级方法执行之前");
        printSelectResult(2);
        switch (propagation) {
            case NESTED:
                try {
                    transactionPropagationService.childWithNested(childError);
                } catch (Exception e) {
                    log.error("childWithNested exception:{}", e.getMessage());
                } finally {
                    log.info("nested 方法执行结束");
                    printSelectResult(2);
                }
                break;
            case REQUIRES_NEW:
                try {
                    transactionPropagationService.childWithRequiresNew(childError);
                } catch (Exception e) {
                    log.error("childWithRequiresNew exception:{}", e.getMessage());
                } finally {
                    log.info("requiresNew 方法执行结束");
                    printSelectResult(2);
                }
                break;
        }
        if (b) {
            throw new RuntimeException("父级开启事务情况下抛出异常");
        }
    }

    @Override
    public void childWithRequiresNew(boolean b) {
        jdbcTemplate.update("update FOO set version=version+1 where id = 2");
        if (b) {
            throw new CustomTransactionException("requires new throw exception");
        }
    }

    @Override
    public void childWithNested(boolean b) {
        jdbcTemplate.update("update FOO set version=version+1 where id = 2");
        if (b) {
            throw new CustomTransactionException("nested throw exception");
        }
    }

    @Override
    public void printSelectResult(int id) {
        Integer version = jdbcTemplate.queryForObject("select `version` from FOO where id = " + id, int.class);
        log.info("id : {},version:{}", id, version);
    }
}
