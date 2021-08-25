package com.ruoyi.homewifi.mapper;

import java.util.List;
import com.ruoyi.homewifi.domain.DataProvRate;

/**
 * 分省份四率统计Mapper接口
 * 
 * @author z
 * @date 2021-08-25
 */
public interface DataProvRateMapper 
{
    /**
     * 查询分省份四率统计
     * 
     * @param id 分省份四率统计ID
     * @return 分省份四率统计
     */
    public DataProvRate selectDataProvRateById(Long id);

    /**
     * 查询分省份四率统计列表
     * 
     * @param dataProvRate 分省份四率统计
     * @return 分省份四率统计集合
     */
    public List<DataProvRate> selectDataProvRateList(DataProvRate dataProvRate);

    /**
     * 新增分省份四率统计
     * 
     * @param dataProvRate 分省份四率统计
     * @return 结果
     */
    public int insertDataProvRate(DataProvRate dataProvRate);

    /**
     * 修改分省份四率统计
     * 
     * @param dataProvRate 分省份四率统计
     * @return 结果
     */
    public int updateDataProvRate(DataProvRate dataProvRate);

    /**
     * 删除分省份四率统计
     * 
     * @param id 分省份四率统计ID
     * @return 结果
     */
    public int deleteDataProvRateById(Long id);

    /**
     * 批量删除分省份四率统计
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataProvRateByIds(Long[] ids);
}
