package com.ruoyi.homewifi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 数据湖工单下发对象 data_installation_list
 * 
 * @author ruoyi
 * @date 2021-08-11
 */
public class DataInstallationList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 工单号 */
    @Excel(name = "工单号")
    private String workOrderNum;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dayId;

    /** 省份id */
    @Excel(name = "省份id")
    private Integer deptId;

    /** 地市id */
    @Excel(name = "地市id")
    private Integer cityId;

    /** 地市编号 */
    @Excel(name = "地市编号")
    private String areaId;

    /** 交付地一致性校验是否成功 */
    @Excel(name = "交付地一致性校验是否成功")
    private Integer sameArea;

    /** 是否有效竣工报告 */
    @Excel(name = "是否有效竣工报告")
    private Integer effectiveReport;

    /** 终端稽核是否成功 */
    @Excel(name = "终端稽核是否成功")
    private Integer elinkChecked;

    /** WiFi测速是否达标 */
    @Excel(name = "WiFi测速是否达标")
    private Integer wifiChecked;

    /** 交付报告是否分享 */
    @Excel(name = "交付报告是否分享")
    private Integer shareChecked;

    /** 交付报告分享渠道 */
    @Excel(name = "交付报告分享渠道")
    private Integer shareMethod;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWorkOrderNum(String workOrderNum) 
    {
        this.workOrderNum = workOrderNum;
    }

    public String getWorkOrderNum() 
    {
        return workOrderNum;
    }
    public void setDayId(Date dayId) 
    {
        this.dayId = dayId;
    }

    public Date getDayId() 
    {
        return dayId;
    }
    public void setDeptId(Integer deptId) 
    {
        this.deptId = deptId;
    }

    public Integer getDeptId() 
    {
        return deptId;
    }
    public void setCityId(Integer cityId) 
    {
        this.cityId = cityId;
    }

    public Integer getCityId() 
    {
        return cityId;
    }
    public void setAreaId(String areaId) 
    {
        this.areaId = areaId;
    }

    public String getAreaId() 
    {
        return areaId;
    }
    public void setSameArea(Integer sameArea) 
    {
        this.sameArea = sameArea;
    }

    public Integer getSameArea() 
    {
        return sameArea;
    }
    public void setEffectiveReport(Integer effectiveReport) 
    {
        this.effectiveReport = effectiveReport;
    }

    public Integer getEffectiveReport() 
    {
        return effectiveReport;
    }
    public void setElinkChecked(Integer elinkChecked) 
    {
        this.elinkChecked = elinkChecked;
    }

    public Integer getElinkChecked() 
    {
        return elinkChecked;
    }
    public void setWifiChecked(Integer wifiChecked) 
    {
        this.wifiChecked = wifiChecked;
    }

    public Integer getWifiChecked() 
    {
        return wifiChecked;
    }
    public void setShareChecked(Integer shareChecked) 
    {
        this.shareChecked = shareChecked;
    }

    public Integer getShareChecked() 
    {
        return shareChecked;
    }
    public void setShareMethod(Integer shareMethod) 
    {
        this.shareMethod = shareMethod;
    }

    public Integer getShareMethod() 
    {
        return shareMethod;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workOrderNum", getWorkOrderNum())
            .append("dayId", getDayId())
            .append("deptId", getDeptId())
            .append("cityId", getCityId())
            .append("areaId", getAreaId())
            .append("sameArea", getSameArea())
            .append("effectiveReport", getEffectiveReport())
            .append("elinkChecked", getElinkChecked())
            .append("wifiChecked", getWifiChecked())
            .append("shareChecked", getShareChecked())
            .append("shareMethod", getShareMethod())
            .toString();
    }
}
