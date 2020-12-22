/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.jdbc.exception;

import org.springframework.dao.DuplicateKeyException;

/**
 * CustomDuplicateKeyException
 * {@link DuplicateKeyException}
 * 自定义主键重复异常
 *
 * @author guoxing
 * @date 2020/12/22 2:35 PM
 * @since
 */
public class CustomDuplicateKeyException extends DuplicateKeyException {

    public CustomDuplicateKeyException(String msg) {
        super(msg);
    }

    public CustomDuplicateKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
