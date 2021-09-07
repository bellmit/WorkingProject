package com.ruoyi.homewifi.vo;

import com.ruoyi.common.core.domain.BaseEntity;

import java.sql.Date;

/**
 * @Author:Z
 * @Data:2021/9/1 00:13
 * @Description: 省份四率条件查询视觉对象
 * @Version:1.0
 */
public class ProvRateVo extends BaseEntity {
    private Date startDate;
    private Date endDate;
    private String wProvId;

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

    @Override
    public String toString() {
        return "ProvRateVo{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", wProvId='" + wProvId + '\'' +
                '}';
    }
}
