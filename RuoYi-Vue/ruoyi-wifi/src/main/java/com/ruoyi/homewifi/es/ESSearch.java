package com.ruoyi.homewifi.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.homewifi.config.BaseConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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
    private BaseConfig baseConfig;


    public int getApSum(Long startTime,Long endTime) {
        try {
            //String url = "http://127.0.0.1:9200/wifi_aponline/messagedb/_search";
            String url = "http://"+baseConfig.getEsAddress()+":"+baseConfig.getEsPort()+"/"+baseConfig.getApIndex()+"/"+baseConfig.getApType()+"/_search";
            String searchStr = "{\n" +
                    "  \"query\": {\n" +
                    "    \"bool\": {\n" +
                    "      \"must\": [\n" +
                    "        {\n" +
                    "          \"range\": {\n" +
                    "            \"Add_Time\": {\n" +
                    "              \"gte\": "+startTime+",\n" +
                    "              \"lte\": "+endTime+"\n" +
                    "            }\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            ResponseEntity<JSONObject> responseEntity = esUtils.query(url ,searchStr);
            JSONObject resultJson = responseEntity.getBody();
            JSONArray jsonArray = resultJson.getJSONObject("hits").getJSONArray("hits");
            if(jsonArray==null || jsonArray.size()==0){
                logger.info("查询结果为空");
                return 0;
            }
            int apSum = Integer.parseInt(resultJson.getJSONObject("hits").get("total").toString());
            System.out.println(apSum);
            return apSum;
        } catch (Exception e) {
            logger.error("获取在ap总数出错!错误:{}",e);
        } finally {
            return 0;
        }
    }
    @Test
    public void test1(){
        getApSum(1629216000000L,1629291600000L);
    }



}
