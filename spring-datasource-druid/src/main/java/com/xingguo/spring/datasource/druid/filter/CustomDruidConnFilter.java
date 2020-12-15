/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.datasource.druid.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;

import java.util.Properties;

/**
 * CustomDruidConnFilter
 * 对于自定义 druid conn filter
 * 参考druid 默认配置{@see com.alibaba.druid.filter.stat.StatFilter}
 *
 * @author guoxing
 * @date 2020/12/15 9:40 AM
 * @since
 */
public class CustomDruidConnFilter extends FilterEventAdapter {
    /**
     * 可以通过利用{@link FilterEventAdapter}中提供的扩展点进行有选择的通过重写扩展
     * 执行相关扩展操作
     */


    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        System.out.println("BEFORE CONNECTION");
    }

    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        System.out.println("AFTER CONNECTION");
    }
}
