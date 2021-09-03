package com.ruoyi.homewifi.mapper;

import java.util.List;

import com.ruoyi.homewifi.dobj.LakeGiftDo;
import com.ruoyi.homewifi.dobj.LakeGiftSumDo;
import com.ruoyi.homewifi.dobj.LakeReportDo;
import com.ruoyi.homewifi.dobj.LakeReportSumDo;
import com.ruoyi.homewifi.domain.DataCityRate;
import com.ruoyi.homewifi.vo.CityRateVo;
import com.ruoyi.homewifi.vo.LakeCityRateVo;

/**
 * 分地市四率统计Mapper接口
 * 
 * @author z
 * @date 2021-08-25
 */
public interface DataCityRateMapper 
{

    /**
     * 查询数据湖下发的工单数据统计结果
     */
    public List<LakeReportSumDo> selectLakeReportSumList(CityRateVo cityRateVO);



    /**
     * 查询数据湖下发的礼包数据统计结果
     */
    public List<LakeGiftSumDo> selectLakeGiftSumList(LakeCityRateVo lakeCityRateVO);

    /**
     * 查询数据湖下发的竣工报告数据
     */
    public List<LakeReportDo> selectLakeReportList(CityRateVo cityRateVO);

    /**
     * 数据湖下发的竣工报告批量入库
     */
    public int insertLakeReportList(List<LakeReportDo> lakeReportList);

    /**
     * 查询数据湖下发的礼包数据
     */
    public List<LakeGiftDo> selectLakeGiftList(LakeCityRateVo lakeCityRateVo);

    /**
     * 数据湖下发的礼包数据批量入库
     */
    public int insertLakeGiftList(List<LakeGiftDo> lakeGiftList);









    /**
     * 查询分地市四率统计列表
     *
     * @param cityRateVO 分地市四率统计
     * @return 分地市四率统计集合
     */
    /*public List<DataCityRate> selectDataCityRateList(CityRateVo cityRateVO);*/

    /**
     * 查询分地市四率统计
     *
     * @param id 分地市四率统计ID
     * @return 分地市四率统计
     */
    /*public DataCityRate selectDataCityRateById(Long id);*/

    /**
     * 新增分地市四率统计
     * 
     * @param dataCityRate 分地市四率统计
     * @return 结果
     */
    /*public int insertDataCityRate(DataCityRate dataCityRate);*/

    /**
     * 修改分地市四率统计
     * 
     * @param dataCityRate 分地市四率统计
     * @return 结果
     */
    /*public int updateDataCityRate(DataCityRate dataCityRate);*/

    /**
     * 删除分地市四率统计
     * 
     * @param id 分地市四率统计ID
     * @return 结果
     */
    /*public int deleteDataCityRateById(Long id);*/

    /**
     * 批量删除分地市四率统计
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    /*public int deleteDataCityRateByIds(Long[] ids);*/
}
