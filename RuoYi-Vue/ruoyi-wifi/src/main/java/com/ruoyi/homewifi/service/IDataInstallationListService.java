package com.ruoyi.homewifi.service;

import java.util.List;
import com.ruoyi.homewifi.domain.DataInstallationList;

/**
 * 数据湖工单下发Service接口
 * 
 * @author ruoyi
 * @date 2021-08-11
 */
public interface IDataInstallationListService 
{
    /**
     * 查询数据湖工单下发
     * 
     * @param id 数据湖工单下发ID
     * @return 数据湖工单下发
     */
    public DataInstallationList selectDataInstallationListById(Long id);

    /**
     * 查询数据湖工单下发列表
     * 
     * @param dataInstallationList 数据湖工单下发
     * @return 数据湖工单下发集合
     */
    public List<DataInstallationList> selectDataInstallationListList(DataInstallationList dataInstallationList);

    /**
     * 新增数据湖工单下发
     * 
     * @param dataInstallationList 数据湖工单下发
     * @return 结果
     */
    public int insertDataInstallationList(DataInstallationList dataInstallationList);

    /**
     * 修改数据湖工单下发
     * 
     * @param dataInstallationList 数据湖工单下发
     * @return 结果
     */
    public int updateDataInstallationList(DataInstallationList dataInstallationList);

    /**
     * 批量删除数据湖工单下发
     * 
     * @param ids 需要删除的数据湖工单下发ID
     * @return 结果
     */
    public int deleteDataInstallationListByIds(Long[] ids);

    /**
     * 删除数据湖工单下发信息
     * 
     * @param id 数据湖工单下发ID
     * @return 结果
     */
    public int deleteDataInstallationListById(Long id);
}
