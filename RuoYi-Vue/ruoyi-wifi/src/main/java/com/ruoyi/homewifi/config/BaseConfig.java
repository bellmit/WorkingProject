package com.ruoyi.homewifi.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//@Configuration
//@PropertySource("classpath:application.yml")
@Component
public class BaseConfig {

    //sftp配置
    @Value("${spring.datatransport.localdir}")
    private String localdir;
    //sftp 配置
    @Value("${spring.datatransport.sftpdir}")
    private String sftpdir;
    @Value("${spring.datatransport.sftp.name}")
    private String sftpname;
    @Value("${spring.datatransport.sftp.pass}")
    private String sftppass;
    @Value("${spring.datatransport.sftp.ip}")
    private String sftpip;
    @Value("${spring.datatransport.sftp.port}")
    private String sftpport;


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

    public String getLocaldir() {
        return localdir;
    }

    public void setLocaldir(String localdir) {
        this.localdir = localdir;
    }

    public String getSftpdir() {
        return sftpdir;
    }

    public void setSftpdir(String sftpdir) {
        this.sftpdir = sftpdir;
    }

    public String getSftpname() {
        return sftpname;
    }

    public void setSftpname(String sftpname) {
        this.sftpname = sftpname;
    }

    public String getSftppass() {
        return sftppass;
    }

    public void setSftppass(String sftppass) {
        this.sftppass = sftppass;
    }

    public String getSftpip() {
        return sftpip;
    }

    public void setSftpip(String sftpip) {
        this.sftpip = sftpip;
    }

    public String getSftpport() {
        return sftpport;
    }

    public void setSftpport(String sftpport) {
        this.sftpport = sftpport;
    }

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

    @Override
    public String toString() {
        return "BaseConfig{" +
                "localdir='" + localdir + '\'' +
                ", sftpdir='" + sftpdir + '\'' +
                ", sftpname='" + sftpname + '\'' +
                ", sftppass='" + sftppass + '\'' +
                ", sftpip='" + sftpip + '\'' +
                ", sftpport='" + sftpport + '\'' +
                ", esCluseterName='" + esCluseterName + '\'' +
                ", esAddress='" + esAddress + '\'' +
                ", esZenPort='" + esZenPort + '\'' +
                ", esPort='" + esPort + '\'' +
                ", apIndex='" + apIndex + '\'' +
                ", apType='" + apType + '\'' +
                '}';
    }
}
