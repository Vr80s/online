package com.xczhihui.bxg.online.manager.cloudClass.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.common.util.DateUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.LiveAppealInfo;
import com.xczhihui.bxg.online.common.domain.LiveExamineInfo;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LiveAppealInfoVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LiveExamineInfoVo;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import com.xczhihui.bxg.online.manager.utils.CountUtils;
import com.xczhihui.bxg.online.manager.utils.Group;

@Repository("examineCourseDao")
public class ExamineCourseDao extends HibernateDao<Course>{

	
//	@Value("${rate}")
//	private double rate;
	
	public Page<CourseVo> findCloudClassCoursePage(CourseVo courseVo,
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
	 	//StringBuilder sql =new StringBuilder("select c.*,c.grade_name as courseName ,l.name as lecturerName ,m.name as menuName from oe_course c,oe_menu m,oe_lecturer l where c.menu_id=m.id and c.lecturer_id = l.id and c.is_delete=0 and c.type=1");
		/**
		 * 2017-08-13 杨宣修改
		 * 讲师角色切换  当前  --用户角色
		 * 
		 * oe_course_mobile 从这个里面判断是否存在课程详情啊
		 */
		StringBuilder sql =new StringBuilder("select c.*,c.grade_name as courseName ,ou.name as lecturerName ,m.name as menuName,c.`course_pwd` coursePwd "
				+ "from oe_course c,oe_menu m,oe_user ou "
				+ "where c.menu_id=m.id and c.user_lecturer_id = ou.id and c.is_delete=0 and c.type=1 ");
	 	
	 	if(courseVo.getCourseName() != null){
	 		paramMap.put("courseName", "%"+courseVo.getCourseName()+"%");
	 		sql.append(" and c.grade_name like :courseName ");
	 	}
		/*if(courseVo.getLecturerName() != null){
	 		paramMap.put("lecturerName", "%"+courseVo.getLecturerName()+"%");
	 		sql.append(" and l.name like :lecturerName ");
	 	}*/

	 	if(courseVo.getLecturerName() != null){
	 		paramMap.put("lecturerName", "%"+courseVo.getLecturerName()+"%");
	 		sql.append(" and ou.name like :lecturerName ");
	 	}
	 	
	 	if(courseVo.getMenuId() != null){
	 		paramMap.put("menuId", courseVo.getMenuId());
	 		sql.append(" and c.menu_id = :menuId ");
	 	}
	 	if(courseVo.getStatus() != null){
	 		paramMap.put("status", courseVo.getStatus());
	 		sql.append(" and c.status = :status ");
	 	}
	 	
	 	 if(courseVo.getStartTime()!=null){
	            sql.append(" and c.start_time >= :startTime ");
	            paramMap.put("startTime",courseVo.getStartTime());
	        }
	        if(courseVo.getEndTime()!=null){
	            sql.append(" and c.end_time <= :endTime ");
	            paramMap.put("endTime",courseVo.getEndTime());
	        }
	 	sql.append(" order by c.status desc, c.sort desc");
 		
		return this.findPageBySQL(sql.toString(), paramMap, CourseVo.class, pageNumber, pageSize);
	}


	public Page<LiveExamineInfoVo> findCloudClassCoursePage(LiveExamineInfoVo liveExamineInfoVo, 
			int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		
		StringBuilder sql =new StringBuilder("select le.*,ou.name as lecturerName ,m.name as menuName,"
				+ " (select u.login_name from user as u where u.id = le.audit_person ) as auditPersonStr"
				+ " from live_examine_info le,oe_menu m,oe_user ou  "
				+ " where le.type=m.id and le.user_id = ou.id ");
	 	if(liveExamineInfoVo.getS_startTime() != null){
	 		 sql.append(" and DATE_FORMAT(le.create_time,'%Y-%m-%d') >=:startTime");
	            paramMap.put("startTime", liveExamineInfoVo.getS_startTime());
	 	}
		if(liveExamineInfoVo.getS_endTime() != null){
			sql.append(" and DATE_FORMAT(le.create_time,'%Y-%m-%d') <=:stopTime");
        	paramMap.put("stopTime", liveExamineInfoVo.getS_endTime());
	 	}
	 	if(liveExamineInfoVo.getExamineStatus() != null){
	 		paramMap.put("status", liveExamineInfoVo.getExamineStatus());
	 		sql.append(" and le.examine_status = :status ");
	 	}
	 	
	 	if(liveExamineInfoVo.getType() != null){
	 		paramMap.put("type", liveExamineInfoVo.getType());
	 		sql.append(" and le.see_mode = :type ");
	 	}
	 	
	 	if(liveExamineInfoVo.getLecturerName() != null){
	 		paramMap.put("title", "%"+liveExamineInfoVo.getLecturerName()+"%" );
	 		paramMap.put("gradeName", "%" +liveExamineInfoVo.getLecturerName()+"%");
	 		sql.append(" and (ou.name like :title or le.title like :gradeName)");
	 	}
	 	
	 	if(liveExamineInfoVo.getIsDelete() != null){
	 		paramMap.put("is_delete", liveExamineInfoVo.getIsDelete() ? 1 : 0 );
	 		sql.append(" and le.is_delete = :is_delete ");
	 	}
	 	
	 	sql.append(" order by le.create_time desc ");
	 	
	 	System.out.println("sql.toString():"+sql.toString());
	 	Page<LiveExamineInfoVo> pageList =this.findPageBySQL(sql.toString(), paramMap, LiveExamineInfoVo.class, pageNumber, pageSize);
		
 	/*	for (LiveExamineInfoVo lv : pageList.getItems()) {
 			if(lv.getPrice()!=null){
 				BigDecimal bd = lv.getPrice();
 	 			BigDecimal bd_half_up = bd.setScale(2,RoundingMode.HALF_UP);
 	 			lv.setPrice(bd_half_up);
 				BigDecimal bd = lv.getPrice();
 				lv.setPrice(CountUtils.div(bd,rate,4));
 			}
		}*/
	    return pageList;
	}

	
	public static void main(String[] args) {
		
		System.out.println(CountUtils.div(new BigDecimal(""+9),1000d,4));
	}
	
	
	public void updateCourseDirectId(Course course) {
		String sql="update oe_course set direct_id = :direct_id where  id = :id";
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", course.getId());
		params.put("direct_id", course.getDirectId());
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}
	
