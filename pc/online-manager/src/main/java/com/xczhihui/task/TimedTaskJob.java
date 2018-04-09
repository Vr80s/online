package com.xczhihui.task;

import com.xczhihui.cloudClass.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @ClassName: TimedTaskJob
 * @Description: 定时恢复推荐值
 * @Author: wangyishuai
 * @email: 15210815880@163.com
 * @CreateDate: 2018/3/14 15:47
 **/
public class TimedTaskJob {
	@Autowired
	private CourseService courseService;

	public void courSerecommendAging() {
		System.out.println("work done----------" + new Date());
		courseService.updateDefaultSort();

	}

}