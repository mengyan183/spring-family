/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.jdbc.operate;

import com.xingguo.jdbc.operate.dao.FooBatchDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * FooBatchDAOTest
 *
 * @author guoxing
 * @date 2020/12/15 3:28 PM
 * @since
 */
@SpringBootTest
public class FooBatchDAOTest {
    @Autowired
    private FooBatchDAO fooBatchDAO;

    @Test
    public void batchInsert(){
        fooBatchDAO.batchInsert();
    }

    @Test
    public void nameParameterBatchInsert(){
        fooBatchDAO.nameParameterBatchInsert();
    }
}
