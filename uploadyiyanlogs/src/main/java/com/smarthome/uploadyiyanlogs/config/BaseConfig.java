package com.smarthome.uploadyiyanlogs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author:KUN
 * @Data:2021/7/2 08:56
 * @Description: 配置类
 * @Version:1.0
 */

@Component
public class BaseConfig {
    //ES数据库
    @Value("${spring.elasticsearch.address}")
    private String esAddress;

    @Value("${spring.elasticsearch.zen.discovery.port}")
    private String esZenPort;

    @Value("${spring.elasticsearch.port}")
    private String esPort;

    @Value("${spring.elasticsearch.cluster.name}")
    private String esClusterName;

    @Value("${spring.datatransport.localdir}")
    private String filePath;

    @Value("${spring.elasticsearch.loginLog}")
    private String loginLogTable;

    @Value("${spring.elasticsearch.operationLog}")
    private String searchLogTable;

    public String getLoginLogTable() {
        return loginLogTable;
    }

    public void setLoginLogTable(String loginLogTable) {
        this.loginLogTable = loginLogTable;
    }

    public String getSearchLogTable() {
        return searchLogTable;
    }

    public void setSearchLogTable(String searchLogTable) {
        this.searchLogTable = searchLogTable;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getEsAddress() {
        return esAddress;
    }

    public void setEsAddress(String esAddress) {
        this.esAddress = esAddress;
    }

    public String getEsZenPort() {
        return esZenPort;
    }

    public void setEsZenPort(String esZenPort) {
        this.esZenPort = esZenPort;
    }

    public String getEsPort() {
        return esPort;
    }

    public void setEsPort(String esPort) {
        this.esPort = esPort;
    }

    public String getEsClusterName() {
        return esClusterName;
    }

    public void setEsClusterName(String esClusterName) {
        this.esClusterName = esClusterName;
    }
}
