package com.smart.homewifi.mesh.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:Z
 * @Date:2021/11/15 16:30
 * @Description: ES配置类
 * @Version:1.0
 */

@Data
@Configuration
public class EsConfig {
    //ES数据库
    @Value("${spring.elasticsearch.cluster.name}")
    private String esCluseterName;
    @Value("${spring.elasticsearch.address}")
    private String esAddress;
    @Value("${spring.elasticsearch.zen.discovery.port}")
    private String esZenPort;
    @Value("${spring.elasticsearch.port}")
    private String esPort;
    @Value("${spring.elasticsearch.index.apIndexName}")
    private String apIndex;
    @Value("${spring.elasticsearch.type.apTypeName}")
    private String apType;
    @Value("${spring.elasticsearch.index.gwIndexName}")
    private String gwIndex;
    @Value("${spring.elasticsearch.type.gwTypeName}")
    private String gwType;

}
