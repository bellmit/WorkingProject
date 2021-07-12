package com.smarthome.uploadyiyanlogs.sql.mapper;

import com.smarthome.uploadyiyanlogs.pojo.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author:KUN
 * @Data:2021/7/2 14:02
 * @Description:从Mysql获取前一天操作日志
 * @Version:1.0
 */
@Mapper
public interface LogMapper {

    @Select("select o.*,u.`name` from mh_operating_log o join mh_user u on o.username = u.login_name " +
            "where date(start_date) = date_sub(curdate(),interval 1 day) order by start_date desc")
    @Results({
            @Result(column = "end_date", property = "endDate"),
            @Result(column = "operating_target", property = "operatingTarget"),
            @Result(column = "processing_time", property = "processingTime"),
            @Result(column = "start_date", property = "startDate"),
            @Result(column = "fk_user_id", property = "fkUserId")
    })
    List<OperationLog> getOperationLogs();
    @Select("select name from mh_user where login_name = #{logName}")
    String getName(String logName);
}
