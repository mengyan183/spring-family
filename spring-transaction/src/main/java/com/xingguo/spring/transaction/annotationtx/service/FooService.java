/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.annotationtx.service;

/**
 * FooService
 *
 * @author guoxing
 * @date 2020/12/20 6:10 PM
 * @since
 */
public interface FooService {

    void insertFoo();

    void insertFooWithRollBack();

    void invokeInsertFooWithRollBack();

    void printListData();
}
