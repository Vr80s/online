package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.OnlineWebService;
import com.xczh.consumer.market.utils.JdbcUtil;

import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OnlineWebServiceImpl extends BasicSimpleDao implements OnlineWebService {
	
	   /**
	    * 免费课程 
	    *   用户报名时，将课程下所有视频插入用户视频中间表中
	    *   和输入完密码后，都需要这样搞的啊。
	    *     
	    * @param courseId 课程id
	    * @return
	    * @throws SQLException 
	    */
	   @Override
	   public  void saveEntryVideo(Integer  courseId, OnlineUser u) throws SQLException{
	        //OnlineUser u =  (OnlineUser) request.getSession().getAttribute("_user_");

		    Map<String,Object> paramMap = new HashMap<>();
	        paramMap.put("courseId",courseId);
	        paramMap.put("userId",u.getId());
	        paramMap.put("loginName",u.getLoginName());

    	   if(this.getLiveUserCourse(courseId,u.getId())){
  	           return;
  	       };
	       String sql="";
	       //写用户报名信息表，如果有就不写了
	       String apply_id = UUID.randomUUID().toString().replace("-", "");
	       sql = "select id from oe_apply where user_id=? ";
	       Object [] params ={u.getId()};
	       List<Map<String, Object>> applies = this.query(JdbcUtil.getCurrentConnection(),sql.toString(),new MapListHandler(),params);
	       
	       if (applies.size() > 0) {
	           apply_id = applies.get(0).get("id").toString();
	       } else {
	           sql = "insert into oe_apply(id,user_id,create_time,is_delete,create_person) "
	                   /*+ " values ("+apply_id+","+u.getId()+",now(),0,"+u.getName()+")";*/
	        		   + " values (?,?,now(),0,?)";
	           Object []  ps = {apply_id,u.getId(),u.getName()};
	           this.update(JdbcUtil.getCurrentConnection(), sql,ps);
	       }
	       //写用户报名中间表
	       String id = UUID.randomUUID().toString().replace("-", "");
	       
	       sql = "select (ifnull(max(cast(student_number as signed)),'0'))+1 as allCount from apply_r_grade_course where grade_id=-1";
	       Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql,new MapHandler());
	       
	       //报名此班级的学生人数
		   Double no = 0d;
			if(map!=null && map.size()>0){
				Object allCount = map.get("allCount");
				no = Double.valueOf(allCount.toString());
			}
	       String sno = no < 10 ? "00"+no : (no < 100 ? "0"+no : no.toString());
	       sql = "insert into apply_r_grade_course (id,course_id,grade_id,apply_id,is_payment,create_person,user_id,create_time,cost,student_number)"
	               + " values(?,?,-1,?,0,?,?,now(),0,?)";
	       Object [] ops = {id,courseId,apply_id,u.getName(),u.getId(),sno};
	       this.update(JdbcUtil.getCurrentConnection(), sql,ops);

	   }
	
	   /**
	     * 查看当前登录用户是否购买过当前课程 (音频和视频)
	     * @param courseId 课程id
	     * @param userId
	     * @return
	     * @throws SQLException 
	     */
	    @Override
	    public List<Map<String, Object>> getUserCourse(Integer courseId,String userId) throws SQLException {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT urv.course_id ");
	        sql.append(" from user_r_video urv ");
	        sql.append(" where urv.is_delete=0 and urv.course_id =? and urv.user_id= ?  limit 1");
	        Object [] params = {courseId,userId};
 	        return this.query(JdbcUtil.getCurrentConnection(),sql.toString(),new MapListHandler(),params);
	    }
	    
	    /**
	     * 查看当前登录用户是否购买过当前直播(直播)
	     * @param courseId 课程id
	     * @param userId
	     * @return
	     * @throws SQLException 
	     */
	    @Override
	    public Boolean getLiveUserCourse(Integer courseId,String userId) throws SQLException {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT argc.course_id ");
	        sql.append(" from apply_r_grade_course argc ");
	        sql.append(" where argc.is_delete=0 and argc.course_id =? and argc.user_id= ? limit 1");
	        Object [] params = {courseId,userId};
	        List<Map<String,Object>>  list =  this.query(JdbcUtil.getCurrentConnection(),sql.toString(),new MapListHandler(),params);
	        if(list!=null && list.size()>0){
	        	return true;
	        }
 	        return false;
	    }
	    
	    
	    @Override
	    public Boolean getLiveUserCourseAndIsFree(Integer courseId,String userId) throws SQLException {
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT argc.course_id ");
	        sql.append(" from apply_r_grade_course argc ");
	        sql.append(" where argc.is_delete=0 and argc.course_id =? and argc.user_id= ? and args.order_no is not null limit 1");
	        Object [] params = {courseId,userId};
	        List<Map<String,Object>>  list =  this.query(JdbcUtil.getCurrentConnection(),sql.toString(),new MapListHandler(),params);
	        if(list!=null && list.size()>0){
	        	return true;
	        }
 	        return false;
	    }
	    
	    
}
