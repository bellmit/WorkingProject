package com.ruoyi.homewifi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.homewifi.mapper.DataProvRateMapper;
import com.ruoyi.homewifi.domain.DataProvRate;
import com.ruoyi.homewifi.service.IDataProvRateService;

/**
 * 分省份四率统计Service业务层处理
 * 
 * @author z
 * @date 2021-08-25
 */
@Service
public class DataProvRateServiceImpl implements IDataProvRateService 
{
    @Autowired
    private DataProvRateMapper dataProvRateMapper;

    /**
     * 查询分省份四率统计
     * 
     * @param id 分省份四率统计ID
     * @return 分省份四率统计
     */
    @Override
    public DataProvRate selectDataProvRateById(Long id)
    {
        return dataProvRateMapper.selectDataProvRateById(id);
    }

    /**
     * 查询分省份四率统计列表
     * 
     * @param dataProvRate 分省份四率统计
     * @return 分省份四率统计
     */
    @Override
    public List<DataProvRate> selectDataProvRateList(DataProvRate dataProvRate)
    {
        return dataProvRateMapper.selectDataProvRateList(dataProvRate);
    }

    /**
     * 新增分省份四率统计
     * 
     * @param dataProvRate 分省份四率统计
     * @return 结果
     */
    @Override
    public int insertDataProvRate(DataProvRate dataProvRate)
    {
        return dataProvRateMapper.insertDataProvRate(dataProvRate);
    }

    /**
     * 修改分省份四率统计
     * 
     * @param dataProvRate 分省份四率统计
     * @return 结果
     */
    @Override
    public int updateDataProvRate(DataProvRate dataProvRate)
    {
        return dataProvRateMapper.updateDataProvRate(dataProvRate);
    }

    /**
     * 批量删除分省份四率统计
     * 
     * @param ids 需要删除的分省份四率统计ID
     * @return 结果
     */
    @Override
    public int deleteDataProvRateByIds(Long[] ids)
    {
        return dataProvRateMapper.deleteDataProvRateByIds(ids);
    }

    /**
     * 删除分省份四率统计信息
     * 
     * @param id 分省份四率统计ID
     * @return 结果
     */
    @Override
    public int deleteDataProvRateById(Long id)
    {
        return dataProvRateMapper.deleteDataProvRateById(id);
    }
}
