package com.smart.homewifi.mesh.es;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.<br/>
 * 功能描述:List封装通用view  ,各字段按需求随意使用
 *
 * @author: chenshenjun
 * Date: 2017/1/10
 * Time: 14:17
 */
public class JsonView implements Serializable {


    private String id;
    private String name;
    private String type;
    private String value;
    private String scrollId;
    private Long number =0L;
    private Long number2 =0L;
    private Map<String,Object> data= Maps.newHashMap();
    private List<JSONObject> list = Lists.newArrayList();

    public JsonView(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public JsonView() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Map<String, Object> getData() {
        return data;
    }

    public void addData(String key,Object value){
        data.put(key,value);
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumber2() {
        return number2;
    }

    public void setNumber2(Long number2) {
        this.number2 = number2;
    }

    public List<JSONObject> getList() {
        return list;
    }

    public void setList(List<JSONObject> list) {
        this.list = list;
    }
}
