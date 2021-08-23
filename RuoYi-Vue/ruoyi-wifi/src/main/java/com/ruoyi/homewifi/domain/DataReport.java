package com.ruoyi.homewifi.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.sql.Date;

/**
 * 竣工报告查询对象 data_report
 * 
 * @author zyk
 * @date 2021-08-19
 */
public class DataReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 省份 */
    @Excel(name = "省份")
    private String provName;

    /** 地市 */
    @Excel(name = "地市")
    private String cityName;

    /** 县区 */
    @Excel(name = "县区")
    private String areaName;

    /** 工单号 */
    @Excel(name = "工单号")
    private String orderid;

    /** 工单类型 */
    @Excel(name = "工单类型")
    private Integer ordertype;

    /** 装维工号 */
    @Excel(name = "装维工号")
    private String engineerId;

    /** 装维姓名 */
    @Excel(name = "装维姓名")
    private String engineerName;

    /** 装维手机号 */
    @Excel(name = "装维手机号")
    private String engineerTel;

    /** 工单宽带账号 */
    @Excel(name = "工单宽带账号")
    private String userAccount;

    /** 3A返查宽带账号 */
    @Excel(name = "3A返查宽带账号")
    private String aaaPppoe;

    /** 签约速率 / M */
    @Excel(name = "签约速率 / M")
    private Integer clientSignedSpeed;

    /** 终端信息 */
    @Excel(name = "终端信息")
    private String terminalMacList;

    /** 分享校验 */
    @Excel(name = "分享校验")
    private Integer wwShareChecked;

    /** 分享渠道 */
    @Excel(name = "分享渠道")
    private Integer wwShareMethod;

    /** 分享时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "分享时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date wwShareTime;

    /** 交付地一致性校验 */
    @Excel(name = "交付地一致性校验")
    private Integer sameArea;

    /** 有效报告校验 */
    @Excel(name = "有效报告校验")
    private Integer effectiveReport;

    /** 终端稽核校验 */
    @Excel(name = "终端稽核校验")
    private Integer elinkChecked;

    /** WiFi测速校验 */
    @Excel(name = "WiFi测速校验")
    private Integer wifiChecked;

    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProvName(String provName) 
    {
        this.provName = provName;
    }

    public String getProvName() 
    {
        return provName;
    }
    public void setCityName(String cityName) 
    {
        this.cityName = cityName;
    }

    public String getCityName() 
    {
        return cityName;
    }
    public void setAreaName(String areaName) 
    {
        this.areaName = areaName;
    }

    public String getAreaName() 
    {
        return areaName;
    }
    public void setOrderid(String orderid) 
    {
        this.orderid = orderid;
    }

    public String getOrderid() 
    {
        return orderid;
    }
    public void setOrdertype(Integer ordertype) 
    {
        this.ordertype = ordertype;
    }

    public Integer getOrdertype() 
    {
        return ordertype;
    }
    public void setEngineerId(String engineerId) 
    {
        this.engineerId = engineerId;
    }

    public String getEngineerId() 
    {
        return engineerId;
    }
    public void setEngineerName(String engineerName) 
    {
        this.engineerName = engineerName;
    }

    public String getEngineerName() 
    {
        return engineerName;
    }
    public void setEngineerTel(String engineerTel) 
    {
        this.engineerTel = engineerTel;
    }

    public String getEngineerTel() 
    {
        return engineerTel;
    }
    public void setUserAccount(String userAccount) 
    {
        this.userAccount = userAccount;
    }

    public String getUserAccount() 
    {
        return userAccount;
    }
    public void setAaaPppoe(String aaaPppoe) 
    {
        this.aaaPppoe = aaaPppoe;
    }

    public String getAaaPppoe() 
    {
        return aaaPppoe;
    }
    public void setClientSignedSpeed(Integer clientSignedSpeed) 
    {
        this.clientSignedSpeed = clientSignedSpeed;
    }

    public Integer getClientSignedSpeed() 
    {
        return clientSignedSpeed;
    }
    public void setTerminalMacList(String terminalMacList) 
    {
        this.terminalMacList = terminalMacList;
    }

    public String getTerminalMacList() 
    {
        return terminalMacList;
    }
    public void setWwShareChecked(Integer wwShareChecked) 
    {
        this.wwShareChecked = wwShareChecked;
    }

    public Integer getWwShareChecked() 
    {
        return wwShareChecked;
    }
    public void setWwShareMethod(Integer wwShareMethod) 
    {
        this.wwShareMethod = wwShareMethod;
    }

    public Integer getWwShareMethod() 
    {
        return wwShareMethod;
    }
    public void setWwShareTime(Date wwShareTime) 
    {
        this.wwShareTime = wwShareTime;
    }

    public Date getWwShareTime() 
    {
        return wwShareTime;
    }
    public void setSameArea(Integer sameArea) 
    {
        this.sameArea = sameArea;
    }

    public Integer getSameArea() 
    {
        return sameArea;
    }
    public void setEffectiveReport(Integer effectiveReport) 
    {
        this.effectiveReport = effectiveReport;
    }

    public Integer getEffectiveReport() 
    {
        return effectiveReport;
    }
    public void setElinkChecked(Integer elinkChecked) 
    {
        this.elinkChecked = elinkChecked;
    }

    public Integer getElinkChecked() 
    {
        return elinkChecked;
    }
    public void setWifiChecked(Integer wifiChecked) 
    {
        this.wifiChecked = wifiChecked;
    }

    public Integer getWifiChecked() 
    {
        return wifiChecked;
    }

    /*@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("provName", getProvName())
            .append("cityName", getCityName())
            .append("areaName", getAreaName())
            .append("orderid", getOrderid())
            .append("ordertype", getOrdertype())
            .append("engineerId", getEngineerId())
            .append("engineerName", getEngineerName())
            .append("engineerTel", getEngineerTel())
            .append("createTime", getCreateTime())
            .append("userAccount", getUserAccount())
            .append("aaaPppoe", getAaaPppoe())
            .append("clientSignedSpeed", getClientSignedSpeed())
            .append("terminalMacList", getTerminalMacList())
            .append("wwShareChecked", getWwShareChecked())
            .append("wwShareMethod", getWwShareMethod())
            .append("wwShareTime", getWwShareTime())
            .append("sameArea", getSameArea())
            .append("effectiveReport", getEffectiveReport())
            .append("elinkChecked", getElinkChecked())
            .append("wifiChecked", getWifiChecked())
            .toString();
    }*/

    @Override
    public String toString() {
        return "DataReport{" +
                "id=" + id +
                ", provName='" + provName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", areaName='" + areaName + '\'' +
                ", orderid='" + orderid + '\'' +
                ", ordertype=" + ordertype +
                ", engineerId='" + engineerId + '\'' +
                ", engineerName='" + engineerName + '\'' +
                ", engineerTel='" + engineerTel + '\'' +
                ", createTime '" + getCreateTime() +'\''+
                ", userAccount='" + userAccount + '\'' +
                ", aaaPppoe='" + aaaPppoe + '\'' +
                ", clientSignedSpeed=" + clientSignedSpeed +
                ", terminalMacList='" + terminalMacList + '\'' +
                ", wwShareChecked=" + wwShareChecked +
                ", wwShareMethod=" + wwShareMethod +
                ", wwShareTime=" + wwShareTime +
                ", sameArea=" + sameArea +
                ", effectiveReport=" + effectiveReport +
                ", elinkChecked=" + elinkChecked +
                ", wifiChecked=" + wifiChecked +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
