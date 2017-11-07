package com.xczhihui.bxg.online.manager.activity.service.impl;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.support.service.SystemVariateService;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.activity.dao.ActivityDetailDao;
import com.xczhihui.bxg.online.manager.activity.service.ActivityDetailService;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;
import com.xczhihui.bxg.online.manager.utils.StringUtil;

@Service
public class ActivityDetailServiceImpl extends OnlineBaseServiceImpl implements ActivityDetailService{
	@Autowired
	private ActivityDetailDao activityDetailDao;
	@Autowired
	private SystemVariateService sv;
	@Override
	public Page<ActivityRuleVo> findActivityDetailPage(ActivityRuleVo activityRuleVo, Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		Page<ActivityRuleVo> page = activityDetailDao.findActivityDetailPage(activityRuleVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public List getActivityDetailPreferenty(String actId) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		String sql="SELECT act_content as actContent, act_order_sum as actOrderSum, act_user_sum as actUserSum, act_order_money_total as actOrderMoneyTotal,create_time as createTime FROM  oe_activity_rule_data WHERE act_id=:actId";
		paramMap.put("actId",actId);
		List<Map<String, Object>> list = activityDetailDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		for (Map<String, Object> r : list) {
			if(r.get("preferenty")!=null&&StringUtil.isNumber(r.get("preferenty").toString())){
				r.put("preferenty", sv.getNameByValue("preferentyType", r.get("preferenty").toString()));
			}
		}
		return list;
	}

	@Override
	public List getActivityDetailCourse(String actId) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		String sql="SELECT course_name as courseName, sale_sum as saleSum, price, create_time as createTime FROM oe_activity_rule_course WHERE act_id=:actId";
		paramMap.put("actId",actId);
		List<Map<String, Object>> list = activityDetailDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		for (Map<String, Object> r : list) {
			if(r.get("preferenty")!=null&&StringUtil.isNumber(r.get("preferenty").toString())){
				r.put("preferenty", sv.getNameByValue("preferentyType", r.get("preferenty").toString()));
			}
		}
		return list;
	}

}
