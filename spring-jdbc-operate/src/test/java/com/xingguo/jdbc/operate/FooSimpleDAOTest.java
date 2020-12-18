/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.jdbc.operate;

import com.xingguo.jdbc.operate.dao.FooSimpleDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 * FooSimpleDAOTest
 *
 * @author guoxing
 * @date 2020/12/15 2:32 PM
 * @since
 */
@SpringBootTest
public class FooSimpleDAOTest {
    @Autowired
    private FooSimpleDAO fooSimpleDAO;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @Test
    public void listData() {
        fooSimpleDAO.listData();
    }

    @Test
    public void insertData(){
        fooSimpleDAO.insertData();
    }
}
