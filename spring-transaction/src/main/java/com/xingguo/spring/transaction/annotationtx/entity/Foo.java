/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.spring.transaction.annotationtx.entity;

/**
 * Foo
 *
 * @author guoxing
 * @date 2020/12/20 6:10 PM
 * @since
 */
public class Foo {
    private Long id;

    private String bar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "id=" + id +
                ", bar='" + bar + '\'' +
                '}';
    }
}
