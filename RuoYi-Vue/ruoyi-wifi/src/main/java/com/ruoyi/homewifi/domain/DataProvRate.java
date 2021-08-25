package com.ruoyi.homewifi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 分省份四率统计对象 data_prov_rate
 * 
 * @author z
 * @date 2021-08-25
 */
public class DataProvRate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 省份 */
    @Excel(name = "省份")
    private String provName;

    /** 有效报告数 */
    @Excel(name = "有效报告数")
    private Integer effectiveSum;

    /** 新增礼包数 */
    @Excel(name = "新增礼包数")
    private Integer newGiftSum;

    /** 新增含终端礼包数 */
    @Excel(name = "新增含终端礼包数")
    private Integer newTermiGiftSum;

    /** 新增纯服务礼包数 */
    @Excel(name = "新增纯服务礼包数")
    private Integer newServiGiftSum;

    /** 新增e_link/e_OS终端数 */
    @Excel(name = "新增e_link/e_OS终端数")
    private Integer newElinkSum;

    /** 交付地一致数 */
    @Excel(name = "交付地一致数")
    private Integer sameAreaSum;

    /** 测速达标数 */
    @Excel(name = "测速达标数")
    private Integer wifiCheckedSum;

    /** 报告分享数 */
    @Excel(name = "报告分享数")
    private Integer shareSum;

    /** 小翼管家分享数 */
    @Excel(name = "小翼管家分享数")
    private Integer yiShareSum;

    /** 短信分享数 */
    @Excel(name = "短信分享数")
    private Integer messageShareSum;

    /** 微信分享数 */
    @Excel(name = "微信分享数")
    private Integer wechatShareSum;

    /** 其他分享数 */
    @Excel(name = "其他分享数")
    private Integer otherShareSum;

    /** 有效报告匹配率 */
    @Excel(name = "有效报告匹配率")
    private String effectiveReportRate;

    /** 交付地一致率 */
    @Excel(name = "交付地一致率")
    private String sameAreaRate;

    /** WiFi测速达标率 */
    @Excel(name = "WiFi测速达标率")
    private String wifiCheckedRate;

    /** 终端稽核率 */
    @Excel(name = "终端稽核率")
    private String elinkCheckedRate;

    /** 报告分享率 */
    @Excel(name = "报告分享率")
    private String shareRate;

    /** 四率保留字段1 */
    private String rateReserve1;

    /** 四率保留字段2 */
    private String rateReserve2;

    /** 四率保留字段3 */
    private String rateReserve3;

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
    public void setEffectiveSum(Integer effectiveSum) 
    {
        this.effectiveSum = effectiveSum;
    }

    public Integer getEffectiveSum() 
    {
        return effectiveSum;
    }
    public void setNewGiftSum(Integer newGiftSum) 
    {
        this.newGiftSum = newGiftSum;
    }

    public Integer getNewGiftSum() 
    {
        return newGiftSum;
    }
    public void setNewTermiGiftSum(Integer newTermiGiftSum) 
    {
        this.newTermiGiftSum = newTermiGiftSum;
    }

    public Integer getNewTermiGiftSum() 
    {
        return newTermiGiftSum;
    }
    public void setNewServiGiftSum(Integer newServiGiftSum) 
    {
        this.newServiGiftSum = newServiGiftSum;
    }

    public Integer getNewServiGiftSum() 
    {
        return newServiGiftSum;
    }
    public void setNewElinkSum(Integer newElinkSum) 
    {
        this.newElinkSum = newElinkSum;
    }

    public Integer getNewElinkSum() 
    {
        return newElinkSum;
    }
    public void setSameAreaSum(Integer sameAreaSum) 
    {
        this.sameAreaSum = sameAreaSum;
    }

    public Integer getSameAreaSum() 
    {
        return sameAreaSum;
    }
    public void setWifiCheckedSum(Integer wifiCheckedSum) 
    {
        this.wifiCheckedSum = wifiCheckedSum;
    }

    public Integer getWifiCheckedSum() 
    {
        return wifiCheckedSum;
    }
    public void setShareSum(Integer shareSum) 
    {
        this.shareSum = shareSum;
    }

    public Integer getShareSum() 
    {
        return shareSum;
    }
    public void setYiShareSum(Integer yiShareSum) 
    {
        this.yiShareSum = yiShareSum;
    }

    public Integer getYiShareSum() 
    {
        return yiShareSum;
    }
    public void setMessageShareSum(Integer messageShareSum) 
    {
        this.messageShareSum = messageShareSum;
    }

    public Integer getMessageShareSum() 
    {
        return messageShareSum;
    }
    public void setWechatShareSum(Integer wechatShareSum) 
    {
        this.wechatShareSum = wechatShareSum;
    }

    public Integer getWechatShareSum() 
    {
        return wechatShareSum;
    }
    public void setOtherShareSum(Integer otherShareSum) 
    {
        this.otherShareSum = otherShareSum;
    }

    public Integer getOtherShareSum() 
    {
        return otherShareSum;
    }
    public void setEffectiveReportRate(String effectiveReportRate) 
    {
        this.effectiveReportRate = effectiveReportRate;
    }

    public String getEffectiveReportRate() 
    {
        return effectiveReportRate;
    }
    public void setSameAreaRate(String sameAreaRate) 
    {
        this.sameAreaRate = sameAreaRate;
    }

    public String getSameAreaRate() 
    {
        return sameAreaRate;
    }
    public void setWifiCheckedRate(String wifiCheckedRate) 
    {
        this.wifiCheckedRate = wifiCheckedRate;
    }

    public String getWifiCheckedRate() 
    {
        return wifiCheckedRate;
    }
    public void setElinkCheckedRate(String elinkCheckedRate) 
    {
        this.elinkCheckedRate = elinkCheckedRate;
    }

    public String getElinkCheckedRate() 
    {
        return elinkCheckedRate;
    }
    public void setShareRate(String shareRate) 
    {
        this.shareRate = shareRate;
    }

    public String getShareRate() 
    {
        return shareRate;
    }
    public void setRateReserve1(String rateReserve1) 
    {
        this.rateReserve1 = rateReserve1;
    }

    public String getRateReserve1() 
    {
        return rateReserve1;
    }
    public void setRateReserve2(String rateReserve2) 
    {
        this.rateReserve2 = rateReserve2;
    }

    public String getRateReserve2() 
    {
        return rateReserve2;
    }
    public void setRateReserve3(String rateReserve3) 
    {
        this.rateReserve3 = rateReserve3;
    }

    public String getRateReserve3() 
    {
        return rateReserve3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("provName", getProvName())
            .append("effectiveSum", getEffectiveSum())
            .append("newGiftSum", getNewGiftSum())
            .append("newTermiGiftSum", getNewTermiGiftSum())
            .append("newServiGiftSum", getNewServiGiftSum())
            .append("newElinkSum", getNewElinkSum())
            .append("sameAreaSum", getSameAreaSum())
            .append("wifiCheckedSum", getWifiCheckedSum())
            .append("shareSum", getShareSum())
            .append("yiShareSum", getYiShareSum())
            .append("messageShareSum", getMessageShareSum())
            .append("wechatShareSum", getWechatShareSum())
            .append("otherShareSum", getOtherShareSum())
            .append("effectiveReportRate", getEffectiveReportRate())
            .append("sameAreaRate", getSameAreaRate())
            .append("wifiCheckedRate", getWifiCheckedRate())
            .append("elinkCheckedRate", getElinkCheckedRate())
            .append("shareRate", getShareRate())
            .append("rateReserve1", getRateReserve1())
            .append("rateReserve2", getRateReserve2())
            .append("rateReserve3", getRateReserve3())
            .toString();
    }
}
