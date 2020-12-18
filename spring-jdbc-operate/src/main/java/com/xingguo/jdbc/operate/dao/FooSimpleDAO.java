/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.jdbc.operate.dao;

import com.xingguo.jdbc.operate.entity.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * FooSimpleDAO
 * <p>
 * 相关测试类{@see com.xingguo.jdbc.operate.FooSimpleDAOTest}
 *
 * @author guoxing
 * @date 2020/12/15 2:22 PM
 * @since
 */
@Repository
public class FooSimpleDAO {
    // 注入springboot 自动装配 的 jdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // 注入自定义的 jdbc insert 工具类
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    public void insertData() {
        // 使用 jdbcTemplate 进行数据插入
        Arrays.asList("a", "b").forEach(s -> {
            // 通过 "?" 占位符传递参数
            jdbcTemplate.update("insert into FOO(BAR) values (?)", s);
        });

        // 单个写入数据 利用 simpleJdbcInsert 进行单个数据写入
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        // key 为字段名, value为数据
        stringObjectHashMap.put("BAR", "c");

        Number id = simpleJdbcInsert.executeAndReturnKey(stringObjectHashMap);
        System.out.printf("当前新增的数据自动生成的主键为:%s\n", id);


        // 为了展示写入的数据
        listData();
    }

    /**
     * 数据查询
     *
     * @author guoxing
     * @date 2020-12-15 2:29 PM
     * @since 1.0.0
     */
    public void listData() {
        // 统计当前表中的数据量
        Long count = jdbcTemplate.queryForObject("select count(*) from FOO", Long.class);
        System.out.printf("数据量为:%d\n", count);
        /// 查询列表对象数据;自定义组装数据
        List<Foo> fooList = jdbcTemplate.query("select * from FOO", (rs, rowNum) -> { // 采用lambda语法
            Foo foo = new Foo();
            // java.sql.ResultSet.getLong(int) 注释 the first column is 1
            foo.setId(rs.getLong(1));
            foo.setBar(rs.getString(2));
            return foo;
        });
        //控制台 打印数据
        fooList.forEach(System.out::println);

        // 查询单一字段列表数据
        List<String> bars = jdbcTemplate.queryForList("select BAR from FOO", String.class);
        bars.forEach(System.out::println);
    }
}
