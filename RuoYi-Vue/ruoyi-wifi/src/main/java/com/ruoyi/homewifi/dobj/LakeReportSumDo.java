package com.ruoyi.homewifi.dobj;

import org.springframework.stereotype.Component;

/**
 * @Author:Z
 * @Data:2021/8/30 15:19
 * @Description: 数据湖竣工报告统计结果
 * @Version:1.0
 */
@Component
public class LakeReportSumDo {
    private String deptId;
    private String wProId;
    private String lakeCityId;
    private String wCityId;
    private Integer sameAreaSum;
    private Integer effectiveSum;
    private Integer wifiCheckedSum;
    private Integer shareSum;
    private Integer messageShareSum;
    private Integer wechatShareSum;
    private Integer yiShareSum;
    private Integer otherShareSum;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getwProId() {
        return wProId;
    }

    public void setwProId(String wProId) {
        this.wProId = wProId;
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

    public Integer getSameAreaSum() {
        return sameAreaSum;
    }

    public void setSameAreaSum(Integer sameAreaSum) {
        this.sameAreaSum = sameAreaSum;
    }

    public Integer getEffectiveSum() {
        return effectiveSum;
    }

    public void setEffectiveSum(Integer effectiveSum) {
        this.effectiveSum = effectiveSum;
    }

    public Integer getWifiCheckedSum() {
        return wifiCheckedSum;
    }

    public void setWifiCheckedSum(Integer wifiCheckedSum) {
        this.wifiCheckedSum = wifiCheckedSum;
    }

    public Integer getShareSum() {
        return shareSum;
    }

    public void setShareSum(Integer shareSum) {
        this.shareSum = shareSum;
    }

    public Integer getMessageShareSum() {
        return messageShareSum;
    }

    public void setMessageShareSum(Integer messageShareSum) {
        this.messageShareSum = messageShareSum;
    }

    public Integer getWechatShareSum() {
        return wechatShareSum;
    }

    public void setWechatShareSum(Integer wechatShareSum) {
        this.wechatShareSum = wechatShareSum;
    }

    public Integer getYiShareSum() {
        return yiShareSum;
    }

    public void setYiShareSum(Integer yiShareSum) {
        this.yiShareSum = yiShareSum;
    }

    public Integer getOtherShareSum() {
        return otherShareSum;
    }

    public void setOtherShareSum(Integer otherShareSum) {
        this.otherShareSum = otherShareSum;
    }

    @Override
    public String toString() {
        return "LakeReportSumDo{" +
                "deptId='" + deptId + '\'' +
                ", wProId='" + wProId + '\'' +
                ", lakeCityId='" + lakeCityId + '\'' +
                ", wCityId='" + wCityId + '\'' +
                ", sameAreaSum=" + sameAreaSum +
                ", effectiveSum=" + effectiveSum +
                ", wifiCheckedSum=" + wifiCheckedSum +
                ", shareSum=" + shareSum +
                ", messageShareSum=" + messageShareSum +
                ", wechatShareSum=" + wechatShareSum +
                ", yiShareSum=" + yiShareSum +
                ", otherShareSum=" + otherShareSum +
                '}';
    }
}
