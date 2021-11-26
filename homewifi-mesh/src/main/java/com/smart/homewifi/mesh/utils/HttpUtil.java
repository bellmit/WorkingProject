package com.smart.homewifi.mesh.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * 1、HttpClient：代码复杂，还得操心资源回收等。代码很复杂，冗余代码多，不建议直接使用。
 * 2、RestTemplate：是Spring 提供的用于访问Rest服务的客户端， RestTemplate 提供了多种
 *          便捷访问远程Http服务的方法,能够大大提高客户端的编写效率。
 */

public class HttpUtil {

    public static JSONObject post(String url, Object requestBody){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate=new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(url, entity, JSONObject.class).getBody();
    }

    public static JSONObject get(String url){
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate.getForEntity(url,JSONObject.class).getBody();
    }

    public static void put(String url, Object requestBody){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate=new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        restTemplate.put(url, entity, JSONObject.class);
    }

    public static void delete(String url){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.delete(url);
    }

    public static JSONObject exchange(String url, HttpMethod httpMethod, Object requestBody){
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate=new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url,httpMethod,entity,JSONObject.class).getBody();
    }




    public static void main(String[] args) {
        //删除上上账期的数据
        /*String queryJson = "{\"query\": { \"match\": { \"accounting_id\": \""+2021302+"\" }}}";
        String url = "http://127.0.0.1:9200/deletebyquery/hello/_delete_by_query";
        String response = post(url,queryJson).toJSONString();
        System.out.println("post请求执行deletebyquery结果："+response);*/
        //删除好用
        //delete("http://127.0.0.1:9200/deletebyquerytest/hello/101");
        //写入好用
        String url = "http://127.0.0.1:9200/deletebyquerytest/hello/101/_update";
        String state = "{\"doc\":{\"supportState\": 1,\"openState\": 1}}";
        System.out.println(post(url,state).toJSONString());
    }
}

