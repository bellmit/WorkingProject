package com.ruoyi.homewifi.mapper;

import java.util.List;
import com.ruoyi.homewifi.domain.DataCityRate;

/**
 * 分地市四率统计Mapper接口
 * 
 * @author z
 * @date 2021-08-25
 */
public interface DataCityRateMapper 
{
    /**
     * 查询分地市四率统计
     * 
     * @param id 分地市四率统计ID
     * @return 分地市四率统计
     */
    public DataCityRate selectDataCityRateById(Long id);

    /**
     * 查询分地市四率统计列表
     * 
     * @param dataCityRate 分地市四率统计
     * @return 分地市四率统计集合
     */
    public List<DataCityRate> selectDataCityRateList(DataCityRate dataCityRate);

    /**
     * 新增分地市四率统计
     * 
     * @param dataCityRate 分地市四率统计
     * @return 结果
     */
    public int insertDataCityRate(DataCityRate dataCityRate);

    /**
     * 修改分地市四率统计
     * 
     * @param dataCityRate 分地市四率统计
     * @return 结果
     */
    public int updateDataCityRate(DataCityRate dataCityRate);

    /**
     * 删除分地市四率统计
     * 
     * @param id 分地市四率统计ID
     * @return 结果
     */
    public int deleteDataCityRateById(Long id);

    /**
     * 批量删除分地市四率统计
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataCityRateByIds(Long[] ids);
}