package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.wxpay.entity.OeWatchHistory;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class OnlineWatchHistoryMapper extends BasicSimpleDao{

	public List<OeWatchHistory> getOeWatchHistotyList(int pageNumber, int pageSize, String userId, String type) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("");
		//课程id一样，并且
		/*sql.append("select user_id as userId,lecturer_id as lecturerId,course_id as courseId,smallimg_path as smallimgPath,grade_name as gradeName,"
				+ "lecturer_name as lecturerName,teacher_head_img as teacherHeadImg,watch_time as watchTime,"
				+ "is_free as isFree,is_approve as isApprove,start_time as startTime,end_time as endTime,type as type"
				+ " from oe_watch_history where user_id = ? and type = ? order by watch_time desc ");*/
		/*sql.append("select owh.user_id as userId,owh.lecturer_id as lecturerId,owh.course_id as courseId,");
		sql.append("oc.smallimg_path as smallimgPath,oc.grade_name as gradeName,");
		sql.append("ou.name as lecturerName,ou.small_head_photo as teacherHeadImg,owh.watch_time as watchTime,");
		sql.append("oc.is_free as isFree,oc.start_time as startTime,oc.end_time as endTime, owh.type as type  ");
		sql.append("from oe_watch_history as owh,oe_user as ou,oe_course as oc  ");
		sql.append("where owh.lecturer_id = ou.id and owh.course_id = oc.id  ");
		sql.append("and owh.user_id = ? and owh.type = ? order by owh.watch_time desc");*/
		if(type.equals("1")){
			sql.append("select owh.user_id as userId,owh.lecturer_id as lecturerId,owh.course_id as courseId,");
			sql.append("oc.smallimg_path as smallimgPath,oc.grade_name as gradeName,");
			sql.append("ou.name as lecturerName,ou.small_head_photo as teacherHeadImg,owh.watch_time as watchTime,");
			sql.append("oc.is_free as isFree,oc.start_time as startTime,oc.end_time as endTime, owh.type as type  ");
			sql.append("from oe_watch_history as owh,oe_user as ou,oe_course as oc  ");
			sql.append("where owh.lecturer_id = ou.id and owh.course_id = oc.id  ");
			sql.append("and owh.user_id = ? and owh.type = ? order by owh.watch_time desc");
		}else{
			sql.append("select owh.user_id as userId,owh.lecturer_id as lecturerId,owh.course_id as courseId,");
			sql.append("ocm.img_url as smallimgPath,oc.grade_name as gradeName,");
			sql.append("ou.name as lecturerName,ou.small_head_photo as teacherHeadImg,owh.watch_time as watchTime,");
			sql.append("oc.is_free as isFree,oc.start_time as startTime,oc.end_time as endTime, owh.type as type  ");
			sql.append("from oe_watch_history as owh,oe_user as ou,oe_course as oc,oe_course_mobile as ocm  ");
			sql.append("where owh.lecturer_id = ou.id and owh.course_id = oc.id and ocm.course_id =oc.id  ");
			sql.append("and owh.user_id = ? and owh.type = ? order by owh.watch_time desc");
		}
		System.out.println(sql.toString());
		Object[] params = {userId,type};
		return super.queryPage(JdbcUtil.getCurrentConnection(), sql.toString(),pageNumber,pageSize,OeWatchHistory.class,params);
	}

	public Integer findOnlineWatchHistory(OnlineUser ou, String courseId) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(*) as allCount from oe_watch_history where user_id =? and course_id = ?");    
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new MapHandler(),ou.getId(),courseId);
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			Object allCount = map.get("allCount");
			all_count = Integer.parseInt(allCount.toString());
		}
		return all_count;
	}
	
	
	public void updateOnlineWatchHistory(OnlineUser ou, String courseId) throws SQLException{
		StringBuilder sql = new StringBuilder();
		sql.append("update oe_watch_history set watch_time =? where user_id = ? and course_id =?	");
		this.update(JdbcUtil.getCurrentConnection(),sql.toString(),new Date(),ou.getId(),courseId);
	}
	
	public void saveOnlineWatchHistory1(OnlineUser ou, String courseId, String type) throws SQLException {
		// TODO Auto-generated method stub
		
		if("1".equals(type)){ //直播啦
			//先根据课程id得到讲师信息
			String strSql= "select ou.id as userId,ou.name as name,ou.small_head_photo as headImg,oc.smallimg_path as smallImgPath,"
					+ "oc.grade_name as gradeName,if(oc.is_free = 0,1,if(oc.course_pwd is null,0,2)) as watchState,"
					+ "oc.type as type,oc.multimedia_type as multimediaType,oc.start_time as startTime,oc.end_time as endTime,"
					+ "oc.direct_Id as directId "
					+ "from oe_user as ou ,oe_course as oc where ou.id = oc.user_lecturer_id and oc.id = ?";
			Object[] params = {courseId};
			CourseLecturVo cv = this.query(JdbcUtil.getCurrentConnection(),strSql,new BeanHandler<>(CourseLecturVo.class),params);
			//查找出这个课程
			StringBuilder sql = new StringBuilder();
			sql.append("insert into oe_watch_history 		");
			sql.append("(user_id,lecturer_id,course_id,smallimg_path,grade_name, ");
			sql.append("lecturer_name,teacher_head_img,watch_time,type,is_free,is_approve,start_time,end_time,direct_Id) ");
			sql.append("values                              ");
			sql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?)  ");
			this.update(JdbcUtil.getCurrentConnection(),sql.toString(), 
					ou.getUserId(),cv.getUserId(),courseId,cv.getSmallImgPath(),
					cv.getGradeName(),cv.getName(),cv.getHeadImg(),new Date(),
					type,cv.getIsFree(),cv.getIsApprove(),cv.getStartTime(),cv.getEndTime(),cv.getDirectId());
		}else if("2".equals(type) || "3".equals(type)){ //点播和音频
			String strSql= "select ou.id as userId,ou.name as name,ou.small_head_photo as headImg,ocm.img_url as smallImgPath,"
					+ "oc.grade_name as gradeName,if(oc.is_free = 0,1,if(oc.course_pwd is null,0,2)) as watchState,"
					+ "oc.type as type,oc.multimedia_type as multimediaType "
					+ "from oe_user as ou ,oe_course as oc,oe_course_mobile ocm where "
					+ " oc.user_lecturer_id = ou.id and oc.id=ocm.course_id and oc.is_delete=0 and oc.status=1 and oc.id = ?";
			
			Object[] params = {courseId};
			CourseLecturVo cv = this.query(JdbcUtil.getCurrentConnection(),strSql,new BeanHandler<>(CourseLecturVo.class),params);
			//查找出这个课程
			StringBuilder sql = new StringBuilder();
			sql.append("insert into oe_watch_history 		");
			sql.append("(user_id,lecturer_id,course_id,smallimg_path,grade_name, ");
			sql.append("lecturer_name,teacher_head_img,watch_time,type,is_free,is_approve) ");
			sql.append("values                              ");
			sql.append("(?,?,?,?,?,?,?,?,?,?,?)  ");
			this.update(JdbcUtil.getCurrentConnection(),sql.toString(), 
					ou.getUserId(),cv.getUserId(),courseId,cv.getSmallImgPath(),
					cv.getGradeName(),cv.getName(),cv.getHeadImg(),new Date(),
					type,cv.getIsFree(),cv.getIsApprove());
		}
		

