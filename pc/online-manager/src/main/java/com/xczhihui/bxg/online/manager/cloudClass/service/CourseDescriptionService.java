package com.xczhihui.bxg.online.manager.cloudClass.service;

import java.util.List;
import java.util.Map;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseDescriptionVo;

public interface CourseDescriptionService {

	public List<Map<String,Object>> getDesList(CourseDescriptionVo courseDescriptionVo);
	
	/**
	 * 新增
	 * 
	 *@return void
	 */
	public void addCourseDescription(CourseDescriptionVo courseDescriptionVo);

	/**
	 * 修改
	 * 
	 *@return void
	 */
	public void updateCourseDescription(CourseDescriptionVo courseDescriptionVo);
	
	/**
	 * 预览
	 * 
	 *@return void
	 */
	public void updateTestCourseDescription(CourseDescriptionVo courseDescriptionVo);

	/**
	 * 逻辑批量删除
	 * 
	 *@return void
	 */
	public void deletes(String[] ids);
}
