/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xing.autoconfig;

import com.xing.config.AConfig;
import com.xing.dto.A;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * AAutoConfiguration
 *
 * @author guoxing
 * @date 2021/11/1 10:24 PM
 * @since
 */
@ConditionalOnClass(A.class)
@Configuration
@Import({AConfig.class})
public class AAutoConfiguration {
}
