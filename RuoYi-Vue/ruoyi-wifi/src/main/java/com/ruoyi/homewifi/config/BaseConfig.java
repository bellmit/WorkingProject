package com.ruoyi.homewifi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
//@Configuration
//@PropertySource("classpath:application.yml")
public class BaseConfig {

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

    public String getApType() {
        return apType;
    }

    public void setApType(String apType) {
        this.apType = apType;
    }

    public String getApIndex() {
        return apIndex;
    }

    public void setApIndex(String apIndex) {
        this.apIndex = apIndex;
    }

    public String getEsCluseterName() {
        return esCluseterName;
    }

    public void setEsCluseterName(String esCluseterName) {
        this.esCluseterName = esCluseterName;
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
}
