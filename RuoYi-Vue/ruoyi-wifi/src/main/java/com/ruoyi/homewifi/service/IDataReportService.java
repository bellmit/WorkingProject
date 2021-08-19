package com.ruoyi.homewifi.service;

import java.util.List;
import com.ruoyi.homewifi.domain.DataReport;

/**
 * 竣工报告查询Service接口
 * 
 * @author zyk
 * @date 2021-08-19
 */
public interface IDataReportService 
{
    /**
     * 查询竣工报告查询
     * 
     * @param id 竣工报告查询ID
     * @return 竣工报告查询
     */
    public DataReport selectDataReportById(Long id);

    /**
     * 查询竣工报告查询列表
     * 
     * @param dataReport 竣工报告查询
     * @return 竣工报告查询集合
     */
    public List<DataReport> selectDataReportList(DataReport dataReport);

    /**
     * 新增竣工报告查询
     * 
     * @param dataReport 竣工报告查询
     * @return 结果
     */
    public int insertDataReport(DataReport dataReport);

    /**
     * 修改竣工报告查询
     * 
     * @param dataReport 竣工报告查询
     * @return 结果
     */
    public int updateDataReport(DataReport dataReport);

    /**
     * 批量删除竣工报告查询
     * 
     * @param ids 需要删除的竣工报告查询ID
     * @return 结果
     */
    public int deleteDataReportByIds(Long[] ids);

    /**
     * 删除竣工报告查询信息
     * 
     * @param id 竣工报告查询ID
     * @return 结果
     */
    public int deleteDataReportById(Long id);
}
