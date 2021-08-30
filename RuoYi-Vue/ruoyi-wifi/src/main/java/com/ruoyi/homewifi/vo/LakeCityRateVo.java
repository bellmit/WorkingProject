package com.ruoyi.homewifi.vo;

import java.sql.Date;

/**
 * @Author:Z
 * @Data:2021/8/26 15:40
 * @Description: 数据湖下发的礼包数据查询需要使用集团的地名编码
 * @Version:1.0
 */
public class LakeCityRateVo {
    private Date startDate;
    private Date endDate;
    private String lakeProvId;
    private String lakeCityId;

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

    public String getLakeProvId() {
        return lakeProvId;
    }

    public void setLakeProvId(String lakeProvId) {
        this.lakeProvId = lakeProvId;
    }

    public String getLakeCityId() {
        return lakeCityId;
    }

    public void setLakeCityId(String lakeCityId) {
        this.lakeCityId = lakeCityId;
    }

    @Override
    public String toString() {
        return "LakeCityRateVo{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", lakeProvId='" + lakeProvId + '\'' +
                ", lakeCityId='" + lakeCityId + '\'' +
                '}';
    }
}
