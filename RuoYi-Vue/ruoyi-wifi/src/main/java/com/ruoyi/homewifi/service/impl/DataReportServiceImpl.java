package com.ruoyi.homewifi.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.homewifi.district.DistrictDirc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.homewifi.mapper.DataReportMapper;
import com.ruoyi.homewifi.domain.DataReport;
import com.ruoyi.homewifi.service.IDataReportService;

/**
 * 竣工报告查询Service业务层处理
 * 
 * @author zyk
 * @date 2021-08-19
 */
@Service
public class DataReportServiceImpl implements IDataReportService 
{
    @Autowired
    private DataReportMapper dataReportMapper;

    protected static final Logger logger = LoggerFactory.getLogger(DataReportServiceImpl.class);

    /**
     * 查询竣工报告查询
     * 
     * @param id 竣工报告查询ID
     * @return 竣工报告查询
     */
    /*@Override
    public DataReport selectDataReportById(Long id)
    {
        return dataReportMapper.selectDataReportById(id);
    }*/

    /**
     * 查询竣工报告查询列表
     * 
     * @param dataReport 竣工报告查询
     * @return 竣工报告查询
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<DataReport> selectDataReportList(DataReport dataReport)
    {
        List<DataReport> dataReportList = dataReportMapper.selectDataReportList(dataReport);
        return transforList(dataReportList);
    }

    /**
     * 获取省份列表
     */
    @Override
    public List<JSONObject> getProvList() {
        logger.info("获取省份列表");
        List<JSONObject> provList = new ArrayList<>();
        if(DistrictDirc.provMap.size() == 0){
            DistrictDirc.findAllDistrict();
        }
        for(String provId:DistrictDirc.provMap.keySet()){
            //System.out.println("执行到这里");
            String provName = DistrictDirc.getDistrict(provId);
            JSONObject tmpJson = new JSONObject();
            tmpJson.put("key",provId);
            tmpJson.put("value",provName);
            provList.add(tmpJson);
        }
        return provList;
    }

    /**
     * 获取指定省份对应的城市列表
     */
    @Override
    public List<JSONObject> getCityList(String provId) {
        List<JSONObject> cityList = new ArrayList<>();
        //获取省份对应的地区编码，再去查对用的地市。最后获取地市名称
        Long provNum = DistrictDirc.provMap.get(provId);
        for(String cityId:DistrictDirc.cityMap.keySet()){
            JSONObject tmpJson = new JSONObject();
            if(DistrictDirc.cityMap.get(cityId).equals(provNum)){
                String cityName = DistrictDirc.getDistrict(cityId);
                tmpJson.put("key",cityId);
                tmpJson.put("value",cityName);
                cityList.add(tmpJson);
            }
        }
        return cityList;
    }

    /**
     * 万维地区编码转文字
     */
    public List transforList(List<DataReport> list){
        List<DataReport> resultList = new ArrayList<>();
        for(DataReport dr:list){
            String provCode = dr.getProvName();
            String cityCode = dr.getCityName();
            String areaCode = dr.getAreaName();
            if(provCode != null && !"".equals(provCode)){
                String provName = DistrictDirc.getDistrict(provCode);
                dr.setProvName(provName);
            }
            if(cityCode != null && !"".equals(cityCode)){
                String cityName = DistrictDirc.getDistrict(cityCode);
                dr.setCityName(cityName);
            }
            if(areaCode != null && !"".equals(areaCode)){
                String areaName = DistrictDirc.getDistrict(areaCode);
                dr.setAreaName(areaName);
            }
            resultList.add(dr);
        }
        return resultList;
    }

    /**
     * 新增竣工报告查询
     * 
     * @param dataReport 竣工报告查询
     * @return 结果
     */
    /*@Override
    public int insertDataReport(DataReport dataReport)
    {
        dataReport.setCreateTime(DateUtils.getNowDate());
        return dataReportMapper.insertDataReport(dataReport);
    }*/

    /**
     * 修改竣工报告查询
     * 
     * @param dataReport 竣工报告查询
     * @return 结果
     */
    /*@Override
    public int updateDataReport(DataReport dataReport)
    {
        return dataReportMapper.updateDataReport(dataReport);
    }*/

    /**
     * 批量删除竣工报告查询
     * 
     * @param ids 需要删除的竣工报告查询ID
     * @return 结果
     */
    /*@Override
    public int deleteDataReportByIds(Long[] ids)
    {
        return dataReportMapper.deleteDataReportByIds(ids);
    }*/

    /**
     * 删除竣工报告查询信息
     * 
     * @param id 竣工报告查询ID
     * @return 结果
     */
    /*@Override
    public int deleteDataReportById(Long id)
    {
        return dataReportMapper.deleteDataReportById(id);
    }*/
}