	public LiveExamineInfo findExamineById(Integer id) {
		 String hqlPre="from Course where  isDelete=0 and id = ?";
	     LiveExamineInfo lei = this.findByHQLOne(hqlPre,new Object[] {id});
	     return lei;
	}

	
	public List<LiveAppealInfo> findeAppealInfosByExamineId(String examineId) {
		
		 String sql="select la.*,ou.name as name from live_appeal as la,user as ou  where la.reviewer_person = ou.id and"
		 		+ "  la.examine_id = :examineId and la.is_delete = 0 ";
		 Map<String,Object> paramMap=new HashMap<String,Object>();
		 paramMap.put("examineId", examineId);
		 return this.getNamedParameterJdbcTemplate().query(sql.toString(),paramMap,BeanPropertyRowMapper.newInstance(LiveAppealInfo.class));
	}

	
	
	public List<LiveAppealInfoVo> findeAppealInfoVosByExamineId(String examineId) {
		
		 String sql="select la.*,ou.name as name from live_appeal as la,user as ou  where la.reviewer_person = ou.id "
		 		+ " and  la.examine_id = :examineId and  la.is_delete = 0 ";
		 Map<String,Object> paramMap=new HashMap<String,Object>();
		 paramMap.put("examineId", examineId);
		 return this.getNamedParameterJdbcTemplate().query(sql.toString(),paramMap,BeanPropertyRowMapper.newInstance(LiveAppealInfoVo.class));
	}
	public Page<LiveExamineInfoVo> findCloudClassCoursePage1(
			LiveExamineInfoVo liveExamineInfoVo, int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
				Map<String,Object> paramMap=new HashMap<String,Object>();
				/**
				 * 2017-08-13 杨宣修改
				 * 讲师角色切换  当前  --用户角色
				 * 
				 * oe_course_mobile 从这个里面判断是否存在课程详情啊
				 */
				//主播、驳回原因、申述时间、申述理由、申述详情、审核人、审核时间
				
				StringBuilder sql =new StringBuilder(); //live_appeal  live_examine_info
			 	sql.append("select le.*,la.id as appId,la.appeal_reason as appealReason,la.is_delete as ssisDelete,"
			 			+ "(select u.login_name from user as u where u.id = la.reviewer_person ) as auditPersonStr,la.reviewer_time as reviewerTime,"
			 			+ "la.against_reason as againstReason,la.appeal_time  as appealTime,"
			 			+ " ou.name as lecturerName,m.name as menuName "
			 			+ "from live_appeal as la,live_examine_info as le ,oe_user as ou,oe_menu as m where ");
			 	sql.append("  la.examine_id = le.id and le.user_id = ou.id and le.type=m.id  ");
			 	if(liveExamineInfoVo.getS_startTime() != null){
			 		 sql.append(" and DATE_FORMAT(le.create_time,'%Y-%m-%d') >=:startTime");
			            paramMap.put("startTime", liveExamineInfoVo.getStartTime());
			 	}
				if(liveExamineInfoVo.getS_endTime() != null){
					sql.append(" and DATE_FORMAT(le.create_time,'%Y-%m-%d') <=:stopTime");
		        	paramMap.put("stopTime", liveExamineInfoVo.getS_endTime());
			 	}
			 	if(liveExamineInfoVo.getExamineStatus() != null){
			 		paramMap.put("status", liveExamineInfoVo.getExamineStatus());
			 		sql.append(" and le.examine_status = :status ");
			 	}
			 	if(liveExamineInfoVo.getLecturerName() != null){
			 		paramMap.put("title", "%"+liveExamineInfoVo.getLecturerName()+"%" );
			 		paramMap.put("gradeName", "%" +liveExamineInfoVo.getLecturerName()+"%");
			 		sql.append(" and (ou.name like :title or le.title like :gradeName)");
			 	}
			 	
				if(liveExamineInfoVo.getSsisDelete() != null){
			 		paramMap.put("is_delete", liveExamineInfoVo.getSsisDelete() ? 1 : 0 );
			 		sql.append(" and la.is_delete = :is_delete ");
			 	}
			 	
			 	System.out.println("sql.toString():"+sql.toString());
			 	System.out.println(pageNumber+"========"+pageSize);
			 	Page<LiveExamineInfoVo> pageList =  this.findPageBySQL(sql.toString(), paramMap, LiveExamineInfoVo.class, pageNumber, pageSize);
		
				return pageList;
	}
	
	
	

}
