package com.xczh.consumer.market.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * 有关日期方面的操作类
 * 
 */
public class DateUtil {

	/**
	 * day的format形式，格式为为：2015-12-14
	 */
	public final static String FORMAT_DAY = "yyyy-MM-dd";

	/**
	 * day的format形式，格式为为：2015-12-14 05:15:31
	 */
	public final static String FORMAT_DAY_TIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * day的format形式，格式为为：2015年12月14日
	 */
	public final static String FORMAT_CHINA_DAY = "yyyy年MM月dd日";

	/**
	 * day的format形式，格式为为：2015年12月14日 05时15分31秒
	 */
	public final static String FORMAT_CHINA_DAY_TIME = "yyyy年MM月dd日 HH时mm分ss秒";

	/**
	 * day的format形式，格式为为：05:15:31
	 */
	public final static String FORMAT_TIME = "HH:mm:ss";
	
	/**
	 * 格式化日期，格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		return formatDate(date, null);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param formate
	 *            null：yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String formatDate(Date date, String format) {
		if (date == null) {
			date = new Date(System.currentTimeMillis());
		}
		if (format == null || format.trim().length() < 1) {
			format = FORMAT_DAY_TIME;
		}

		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 解析日期
	 * 
	 * @param value
	 * @param format
	 *            默认值为"yyyy-MM-dd HH:mm:ss"
	 * @return Date
	 */
	public static Date parseDate(String value, String format) {
		if (IStringUtil.isNotBlank(value)) {
			if (!IStringUtil.isNotBlank(format)) {
				format = FORMAT_DAY_TIME;
			}
			DateFormat dateFormate = new SimpleDateFormat(format);
			Date date = null;
			try {
				date = dateFormate.parse(value);
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			return date;
		}
		return null;
	}

	/**
	 * 根据日期计算星座
	 * 
	 * @param month
	 * @param date
	 */
	public static String getConstellation(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		return getConstellation(month, day);
	}

	/**
	 * 根据月份和日期计算星座
	 * 
	 * @param month
	 * @param day
	 * @return String
	 */
	public static String getConstellation(int month, int day) {
		String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arr = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		int num = month * 2 - (day < arr[month - 1] ? 2 : 0);
		return s.substring(num, num + 2);
	}

	/**
	 * 
	 * 根据年份计算生肖
	 * 
	 * @param year
	 * @return String
	 */
	public static String getZodiac(int year) {
		if (year < 1900) {
            return null;
        }
		String str = "鼠牛虎兔龙蛇马羊猴鸡狗猪";
		int num = (year - 1900) % 12;
		return str.substring(num, num + 1);
	}
	
	 /** 
	  * 将长整型数字转换为日期格式的字符串 
	  *  
	  * @param time 
	  * @param format 
	  * @return 
	  */  
	 public static String convert2String(long time, String format) {  
		  if (time > 0L) {
		   if (IStringUtil.isNotBlank(format)){
			   SimpleDateFormat sf = new SimpleDateFormat(format);  
			   String hms = sf.format(time);
			   return hms;
		   }  
		  }  
		  return "";  
	 }  
	 
	  public static String converLongTimeToStr(long time) {
		  
		  
          int ss = 1000;
          int mi = ss * 60;
          int hh = mi * 60;
   
          long hour = (time) / hh;
          long minute = (time - hour * hh) / mi;
          long second = (time - hour * hh - minute * mi) / ss;
   
          String strHour = hour < 10 ? "0" + hour : "" + hour;
          String strMinute = minute < 10 ? "0" + minute : "" + minute;
          String strSecond = second < 10 ? "0" + second : "" + second;
          if (hour > 0) {
              return strHour + ":" + strMinute + ":" + strSecond;
          } else {
              return "00:"+strMinute + ":" + strSecond;
          }
      }
	 
}
