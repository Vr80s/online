package com.xczhihui.bxg.online.manager.activity.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;

public interface ActivityDetailService {

	public Page<ActivityRuleVo> findActivityDetailPage(ActivityRuleVo activityRuleVo, Integer pageNumber, Integer pageSize);


	public List getActivityDetailPreferenty(String actId);


	public List getActivityDetailCourse(String actId);

	 

}
