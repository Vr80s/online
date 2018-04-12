package com.xczh.consumer.market.vo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String getNowDateString() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + month + "-" + day;
        return date;
    }

    public static String getNextYearDateString() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = (year + 1) + "-" + month + "-" + day;
        return date;
    }

    public static String getNextYearDateString(String d) {
        if (!"".equals(d) && d != null) {
            String[] date = d.split("-");
            String y = date[0];
            int year = Integer.valueOf(y) + 1;
            String day = year + "-" + date[1] + "-" + date[2];
            return day;
        } else {
            return getNextYearDateString();
        }

    }

    public static String getStringNowDateMMdd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return sdf.format(new Date());
        }
        return sdf.format(date);
    }

    public static String getStringNowDateMMddHHmmss(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return sdf.format(new Date());
        }
        return sdf.format(date);
    }

    public static Date getNowDate(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if ("".equals(date) || date == null) {
            return new Date();
        }

        return sdf.parse(date);
    }

    /**
     * Description：在传入的时间上加上一年 返回类型：yyyy-MM-dd HH:mm:ss
     * 
     * @return
     * @return String
     * @author name：yangxuan <br>
     *         email: 15936216273@163.com
     */
    public static String dateAddYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        // Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        date = calendar.getTime();
        return getStringNowDateMMddHHmmss(date);
    }

    /**
     * Description：获取的指点时间减一天
     */
    public static String dateAddDay(Date date) {
        Calendar cdate = Calendar.getInstance();
        cdate.setTime(date);
        cdate.set(Calendar.DATE, cdate.get(Calendar.DATE) + 1);
        Date endDate = cdate.getTime();
        return getStringNowDateMMddHHmmss(endDate);
    }
}
