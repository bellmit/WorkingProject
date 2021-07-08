package com.smarthome.uploadyiyanlogs.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smarthome.uploadyiyanlogs.config.BaseConfig;
import com.smarthome.uploadyiyanlogs.util.CalendarUtils;
import com.smarthome.uploadyiyanlogs.util.EmptyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * @Author:KUN
 * @Data:2021/7/5 09:27
 * @Description: 查询每天ES库的登录日志和查看日志
 * @Version:1.0
 */

@Component
public class EsSearch {
    @Autowired
    private BaseConfig baseConfig;
    private static final Logger logger = LoggerFactory.getLogger(EsSearch.class);


    /**
     * 从ES库查询当天的登录日志以及查询日志（默认每天登录日志或者查询日志不超过5000条）
     * 2021/7/5
     */
    public JsonView getIntradayLogs(String logName,String time){
        try {
            JsonView jsonView = new JsonView();
            String url="http://"+baseConfig.getEsAddress()+":"+baseConfig.getEsPort();
            HttpHeaders headers = new HttpHeaders();
            JSONObject queryRoot = new JSONObject();
            url+="/"+logName+"/_search?size=5000";
            JSONObject boolString = new JSONObject();
            JSONArray mustString = new JSONArray();
            JSONArray sortString = new JSONArray();
            JSONObject todayRange = (JSONObject) newJSONObject("gte",CalendarUtils.getLastday0AmMillisecond());
            todayRange.put("lt",CalendarUtils.get0AmMillisecond());
            mustString.add(newJSONObject("range",newJSONObject(time, todayRange)));
            sortString.add(newJSONObject(time,newJSONObject("order", "desc")));
            boolString.put("must", mustString);
            queryRoot.put("sort",sortString);
            queryRoot.put("query",newJSONObject("bool",boolString));

            logger.info("ES查询语句：{}",queryRoot.toJSONString());
            //发送http请求
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(queryRoot, headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, entity, JSONObject.class);
            //获取查询结果
            JSONObject result = responseEntity.getBody();
            JSONArray jsonHits = result.getJSONObject("hits").getJSONArray("hits");
            for(int i=0;i<jsonHits.size();i++){
                JSONObject jsonobject = jsonHits.getJSONObject(i);
                JSONObject data= JSONObject.parseObject(jsonobject.getString("_source"));
                data.put("id",jsonobject.getString("_id"));
                jsonView.getList().add(data);
            }
            jsonView.setNumber(result.getJSONObject("hits").getLong("total"));
            return jsonView;
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error("=====ES读取异常=====" + e);
            return null;
        }
    }

    /**
     * Scroll查询
     * 2021/7/5 9:53
     */
    public JsonView essearchScroll(String scrollId){
        try {
            JsonView jsonView = new JsonView();
            //BaseConfig baseConfig = SpringUtil.getBean(BaseConfig.class);
            String url="http://"+baseConfig.getEsAddress()+":"+baseConfig.getEsPort();
            HttpHeaders headers = new HttpHeaders();
            JSONObject queryRoot = new JSONObject();
            if(EmptyUtil.isEmpty(scrollId)){
                url+="/labeldata/_search?scroll=10m&size=10000";
                JSONObject boolString = new JSONObject();
                JSONArray mustString = new JSONArray();
                JSONArray mustNotString = new JSONArray();
                JSONArray sortString = new JSONArray();
                mustString.add(newJSONObject("exists",newJSONObject("field", "last_time")));
                mustNotString.add(newJSONObject("term",newJSONObject("last_time.keyword","")));
                sortString.add(newJSONObject("lan_attached_mac.keyword",newJSONObject("order", "asc")));//desc
                boolString.put("must", mustString);
                boolString.put("must_not", mustNotString);
                queryRoot.put("sort",sortString);
                queryRoot.put("query",newJSONObject("bool",boolString));
            }else{
                url+="/_search/scroll?scroll=10m&scroll_id="+scrollId;
            }
            //发送http请求
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<JSONObject> entity = new HttpEntity<JSONObject>(queryRoot, headers);
            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, entity, JSONObject.class);
            //获取查询结果
            JSONObject result = responseEntity.getBody();
            scrollId = result.getString("_scroll_id");
            JSONArray jsonHits = result.getJSONObject("hits").getJSONArray("hits");
            for(int i=0;i<jsonHits.size();i++){
                JSONObject jsonobject = jsonHits.getJSONObject(i);
                JSONObject data= JSONObject.parseObject(jsonobject.getString("_source"));
                data.put("id",jsonobject.getString("_id"));
                jsonView.getList().add(data);
            }
            jsonView.setScrollId(scrollId);
            jsonView.setNumber(result.getJSONObject("hits").getLong("total"));
            return jsonView;
        } catch (RestClientException e) {
            e.printStackTrace();
            logger.error("=====ES读取异常=====" + e);
            return null;
        }
    }

    public static  <V> Map newJSONObject(String key, V value){
        JSONObject jsonObject =new JSONObject();
        try {
            if(key!=null){
                jsonObject.put(key,value);
            }
        }catch (Exception e){
        }
        return jsonObject;
    }
}
