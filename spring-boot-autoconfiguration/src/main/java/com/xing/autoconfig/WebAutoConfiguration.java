/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xing.autoconfig;

import com.xing.config.WebConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * WebAutoConfiguration
 *
 * @author guoxing
 * @date 2021/11/1 10:18 PM
 * @since
 */
@ConditionalOnWebApplication
@Configuration
@Import(WebConfig.class)
public class WebAutoConfiguration {
}
