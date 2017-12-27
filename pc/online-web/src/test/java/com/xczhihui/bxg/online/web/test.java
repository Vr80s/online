package com.xczhihui.bxg.online.web;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * ClassName: UserCoin.java <br>
 * Description:用户-代币余额表 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 8:55 2017/12/4 0004<br>
 */
public class test {
    public static void main(String[] args) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("Today is:"+format.format(Calendar.getInstance().getTime()));
        Calendar calendar = new GregorianCalendar(2017, 11, 6,10,20,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);//时
        calendar.set(Calendar.MINUTE, 0);//分
        calendar.set(Calendar.SECOND, 0);//秒
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-2);//日
        Date date = calendar.getTime();
        System.out.println("2007 Christmas is:"+format.format(date));
        String now = format.format(Calendar.getInstance().getTime());
        Date nowd;
        try {
            nowd = format.parse(now);
            int flag = nowd.compareTo(date);
            if (flag > 0) {//当天及当天之后，<0就是在日期之前
                System.out.println("已过期");
            }else{
                System.out.println("未过期");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
