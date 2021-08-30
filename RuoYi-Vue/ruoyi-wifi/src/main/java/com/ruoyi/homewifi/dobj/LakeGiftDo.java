package com.ruoyi.homewifi.dobj;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.sql.Date;

/**
 * @Author:Z
 * @Data:2021/8/26 13:41
 * @Description: 数据湖下发礼包数据DO对象
 * @Version:1.0
 */
public class LakeGiftDo {

    /** 主键id */
    private Long id;

    /** 分享时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date sealingDate;

    /** 集团省份编码 */
    private String deptId;

    /** 集团城市编码 */
    private String lakeCityId;

    /** 集团县区编码 */
    private String lakeAreaId;

    /** 礼包销售品编码 */
    private String giftCode;

    /** 礼包销售品订单号 */
    private String giftOrderId;

    public Date getSealingDate() {
        return sealingDate;
    }

    public void setSealingDate(Date sealingTime) {
        this.sealingDate = sealingTime;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getLakeCityId() {
        return lakeCityId;
    }

    public void setLakeCityId(String lakeCityId) {
        this.lakeCityId = lakeCityId;
    }

    public String getLakeAreaId() {
        return lakeAreaId;
    }

    public void setLakeAreaId(String lakeAreaId) {
        this.lakeAreaId = lakeAreaId;
    }

    public String getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
    }

    public String getGiftOrderId() {
        return giftOrderId;
    }

    public void setGiftOrderId(String giftId) {
        this.giftOrderId = giftId;
    }

    @Override
    public String toString() {
        return "LakeGiftDo{" +
                "id=" + id +
                ", sealingTime=" + sealingDate +
                ", deptId='" + deptId + '\'' +
                ", lakeCityId='" + lakeCityId + '\'' +
                ", lakeAreaId='" + lakeAreaId + '\'' +
                ", giftCode='" + giftCode + '\'' +
                ", giftId='" + giftOrderId + '\'' +
                '}';
    }
}
