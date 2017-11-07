package com.xczhihui.bxg.online.manager.activity.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;

public interface ActivityRuleService {

	public Page<ActivityRuleVo> findActivityRulePage(ActivityRuleVo activityRuleVo, Integer pageNumber, Integer pageSize);
	
	/**
	 * 新增
	 * 
	 *@return void
	 */
	public void addActivityRule(ActivityRuleVo activityRuleVo);

	/**
	 * 修改
	 * 
	 *@return void
	 */
	public void updateActivityRule(ActivityRuleVo activityRuleVo);

	/**
	 * 根据学科查询所有的微课
	 * @param menuIds
	 * @return
	 */
	public List getMicroCourseList(String menuIds);
	
}
