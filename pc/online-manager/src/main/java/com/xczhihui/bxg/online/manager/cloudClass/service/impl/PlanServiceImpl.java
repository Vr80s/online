package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.manager.cloudClass.dao.PlanDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.PlanService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.GradeVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.PlanVo;

@Service
public class PlanServiceImpl extends OnlineBaseServiceImpl implements PlanService {

	@Autowired
    private PlanDao planDao;
	
	@Value("${online.web.url}")
	private String weburl;
	
	@Override
	public void addPlan(PlanVo planVo) {
		//查询出必要的参数 上几天 休息几天  开课时间
		String sql = "select og.work_day_sum,og.rest_day_sum,og.curriculum_time from oe_grade og where og.id = :id";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", planVo.getGradeId());
		GradeVo gradeVo = planDao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, new BeanPropertyRowMapper<GradeVo>(GradeVo.class));
		
		//先批量查出应该添加的值
		sql = "SELECT " +
					"	REPLACE (UUID(), '-', '') id, " +
					"	opte.id templateId, " +
					"	date_add(:curriculumTime,INTERVAL opte.day day) planDate, " +
					"	(case DAYOFWEEK(date_add(:curriculumTime,INTERVAL  opte.day day))  " +
					"			when 1 THEN '星期日'  " +
					"			when 2 THEN '星期一'  " +
					"			when 3 THEN '星期二' " +
					"		  	when 4 THEN '星期三' " +
					"			when 5 THEN '星期四' " +
					"			when 6 THEN '星期五' " +
					"			when 7 THEN '星期六' " +
					"	end) week, " +
					"	opte.day, " +
					"	0 chuanjiangHas, " +
					"	null chuanjiangName, " +
					"	null chuanjiangStartTime,  " +
					"	null chuanjiangEndTime, " +
					"	0 chuanjiangDuration, " +
					"	0 chuanjiangMode, " +
					"	null chuanjiangRoomId, " +
					"	null chuanjiangRoomLink, " +
					"	0 restHas, " +
					"	'' gradeId " +
					"FROM " +
					"	oe_plan_template opte " +
					"WHERE opte.course_id = :courseId order by planDate asc ";

		paramMap.clear();//清除上面的语句
		
		paramMap.put("courseId", planVo.getCourseId());
		paramMap.put("curriculumTime", gradeVo.getCurriculumTime());
		
		List<PlanVo> list = planDao.getNamedParameterJdbcTemplate().query(sql, paramMap, new BeanPropertyRowMapper<PlanVo>(PlanVo.class));

		//新增学习计划的语句
		sql =  " INSERT INTO oe_plan (id, template_id, plan_date, week, day, chuanjiang_has, chuanjiang_name, chuanjiang_start_time, chuanjiang_end_time, "+ 
			   " chuanjiang_duration, chuanjiang_mode, chuanjiang_room_id, chuanjiang_room_link, rest_has, grade_id, create_person, create_time) VALUES " +
			   "	( " +
			   "	:id, " +
			   "	:templateId, " +
			   "	:planDate, " +
			   "	(case DAYOFWEEK(:planDate)  " +
			   "			when 1 THEN '星期日'  " +
			   "			when 2 THEN '星期一'  " +
			   "			when 3 THEN '星期二' " +
			   "		  	when 4 THEN '星期三' " +
			   "			when 5 THEN '星期四' " +
			   "			when 6 THEN '星期五' " +
			   "			when 7 THEN '星期六' " +
			   "	end), " +
			   "	:day, " +
			   "	:chuanjiangHas, " +
			   "	:chuanjiangName, " +
			   "	:chuanjiangStartTime, " +
			   "	:chuanjiangEndTime, " +
			   "	:chuanjiangDuration, " +
			   "	:chuanjiangMode, " +
			   "	:chuanjiangRoomId, " +
			   "	:chuanjiangRoomLink, " +
			   "	:restHas, " +
			   "	:gradeId, " + 
			   "	:createPerson, " + 
			   "	now() )";
		
