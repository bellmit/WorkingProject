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
    @Value("${spring.elasticsearch.loginLog}")
    private String loginLogTable;
    @Value("${spring.elasticsearch.operationLog}")
    private String searchLogTable;

    //生成的日志存储地址
    @Value("${spring.datatransport.localdir}")
    private String filePath;

    //sftp 配置
    @Value("${spring.datatransport.sftplocaldir}")
    private String sftplocaldir;
    @Value("${spring.datatransport.sftpdir}")
    private String sftpremotedir;
    @Value("${spring.datatransport.sftp.name}")
    private String sftpname;
    @Value("${spring.datatransport.sftp.pass}")
    private String sftppass;
    @Value("${spring.datatransport.sftp.ip}")
    private String sftpip;
    @Value("${spring.datatransport.sftp.port}")
    private String sftpport;

    public String getSftplocaldir() {
        return sftplocaldir;
    }

    public void setSftplocaldir(String sftplocaldir) {
        this.sftplocaldir = sftplocaldir;
    }

    public String getSftpremotedir() {
        return sftpremotedir;
    }

    public void setSftpremotedir(String sftpremotedir) {
        this.sftpremotedir = sftpremotedir;
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
