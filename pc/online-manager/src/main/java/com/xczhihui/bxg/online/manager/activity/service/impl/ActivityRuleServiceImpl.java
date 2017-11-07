package com.xczhihui.bxg.online.manager.activity.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.manager.activity.dao.ActivityRuleDao;
import com.xczhihui.bxg.online.manager.activity.service.ActivityRuleService;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;

@Service
public class ActivityRuleServiceImpl extends OnlineBaseServiceImpl implements ActivityRuleService {

	@Autowired
    private ActivityRuleDao activityRuleDao;
	
	@Override
	public Page<ActivityRuleVo> findActivityRulePage(ActivityRuleVo activityRuleVo, Integer pageNumber, Integer pageSize) {
		Page<ActivityRuleVo> page = activityRuleDao.findActivityRulePage(activityRuleVo, pageNumber, pageSize);
		return page;
	}
	
	@Override
	public void addActivityRule(ActivityRuleVo activityRuleVo) {
		if(activityRuleVo.getStartTime().getTime() <= (new Date().getTime())){
			throw new RuntimeException("开始时间不能早于当前时间！");
		}
		String id = UUID.randomUUID().toString().replace("-", "");
		String checkSql =   " SELECT " +
							"	GROUP_CONCAT(DISTINCT t2.grade_name) " +
							" FROM " +
							"	oe_activity_rule_detail t, " +
							"	oe_course t2 " +
							" WHERE " +
							"	t.course_Id in ("+activityRuleVo.getCourseIds()+") " +
							" AND t.rule_id <> :ruleId " + //本次新建的规则开始时间在已经存在的规则时间段中			//本次新建的结束时间在已经存在的规则时间段中 
							" AND ((t.start_time <= :startTime and t.end_time >= :startTime) or (t.start_time <= :endTime and t.end_time >= :endTime) "+
										//已经存在的开始时间在本次新建的时间段中									//已经存在的结束时间 在本次新建的时间段内
							" 		or (:startTime <= t.start_time and :endTime >= t.start_time) or (:startTime <= t.end_time and :endTime >= t.end_time)) " +
							" AND t.course_id = t2.id ";
		Map<String,Object> paramMap=new HashMap<String,Object>();
		String tempCourseName;
		
		paramMap.put("ruleId", id);
		paramMap.put("startTime", activityRuleVo.getStartTime());
		paramMap.put("endTime", activityRuleVo.getEndTime());
		try{
			//首先判断该课程是否在同一时间段内活动
			tempCourseName = activityRuleDao.getNamedParameterJdbcTemplate().queryForObject(checkSql, paramMap, String.class);
		}catch(Exception e){
			//	e.printStackTrace();
			tempCourseName = null;
		}

		if(tempCourseName != null){//只要不为null 就抛出异常
			throw new RuntimeException("课程"+tempCourseName+"在该时间段内有优惠活动！");
		}
		
		String sql= " INSERT INTO oe_activity_rule (`id`, `name`, `url`, `subject_ids`, `create_time`, `create_person`, start_time, end_time) " +
					" VALUES (?, ?, ?, ?, now(), ?, ?, ?)";

		//保存规则主表
		activityRuleDao.getNamedParameterJdbcTemplate().getJdbcOperations()
					.update(sql, new Object[]{id,activityRuleVo.getName(),activityRuleVo.getUrl(),activityRuleVo.getSubjectIds(),
										activityRuleVo.getCreatePerson(),activityRuleVo.getStartTime(),activityRuleVo.getEndTime()});
		
		sql = " INSERT INTO oe_activity_rule_detail (`id`, `rule_id`, `course_id`, `start_time`, `end_time`, `reach_money`, `minus_money`) \n" +
			  " VALUES (REPLACE(UUID(),'-',''), ?, ?, ?, ?, ?, ?)";

		String[] courseIds = activityRuleVo.getCourseIds().split(",");
		String[] reachMoneys = activityRuleVo.getReachMoneys().split(",");
		String[] minusMoneys = activityRuleVo.getMinusMoneys().split(",");

		//保存副表
		for(int i = 0;i <courseIds.length; i++){
			for(int j = 0;j<reachMoneys.length;j++ ){
				activityRuleDao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, new Object[]{id,courseIds[i].trim(),activityRuleVo.getStartTime(),activityRuleVo.getEndTime(),Double.parseDouble(reachMoneys[j].trim()),Double.parseDouble(minusMoneys[j].trim())});
			}
		}
	}

	@Override
	public void updateActivityRule(ActivityRuleVo activityRuleVo) {
		//时间段内课程重复优惠验证
		String checkSql =   " SELECT " +
							"	GROUP_CONCAT(DISTINCT t2.grade_name) " +
							" FROM " +
							"	oe_activity_rule_detail t, " +
							"	oe_course t2 " +
							" WHERE " +
							"	t.course_Id in ("+activityRuleVo.getCourseIds()+") " +
							" AND t.rule_id <> :ruleId " + //本次新建的规则开始时间在已经存在的规则时间段中			//本次新建的结束时间在已经存在的规则时间段中 
							" AND ((t.start_time <= :startTime and t.end_time >= :startTime) or (t.start_time <= :endTime and t.end_time >= :endTime) "+
										//已经存在的开始时间在本次新建的时间段中									//已经存在的结束时间 在本次新建的时间段内
							" 		or (:startTime <= t.start_time and :endTime >= t.start_time) or (:startTime <= t.end_time and :endTime >= t.end_time)) " +
							" AND t.course_id = t2.id ";

		Map<String,Object> paramMap=new HashMap<String,Object>();
		String tempCourseName;
		
		paramMap.put("ruleId", activityRuleVo.getId());
		paramMap.put("startTime", activityRuleVo.getStartTime());
		paramMap.put("endTime", activityRuleVo.getEndTime());

		try{
			//首先判断该课程是否在同一时间段内活动
			tempCourseName = activityRuleDao.getNamedParameterJdbcTemplate().queryForObject(checkSql, paramMap, String.class);
		}catch(Exception e){
			//	e.printStackTrace();
			tempCourseName = null;
		}
		
		if(tempCourseName != null){//只要不为null 就抛出异常
			throw new RuntimeException("课程"+tempCourseName+"在该时间段内有优惠活动！");
		}

		if(!activityRuleVo.getIsEdit()){//仅修改结束时间
			String sql = " update oe_activity_rule t set t.end_time = ? where t.id = ? ";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,new Object[]{activityRuleVo.getEndTime(),activityRuleVo.getId()});
			sql = "update oe_activity_rule_detail t set t.end_time = ? where t.rule_id = ? ";
			dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,new Object[]{activityRuleVo.getEndTime(),activityRuleVo.getId()});
			return;
		}else{//修改所有
//			String sql = "select count(1) from oe_activity_rule_detail t where t.start_time <= now() and t.rule_id = ? ";
//			if(dao.queryForInt(sql, new Object[]{activityRuleVo.getId()}) > 0){
//				//如果已经生效 那么判断 是否修改过其他字段 如果 其他字段未修改 那么就更新否则  就异常
//				sql = "select count(1) from oe_activity_rule t where t.id = ? and t.`name` = ? and t.url = ? and t.subject_ids = ? and t.start_time = ?";
//				//未修改其他字段 更新结束时间
//				Boolean mainIsEdit = dao.queryForInt(sql, new Object[]{activityRuleVo.getId(),activityRuleVo.getName(),activityRuleVo.getUrl(),activityRuleVo.getSubjectIds(),activityRuleVo.getStartTime()}) > 0;
//				if(mainIsEdit){//主表副表信息全部没有修改才能修改
//					throw new RuntimeException("规则已经生效不能修改！");
//				}
//			}
			
			String sql ="UPDATE oe_activity_rule " +
						"	SET `name` = ?, " +
						"		 `url` = ?, " +
						"		 `subject_ids` = ?, " +
						"		 `start_time` = ?, " +
						"		 `end_time` = ? " +
						"	WHERE " +
						"		`id` = ? ";
			//更新规则主表
			activityRuleDao.getNamedParameterJdbcTemplate().getJdbcOperations()
				   .update(sql, new Object[]{activityRuleVo.getName(),activityRuleVo.getUrl(),activityRuleVo.getSubjectIds(),
						   	activityRuleVo.getStartTime(),activityRuleVo.getEndTime(),activityRuleVo.getId()});

			//把子表所有的数据删除 然后重新生成
			sql = "delete from oe_activity_rule_detail where rule_id = ?";
			activityRuleDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{activityRuleVo.getId()});
			
			sql = " INSERT INTO oe_activity_rule_detail (`id`, `rule_id`, `course_id`, `start_time`, `end_time`, `reach_money`, `minus_money`) \n" +
				  " VALUES (REPLACE(UUID(),'-',''), ?, ?, ?, ?, ?, ?)";
			
			String[] courseIds = activityRuleVo.getCourseIds().split(",");
			String[] reachMoneys = activityRuleVo.getReachMoneys().split(",");
			String[] minusMoneys = activityRuleVo.getMinusMoneys().split(",");
			
			//保存副表
			for(int i = 0;i <courseIds.length; i++){
				for(int j = 0;j<reachMoneys.length;j++ ){
					activityRuleDao.getNamedParameterJdbcTemplate().getJdbcOperations()
					.update(sql, new Object[]{activityRuleVo.getId(),courseIds[i].trim(),activityRuleVo.getStartTime(),activityRuleVo.getEndTime(),Double.parseDouble(reachMoneys[j].trim()),Double.parseDouble(minusMoneys[j].trim())});
				}
			}
		}
	}

	@Override
	public List<CourseVo> getMicroCourseList(String menuIds) {
		String sql= " SELECT " +
					"	id,grade_name as courseName " +
					" FROM " +
					"	oe_course t " +
					" WHERE t.course_type = 1 and t.is_delete = 0 and  t.`status` = 1 and " +
					"	t.menu_id in ( " + menuIds + " ) " +
					" order by t.menu_id desc,t.grade_name asc";
		List<CourseVo> voList=dao.findEntitiesByJdbc(CourseVo.class, sql, null);
		return voList;
	}
}
