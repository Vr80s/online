package com.xczhihui.course.service;

import java.util.List;

import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.common.util.bean.MinuteTaskMessageVo;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/9 0009-下午 1:43<br>
 */
public interface MessageRemindingService {
	
    void saveCourseMessageReminding(Course course, String key);

    void deleteCourseMessageReminding(Course course, String key);

    List<Course> getCourseMessageRemindingList(String key);

    void liveCourseMessageReminding();

    void offlineCourseMessageReminding();

    void collectionUpdateRemind();

	/**  
	 * <p>Title: 分钟定时任务提醒</p>  
	 * <p>Description: </p>    
	 */ 
	void minuteTaskMessage();

	/**  
	 * <p>Title: getCommonMinuteRemindingList</p>  
	 * <p>Description: </p>  
	 * @param redisKey
	 * @return  
	 */ 
	List<MinuteTaskMessageVo> getCommonMinuteRemindingList(String redisKey);
}
