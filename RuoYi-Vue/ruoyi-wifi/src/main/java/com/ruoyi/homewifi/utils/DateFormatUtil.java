package com.ruoyi.homewifi.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFormatUtil {
	public static String getDayDate(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date currentDate = dateFormat.parse(dayFormat.format(new Date()) + " " +time);
			return currentDate.getTime() ;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getLastDayDate(Date date, String format){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
	}

	public static Long getThreeDayDate() {
		Calendar cal_1 = Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.DATE, -3);//
		cal_1.set(Calendar.HOUR_OF_DAY, 0);
		cal_1.set(Calendar.MINUTE, 0);
		cal_1.set(Calendar.SECOND, 0);
		Date d = cal_1.getTime();
		return (d.getTime() / 1000) * 1000;
	}
	public static Long getOneDayDate() {
		Calendar cal_1 = Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.DATE, -1);//
		cal_1.set(Calendar.HOUR_OF_DAY, 0);
		cal_1.set(Calendar.MINUTE, 0);
		cal_1.set(Calendar.SECOND, 0);
		Date d = cal_1.getTime();
		return (d.getTime() / 1000) * 1000;
	}

	public static Long getDayDate() {
		Calendar cal_1 = Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.DATE, -1);//
		cal_1.set(Calendar.HOUR_OF_DAY, 0);
		cal_1.set(Calendar.MINUTE, 0);
		cal_1.set(Calendar.SECOND, 0);
		Date d = cal_1.getTime();
		return (d.getTime() / 1000) * 1000;
	}


	public static long dayEarlyTime(Integer day) {
		if(null == day){
			day=0;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.DATE, -day);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date d = c.getTime();
		return d.getTime()/1000*1000;
	}

	/**
	 * 获取前day天起始时间毫秒数
	 * @param day
	 * @return
	 */
	public static long getStartTime(Integer day) {
		if(null == day){
			day=0;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.DATE, -day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date d = c.getTime();
		return d.getTime()/1000*1000;
	}

	/**
	 * 获取前day天结束时间00-00-00毫秒数（也就是day-1天00点00分00秒对应的毫秒数）
	 * @param day
	 * @return
	 */
	public static long getEndTime(Integer day) {
		if(null == day){
			day=0;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.DATE, -day);
		//c.set(Calendar.HOUR_OF_DAY, 8);  //表示到8点
		c.set(Calendar.HOUR_OF_DAY,24);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date d = c.getTime();
		return d.getTime()/1000*1000;
	}

	public static String get2LastDayDate(Date date, String format){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        date = calendar.getTime();  
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
	}


	public static Long getLastTowDayDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal_1 = Calendar.getInstance();//获取当前日期
//        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
		cal_1.add(Calendar.MONTH, -3);//设置为1号,当前日期既为本月第一天
		cal_1.set(Calendar.HOUR_OF_DAY, 0);
		cal_1.set(Calendar.MINUTE, 0);
		cal_1.set(Calendar.SECOND, 0);
		Date d = cal_1.getTime();
		return (d.getTime() / 1000) * 1000;
	}
	public static long getLastDayTimestamp() {
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return (calendar.getTimeInMillis()/1000)*1000;
	}
	public static long getLastDayTimestamp(Date date, long offset){
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTimeInMillis() + offset;
	}


	public static long get2LastDayTimestamp(){
		Date date = new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); 
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        return calendar.getTimeInMillis()-1000;
	}
	public static long getTimestapFromDate(int hour,int minute){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] split = sdf.format(date).split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
        calendar.set(Integer.parseInt(split[0]), Integer.parseInt(split[1])-1, Integer.parseInt(split[2]), hour, minute, 00);
        long period = calendar.getTimeInMillis() - System.currentTimeMillis();
        if(period < 0){
        	calendar.clear();
            calendar.set(Integer.parseInt(split[0]), Integer.parseInt(split[1])-1, Integer.parseInt(split[2])+1, hour, minute, 00);
        	period = calendar.getTimeInMillis() - System.currentTimeMillis();
        }
        return period;
	}

	public static Long getLastMonthDate(int i){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MONTH, -i);
        Date date = calendar.getTime();
        return (date.getTime()/1000)*1000;
	}
	public static String getMonthDate(Date date, String format){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);  
        //如果是每月1号
      	if(calendar.get(Calendar.DAY_OF_MONTH) == 1){
      		calendar.add(Calendar.MONTH, -1);
      	}
        date = calendar.getTime();  
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
	}
	public static int getDateDay(){
		Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	 * 
	 * @param split:每个线程获取数据的天数，6表示每个线程获取6天的数据
	 * @return 返回计算出的时间段数组
	 */
	public static List<Long> generateLastMonthSplitDays(int split){
		Calendar calendar = Calendar.getInstance();
		//获取零时数据，先注释掉月份减一
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		int maxDay = calendar.get(Calendar.DATE);
		//System.out.println(maxDay);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		List<Long> retList = new ArrayList<Long>();
		long firstDayTimes = c.getTimeInMillis()-1000;
		retList.add(firstDayTimes);
		if(split >= maxDay){
			retList.add((firstDayTimes+(long)maxDay*1000*60*60*24));
			return retList;
		}
		int perDays = maxDay/split;
		int leaveDays = maxDay%split;
		
		long currentTimes = firstDayTimes;
		for(int i=0; i < perDays;i++){
			currentTimes = currentTimes + (long)split*1000*60*60*24;
			retList.add(currentTimes);
		}
		retList.add(currentTimes + (long)leaveDays*1000*60*60*24);
		//System.out.println(perDays+"---"+leaveDays);
		return retList;
	}
	public static String getActiveDbMonth(Date date, String format){
		Calendar calendar = Calendar.getInstance();
        //如果是每月1号
		if(calendar.get(Calendar.DAY_OF_MONTH) == 1){
			calendar.setTime(date);  
	        calendar.add(Calendar.MONTH, -1);
	        date = calendar.getTime();
	        SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
        }
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static long dateString2Long(String date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date parse = null;
		try {
			parse = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parse.getTime();
	}


	public static void main(String[] args) {
		System.out.println("start："+getStartTime(1));
		System.out.println("end："+getEndTime(1));
	}
}
