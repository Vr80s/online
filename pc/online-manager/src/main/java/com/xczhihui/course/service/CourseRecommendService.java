package com.xczhihui.course.service;

import com.xczhihui.bxg.common.util.bean.Page;

import com.xczhihui.course.vo.CourseRecommendVo;
import com.xczhihui.course.vo.CourseVo;

public interface CourseRecommendService {

	public Page<CourseVo> findCourseRecommendPage(
			CourseRecommendVo courseRecommendVo, Integer pageNumber,
			Integer pageSize);

	/**
	 * 向上调整顺序
	 * 
	 * @return void
	 */
	public void updateSortUp(Integer id);

	/**
	 * 向上调整顺序
	 * 
	 * @return void
	 */
	public void updateSortDown(Integer id);

	/**
	 * 逻辑批量删除
	 * 
	 * @return void
	 */
	public void deletes(String[] ids);

	/**
	 * 新增
	 * 
	 * @return void
	 */
	public void addCourseRecommend(String showCourseId, String[] recCourseHids,
			String createPerson);

}
