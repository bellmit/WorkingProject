package com.ruoyi.homewifi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.homewifi.mapper.DataCityRateMapper;
import com.ruoyi.homewifi.domain.DataCityRate;
import com.ruoyi.homewifi.service.IDataCityRateService;

/**
 * 分地市四率统计Service业务层处理
 * 
 * @author z
 * @date 2021-08-25
 */
@Service
public class DataCityRateServiceImpl implements IDataCityRateService 
{
    @Autowired
    private DataCityRateMapper dataCityRateMapper;

    /**
     * 查询分地市四率统计
     * 
     * @param id 分地市四率统计ID
     * @return 分地市四率统计
     */
    @Override
    public DataCityRate selectDataCityRateById(Long id)
    {
        return dataCityRateMapper.selectDataCityRateById(id);
    }

    /**
     * 查询分地市四率统计列表
     * 
     * @param dataCityRate 分地市四率统计
     * @return 分地市四率统计
     */
    @Override
    public List<DataCityRate> selectDataCityRateList(DataCityRate dataCityRate)
    {
        return dataCityRateMapper.selectDataCityRateList(dataCityRate);
    }

    /**
     * 新增分地市四率统计
     * 
     * @param dataCityRate 分地市四率统计
     * @return 结果
     */
    @Override
    public int insertDataCityRate(DataCityRate dataCityRate)
    {
        return dataCityRateMapper.insertDataCityRate(dataCityRate);
    }

    /**
     * 修改分地市四率统计
     * 
     * @param dataCityRate 分地市四率统计
     * @return 结果
     */
    @Override
    public int updateDataCityRate(DataCityRate dataCityRate)
    {
        return dataCityRateMapper.updateDataCityRate(dataCityRate);
    }

    /**
     * 批量删除分地市四率统计
     * 
     * @param ids 需要删除的分地市四率统计ID
     * @return 结果
     */
    @Override
    public int deleteDataCityRateByIds(Long[] ids)
    {
        return dataCityRateMapper.deleteDataCityRateByIds(ids);
    }

    /**
     * 删除分地市四率统计信息
     * 
     * @param id 分地市四率统计ID
     * @return 结果
     */
    @Override
    public int deleteDataCityRateById(Long id)
    {
        return dataCityRateMapper.deleteDataCityRateById(id);
    }
}
