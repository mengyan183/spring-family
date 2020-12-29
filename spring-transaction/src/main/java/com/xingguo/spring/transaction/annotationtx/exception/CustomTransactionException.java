/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.annotationtx.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * CustomTransactionException
 * 自定义事务异常
 *
 * @author guoxing
 * @date 2020/12/29 10:43 AM
 * @since
 */
@NoArgsConstructor
public class CustomTransactionException extends RuntimeException {

    public CustomTransactionException(String message) {
        super(message);
    }
}
