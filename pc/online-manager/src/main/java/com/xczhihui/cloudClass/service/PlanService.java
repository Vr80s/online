package com.xczhihui.cloudClass.service;

import java.util.List;

import com.xczhihui.course.vo.PlanVo;

public interface PlanService {

	/**
	 * 新增
	 * 
	 * @return void
	 */
	public void addPlan(PlanVo planVo);

	/**
	 * 追加
	 * 
	 * @return void
	 */
	public void addAppendPlan(PlanVo planVo);

	/**
	 * 新增
	 * 
	 * @return void
	 */
	public void addOneRestPlan(PlanVo planVo);

	/**
	 * 下载模板
	 * 
	 * @return void
	 */
	public List exportExcelPlan(PlanVo planVo);

	/**
	 * 
	 * 获取到计划list
	 * 
	 * @param planVo
	 */
	public List<PlanVo> findPlanList(PlanVo planVo);

	/**
	 * 
	 * 获取知识计划
	 * 
	 * @param planVo
	 */
	public List getGradePlanChapter(PlanVo planVo);

	/**
	 * 修改学习计划
	 * 
	 * @return void
	 */
	public void updatePlan(PlanVo planVo);

	/**
	 * 删除串讲
	 * 
	 * @return void
	 */
	public void deletePlanChuanJiangById(PlanVo planVo);

	/**
	 * 逻辑批量删除
	 * 
	 * @return void
	 */
	public void deletes(String[] ids);

	/**
	 * 获取到教师下拉框
	 * 
	 * @param getLecturers
	 */
	public List getLecturers(Integer courseId);

	/**
	 * 获取到该课程同一学科下的下拉框
	 * 
	 * @param getLecturers
	 */
	public List getMicroCourseList(Integer courseId);
}
