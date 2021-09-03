package com.ruoyi.homewifi.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.homewifi.config.BaseConfig;
import com.ruoyi.homewifi.district.ProvinceCode;
import com.ruoyi.homewifi.redis.RedisUtils;
import com.ruoyi.homewifi.vo.LakeCityRateVo;
import com.ruoyi.homewifi.vo.LakeRateVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Map;

/**
 * @Author:KUN
 * @Data:2021/3/31 14:36
 * @Description: 根据时间区间查看elink总数
 * @Version:1.0
 */
public class ESSearch {
    private static final Logger logger = LoggerFactory.getLogger(ESSearch.class);
    @Autowired
    private EsUtils esUtils;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BaseConfig baseConfig;


    public int getApSum(LakeRateVo lakeRateVo) {
        try {
            String url = "http://"+baseConfig.getEsAddress()+":"+baseConfig.getEsPort()+"/"+baseConfig.getApIndex()+"/"+baseConfig.getApType()+"/_search";
            String searchStr = checkCityRateUrl(lakeRateVo);
            if(searchStr == null || "".equals(searchStr)){
                logger.error("获取ES查询语句出错!");
                return 0;
            }
            ResponseEntity<JSONObject> responseEntity = esUtils.query(url ,searchStr);
            JSONObject resultJson = responseEntity.getBody();
            JSONArray jsonArray = resultJson.getJSONObject("hits").getJSONArray("hits");
            if(jsonArray==null || jsonArray.size()==0){
                logger.info("查询结果为空");
                return 0;
            }
            int apSum = Integer.parseInt(resultJson.getJSONObject("hits").get("total").toString());
            System.out.println("apSum"+apSum);
            return apSum;
        } catch (Exception e) {
            logger.error("获取ap总数出错!错误:{}",e);
        } finally {
            return 0;
        }
    }

    public String checkCityRateUrl(LakeRateVo lakeRateVo){
        JSONObject queryRoot = new JSONObject();
        JSONObject boolString = new JSONObject();
        JSONArray mustString = new JSONArray();
        Long startTime = lakeRateVo.getStartDate().getTime();
        Long endTime = lakeRateVo.getEndDate().getTime();
        String lakeProvId = lakeRateVo.getLakeProvId();
        if(lakeProvId != null && !"".equals(lakeProvId)){
            String flProvId = ProvinceCode.parse(lakeProvId).getCode();
            if(flProvId != null && !"".equals(flProvId)){
                mustString.add(newJSONObject("term",newJSONObject("province",flProvId)));
            }else{
                logger.info("集团省份编码：{},不存在对应丰联省份编码",lakeProvId);
                return "";
            }
        }

        if(lakeRateVo instanceof LakeCityRateVo){
            String lakeCityId = ((LakeCityRateVo)lakeRateVo).getLakeCityId();
            if(lakeCityId != null && !"".equals(lakeCityId)) {
                String flCityId = redisUtils.getFlCityCode("assetmanage_citycode_" + lakeCityId);
                if(flCityId != null && !"".equals(flCityId)){
                    mustString.add(newJSONObject("term",newJSONObject("city",flCityId)));
                }else{
                    logger.info("集团城市编码：{},不存在对应丰联城市编码",lakeCityId);
                    return "";
                }
            }
        }
        JSONObject timeRang = null;
        if(startTime != null){
            timeRang = newJSONObject("gte", startTime);
        }
        if(endTime != null){
            if(timeRang != null){
                timeRang.put("lte",endTime);
            }else{
                timeRang = newJSONObject("lte", endTime);
            }
        }
        if(timeRang!= null){
            mustString.add(newJSONObject("range",newJSONObject("ADD_TIME",timeRang)));
        }
        boolString.put("must", mustString);
        queryRoot.put("query",newJSONObject("bool",boolString));
        return queryRoot.toJSONString();
    }


    public static  JSONObject newJSONObject(String key, Object o){
        JSONObject jsonObject =new JSONObject();
        try {
            if(key!=null){
                jsonObject.put(key,o);
            }
        }catch (Exception e){

        }
        return jsonObject;
    }

    /*@Test
    public void test1(){
        getApSum(1629216000000L,1629291600000L);
    }*/



}
