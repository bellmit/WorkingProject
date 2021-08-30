package com.ruoyi.homewifi.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.homewifi.district.DistrictDirc;
import com.ruoyi.homewifi.dobj.LakeGiftDo;
import com.ruoyi.homewifi.dobj.LakeReportDo;
import com.ruoyi.homewifi.vo.CityRateVo;
import com.ruoyi.homewifi.vo.LakeCityRateVo;
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
     * 查询分地市四率统计列表
     * 
     * @param cityRateVo 分地市四率统计
     * @return 分地市四率统计
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<DataCityRate> selectDataCityRateList(CityRateVo cityRateVo)
    {
        //List<LakeReportDo> lakeReportList = dataCityRateMapper.selectLakeReportList(cityRateVo);
        //List<LakeGiftDo> lakeGiftList = dataCityRateMapper.selectLakeGiftList(toLakeVo(cityRateVo));
        //分4种情况展示查询结果
        //1、无条件查询，进入页面不显示
        //2、只有时间(必选字段)范围内查询权限范围内的所有城市
        //3、cityRateVo只有时间段和省份编码，显示省内所有城市的四率信息
        //4、cityRateVo有时间段、省份编码和城市编码，

        ArrayList<DataCityRate> cityRateList = new ArrayList<>();


        return cityRateList;
    }

    public LakeCityRateVo toLakeVo(CityRateVo cityRateVo){
        LakeCityRateVo lakeCityRateVo = new LakeCityRateVo();
        lakeCityRateVo.setStartDate(cityRateVo.getStartDate());
        lakeCityRateVo.setEndDate(cityRateVo.getEndDate());
        String lakeProvId = DistrictDirc.IdTransferMap.get(cityRateVo.getwProvId());
        lakeCityRateVo.setLakeProvId(lakeProvId);
        String lakeCityId = DistrictDirc.IdTransferMap.get(cityRateVo.getwCityId());
        lakeCityRateVo.setLakeCityId(lakeCityId);
        return lakeCityRateVo;

    }




















    /**
     * 查询分地市四率统计
     *
     * @param id 分地市四率统计ID
     * @return 分地市四率统计
     */
    /*@Override
    public DataCityRate selectDataCityRateById(Long id)
    {
        return dataCityRateMapper.selectDataCityRateById(id);
    }*/

    /**
     * 新增分地市四率统计
     * 
     * @param dataCityRate 分地市四率统计
     * @return 结果
     */
    /*@Override
    public int insertDataCityRate(DataCityRate dataCityRate)
    {
        return dataCityRateMapper.insertDataCityRate(dataCityRate);
    }*/

    /**
     * 修改分地市四率统计
     * 
     * @param dataCityRate 分地市四率统计
     * @return 结果
     */
    /*@Override
    public int updateDataCityRate(DataCityRate dataCityRate)
    {
        return dataCityRateMapper.updateDataCityRate(dataCityRate);
    }*/

    /**
     * 批量删除分地市四率统计
     * 
     * @param ids 需要删除的分地市四率统计ID
     * @return 结果
     */
    /*@Override
    public int deleteDataCityRateByIds(Long[] ids)
    {
        return dataCityRateMapper.deleteDataCityRateByIds(ids);
    }*/

    /**
     * 删除分地市四率统计信息
     * 
     * @param id 分地市四率统计ID
     * @return 结果
     */
    /*@Override
    public int deleteDataCityRateById(Long id)
    {
        return dataCityRateMapper.deleteDataCityRateById(id);
    }*/
}
