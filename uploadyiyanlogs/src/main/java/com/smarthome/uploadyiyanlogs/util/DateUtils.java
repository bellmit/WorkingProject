package com.smarthome.uploadyiyanlogs.util;

/**
 * @Author:KUN
 * @Data:2021/7/5 23:48
 * @Description: 日期工具类
 * @Version:1.0
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * 日期格式字符串转化为时间
     * 注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
     *
     * @param strDate 如：2020-02-24 02:24:15
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return Date
     * @throws Exception
     */
    public static Date strDate2Date(String strDate, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        // 抛出异常
        Date date = simpleDateFormat.parse(strDate);
        return date;
    }

    /**
     * 时间转化为日期格式字符串
     *
     * @param date
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2StrDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param strDate 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String strDate, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(strDate).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }



    //将拿到的时间转毫秒值          ↓↓ 要转毫秒的时间
    public static long TransformMr(String de) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        long time = simpleDateFormat.parse(de).getTime();
        return time;


    }

    //将毫秒值转时间                    ↓↓long类型的毫秒值
    public static String MrTransformTime(long mr) {
        Date date = new Date();
        date.setTime(mr);
        String transformTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return transformTime;
    }


    public static void main(String[] args) {
        String timeStamp = timeStamp();
        System.out.println("timeStamp=" + timeStamp); //运行输出:timeStamp=1470278082
        System.out.println(System.currentTimeMillis());//运行输出:1470278082980
        //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

        String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
        System.out.println("date=" + date);//运行输出:date=2016-08-04 10:34:42

        String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
        System.out.println(timeStamp2);  //运行输出:1470278082

        //必须捕获异常
        try {
            String strDate = "2020-04-24 20:20:20";
            //注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
            Date strDate2Date = strDate2Date(strDate, "yyyy-MM-dd HH:mm:ss");
            System.out.println(strDate2Date);
            String res = date2StrDate(strDate2Date, "yyyyMMddHHmmss");
            System.out.println(res);
        } catch (ParseException px) {
            px.printStackTrace();
        }
    }
}
