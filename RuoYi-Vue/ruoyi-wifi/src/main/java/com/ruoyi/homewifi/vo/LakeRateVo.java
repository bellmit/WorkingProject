package com.ruoyi.homewifi.vo;

import com.ruoyi.common.core.domain.BaseEntity;

import java.sql.Date;

/**
 * @Author:Z
 * @Data:2021/9/1 14:04
 * @Description: 四率条件查询视觉转换对象父类
 * @Version:1.0
 */
public class LakeRateVo extends BaseEntity {
    private Date startDate;
    private Date endDate;
    private String lakeProvId;

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

    @Override
    public String toString() {
        return "LakeRateVo{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", lakeProvId='" + lakeProvId + '\'' +
                '}';
    }
}
