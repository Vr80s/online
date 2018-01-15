package com.xczhihui.wechat.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

public interface ICourseService {
	
	  public Page<CourseLecturVo> selectCoursePage(Page<CourseLecturVo> page);
}
