package com.ruoyi.homewifi.vo;

import java.sql.Date;

/**
 * @Author:Z
 * @Data:2021/8/26 15:40
 * @Description: 数据湖下发的礼包数据查询需要使用集团的地名编码
 * @Version:1.0
 */
public class LakeCityRateVo extends LakeRateVo{
    private String lakeCityId;

    public String getLakeCityId() {
        return lakeCityId;
    }

    public void setLakeCityId(String lakeCityId) {
        this.lakeCityId = lakeCityId;
    }

    @Override
    public String toString() {
        return super.toString()+"LakeCityRateVo{ lakeCityId='" + lakeCityId + '\'' +
                '}';
    }
}
