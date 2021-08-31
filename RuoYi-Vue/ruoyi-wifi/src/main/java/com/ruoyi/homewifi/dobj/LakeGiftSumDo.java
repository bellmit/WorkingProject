package com.ruoyi.homewifi.dobj;

/**
 * @Author:Z
 * @Data:2021/8/30 15:28
 * @Description: 数据湖下发礼包数据统计结果
 * @Version:1.0
 */
public class LakeGiftSumDo {
    private String deptId;
    private String lakeCityId;
    private Integer giftCodeSum;
    private Integer giftOrderSum;
    private Integer termiGiftSum;
    private Integer serviGiftSum;

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

    public Integer getGiftCodeSum() {
        return giftCodeSum;
    }

    public void setGiftCodeSum(Integer giftCodeSum) {
        this.giftCodeSum = giftCodeSum;
    }

    public Integer getGiftOrderSum() {
        return giftOrderSum;
    }

    public void setGiftOrderSum(Integer giftOrderSum) {
        this.giftOrderSum = giftOrderSum;
    }

    public Integer getTermiGiftSum() {
        return termiGiftSum;
    }

    public void setTermiGiftSum(Integer termiGiftSum) {
        this.termiGiftSum = termiGiftSum;
    }

    public Integer getServiGiftSum() {
        return serviGiftSum;
    }

    public void setServiGiftSum(Integer serviGiftSum) {
        this.serviGiftSum = serviGiftSum;
    }

    @Override
    public String toString() {
        return "LakeGiftSumDo{" +
                "deptId='" + deptId + '\'' +
                ", lakeCityId='" + lakeCityId + '\'' +
                ", giftCodeSum=" + giftCodeSum +
                ", giftOrderSum=" + giftOrderSum +
                ", termiGiftSum=" + termiGiftSum +
                ", serviGiftSum=" + serviGiftSum +
                '}';
    }
}
