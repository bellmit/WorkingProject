package com.smarthome.uploadyiyanlogs.pojo;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @Author:KUN
 * @Data:2021/7/2 11:00
 * @Description: 操作类日志
 * @Version:1.0
 */
public class OperationLog {
    private String id;
    private String code;
    private Timestamp endDate;
    private String function;
    private String ip;
    private String method;
    private String module;
    private String operatingTarget;
    private BigInteger processingTime;
    private String remark;
    private Timestamp startDate;
    private int state;
    private String fkUserId;
    private String username;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperatingTarget() {
        return operatingTarget;
    }

    public void setOperatingTarget(String operatingTarget) {
        this.operatingTarget = operatingTarget;
    }

    public BigInteger getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(BigInteger processingTime) {
        this.processingTime = processingTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(String fkUserId) {
        this.fkUserId = fkUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", endDate=" + endDate +
                ", function='" + function + '\'' +
                ", ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                ", module='" + module + '\'' +
                ", operatingTarget='" + operatingTarget + '\'' +
                ", processingTime=" + processingTime +
                ", remark='" + remark + '\'' +
                ", startDate=" + startDate +
                ", state=" + state +
                ", fkUserId='" + fkUserId + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
