package com.smart.homewifi.mesh.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Author:KUN
 * @Data:2021/7/5 10:35
 * @Description: 日期工具类
 * @Version:1.0
 */
public class CalendarUtils {
    /*    Calendar.HOUR_OF_DAY     24小时制
    Calendar.HOUR     12小时制*/

    //获取当天日期字符串
    public static String getDate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(cal.getTime());
    }

    //获取前一天日期字符串
    public static String getLastDayDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);//控制天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(cal.getTime());
    }

    //获取前一个月月份字符串
    public static String getLastMonth(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);//控制月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(cal.getTime());
    }

    //获取当天0点时间戳字符串
    public static String get0AmDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
        cal.set(Calendar.MINUTE, 0);//控制分
        cal.set(Calendar.SECOND, 0);//控制秒
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    //获取当天0点毫秒值
    public static Long get0AmMillisecond(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
        cal.set(Calendar.MINUTE, 0);//控制分
        cal.set(Calendar.SECOND, 0);//控制秒
        return cal.getTime().getTime();
    }

    //获取次日0点毫秒值
    public static Long getLastday0AmMillisecond(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);//控制天
        cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
        cal.set(Calendar.MINUTE, 0);//控制分
        cal.set(Calendar.SECOND, 0);//控制秒
        return cal.getTime().getTime();
    }






    //获取当天12点时间
    public static String getDate2(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    //获取本周一0点时间
    public static String getDate3(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    //获取本月第一天0点时间
    public static String getDate4(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    //获得本月最后一天24点时间
    public static String getDate5(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(cal.getTime());
    }

}
