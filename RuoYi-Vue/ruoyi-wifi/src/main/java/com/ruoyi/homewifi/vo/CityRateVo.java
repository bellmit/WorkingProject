package com.ruoyi.homewifi.vo;

import java.sql.Date;

/**
 * @Author:Z
 * @Data:2021/8/26 10:22
 * @Description: 分地市四率查询视觉对象
 * @Version:1.0
 */
public class CityRateVo {
    private Date startDate;
    private Date endDate;
    private String wProvId;
    private String wCityId;

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

    public String getwProvId() {
        return wProvId;
    }

    public void setwProvId(String wProvId) {
        this.wProvId = wProvId;
    }

    public String getwCityId() {
        return wCityId;
    }

    public void setwCityId(String wCityId) {
        this.wCityId = wCityId;
    }

    @Override
    public String toString() {
        return "CityRateVO{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", wProvId='" + wProvId + '\'' +
                ", wCityId='" + wCityId + '\'' +
                '}';
    }
}
