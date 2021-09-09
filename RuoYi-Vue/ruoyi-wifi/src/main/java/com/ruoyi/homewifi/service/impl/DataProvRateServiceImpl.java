package com.ruoyi.homewifi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.Page;
import com.ruoyi.homewifi.district.DistrictDirc;
import com.ruoyi.homewifi.dobj.LakeGiftSumDo;
import com.ruoyi.homewifi.dobj.LakeReportSumDo;
import com.ruoyi.homewifi.domain.DataCityRate;
import com.ruoyi.homewifi.es.ESSearch;
import com.ruoyi.homewifi.vo.CityRateVo;
import com.ruoyi.homewifi.vo.LakeCityRateVo;
import com.ruoyi.homewifi.vo.LakeProvRateVo;
import com.ruoyi.homewifi.vo.ProvRateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    protected static final Logger logger = LoggerFactory.getLogger(DataCityRateServiceImpl.class);

    @Autowired
    private DataProvRateMapper dataProvRateMapper;
    @Autowired
    private ESSearch esSearch;

    /**
     * 查询分省份四率统计列表
     * 
     * @param provRateVo 分省份四率统计查询条件
     * @return 分省份四率统计
     */
    @Override
    public List<DataProvRate> selectDataProvRateList(ProvRateVo provRateVo)
    {
        //条件查询获得竣工报告统计结果
        List<LakeReportSumDo> lakeReportSumList = dataProvRateMapper.selectLakeReportSumList(provRateVo);
        if(lakeReportSumList != null && lakeReportSumList.size() != 0){
            return getDataProvRateList(provRateVo,lakeReportSumList);
        }else{
            logger.info("条件为{}时，数据湖省级级竣工报告数据为空",provRateVo.toString());
            Page provRateList = new Page();
            provRateList.setTotal(0);
            return provRateList;
        }
    }

    /**
     * 拼接数据湖礼包数据统计结果，返回完整四率统计数据
     */
    public ArrayList<DataProvRate> getDataProvRateList(ProvRateVo provRateVo, List<LakeReportSumDo> lakeReportSumList){
        //ArrayList<DataProvRate> provRateList = new ArrayList<>();
        Page provRateList = new Page<DataProvRate>();
        provRateList.setTotal(((Page)lakeReportSumList).getTotal());
        for(LakeReportSumDo lakeReportSumDo:lakeReportSumList){
            LakeProvRateVo lakeProvRateVo = new LakeProvRateVo();
            lakeProvRateVo.setStartDate(provRateVo.getStartDate());
            lakeProvRateVo.setEndDate(provRateVo.getEndDate());
            lakeProvRateVo.setLakeProvId(lakeReportSumDo.getDeptId());
            /*装载统计数据*/
            DataProvRate dataProvRate = new DataProvRate();
            //获取指定省份的礼包统计数据
            List<LakeGiftSumDo> lakeGiftSumList = dataProvRateMapper.selectLakeGiftSumList(lakeProvRateVo);
            Integer giftOrderSum = 0;
            Integer termiGiftSum = 0;
            Integer serviGiftSum = 0;
            if(lakeGiftSumList.size() != 0){
                LakeGiftSumDo lakeGiftSumDo = lakeGiftSumList.get(0);
                giftOrderSum = lakeGiftSumDo.getGiftOrderSum();
                dataProvRate.setNewGiftSum(giftOrderSum);
                termiGiftSum = lakeGiftSumDo.getTermiGiftSum();
                dataProvRate.setNewTermiGiftSum(termiGiftSum);
                serviGiftSum = lakeGiftSumDo.getServiGiftSum();
                dataProvRate.setNewServiGiftSum(serviGiftSum);
            }
            String provName = DistrictDirc.districtMap.get(lakeReportSumDo.getwProId());
            dataProvRate.setProvName(provName);
            Integer effectiveSum = lakeReportSumDo.getEffectiveSum();
            dataProvRate.setEffectiveSum(effectiveSum);

            //配置ES查询新增e_link/e_OS终端数
            int newElinkSum = esSearch.getApSum(lakeProvRateVo);
            dataProvRate.setNewElinkSum(newElinkSum);
            Integer sameAreaSum = lakeReportSumDo.getSameAreaSum();
            dataProvRate.setSameAreaSum(sameAreaSum);
            Integer wifiCheckedSum = lakeReportSumDo.getWifiCheckedSum();
            dataProvRate.setWifiCheckedSum(wifiCheckedSum);
            Integer shareSum = lakeReportSumDo.getShareSum();
            dataProvRate.setShareSum(shareSum);
            Integer yiShareSum = lakeReportSumDo.getYiShareSum();
            dataProvRate.setYiShareSum(yiShareSum);
            Integer messageShareSum = lakeReportSumDo.getMessageShareSum();
            dataProvRate.setMessageShareSum(messageShareSum);
            Integer wechatShareSum = lakeReportSumDo.getWechatShareSum();
            dataProvRate.setWechatShareSum(wechatShareSum);
            Integer otherShareSum = lakeReportSumDo.getOtherShareSum();
            dataProvRate.setOtherShareSum(otherShareSum);
            //有效报告率:有效竣工报告数/全部礼包数
            dataProvRate.setEffectiveReportRate(aDivideBPercent(new BigDecimal(effectiveSum),new BigDecimal(giftOrderSum)));
            //交付地一致率：交付地一致数/有效报告数
            dataProvRate.setSameAreaRate(aDivideBPercent(new BigDecimal(sameAreaSum),new BigDecimal(effectiveSum)));
            //WiFi测速达标率:WiFi测速达标数/有效报告数
            dataProvRate.setWifiCheckedRate(aDivideBPercent(new BigDecimal(wifiCheckedSum),new BigDecimal(effectiveSum)));
            //终端稽核率:e-link终端数/含终端礼包数
            dataProvRate.setElinkCheckedRate(aDivideBPercent(new BigDecimal(newElinkSum),new BigDecimal(termiGiftSum)));
            //交付报告分享率:有效报告分享数/有效报告数
            dataProvRate.setShareRate(aDivideBPercent(new BigDecimal(shareSum),new BigDecimal(effectiveSum)));
            provRateList.add(dataProvRate);
        }
        return provRateList;
    }

    private String aDivideBPercent(BigDecimal a, BigDecimal b) {
        String percent =
                b == null ? "-" :
                        b.compareTo(new BigDecimal(0)) == 0 ? "-":
                                a == null ? "0.00%" :
                                        a.multiply(new BigDecimal(100)).divide(b,2,BigDecimal.ROUND_HALF_UP) + "%";
        return percent;
    }


    /**
     * 查询分省份四率统计
     *
     * @param id 分省份四率统计ID
     * @return 分省份四率统计
     */
    /*@Override
    public DataProvRate selectDataProvRateById(Long id)
    {
        return dataProvRateMapper.selectDataProvRateById(id);
    }*/

    /**
     * 新增分省份四率统计
     * 
     * @param dataProvRate 分省份四率统计
     * @return 结果
     */
    /*@Override
    public int insertDataProvRate(DataProvRate dataProvRate)
    {
        return dataProvRateMapper.insertDataProvRate(dataProvRate);
    }*/

    /**
     * 修改分省份四率统计
     * 
     * @param dataProvRate 分省份四率统计
     * @return 结果
     */
    /*@Override
    public int updateDataProvRate(DataProvRate dataProvRate)
    {
        return dataProvRateMapper.updateDataProvRate(dataProvRate);
    }*/

    /**
     * 批量删除分省份四率统计
     * 
     * @param ids 需要删除的分省份四率统计ID
     * @return 结果
     */
    /*@Override
    public int deleteDataProvRateByIds(Long[] ids)
    {
        return dataProvRateMapper.deleteDataProvRateByIds(ids);
    }*/

    /**
     * 删除分省份四率统计信息
     * 
     * @param id 分省份四率统计ID
     * @return 结果
     */
    /*@Override
    public int deleteDataProvRateById(Long id)
    {
        return dataProvRateMapper.deleteDataProvRateById(id);
    }*/
}