//		Object[] params = {courseId};
//		CourseLecturVo cv = this.query(JdbcUtil.getCurrentConnection(),strSql,new BeanHandler<>(CourseLecturVo.class),params);
	}
	
	
	
	public void saveOnlineWatchHistory(OnlineUser ou, String courseId) throws SQLException {
		// TODO Auto-generated method stub
		//先根据课程id得到讲师信息
		String strSql= "select ou.id as userId,ou.name as name,ou.small_head_photo as headImg,oc.smallimg_path as smallImgPath,"
				+ "oc.grade_name as gradeName,if(oc.is_free = 0,1,if(oc.course_pwd is null,0,2)) as watchState,"
				+ "oc.type as type,oc.multimedia_type as multimediaType "
				+ "from oe_user as ou ,oe_course as oc where ou.id = oc.user_lecturer_id and oc.id = ?";
		Object[] params = {courseId};
		CourseLecturVo cv = this.query(JdbcUtil.getCurrentConnection(),strSql,new BeanHandler<>(CourseLecturVo.class),params);
		
	    String type = "1";
		if(cv!=null && null != cv.getType() &&  cv.getType() ==1){
			type ="1";
		}
		if(cv!=null && null == cv.getType() && cv.getMultimediaType() ==1){//点播
			
			type ="2";
		}
		if(cv!=null && null == cv.getType() && cv.getMultimediaType() ==2){//音频
			type ="3";
		}
		//查找出这个课程
		StringBuilder sql = new StringBuilder();
		sql.append("insert into oe_watch_history 		");
		sql.append("(user_id,lecturer_id,course_id,smallimg_path,grade_name, ");
		sql.append("lecturer_name,teacher_head_img,watch_time,type,is_free,is_approve) ");
		sql.append("values                              ");
		sql.append("(?,?,?,?,?,?,?,?,?,?,?)  ");
		this.update(JdbcUtil.getCurrentConnection(),sql.toString(), 
				ou.getUserId(),cv.getUserId(),courseId,cv.getSmallImgPath(),
				cv.getGradeName(),cv.getName(),cv.getHeadImg(),new Date(),type,cv.getIsFree(),cv.getIsApprove());	
	}
	public void deleteOnlineWatchHistoryByUserIdAndType(String userId,
			String type) throws SQLException {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("delete from oe_watch_history where user_id = ? and type = ?");
		Object[] params = {userId,type};
		super.update(JdbcUtil.getCurrentConnection(),sql.toString(),params);	
	}
}
