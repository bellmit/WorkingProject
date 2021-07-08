package com.smarthome.uploadyiyanlogs.es;

import com.alibaba.fastjson.JSONObject;
import com.smarthome.uploadyiyanlogs.config.BaseConfig;
import com.smarthome.uploadyiyanlogs.pojo.OperationLog;
import com.smarthome.uploadyiyanlogs.sql.mapper.LogMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:KUN
 * @Data:2021/7/5 14:34
 * @Description: 将查询出的ES数据转List<OperationLog>
 * @Version:1.0
 */
@Component
public class EsLogList {
    @Autowired
    private EsSearch esSearch;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private BaseConfig baseConfig;

    /**
     * 查询ES库的yiyan_user_operation_log表，获取查询操作日志，并封装到list
     */
    public List<OperationLog> getSearchLogList(){
        JsonView intradayLogs = esSearch.getIntradayLogs(baseConfig.getSearchLogTable(),"access_time");
        List<JSONObject> esLoglist = intradayLogs.getList();
        List<OperationLog> logList = new ArrayList<>();
        for(JSONObject jo:esLoglist){
            OperationLog operationLog = new OperationLog();
            String loginName = (String) jo.get("login_name");
            //String authority = loginName == "admin" ? "管理员":"普通";
            operationLog.setName(logMapper.getName(loginName));
            operationLog.setUsername(loginName);
            operationLog.setIp((String)jo.get("ip"));
            operationLog.setModule((String)jo.get("menu_name"));
            operationLog.setFunction((String)jo.get("menu_name"));
            Long ms = Long.valueOf(String.valueOf(jo.get("access_time")));
            operationLog.setStartDate(new Timestamp(ms));
            logList.add(operationLog);
        }
        return logList;
    }

    /**
     * 查询ES库的yiyan_login_log表，获取登录操作日志，并封装到list
     */
    public List<OperationLog> getLoginLogList(){
        JsonView intradayLogs = esSearch.getIntradayLogs(baseConfig.getLoginLogTable(),"login_time");
        List<JSONObject> esLoglist = intradayLogs.getList();
        List<OperationLog> logList = new ArrayList<>();
        for(JSONObject jo:esLoglist){
            OperationLog operationLog = new OperationLog();
            String loginName = (String) jo.get("login_name");
            //String authority = loginName == "admin" ? "管理员":"普通";
            operationLog.setName(logMapper.getName(loginName));
            operationLog.setUsername(loginName);
            operationLog.setIp((String)jo.get("ip"));
            operationLog.setModule("主页登录");
            operationLog.setFunction("登录系统");
            Long ms = Long.valueOf(String.valueOf(jo.get("login_time")));
            operationLog.setStartDate(new Timestamp(ms));
            logList.add(operationLog);
        }
        return logList;
    }

}
