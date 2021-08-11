package com.ruoyi.homewifi.mapper;

import java.util.List;
import com.ruoyi.homewifi.domain.DataInstallationList;

/**
 * 数据湖工单下发Mapper接口
 * 
 * @author ruoyi
 * @date 2021-08-11
 */
public interface DataInstallationListMapper 
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
     * 删除数据湖工单下发
     * 
     * @param id 数据湖工单下发ID
     * @return 结果
     */
    public int deleteDataInstallationListById(Long id);

    /**
     * 批量删除数据湖工单下发
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataInstallationListByIds(Long[] ids);
}
