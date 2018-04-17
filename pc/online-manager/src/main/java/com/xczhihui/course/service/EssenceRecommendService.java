package com.xczhihui.course.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.course.vo.CourseVo;

public interface EssenceRecommendService {
	/**
	 * 课程推荐列表分页
	 * 
	 * @param courseVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
			int pageSize);
}
