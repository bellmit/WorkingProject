package com.smarthome.uploadyiyanlogs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Author:KUN
 * @Data:2021/7/2 09:01
 * @Description: 数据库配置类
 * @Version:1.0
 */

@Configuration
@Order(2) //@Order注解主要用来控制配置类的加载顺序
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private BaseConfig baseConfig;
}
