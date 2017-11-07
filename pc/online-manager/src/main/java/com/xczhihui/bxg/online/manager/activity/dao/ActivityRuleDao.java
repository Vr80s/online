package com.xczhihui.bxg.online.manager.activity.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.manager.activity.vo.ActivityRuleVo;

@Repository
public class ActivityRuleDao extends SimpleHibernateDao {

	public Page<ActivityRuleVo> findActivityRulePage(ActivityRuleVo activityRuleVo, Integer pageNumber, Integer pageSize){

		   Map<String,Object> paramMap=new HashMap<String,Object>();
		   StringBuilder sql=new StringBuilder(" SELECT " +
											   "	oar.id, " +
											   "	oar.name, " +
											   "	oar.url, " +
											   "	oar.subject_ids, " +
											   "	oar.create_time, " +
											   "	oar.create_person, " +
											   "	ou.name createPersonName, " +
											   "	oar.start_time, " +
											   "	oar.end_time, " +
											   "	oar.start_time >= now() isEdit, " +//用户前台判断是否可以修改
											   "	oar.end_time <= now() isEnd " +//如果已经结束就不能修改
											   " FROM " +
											   "	oe_activity_rule oar " +
											   " JOIN user ou ON oar.create_person = ou.login_name " +
											   " where 1 = 1 ");
		   if(activityRuleVo.getName() != null && !"".equals(activityRuleVo.getName())){
			   sql.append(" and oar.name like :name");
			   paramMap.put("name", "%" + activityRuleVo.getName() + "%");
		   }
		   
		   if(activityRuleVo.getStartTime() !=null){
			  sql.append(" and oar.start_time >=:startTime ");
			  paramMap.put("startTime", activityRuleVo.getStartTime());
		   }

		   if(activityRuleVo.getEndTime() !=null){
			   sql.append(" and DATE_FORMAT(oar.end_time,'%Y-%m-%d') <=:endTime ");
			  paramMap.put("endTime", activityRuleVo.getEndTime());
		   }
		   
		   sql.append(" order by oar.create_time desc ");
		   
//		   System.out.println("查询语句"+sql.toString());

		   Page<ActivityRuleVo> ms = this.findPageBySQL(sql.toString(), paramMap, ActivityRuleVo.class, pageNumber, pageSize);
		   
		   List<ActivityRuleVo> list = ms.getItems();
			
		   for(int i=0;i<list.size();i++){
			    String sqlTemp = "select GROUP_CONCAT(t.`name`) subjectNames from oe_menu t where t.id in ("+list.get(i).getSubjectIds()+")";
				list.get(i).setSubjectNames(this.getNamedParameterJdbcTemplate().queryForObject(sqlTemp, paramMap, String.class));

//				sqlTemp = " select oard.start_time,oard.end_time from oe_activity_rule_detail oard where oard.rule_id = :ruleId limit 1 ";
				paramMap.put("ruleId", list.get(i).getId());

//				try{
//					ActivityRuleDetailVo ruleDetail = this.getNamedParameterJdbcTemplate().queryForObject(sqlTemp, paramMap,BeanPropertyRowMapper.newInstance(ActivityRuleDetailVo.class));
//					
//					list.get(i).setStartTime(ruleDetail.getStartTime());
//					list.get(i).setEndTime(ruleDetail.getEndTime());
//				}catch(Exception e){}
				sqlTemp = " select replace(GROUP_CONCAT(distinct concat('满',oard.reach_money,'元减',oard.minus_money,'元')),',',';') from oe_activity_rule_detail oard where oard.rule_id = :ruleId ";
				
				try{
					//首先判断该课程是否在同一时间段内活动
					list.get(i).setRuleContent(this.getNamedParameterJdbcTemplate().queryForObject(sqlTemp, paramMap, String.class));
				}catch(Exception e){}
			
				try{
//					产品要比较复杂的显示
//					sqlTemp = "select group_concat(distinct t2.grade_name) courseNames,group_concat(distinct t2.id) courseIds from oe_activity_rule_detail t,oe_course t2 where t.course_id = t2.id and t.rule_id = :ruleId";
					sqlTemp = "select GROUP_CONCAT(b.showCourse) courseNames,GROUP_CONCAT(b.courseIds) courseIds \n" +
								"	from (\n" +
								"	select \n" +
								"		a.menu_id,\n" +
								"		a.ruleCourseCnt,\n" +
								"		a.menuCourseCnt,\n" +
								"		a.courseNames,\n" +
								"		a.courseIds,\n" +
								"		(case \n" +
								"				when a.ruleCourseCnt = menuCourseCnt\n" +
								"				then \n" +
								"						(select concat(t4.name,'全部课程') from oe_menu t4 where t4.id = a.menu_id) \n" +
								"				ELSE\n" +
								"						a.courseNames \n" +
								"				END\n" +
								"		) showCourse\n" +
								"	FROM\n" +
								"	(\n" +
								"		SELECT\n" +
								"			t2.menu_id,\n" +
								"			count(DISTINCT t.course_id) ruleCourseCnt,\n" +
								"			(select count(1) from oe_course t3 where t2.menu_id = t3.menu_id and t3.is_delete = 0 and t3.course_type = 1) menuCourseCnt,\n" +
								"			group_concat(distinct t2.grade_name) courseNames,\n" +
								"			group_concat(distinct t2.id) courseIds\n" +
								"		FROM\n" +
								"			oe_activity_rule_detail t,\n" +
								"			oe_course t2\n" +
								"		WHERE\n" +
								"			t.course_id = t2.id\n" +
								"		and t.rule_id = :ruleId \n" +
								"		GROUP BY\n" +
								"			t2.menu_id\n" +
								"	) a\n" +
								") b ";
					
					Map<String,Object> map = this.getNamedParameterJdbcTemplate().queryForMap(sqlTemp, paramMap);
					list.get(i).setCourseNames(map.get("courseNames").toString());
					list.get(i).setCourseIds(map.get("courseIds").toString());
				}catch(Exception e){}
				
				try{
					sqlTemp = "select GROUP_CONCAT(CONCAT(a.reach_money,'|',a.minus_money)) from (select DISTINCT t.reach_money,t.minus_money from oe_activity_rule_detail t  where t.rule_id = :ruleId order by t.reach_money desc) a";
					list.get(i).setRuleMoneys(this.getNamedParameterJdbcTemplate().queryForObject(sqlTemp, paramMap, String.class));
				}catch(Exception e){}
		   }

      	   return ms;
	}
}

