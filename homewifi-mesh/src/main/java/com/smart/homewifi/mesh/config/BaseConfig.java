package com.smart.homewifi.mesh.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:Z
 * @Data:2021/11/15 14:01
 * @Description: 配置类
 * @Version:1.0
 */
@Data
@Configuration
public class BaseConfig {
    //路由器状态文件上传时间
    @Value("${mesh.time.routerUpdateDay}")
    private Integer routerUpdateDay;

    //网关状态文件上传时间
    @Value("${mesh.time.gatewayUpdateDay}")
    private Integer gatewayUpdateDay;

    //上传文件地址
    @Value("${mesh.fileUpdate.localdir}")
    private String localdir;

    //线程池核心线程数
    @Value("${spring.threads.pool.corePoolSize}")
    private Integer corePoolSize;

}
