package com.xczhihui.bxg.online.manager.utils.subscribe;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseSubscribeDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;

/** 
 * ClassName: Subscribe.java <br>
 * Description: 直播课预约<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月12日<br>
 */
public class Subscribe {
	
//	private static Timer timer;
//	
//	{
//		 timer = new Timer();
//	}
	
	/** 
	 * Description：设置开播前一小时提醒
	 * @param id
	 * @param date
	 * @param courseService
	 * @param courseSubscribeDao
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public static void setting(int id,CourseService courseService,CourseSubscribeDao courseSubscribeDao){
		
		Calendar calendar = Calendar.getInstance();
		Course course = courseService.findOpenCourseById(id);
		calendar.setTime(course.getStartTime());
		int minute = calendar.get(Calendar.MINUTE);  
        calendar.set(Calendar.MINUTE, minute-10);//提前一小时  
		
        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(new RemindTask(id,course.getVersion(),courseService,courseSubscribeDao), time); 
        System.out.println("课程"+course.getGradeName()+"成功添加进入订阅发送");
	}
	
    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
		calendar.setTime(date);
		int minute = calendar.get(Calendar.MINUTE);  
        calendar.set(Calendar.MINUTE, minute + 1);  
		        
        Date time = calendar.getTime();
        Timer timer = new Timer();

        Timestamp ts = new Timestamp(System.currentTimeMillis());   
        String tsStr = "";   
        DateFormat sdf = new SimpleDateFormat("HH点mm分");   
        try {   
            //方法一   
            tsStr = sdf.format(ts);   
            System.out.println(tsStr);   
            //方法二   
            tsStr = ts.toString();   
            System.out.println(tsStr);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
    }
 
}