		Date planDate = gradeVo.getCurriculumTime();//开课时间
		Integer workDaySum = gradeVo.getWorkDaySum()==null?0:gradeVo.getWorkDaySum();
		Integer restDaySum = gradeVo.getRestDaySum()==null?0:gradeVo.getRestDaySum();
		GregorianCalendar gc=new GregorianCalendar(); 
		PlanVo planVoTemp;
		gc.setTime(planDate); 
		List<PlanVo> listParam = new ArrayList<PlanVo>();
		//遍历所有的模板插入休息日
		for(int i = 0;i<list.size();i++){
			planVoTemp = list.get(i);
			//当i+1大于工作天数 并且余数 等于1的时候  插入休息日
			if((workDaySum == 1 && i > 0) || (workDaySum  > 0 && (i+1)>workDaySum && (i+1)%workDaySum==1)){
				for(int j = 0;j<restDaySum;j++){
					PlanVo planVoTemp2 = new PlanVo(); 
					planVoTemp2.setId(UUID.randomUUID().toString().replace("-", ""));
					planVoTemp2.setTemplateId("0");
					planVoTemp2.setPlanDate(gc.getTime());
					planVoTemp2.setChuanjiangHas(0);
					planVoTemp2.setchuanjiangDuration(0d);
					planVoTemp2.setChuanjiangMode(0);
					planVoTemp2.setRestHas(1);
					planVoTemp2.setGradeId(planVo.getGradeId());
					planVoTemp2.setCreatePerson(planVoTemp.getCreatePerson());
					gc.add(5, 1);
					listParam.add(planVoTemp2);
				}
			}
			
			planVoTemp.setPlanDate(gc.getTime());
			planVoTemp.setCreatePerson(planVo.getCreatePerson());
			planVoTemp.setGradeId(planVo.getGradeId());
			listParam.add(planVoTemp);
			gc.add(5, 1);// 第一个参数  取1加1年,取2加半年,取3加一季度,取4加一周  取5加一天  第二个参数为值  此处为加一天
		}

		SqlParameterSource[] sqlParameterSources =  SqlParameterSourceUtils.createBatch(listParam.toArray()); 
		planDao.getNamedParameterJdbcTemplate().batchUpdate(sql, sqlParameterSources);
	}

	@Override
	public void addAppendPlan(PlanVo planVo) {
		//查询出已有天数
		String sql = "select max(t.`day`) day,max(t.plan_date) plan_date from oe_plan t where t.grade_id = :id and  t.rest_has = 0";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", planVo.getGradeId());
		PlanVo planVoParam = planDao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, new BeanPropertyRowMapper<PlanVo>(PlanVo.class));
		
		sql = "select max(t.`day`) from oe_plan_template t where t.course_id = ?";
		Integer maxDay = planDao.queryForInt(sql, new Object[]{planVo.getCourseId()});
		if(maxDay > planVoParam.getDay()){//只有模板的最大天数 大于现在计划的最大天数 才追加
			//查询出已有的休息日  计算时间用的
			sql = "select count(1) from oe_plan t where t.grade_id = ? and t.rest_has = 1 ";
			Integer restNum = planDao.queryForInt(sql, new Object[]{planVo.getGradeId()});
			
			//查询出必要的参数 上几天 休息几天  开课时间
			sql = "select og.work_day_sum,og.rest_day_sum,og.curriculum_time from oe_grade og where og.id = :id";
			
			GradeVo gradeVo = planDao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, new BeanPropertyRowMapper<GradeVo>(GradeVo.class));
			
			//先批量查出应该添加的值
			sql = "SELECT " +
					"	REPLACE (UUID(), '-', '') id, " +
					"	opte.id templateId, " +
					"	date_add(:curriculumTime,INTERVAL (opte.day + "+restNum+") day) planDate, " +
					"	(case DAYOFWEEK(date_add(:curriculumTime,INTERVAL (opte.day + "+restNum+") day))  " +
					"			when 1 THEN '星期日'  " +
					"			when 2 THEN '星期一'  " +
					"			when 3 THEN '星期二' " +
					"		  	when 4 THEN '星期三' " +
					"			when 5 THEN '星期四' " +
					"			when 6 THEN '星期五' " +
					"			when 7 THEN '星期六' " +
					"	end) week, " +
					"	opte.day, " +
					"	0 chuanjiangHas, " +
					"	null chuanjiangName, " +
					"	null chuanjiangStartTime,  " +
					"	null chuanjiangEndTime, " +
					"	0 chuanjiangDuration, " +
					"	0 chuanjiangMode, " +
					"	null chuanjiangRoomId, " +
					"	null chuanjiangRoomLink, " +
					"	0 restHas, " +
					"	'' gradeId " +
					"FROM " +
					"	oe_plan_template opte " +
					"WHERE opte.course_id = :courseId and opte.day > :day order by planDate asc ";
			
			paramMap.clear();//清除上面的语句
			
			paramMap.put("courseId", planVo.getCourseId());
			paramMap.put("curriculumTime", gradeVo.getCurriculumTime());
			paramMap.put("day", planVoParam.getDay());//追加开始的天数
