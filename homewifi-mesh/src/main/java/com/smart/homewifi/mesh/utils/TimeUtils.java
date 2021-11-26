package com.smart.homewifi.mesh.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author:Z
 * @Date:2021/11/15 13:50
 * @Description: 时间工具类
 * @Version:1.0
 */
public class TimeUtils {

    /**
     * 判断当前日期是否是当月指定日期
     * @param updloadDay
     * @return
     */
    public static boolean isDayOfMonth(Integer updloadDay){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return (calendar.get(Calendar.DAY_OF_MONTH) == updloadDay);
    }
}
