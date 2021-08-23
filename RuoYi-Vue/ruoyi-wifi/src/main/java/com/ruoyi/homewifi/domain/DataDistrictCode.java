package com.ruoyi.homewifi.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 字典数据表 data_district_code
 */
public class DataDistrictCode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 地区编号 */
    private Long strCode;

    /** 人数上限 */
    private Long strZip;

    /** 地区名称 */
    private String strRoleName;

    /** 上级地区编号 */
    private Long strParentCode;

    /** 地区级别 */
    private Integer intLevel;

    /** 地区拼音首字母 */
    private String strFirstLetter;

    /** 万维地区编码 */
    private String strAreaAuth;

    /** 集团地区编码 */
    private String dept_id;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dtUpdateTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dtCreateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStrCode() {
        return strCode;
    }

    public void setStrCode(Long strCode) {
        this.strCode = strCode;
    }

    public Long getStrZip() {
        return strZip;
    }

    public void setStrZip(Long strZip) {
        this.strZip = strZip;
    }

    public String getStrRoleName() {
        return strRoleName;
    }

    public void setStrRoleName(String strRoleName) {
        this.strRoleName = strRoleName;
    }

    public Long getStrParentCode() {
        return strParentCode;
    }

    public void setStrParentCode(Long strParentCode) {
        this.strParentCode = strParentCode;
    }

    public Integer getIntLevel() {
        return intLevel;
    }

    public void setIntLevel(Integer intLevel) {
        this.intLevel = intLevel;
    }

    public String getStrFirstLetter() {
        return strFirstLetter;
    }

    public void setStrFirstLetter(String strFirstLetter) {
        this.strFirstLetter = strFirstLetter;
    }

    public String getStrAreaAuth() {
        return strAreaAuth;
    }

    public void setStrAreaAuth(String strAreaAuth) {
        this.strAreaAuth = strAreaAuth;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public Date getDtUpdateTime() {
        return dtUpdateTime;
    }

    public void setDtUpdateTime(Date dtUpdateTime) {
        this.dtUpdateTime = dtUpdateTime;
    }

    public Date getDtCreateTime() {
        return dtCreateTime;
    }

    public void setDtCreateTime(Date dtCreateTime) {
        this.dtCreateTime = dtCreateTime;
    }
}
