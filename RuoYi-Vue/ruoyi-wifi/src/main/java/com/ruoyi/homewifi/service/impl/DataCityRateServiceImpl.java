package com.ruoyi.homewifi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.homewifi.district.DistrictDirc;
import com.ruoyi.homewifi.dobj.LakeGiftDo;
import com.ruoyi.homewifi.dobj.LakeGiftSumDo;
import com.ruoyi.homewifi.dobj.LakeReportDo;
import com.ruoyi.homewifi.dobj.LakeReportSumDo;
import com.ruoyi.homewifi.es.ESSearch;
import com.ruoyi.homewifi.redis.RedisUtils;
import com.ruoyi.homewifi.vo.CityRateVo;
import com.ruoyi.homewifi.vo.LakeCityRateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected static final Logger logger = LoggerFactory.getLogger(DataCityRateServiceImpl.class);

    @Autowired
    private DataCityRateMapper dataCityRateMapper;
    @Autowired
    private ESSearch esSearch;

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
        //分4种情况展示查询结果
        //1、无条件查询，进入页面不显示
        //2、只有时间(必选字段)范围内查询权限范围内的所有城市
        //3、cityRateVo只有时间段和省份编码，显示省内所有城市的四率信息
        //4、cityRateVo有时间段、省份编码和城市编码，
        List<LakeReportSumDo> lakeReportSumList = dataCityRateMapper.selectLakeReportSumList(cityRateVo);
        if(lakeReportSumList != null && lakeReportSumList.size() != 0){
            return getDataCityRateList(cityRateVo,lakeReportSumList);
        }else{
            logger.info("条件为{}时，数据湖城市级竣工报告数据为空",cityRateVo.toString());
            Page cityRateList = new Page();
            cityRateList.setTotal(0);
            return cityRateList;
        }

    }

    /*@Override
    @DataScope(deptAlias = "d")
    public TableDataInfo selectDataCityRateList(CityRateVo cityRateVo)
    {
        //分4种情况展示查询结果
        //1、无条件查询，进入页面不显示
        //2、只有时间(必选字段)范围内查询权限范围内的所有城市
        //3、cityRateVo只有时间段和省份编码，显示省内所有城市的四率信息
        //4、cityRateVo有时间段、省份编码和城市编码，
        List<LakeReportSumDo> lakeReportSumList = dataCityRateMapper.selectLakeReportSumList(cityRateVo);
        if (lakeReportSumList instanceof Page) {
            Long total = ((Page)lakeReportSumList).getTotal();
            System.out.println(total);
        }


        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        if(lakeReportSumList != null && lakeReportSumList.size() != 0){
            rspData.setTotal(new PageInfo(lakeReportSumList).getTotal());
            rspData.setRows( getDataCityRateList(cityRateVo,lakeReportSumList));
        }else{
            logger.info("条件为{}时，数据湖城市级竣工报告数据为空",cityRateVo.toString());
            rspData.setTotal(0);
        }
        return rspData;

    }*/


    /**
     * 拼接数据湖礼包数据统计结果，返回完整四率统计数据
     */
    public ArrayList<DataCityRate> getDataCityRateList(CityRateVo cityRateVo,List<LakeReportSumDo> lakeReportSumList){
        Page cityRateList = new Page();
        cityRateList.setTotal(((Page)lakeReportSumList).getTotal());
        for(LakeReportSumDo lakeReportSumDo:lakeReportSumList){
            LakeCityRateVo lakeCityRateVo = new LakeCityRateVo();
            lakeCityRateVo.setStartDate(cityRateVo.getStartDate());
            lakeCityRateVo.setEndDate(cityRateVo.getEndDate());
            lakeCityRateVo.setLakeProvId(lakeReportSumDo.getDeptId());
            lakeCityRateVo.setLakeCityId(lakeReportSumDo.getLakeCityId());

            /*装载统计数据*/
            DataCityRate dataCityRate = new DataCityRate();
            //获取指定城市的礼包统计数据
            List<LakeGiftSumDo> lakeGiftSumList = dataCityRateMapper.selectLakeGiftSumList(lakeCityRateVo);
            Integer giftOrderSum = 0;
            Integer termiGiftSum = 0;
            Integer serviGiftSum = 0;
            if(lakeGiftSumList.size() != 0){
                LakeGiftSumDo lakeGiftSumDo = lakeGiftSumList.get(0);
                giftOrderSum = lakeGiftSumDo.getGiftOrderSum();
                dataCityRate.setNewGiftSum(giftOrderSum);
                termiGiftSum = lakeGiftSumDo.getTermiGiftSum();
                dataCityRate.setNewTermiGiftSum(termiGiftSum);
                serviGiftSum = lakeGiftSumDo.getServiGiftSum();
                dataCityRate.setNewServiGiftSum(serviGiftSum);
            }
            String provName = DistrictDirc.districtMap.get(lakeReportSumDo.getwProId());
            dataCityRate.setProvName(provName);
            String cityName = DistrictDirc.districtMap.get(lakeReportSumDo.getwCityId());
            dataCityRate.setCityName(cityName);
            Integer effectiveSum = lakeReportSumDo.getEffectiveSum();
            dataCityRate.setEffectiveSum(effectiveSum);
            int newElinkSum = esSearch.getApSum(lakeCityRateVo);
            dataCityRate.setNewElinkSum(newElinkSum);
            Integer sameAreaSum = lakeReportSumDo.getSameAreaSum();
            dataCityRate.setSameAreaSum(sameAreaSum);
            Integer wifiCheckedSum = lakeReportSumDo.getWifiCheckedSum();
            dataCityRate.setWifiCheckedSum(wifiCheckedSum);
            Integer shareSum = lakeReportSumDo.getShareSum();
            dataCityRate.setShareSum(shareSum);
            Integer yiShareSum = lakeReportSumDo.getYiShareSum();
            dataCityRate.setYiShareSum(yiShareSum);
            Integer messageShareSum = lakeReportSumDo.getMessageShareSum();
            dataCityRate.setMessageShareSum(messageShareSum);
            Integer wechatShareSum = lakeReportSumDo.getWechatShareSum();
            dataCityRate.setWechatShareSum(wechatShareSum);
            Integer otherShareSum = lakeReportSumDo.getOtherShareSum();
            dataCityRate.setOtherShareSum(otherShareSum);
            //有效报告率:有效竣工报告数/全部礼包数
            dataCityRate.setEffectiveReportRate(aDivideBPercent(new BigDecimal(effectiveSum),new BigDecimal(giftOrderSum)));
            //交付地一致率：交付地一致数/有效报告数
            dataCityRate.setSameAreaRate(aDivideBPercent(new BigDecimal(sameAreaSum),new BigDecimal(effectiveSum)));
            //WiFi测速达标率:WiFi测速达标数/有效报告数
            dataCityRate.setWifiCheckedRate(aDivideBPercent(new BigDecimal(wifiCheckedSum),new BigDecimal(effectiveSum)));
            //终端稽核率:e-link终端数/含终端礼包数
            dataCityRate.setElinkCheckedRate(aDivideBPercent(new BigDecimal(newElinkSum),new BigDecimal(termiGiftSum)));
            //交付报告分享率:有效报告分享数/有效报告数
            dataCityRate.setShareRate(aDivideBPercent(new BigDecimal(shareSum),new BigDecimal(effectiveSum)));
            cityRateList.add(dataCityRate);
        }
        return cityRateList;
    }


    /**
     * 查询VO对象转换
     */
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
     * a / b   计算百分比
     * @param a
     * @param b
     * @return eg:65.32%
     */
    public static String aDivideBPercent(BigDecimal a,BigDecimal b){
        String percent =
                b == null ? "-" :
                        b.compareTo(new BigDecimal(0)) == 0 ? "-":
                                a == null ? "0.00%" :
                                        a.multiply(new BigDecimal(100)).divide(b,2,BigDecimal.ROUND_HALF_UP) + "%";
        return percent;
    }

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1);
        BigDecimal b = new BigDecimal(3);
        System.out.println(aDivideBPercent(a,b));
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
