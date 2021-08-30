package com.ruoyi.homewifi.dobj;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.sql.Date;

/**
 * @Author:Z
 * @Data:2021/8/26 13:39
 * @Description: 数据湖下发竣工报告DO对象
 * @Version:1.0
 */
public class LakeReportDo {
    /** 主键id */
    private Long id;

    /** 工单号 */
    private String lakeOrderid;

    /** 工单生成日期 */
    private Date dayId;

    /** 集团省份编码 */
    private String deptId;

    /** 万维省份编码 */
    private String wProvId;

    /** 集团城市编码 */
    private String lakeCityId;

    /** 万维城市编码 */
    private String wCityId;

    /** 万维县区编码 */
    private String wAreaId;

    /** 交付地一致性校验 */
    private Integer sameArea;

    /** 有效报告校验 */
    private Integer effectiveReport;

    /** 终端稽核校验 */
    private Integer elinkChecked;

    /** WiFi测速校验 */
    private Integer wifiChecked;

    /** 分享校验 */
    private Integer lakeShareChecked;

    /** 分享渠道 */
    private Integer lakeShareMethod;

    /** 3A返查宽带账号 */
    private String aaaPppoe;

    public String getLakeOrderid() {
        return lakeOrderid;
    }

    public void setLakeOrderid(String lakeOrderid) {
        this.lakeOrderid = lakeOrderid;
    }

    public Date getDayId() {
        return dayId;
    }

    public void setDayId(Date dayId) {
        this.dayId = dayId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getwProvId() {
        return wProvId;
    }

    public void setwProvId(String wProvId) {
        this.wProvId = wProvId;
    }

    public String getLakeCityId() {
        return lakeCityId;
    }

    public void setLakeCityId(String lakeCityId) {
        this.lakeCityId = lakeCityId;
    }

    public String getwCityId() {
        return wCityId;
    }

    public void setwCityId(String wCityId) {
        this.wCityId = wCityId;
    }

    public String getwAreaId() {
        return wAreaId;
    }

    public void setwAreaId(String wAreaId) {
        this.wAreaId = wAreaId;
    }

    public Integer getSameArea() {
        return sameArea;
    }

    public void setSameArea(Integer sameArea) {
        this.sameArea = sameArea;
    }

    public Integer getEffectiveReport() {
        return effectiveReport;
    }

    public void setEffectiveReport(Integer effectiveReport) {
        this.effectiveReport = effectiveReport;
    }

    public Integer getElinkChecked() {
        return elinkChecked;
    }

    public void setElinkChecked(Integer elinkChecked) {
        this.elinkChecked = elinkChecked;
    }

    public Integer getWifiChecked() {
        return wifiChecked;
    }

    public void setWifiChecked(Integer wifiChecked) {
        this.wifiChecked = wifiChecked;
    }

    public Integer getLakeShareChecked() {
        return lakeShareChecked;
    }

    public void setLakeShareChecked(Integer lakeShareChecked) {
        this.lakeShareChecked = lakeShareChecked;
    }

    public Integer getLakeShareMethod() {
        return lakeShareMethod;
    }

    public void setLakeShareMethod(Integer lakeShareMethod) {
        this.lakeShareMethod = lakeShareMethod;
    }

    public String getAaaPppoe() {
        return aaaPppoe;
    }

    public void setAaaPppoe(String aaaPppoe) {
        this.aaaPppoe = aaaPppoe;
    }

    @Override
    public String toString() {
        return "LakeReportDo{" +
                "id=" + id +
                ", lakeOrderid='" + lakeOrderid + '\'' +
                ", dayId=" + dayId +
                ", deptId='" + deptId + '\'' +
                ", wProvId='" + wProvId + '\'' +
                ", lakeCityId='" + lakeCityId + '\'' +
                ", wCityId='" + wCityId + '\'' +
                ", wAreaId='" + wAreaId + '\'' +
                ", sameArea=" + sameArea +
                ", effectiveReport=" + effectiveReport +
                ", elinkChecked=" + elinkChecked +
                ", wifiChecked=" + wifiChecked +
                ", lakeShareChecked=" + lakeShareChecked +
                ", lakeShareMethod=" + lakeShareMethod +
                ", aaaPppoe='" + aaaPppoe + '\'' +
                '}';
    }
}
