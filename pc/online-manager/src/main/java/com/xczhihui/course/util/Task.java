package com.xczhihui.course.util;

import java.util.Date;
import java.util.TimerTask;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.vhall.VhallUtil;

public class Task extends TimerTask{

	
	CourseService courseService;
	
	private String videoId;
	
	private Integer courseId;
	
	public Task(String videoId,Integer courseId){
		this.videoId = videoId;
		this.courseId = courseId;
	}
	/*
	 * 直接结束后30秒执行这个方法判断是否生成回放，如果没有生成回放，就下架好了
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
    
		 System.out.println("}{}{}{}{}{}"+DateUtil.formatDate(new Date(),null));
		
		 System.out.println("{}{}{}{}{}{}  这个伟大的直播是否生成回访了   开始  ");
		 
		 courseService.updateStatus(courseId);
		 
		 VhallUtil.recordList(videoId);
		
		 System.out.println("{}{}{}{}{}{}  这个伟大的直播是否生成回访了  结束  ");
	}




}