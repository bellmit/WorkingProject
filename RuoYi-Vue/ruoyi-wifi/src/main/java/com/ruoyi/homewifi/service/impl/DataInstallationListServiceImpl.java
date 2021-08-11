package com.ruoyi.homewifi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.homewifi.mapper.DataInstallationListMapper;
import com.ruoyi.homewifi.domain.DataInstallationList;
import com.ruoyi.homewifi.service.IDataInstallationListService;

/**
 * 数据湖工单下发Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-08-11
 */
@Service
public class DataInstallationListServiceImpl implements IDataInstallationListService 
{
    @Autowired
    private DataInstallationListMapper dataInstallationListMapper;

    /**
     * 查询数据湖工单下发
     * 
     * @param id 数据湖工单下发ID
     * @return 数据湖工单下发
     */
    @Override
    public DataInstallationList selectDataInstallationListById(Long id)
    {
        return dataInstallationListMapper.selectDataInstallationListById(id);
    }

    /**
     * 查询数据湖工单下发列表
     * 
     * @param dataInstallationList 数据湖工单下发
     * @return 数据湖工单下发
     */
    @Override
    public List<DataInstallationList> selectDataInstallationListList(DataInstallationList dataInstallationList)
    {
        return dataInstallationListMapper.selectDataInstallationListList(dataInstallationList);
    }

    /**
     * 新增数据湖工单下发
     * 
     * @param dataInstallationList 数据湖工单下发
     * @return 结果
     */
    @Override
    public int insertDataInstallationList(DataInstallationList dataInstallationList)
    {
        return dataInstallationListMapper.insertDataInstallationList(dataInstallationList);
    }

    /**
     * 修改数据湖工单下发
     * 
     * @param dataInstallationList 数据湖工单下发
     * @return 结果
     */
    @Override
    public int updateDataInstallationList(DataInstallationList dataInstallationList)
    {
        return dataInstallationListMapper.updateDataInstallationList(dataInstallationList);
    }

    /**
     * 批量删除数据湖工单下发
     * 
     * @param ids 需要删除的数据湖工单下发ID
     * @return 结果
     */
    @Override
    public int deleteDataInstallationListByIds(Long[] ids)
    {
        return dataInstallationListMapper.deleteDataInstallationListByIds(ids);
    }

    /**
     * 删除数据湖工单下发信息
     * 
     * @param id 数据湖工单下发ID
     * @return 结果
     */
    @Override
    public int deleteDataInstallationListById(Long id)
    {
        return dataInstallationListMapper.deleteDataInstallationListById(id);
    }
}
