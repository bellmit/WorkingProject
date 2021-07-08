package com.smarthome.uploadyiyanlogs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {
	public static String getDayDate(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static String getLastDayDate(Date date,String format){
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
	}

	public static String getDayDate0(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static String getLastDayDate0(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}


	public static void main(String[] args) {
		System.out.println(DateFormatUtil.getDayDate0());
		System.out.println(DateFormatUtil.getLastDayDate0());
	}

	public static long getLastMonthForOne(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long getLastMonthForlast(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static String get2LastDayDate(Date date,String format){
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -2);  
        date = calendar.getTime();  
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
	}

	public static long getLastDayTimestamp(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTimeInMillis();
	}
	public static long getToDayTimestamp(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
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
        calendar.set(Integer.parseInt(split[0]),Integer.parseInt(split[1])-1,Integer.parseInt(split[2]), hour, minute, 00);
        long period = calendar.getTimeInMillis() - System.currentTimeMillis();
        if(period < 0){
        	calendar.clear();
            calendar.set(Integer.parseInt(split[0]),Integer.parseInt(split[1])-1,Integer.parseInt(split[2])+1, hour, minute, 00);
        	period = calendar.getTimeInMillis() - System.currentTimeMillis();
        }
        return period;
	}
}