//			paramMap.put("planDate", planVoParam.getPlanDate());//追加开始的时间
			
			List<PlanVo> list = planDao.getNamedParameterJdbcTemplate().query(sql, paramMap, new BeanPropertyRowMapper<PlanVo>(PlanVo.class));
			
			//新增学习计划的语句
			sql =  " INSERT INTO oe_plan (id, template_id, plan_date, week, day, chuanjiang_has, chuanjiang_name, chuanjiang_start_time, chuanjiang_end_time, "+ 
					" chuanjiang_duration, chuanjiang_mode, chuanjiang_room_id, chuanjiang_room_link, rest_has, grade_id, create_person, create_time) VALUES " +
					"	( " +
					"	:id, " +
					"	:templateId, " +
					"	:planDate, " +
					"	(case DAYOFWEEK(:planDate)  " +
					"			when 1 THEN '星期日'  " +
					"			when 2 THEN '星期一'  " +
					"			when 3 THEN '星期二' " +
					"		  	when 4 THEN '星期三' " +
					"			when 5 THEN '星期四' " +
					"			when 6 THEN '星期五' " +
					"			when 7 THEN '星期六' " +
					"	end), " +
					"	:day, " +
					"	:chuanjiangHas, " +
					"	:chuanjiangName, " +
					"	:chuanjiangStartTime, " +
					"	:chuanjiangEndTime, " +
					"	:chuanjiangDuration, " +
					"	:chuanjiangMode, " +
					"	:chuanjiangRoomId, " +
					"	:chuanjiangRoomLink, " +
					"	:restHas, " +
					"	:gradeId, " + 
					"	:createPerson, " + 
					"	now() )";
			
			Date planDate = gradeVo.getCurriculumTime();//开课时间
			Integer workDaySum = gradeVo.getWorkDaySum()==null?0:gradeVo.getWorkDaySum();
			Integer restDaySum = gradeVo.getRestDaySum()==null?0:gradeVo.getRestDaySum();
			GregorianCalendar gc=new GregorianCalendar(); 
			PlanVo planVoTemp;
			gc.setTime(planVoParam.getPlanDate()); 
			gc.add(5, 1);//先加一天 从上次添加的最后一天 的第二天开始算起
			List<PlanVo> listParam = new ArrayList<PlanVo>();
			
			//遍历所有的模板插入休息日
			for(int i = 0;i<list.size();i++){
				planVoTemp = list.get(i);
				//当i+1大于工作天数 并且余数 等于1的时候  插入休息日
				if((workDaySum == 1 && i > 0) || (workDaySum  > 0 && (i+1)>workDaySum && (i+1)%workDaySum==1)){
					for(int j = 0;j<restDaySum;j++){
						PlanVo planVoTemp2 = new PlanVo(); 
						planVoTemp2.setId(UUID.randomUUID().toString().replace("-", ""));
						planVoTemp2.setTemplateId("0");
						planVoTemp2.setPlanDate(gc.getTime());
						planVoTemp2.setChuanjiangHas(0);
						planVoTemp2.setchuanjiangDuration(0d);
						planVoTemp2.setChuanjiangMode(0);
						planVoTemp2.setRestHas(1);
						planVoTemp2.setGradeId(planVo.getGradeId());
						planVoTemp2.setCreatePerson(planVoTemp.getCreatePerson());
						gc.add(5, 1);
						listParam.add(planVoTemp2);
					}
				}
				
				planVoTemp.setPlanDate(gc.getTime());
				planVoTemp.setCreatePerson(planVo.getCreatePerson());
				planVoTemp.setGradeId(planVo.getGradeId());
				listParam.add(planVoTemp);
				gc.add(5, 1);// 第一个参数  取1加1年,取2加半年,取3加一季度,取4加一周  取5加一天  第二个参数为值  此处为加一天
			}
			
			SqlParameterSource[] sqlParameterSources =  SqlParameterSourceUtils.createBatch(listParam.toArray()); 
			planDao.getNamedParameterJdbcTemplate().batchUpdate(sql, sqlParameterSources);
		}
	}

	@Override
	public void addOneRestPlan(PlanVo planVo) {
		String sql = "select count(1) from oe_plan t where DATE_FORMAT(t.plan_date,'%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-%d') and t.id = ? ";
		if(planDao.queryForInt(sql, new Object[]{planVo.getId()}) == 0){//今天之前的不能添加
			throw new RuntimeException("不能在今天之前的计划新增休息日！");
		}
		
		//首先把这之后的日期推1天
		sql ="UPDATE oe_plan t " +
			"SET t.plan_date = date_add(plan_date, INTERVAL 1 DAY), " +
			"		t.`week` = (case DAYOFWEEK(plan_date) " +
			"							 			when 1 THEN '星期日'   " +
			"							 			when 2 THEN '星期一'   " +
			"							 			when 3 THEN '星期二'   " +
			"							 		  	when 4 THEN '星期三'   " +
			"							 			when 5 THEN '星期四'   " +
			"							 			when 6 THEN '星期五'   " +
			"							 			when 7 THEN '星期六'   " +
			"							 	end) " +
			"where t.id in (select id from (select id from oe_plan t2 where exists( select 1 from oe_plan t3 where t3.plan_date < t2.plan_date and t3.grade_id = t2.grade_id and t3.id = ?)) t4)";
		planDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{planVo.getId()});
	
		
		//然后再插入新增的数据
		sql = " INSERT INTO oe_plan (id, template_id, plan_date, week, day, chuanjiang_has, chuanjiang_name, chuanjiang_start_time, chuanjiang_end_time, "+ 
				" chuanjiang_duration, chuanjiang_mode, chuanjiang_room_id, chuanjiang_room_link, rest_has, grade_id, create_person, create_time) VALUES " +
				"	( " +
				"	:id, " +
				"	:templateId, " +
				"	date_add(:planDate,interval 1  day), " +
				"	(case DAYOFWEEK(date_add(:planDate,interval 1  day))  " +
				"			when 1 THEN '星期日'  " +
				"			when 2 THEN '星期一'  " +
				"			when 3 THEN '星期二' " +
				"		  	when 4 THEN '星期三' " +
				"			when 5 THEN '星期四' " +
				"			when 6 THEN '星期五' " +
				"			when 7 THEN '星期六' " +
				"	end), " +
				"	:day, " +
				"	:chuanjiangHas, " +
				"	:chuanjiangName, " +
				"	:chuanjiangStartTime, " +
				"	:chuanjiangEndTime, " +
				"	:chuanjiangDuration, " +
				"	:chuanjiangMode, " +
				"	:chuanjiangRoomId, " +
				"	:chuanjiangRoomLink, " +
				"	:restHas, " +
				"	:gradeId, " + 
				"	:createPerson, " + 
				"	now() )";
		
			PlanVo planVoTemp2 = new PlanVo(); 
			planVoTemp2.setId(UUID.randomUUID().toString().replace("-", ""));
			planVoTemp2.setTemplateId("0");
			planVoTemp2.setPlanDate(planVo.getPlanDate());
			planVoTemp2.setChuanjiangHas(0);
			planVoTemp2.setchuanjiangDuration(0d);
			planVoTemp2.setChuanjiangMode(0);
			planVoTemp2.setRestHas(1);
			planVoTemp2.setGradeId(planVo.getGradeId());
			planVoTemp2.setCreatePerson(planVo.getCreatePerson());

			planDao.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(planVoTemp2));
	}

	@Override
	public List<PlanVo> findPlanList(PlanVo planVo) {
		String sql =" SELECT " +
					"	id, " +
					"	template_id, " +
					"	plan_date, " +
					"	WEEK, " +
					"	DAY, " +
					"	chuanjiang_has, " +
					"	chuanjiang_name, " +
					"	chuanjiang_start_time, " +
					"	chuanjiang_end_time, " +
					"	chuanjiang_duration, " +
					"	chuanjiang_mode, " +
					"	ifnull(chuanjiang_room_id,'') chuanjiang_room_id, " +
					"	ifnull(chuanjiang_room_link,'') chuanjiang_room_link, " +
					"	rest_has, " +
					"	grade_id, " +
					"	chuanjiang_lecturer_id, " +
					"	micro_course_ids, " +
					"	chuanjiang_is_send " +
					" FROM " +
					"	oe_plan t where t.grade_id = :gradeId order by plan_date asc ";

		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("gradeId", planVo.getGradeId());
		List<PlanVo> list = planDao.getNamedParameterJdbcTemplate().query(sql, paramMap, new BeanPropertyRowMapper<PlanVo>(PlanVo.class));

		return list;
	}
	
	@Override
	public List getGradePlanChapter(PlanVo planVo) {
		String sql ="select oc.`name`, " +
					"     (select IFNULL(sum(time_to_sec(concat('00:',video_time))),'0') from oe_video ov where oc.id = ov.chapter_id and ov.video_time is not null and ov.is_delete = 0) videoTime " +
					"  from oe_chapter oc where oc.plan_template_id = :templateId order by oc.sort ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("templateId", planVo.getTemplateId());
		List<PlanVo> list = planDao.getNamedParameterJdbcTemplate().query(sql, paramMap, new BeanPropertyRowMapper<PlanVo>(PlanVo.class));
		for(int i = 0 ;i<list.size();i++){
			PlanVo planVoTemp = list.get(i);
			int totalSec = Integer.parseInt(planVoTemp.getVideoTime());
			if(totalSec==0){
				planVoTemp.setVideoTime("00:00"); 
			}else{
				int min = totalSec/60;
				int sec = totalSec%60;
				planVoTemp.setVideoTime((min<10?"0"+min:min)+":"+(sec<10?"0"+sec:sec));
			}
			list.set(i, planVoTemp);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updatePlan(PlanVo planVo) {
		String sql = "select chuanjiang_has from oe_plan where id = ?";//先取出之前是否有串讲 判断 是添加串讲 还是修改串讲 如果添加串讲 就发送消息提醒
		Boolean isSendMessage = planDao.queryForInt(sql, new Object[]{planVo.getId()})==0;
		
		if(isSendMessage){//如果是 新增  需要判断 是否是今天之前
			sql = "select count(1) from oe_plan t where DATE_FORMAT(t.plan_date,'%Y-%m-%d') >= DATE_FORMAT(NOW(),'%Y-%m-%d') and t.id = ? ";
			if(planDao.queryForInt(sql, new Object[]{planVo.getId()}) == 0){//今天之前的不能删除
				throw new RuntimeException("不能在今天之前的计划新增串讲！");
			}
			
			//当前时间 大于 开课时间就不能添加
			if(new Date().getTime() > planVo.getchuanjiangStartTime().getTime())//如果开课时间 是今天 并且 时间大于现在 也不能添加
			{
				throw new RuntimeException("串讲开课时间已过不能新增！");
			}
		}else if(!isSendMessage){//修改的话 
			sql = "select count(1) from oe_plan t where t.id = ? and t.chuanjiang_is_send = 0 ";
			if(planDao.queryForInt(sql, new Object[]{planVo.getId()}) == 0){
				throw new RuntimeException("只能修改当前时间两小时后的串讲课！");
			}
		}

		sql ="update oe_plan t set " +
			"	t.chuanjiang_has = ?, " +
			"	t.chuanjiang_name = ?, " +
			"	t.chuanjiang_duration = ?, " +
			"	t.chuanjiang_start_time = ?, " +
			"	t.chuanjiang_end_time = ?, " +
			"	t.chuanjiang_mode = ?, " +
			"	t.chuanjiang_room_id = ?, " +
			"	t.chuanjiang_lecturer_id = ?, " +
			"	t.chuanjiang_room_link = ?, " +
			"	t.micro_course_ids = ? " +
			" where t.id = ? ";
		planDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{
				planVo.getChuanjiangHas(),
				planVo.getChuanjiangName(),
				planVo.getchuanjiangDuration(),
				planVo.getchuanjiangStartTime(),
				planVo.getchuanjiangEndTime(),
				planVo.getChuanjiangMode(),
				planVo.getChuanjiangRoomId(),
				planVo.getChuanjiangLecturerId(),
				planVo.getChuanjiangRoomLink(),
				planVo.getMicroCourseIds(),
				planVo.getId()});
		
		Long sc = (planVo.getchuanjiangStartTime().getTime() - new Date().getTime()) /(1000*60);
		//						当前时间			减去			开课时间										如果在120分钟以内  就发送提醒
		if(isSendMessage && (sc <= 120)){//发送消息提醒  新增 且 在两小时内提醒
			Map paramMap = new HashMap<>();
			sql = "select t.id,t1.course_id courseId,t1.id gradeId,t1.`name` gradeName from oe_plan t,oe_grade t1 where t.id = :id and t.grade_id = t1.id and t1.is_delete = 0 ";
			paramMap.put("id", planVo.getId());
			Map map = planDao.getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);
			
			String courseId = map.get("courseId").toString();
			String planId = map.get("id").toString();
			String gradeName = map.get("gradeName").toString();
			String msg_link = "";
			String msg_link2 = weburl + "/web/html/CourseDetailZhiBo.html?courseId="+courseId;
			
			if(planVo.getChuanjiangMode()==0){//本站
				msg_link = weburl + "/web/livepage/"+ courseId+"/"+planVo.getChuanjiangRoomId()+"/"+planId;//直播间链接地址
			}else{//外部
				msg_link = planVo.getChuanjiangRoomLink();
			}
			
			//拼出消息  
            String time = new SimpleDateFormat("MM月dd日 HH:mm").format(planVo.getchuanjiangStartTime());
            String message = "您的班级<a href=\"javascript:void(0)\" onclick=\"on_click_msg('msg_id','" + msg_link2 + "');\">"+gradeName+"</a>新增一门串讲课，名称为《"+planVo.getChuanjiangName()+"》,"+time+"开课,<a href=\"javascript:void(0)\" onclick=\"on_click_msg('msg_id','" + msg_link + "');\">点击进入直播教室>></a>";
            MapSqlParameterSource params = new MapSqlParameterSource();
            
            //发送给职业课用户
            sql = "insert into oe_message (id,context,user_id,type,create_person,create_time,status,is_online) " +
            	  " select a.id,replace(:context,'msg_id',a.id),a.user_id,1,:createPeson,now(),1,0 from(select REPLACE(UUID(),'-','') id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.grade_id=:gradeId ) a";
            
            params.addValue("context", message);
            params.addValue("createPeson", planVo.getCreatePerson());
            params.addValue("gradeId", map.get("gradeId"));
            
            planDao.getNamedParameterJdbcTemplate().update(sql,params);
            
            if(planVo.getMicroCourseIds() != null && !"".equals(planVo.getMicroCourseIds()))
            {
            	message = "职业课&nbsp;"+gradeName+"&nbsp;新增一门串讲课，名称为《"+planVo.getChuanjiangName()+"》,"+time+"开课,<a href=\"javascript:void(0)\" onclick=\"on_click_msg('msg_id','" + msg_link + "');\">点击进入直播教室>></a>";
                //再发送给包含的微课用户
                sql = "insert into oe_message (id,context,user_id,type,create_person,create_time,status,is_online) " +
                	  " select max(a.id),replace(:context,'msg_id',max(a.id)),a.user_id,1,:createPeson,now(),1,0 from(select REPLACE(UUID(),'-','') id,oa.user_id from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.grade_id<>:gradeId and arg.course_id in("+planVo.getMicroCourseIds()+")) a "+
                	  " where not exists(select 1 from oe_apply oa LEFT JOIN apply_r_grade_course arg on oa.id= arg.apply_id where arg.grade_id=:gradeId and oa.user_id = a.user_id) "+ 
                	  " group by a.user_id ";//加group by 为了剔重 
                params.addValue("context", message);
//                params.addValue("createPeson", planVo.getCreatePerson());
//                params.addValue("gradeId", map.get("gradeId"));

                planDao.getNamedParameterJdbcTemplate().update(sql,params);
            }

            //设置为已发送
            sql ="update oe_plan t set " +
        		 "	t.chuanjiang_is_send = 1 " + 
        		 " where t.id = ? ";
        	planDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{planVo.getId()});
		}
	}
	
	@Override
	public void deletePlanChuanJiangById(PlanVo planVo) {
		String sql = "select count(1) from oe_plan t where t.id = ? and t.chuanjiang_is_send = 0 ";
		if(planDao.queryForInt(sql, new Object[]{planVo.getId()}) == 0){
			throw new RuntimeException("只能删除开始时间为当前时间两小时后的串讲课！");
		}

		sql ="update oe_plan t set " +
				"	t.chuanjiang_has = 0, " +
				"	t.chuanjiang_name = null, " +
				"	t.chuanjiang_duration = '0.00', " +
				"	t.chuanjiang_start_time = null, " +
				"	t.chuanjiang_end_time = null, " +
				"	t.chuanjiang_mode = 0, " +
				"	t.chuanjiang_room_id = null, " +
				"	t.chuanjiang_lecturer_id = null, " +
				"	t.chuanjiang_room_link = null, " +
				"	t.micro_course_ids = null, " +
				"	t.chuanjiang_is_send = 0 " + 
				" where t.id = ? ";
		planDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{planVo.getId()});
	}
	
	@Override
	public void deletes(String[] ids) {
		String sql = "select count(1) from oe_plan t where DATE_FORMAT(t.plan_date,'%Y-%m-%d') > DATE_FORMAT(NOW(),'%Y-%m-%d') and t.id = ? ";
		if(planDao.queryForInt(sql, new Object[]{ids[0]}) == 0){//今天之前的不能删除
			throw new RuntimeException("只能删除当天之后的数据！");
		}
		
		//删除掉以后  先更新日期
		sql ="UPDATE oe_plan t " +
			"SET t.plan_date = date_add(plan_date, INTERVAL -1 DAY), " +
			"		t.`week` = (case DAYOFWEEK(plan_date) " +
			"							 			when 1 THEN '星期日'   " +
			"							 			when 2 THEN '星期一'   " +
			"							 			when 3 THEN '星期二'   " +
			"							 		  	when 4 THEN '星期三'   " +
			"							 			when 5 THEN '星期四'   " +
			"							 			when 6 THEN '星期五'   " +
			"							 			when 7 THEN '星期六'   " +
			"							 	end) " +
			"where t.id in (select id from (select id from oe_plan t2 where exists( select 1 from oe_plan t3 where t3.plan_date < t2.plan_date and t3.grade_id = t2.grade_id and t3.id = ?)) t4)";
		planDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{ids[0]});

		//然后删除数据 
		sql = " delete from oe_plan where id = ? ";
		planDao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, new Object[]{ids[0]});
	}

	@Override
	public List exportExcelPlan(PlanVo planVo) {
		String sql =" select id,planDate,name,week,day,CONVERT(videoTime,CHAR(100)) videoTime,sort,chuanjiangHas from (SELECT " +
					"	op.id, " +
					"	op.plan_date planDate, " +
					"	op.`week` week, " +
					"	ifnull(CONCAT('第',op.`day`,'天'),'休息') day, " +
					"	ifnull(oc.`name`,'') name, " +
//					"	(select IFNULL(sec_to_time(sum(time_to_sec(video_time))),'00:00') from oe_video ov where oc.id = ov.chapter_id and ov.video_time is not null and ov.is_delete = 0) videoTime, " +
					"	(select IFNULL(sum(time_to_sec(concat('00:',video_time))),'0') from oe_video ov where oc.id = ov.chapter_id and ov.video_time is not null and ov.is_delete = 0) videoTime, " +
					"	oc.sort, " +
					"	op.chuanjiang_has chuanjiangHas" +
					" FROM " +
					"	oe_plan op " +
					" LEFT JOIN oe_chapter oc ON op.template_id = oc.plan_template_id " +
					" AND oc.is_delete = 0 " +
					" WHERE " +
					"	op.grade_id = :gradeId " +
					" UNION ALL " +
					" SELECT " +
					"	op.id, " +
					"	op.plan_date planDate, " +
					"	op.`week` week, " +
					"	ifnull(CONCAT('第',op.`day`,'天'),'休息') day, " +
					"	op.chuanjiang_name `name`, " +
					"	concat('串讲时间:',DATE_FORMAT(op.chuanjiang_start_time,'%H:%i'),'-',DATE_FORMAT(op.chuanjiang_end_time,'%H:%i')) videoTime, " +
					"	999999999999 sort, " +
					"	op.chuanjiang_has chuanjiangHas " +
					" FROM " +
					"	oe_plan op " +
					" where op.grade_id = :gradeId " +
					"  and op.chuanjiang_has = 1) t " +
					" where (t.chuanjiangHas = 0 or (t.chuanjiangHas = 1 and t.`name` is not null and t.`name` <> ''))" +//#剔除掉有串讲 但是没有知识点的记录 
					" order by t.planDate  ,t.sort asc ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("gradeId", planVo.getGradeId());
		List<Map<String,Object>> list = planDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		for(int i = 0 ;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			if(map.get("sort")==null || !"999999999999".equals(map.get("sort").toString())){//不处理休息日
				int totalSec = Integer.parseInt(map.get("videoTime").toString());
				if(totalSec==0){
					map.put("videoTime", "00:00"); 
				}else{
					int min = totalSec/60;
					int sec = totalSec%60;
					map.put("videoTime", (min<10?"0"+min:min)+":"+(sec<10?"0"+sec:sec));
				}
			}
			list.set(i, map);
		}
		return list;
	}

	@Override
	public List getLecturers(Integer courseId) {
		String sql =" SELECT " +
					"	t2.`name`, " +
					"	t2.id " +
					" FROM " +
					"	course_r_lecturer t, " +
					"	oe_lecturer t2 " +
					" WHERE " +
					"	t.lecturer_id = t2.id " +
					" AND t2.is_delete = 0 " +
					" AND t.is_delete = 0 " +
					" AND t.course_id = :courseId ";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("courseId", courseId);
		return planDao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
	}

	@Override
	public List getMicroCourseList(Integer courseId) {
		String sql= " SELECT " +
					"	*,grade_name as courseName " +
					" FROM " +
					"	oe_course t " +
					" WHERE t.course_type = 1 and t.is_delete = 0 and  t.`status` = 1 and " +
					"	EXISTS ( " +
					"		SELECT " +
					"			1 " +
					"		FROM " +
					"			oe_course t2 " +
					"		WHERE " +
					"			t2.menu_id = t.menu_id " +
					"		AND t2.id = :courseId ) ";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("courseId", courseId);
		List<CourseVo> voList=dao.findEntitiesByJdbc(CourseVo.class, sql, params);
		return voList;
	}
}
