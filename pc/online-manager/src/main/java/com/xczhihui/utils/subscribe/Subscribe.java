package com.xczhihui.utils.subscribe;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.course.dao.CourseSubscribeDao;
import com.xczhihui.course.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * ClassName: Subscribe.java <br>
 * Description: 直播课预约<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月12日<br>
 */
public class Subscribe {

	protected static Logger logger = LoggerFactory.getLogger(Subscribe.class);
	/** 
	 * Description：设置开播前一小时提醒
	 * @param id
	 * @param courseService
	 * @param courseSubscribeDao
	 * @return void
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public static void setting(int id, CourseService courseService, CourseSubscribeDao courseSubscribeDao){
		
		Calendar calendar = Calendar.getInstance();
		Course course = courseService.findOpenCourseById(id);
		calendar.setTime(course.getStartTime());
		int minute = calendar.get(Calendar.MINUTE);  
        calendar.set(Calendar.MINUTE, minute-10);//提前一小时  
		
        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.schedule(new RemindTask(id,course.getVersion(),courseService,courseSubscribeDao), time);
		logger.info("课程"+course.getGradeName()+"成功添加进入订阅发送");
	}

}
