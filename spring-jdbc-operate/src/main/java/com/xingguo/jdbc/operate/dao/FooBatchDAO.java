/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.jdbc.operate.dao;

import com.xingguo.jdbc.operate.entity.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * FooBatchDAO
 *
 * @author guoxing
 * @date 2020/12/15 2:24 PM
 * @since
 */
@Repository
public class FooBatchDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchInsert() {
        // 返回 每行执行语句影响行数
        int[] ints = jdbcTemplate.batchUpdate("insert into FOO (ID,BAR) values(?,?)", new BatchPreparedStatementSetter() {
            /**
             * 预处理数据
             *
             * @param ps 数据预处理
             * @param i 插入数据计数器
             * @throws SQLException
             */
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // 插入指定的数据
                ps.setInt(1, 2 + i);
                ps.setString(2, "batch-" + i);
            }

            /**
             *
             * @return 当前要插入的数据总数据量
             */
            @Override
            public int getBatchSize() {
                // 设置要插入的数量
                return 2;
            }
        });
        Arrays.stream(ints)
                .forEach(System.out::println);
        List<String> fooList = jdbcTemplate.queryForList("select BAR from FOO", String.class);
        // 当返回结果数量大于1时会抛出相关 异常信息 :
        // org.springframework.dao.IncorrectResultSizeDataAccessException: Incorrect result size: expected 1, actual 3
//         jdbcTemplate.queryForObject("select BAR from FOO", String.class);

        fooList.forEach(System.out::println);
    }

    /**
     * 使用 {@link org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate} 实现自定义批量插入
     *
     * @author guoxing
     * @date 2020-12-15 3:33 PM
     * @since
     */
    public void nameParameterBatchInsert() {
        // 通过提交map数组实现批量操作
        ArrayList<Foo> foos = new ArrayList<>();
        Foo foo = new Foo();
        foo.setBar("name-batch-1");
        foos.add(foo);
        // NamedParameterJdbcTemplate  可以使用 ":{属性名}"的形式来指定 实体中的属性名
//        int[] ints = namedParameterJdbcTemplate.batchUpdate("insert into FOO (BAR) values(:bar)", SqlParameterSourceUtils.createBatch(foos));
        // 虽然 :属性名 和 :{属性名}两种方式都支持, 使用 :{属性名} 这种方式相对来讲性能会更高效一点
        /**
         * {@link NamedParameterUtils#parseSqlStatement(java.lang.String)}
         * 对于 // :{x} style parameter 这种模式的相对而言解析更快捷
         */
        int[] ints = namedParameterJdbcTemplate.batchUpdate("insert into FOO (BAR) values(:{bar})", SqlParameterSourceUtils.createBatch(foos));
        Arrays.stream(ints)
                .forEach(System.out::println);
        List<String> fooList = jdbcTemplate.queryForList("select BAR from FOO", String.class);
        fooList.forEach(System.out::println);
    }
}
