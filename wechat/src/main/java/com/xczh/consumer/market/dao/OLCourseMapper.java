package com.xczh.consumer.market.dao;

import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.*;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OLCourseMapper extends BasicSimpleDao {

	@Autowired
	private OnlineCourseMapper courseMapper;
	
	/***
	 * 学科分类列表
	 * @return
	 * @throws SQLException
	 */
	public List<WxcpOeMenuVo> categoryList() throws SQLException{
		StringBuffer sql = new StringBuffer("");
		sql.append(" select id,name from oe_menu where is_delete=0 and status=1 order by sort asc");
		return super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanListHandler<>(WxcpOeMenuVo.class));
	}
	/***
	 * 新的原型接口 --- 学科分类列表
	 * @return
	 * @throws SQLException
	 */
	public  List<Map<String, Object>> categoryXCList() throws SQLException{
		StringBuffer sql = new StringBuffer("");
		sql.append(" select id as menu_id,name from score_type where is_delete=0 and status=1 order by sort asc");
		return this.query(JdbcUtil.getCurrentConnection(),sql.toString(),new MapListHandler());
	}
	/***
	 * 学科分类下课程列表
	 * @param menu_id
	 * @param number
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public List<WxcpCourseVo> courseCategoryList(int menu_id, int number, int pageSize) throws SQLException{
		menu_id = menu_id == 1 ? 0 : menu_id;//此处后期可以能修改 ----20170725---yuruixin
		
		// 课程名称，主讲人,主讲人头像，课时，观看人数，视频缩略图，课程Id,视频Id 
		if(menu_id==0){
			StringBuffer all = new StringBuffer("");
			all.append(" select oc.id as course_id,oc.grade_name as course_name,oc.original_cost as original_cost,oc.current_price as current_price,oc.course_length as course_length,ocm.img_url as smallimg_path,oc.learnd_count as learnd_count  ");
			all.append(" from oe_course oc, oe_course_mobile ocm ");
			all.append(" where oc.is_delete=0 and oc.status=1  and oc.id=ocm.course_id ");
			all.append(" order by oc.sort asc");
			all.append(" limit " + number+","+pageSize);
			return super.query(JdbcUtil.getCurrentConnection(), all.toString(), new BeanListHandler<>(WxcpCourseVo.class));
		}else{
			StringBuffer sql = new StringBuffer("");
			sql.append(" select oc.id as course_id,oc.grade_name as course_name,oc.original_cost as original_cost,oc.current_price as current_price,oc.course_length as course_length,ocm.img_url as smallimg_path,oc.learnd_count as learnd_count  ");
			sql.append(" from oe_course oc ,oe_course_mobile ocm ");
			sql.append(" where oc.is_delete=0 and oc.status=1  and oc.id=ocm.course_id and oc.menu_id=? ");
			sql.append(" order by oc.sort asc");
			sql.append(" limit " + number+","+pageSize);
		
			Object[] params = {menu_id};
			return super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanListHandler<>(WxcpCourseVo.class),params);
		}
	}
	
	/***
	 * 学科分类下课程列表   --- 原来人家的。在此基础上
	 * @param menu_id
	 * @param number
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public List<CourseLecturVo> courseCategoryXCList(String menu_id, int number, int pageSize,
                                                     Integer multimedia_type) throws SQLException{
		if("0".equals(menu_id)){
			StringBuffer all = new StringBuffer("");
			all.append(" select oc.id,oc.grade_name as gradeName,oc.original_cost as originalCost,oc.current_price as currentPrice,"
					+ "ocm.img_url as smallImgPath,ou.small_head_photo as headImg,ou.name as name,");
			
			all.append(" oc.course_length as courseLength, ");//课程时长 
			
			all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount,");
			all.append(" if(oc.course_pwd is not null,2,if(oc.is_free =0,1,0)) as watchState, ");//判断是否要输入密码
			all.append(" IF(oc.type =1,1,if(oc.multimedia_type=1,2,3)) as type ");
			all.append(" from oe_course oc, oe_course_mobile ocm,oe_user ou ");
			all.append(" where oc.user_lecturer_id = ou.id and oc.id=ocm.course_id  and oc.is_delete=0 and oc.status=1  ");//and oc.is_free=0 oc.course_type=1 and
			all.append(" and oc.multimedia_type =? ");
			all.append(" order by oc.sort desc");

			Object[] params = {multimedia_type};
			
			return super.queryPage(JdbcUtil.getCurrentConnection(),all.toString(),number,pageSize,CourseLecturVo.class,params);
			//return super.query(JdbcUtil.getCurrentConnection(), all.toString(), new BeanListHandler<>(CourseLecturVo.class),params);
		}else{
			StringBuffer sql = new StringBuffer("");
			sql.append(" select oc.id,oc.grade_name as gradeName,oc.original_cost as originalCost,oc.current_price as currentPrice,"
					+ "ocm.img_url as smallImgPath,ou.small_head_photo as headImg,ou.name as name, ");
			
			//sql.append(" (select ROUND(sum(time_to_sec(CONCAT('00:',video_time)))/3600,1) from  oe_video where course_id = oc.id) as courseLength, ");//课程时长 
			sql.append(" oc.course_length as courseLength, ");//课程时长
			
			sql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount,");
			sql.append(" if(oc.course_pwd is not null,2,if(oc.is_free =0,1,0)) as watchState, ");//判断是否要输入密码
			sql.append(" IF(oc.type = 1,1,if(oc.multimedia_type=1,2,3)) as type ");
			sql.append(" from oe_course oc, oe_course_mobile ocm,oe_user ou ");
			sql.append(" where oc.user_lecturer_id = ou.id and oc.id=ocm.course_id and oc.is_delete=0 and oc.status=1  and oc.menu_id=? ");//and oc.is_free=0 oc.course_type=1 and
			sql.append(" and oc.multimedia_type =? ");
			sql.append(" order by oc.sort desc");
			//sql.append(" limit " + number+","+pageSize);
			Object[] params = {menu_id,multimedia_type};
			
			return super.queryPage(JdbcUtil.getCurrentConnection(),sql.toString(),number,pageSize,CourseLecturVo.class,params);
			//return super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanListHandler<>(CourseLecturVo.class),params);
		}
	}
	/***
	 * 直播搜索页面的接口调整
	 * @param number
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public List<CourseLecturVo> courseCategoryXCList1(int number, int pageSize,
                                                      String queryParam) throws SQLException{
		
		StringBuffer all = new StringBuffer("");
		all.append(" select oc.id,oc.grade_name as gradeName,oc.original_cost as originalCost,oc.current_price as currentPrice,"
				+ "ocm.img_url as smallImgPath,"
				+ "ou.small_head_photo as headImg,ou.name as name,");
		all.append(" if(oc.is_free = 0,1,if(oc.course_pwd is null,0,2)) as watchState, ");//观看状态
		all.append(" IF(oc.type = 1,1,if(oc.multimedia_type=1,2,3)) as type, ");//观看状态
		
		all.append(" oc.course_length as courseLength, ");//课程时长 
		
		all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount,");
		all.append(" oc.live_status as  lineState ");
		all.append(" from oe_course oc, oe_course_mobile ocm,oe_user ou ");
		all.append(" where oc.user_lecturer_id = ou.id and oc.id=ocm.course_id  and oc.is_delete=0 and oc.status=1 ");//and oc.is_free=0 oc.course_type=1 and
		//房间编号/主播/课程
		if(queryParam!=null && !"".equals(queryParam) && !"null".equals(queryParam)){
			all.append(" and ("); 
			/*all.append(" ou.room_number like '%"+ queryParam + "%'"); 
			all.append(" or "); */
			all.append(" ou.name like '%"+ queryParam + "%'"); 
			all.append(" or "); 
			all.append(" oc.grade_name like '%"+ queryParam + "%')"); 
		}
		all.append(" order by learndCount desc");
		return super.queryPage(JdbcUtil.getCurrentConnection(),all.toString(),number,pageSize,CourseLecturVo.class);
	}
	
	/**
	 * xc 点播详情页面
	 * Description：
	 * @param course_id
	 * @return
	 * @return CourseLecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @throws SQLException 
	 */
	public CourseLecturVo bunchDetailsByCourseId(int course_id) throws SQLException {
		StringBuffer all = new StringBuffer("");
		all.append(" select oc.id,oc.grade_name as gradeName,"
				+ "ocm.img_url as smallImgPath,ocm.description as description,multimedia_type as multimediaType,"
				+ "ou.small_head_photo as headImg,ou.name as name,ou.id as userId,ou.room_number as roomNumber,direct_id as directId," 
				+ "oc.original_cost as originalCost,oc.current_price as currentPrice,");
		all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount,");
		
		//all.append(" (select ROUND(sum(time_to_sec(CONCAT('00:',video_time)))/3600,1) from  oe_video where course_id = oc.id) as courseLength, ");//课程时长 
		all.append(" oc.course_length  as courseLength, ");//课程时长 
		
		
		all.append("if(oc.course_pwd is not null,2,if(oc.is_free =0,1,0)) as watchState,");//判断是否要输入密码
		all.append(" ocm.description as courseDescription  ");
		
		all.append(" from oe_course oc, oe_course_mobile ocm,oe_user ou ");
		all.append(" where oc.user_lecturer_id = ou.id and oc.id=ocm.course_id and oc.id =?  and oc.is_delete=0 and oc.status=1 ");
		Object[] params = {course_id};
		return  super.query(JdbcUtil.getCurrentConnection(), all.toString(), new BeanHandler<>(CourseLecturVo.class),params);
	}
	/***
	 * 课程详细信息
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	public CourseLecturVo courseDetail1(int course_id, String user_id) throws SQLException{
		CourseLecturVo vo1 = courseMapper.liveDetailsByCourseId(course_id);
		if(vo1!=null){
			return vo1;
		}else{
			CourseLecturVo vo2 = this.bunchDetailsByCourseId(course_id);
			return vo2;
		}
	}
	public boolean queryOrderIsShop(int course_id,String user_id) throws SQLException{
		boolean falg = false;
		StringBuffer isbuy = new StringBuffer("");
		isbuy.append(" select oo.id from oe_order oo,oe_order_detail ood where oo.id=ood.order_id and oo.order_status=1 and oo.user_id=? and ood.course_id=?");
		Map<String, Object> map =super.query(JdbcUtil.getCurrentConnection(), isbuy.toString(), new MapHandler(),user_id,course_id);
		if(map!=null && map.size()>0){
			Object o_id = map.get("id");
			if(o_id!=null){
				falg =true;
			}else{
				falg =false;
			}
		}else{                                                                                        
			falg =false;
		}
		return falg;
	}
	
	
	
	
	/***
	 * 课程详细信息  -- 在原来的基础上进行修改
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	public WxcpCourseVo courseXCDetail1(int course_id, String user_id) throws SQLException{
		StringBuffer sql = new StringBuffer("");
		sql.append(" select oc.id as course_id,oc.grade_name as course_name,oc.original_cost as original_cost,group_concat(ot.name) as teacher_name,");//group_concat(ot.name) 列转行
		sql.append("oc.current_price as current_price,oc.course_length as course_length,ocm.img_url as smallimg_path ");
		sql.append(" from oe_course oc,course_r_lecturer ct,oe_lecturer ot,oe_course_mobile ocm ");
		sql.append(" where oc.id=ct.course_id and ct.lecturer_id=ot.id and oc.id=ocm.course_id and ot.role_type='1' and ct.course_id=? and ocm.course_id=?");
		sql.append(" group by course_id order by ct.create_time asc");
		
		Object[] params = {course_id,course_id};
		WxcpCourseVo vo1 = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(WxcpCourseVo.class),params);
		if(vo1!=null){
			if(user_id!=null && !"".equals(user_id) && (!"null".equals(user_id))){
				StringBuffer isbuy = new StringBuffer("");
				isbuy.append(" select oo.id from oe_order oo,oe_order_detail ood where oo.id=ood.order_id and oo.order_status=1 and oo.user_id=? and ood.course_id=?");
				Map<String, Object> map =super.query(JdbcUtil.getCurrentConnection(), isbuy.toString(), new MapHandler(),user_id,course_id);
				if(map!=null && map.size()>0){
					Object o_id = map.get("id");
					if(o_id!=null){
						vo1.setIs_buy(true);
					}else{
						vo1.setIs_buy(false);
					}
				}else{                                                                                        
					vo1.setIs_buy(false);
				}
				
			}else{
				vo1.setIs_buy(false);
			}
			String tname = vo1.getTeacher_name();
			if(tname!=null && !"".equals(tname)){
				String[] tnames = tname.split(",");
				if(tnames!=null && tnames.length>0){
					if(tnames.length==1){
						vo1.setTeacher_name(tnames[0]);
					}else{
						vo1.setTeacher_name(tnames[0]+"等");
					}
				}else{
					vo1.setTeacher_name("暂无讲师");
				}
			}else{
				vo1.setTeacher_name("暂无讲师");
			}
			return vo1;
		}else{
			StringBuffer sql2 = new StringBuffer(" select oc.id as course_id,oc.grade_name as course_name,oc.original_cost as original_cost,");
			sql2.append("oc.current_price as current_price,oc.course_length as course_length,ocm.img_url as smallimg_path,");
			sql2.append("oc.learnd_count as learnd_count from oe_course oc,oe_course_mobile ocm where oc.id=ocm.course_id and ocm.course_id=?");
			Object[] params2 = {course_id};
			WxcpCourseVo vo2 = super.query(JdbcUtil.getCurrentConnection(), sql2.toString(), new BeanHandler<>(WxcpCourseVo.class),params2);
			if(user_id!=null && !"".equals(user_id)){
				StringBuffer isbuy = new StringBuffer("");
				isbuy.append(" select oo.id from oe_order oo,oe_order_detail ood where oo.id=ood.order_id and oo.order_status=1 and oo.user_id=? and ood.course_id=?");
				Map<String, Object> map =super.query(JdbcUtil.getCurrentConnection(), isbuy.toString(), new MapHandler(),user_id,course_id);
				if(map!=null && map.size()>0){
					Object o_id = map.get("id");
					if(o_id!=null){
						vo2.setIs_buy(true);
					}else{
						vo2.setIs_buy(false);
					}
				}else{
					vo2.setIs_buy(false);
				}
				
			}else{
				vo2.setIs_buy(false);
			}
			return vo2;
		}
	}
	
	
	/***
	 * 课程详细信息
	 * @param course_id
	 *  1课程介绍， 2课程大纲
	 * @return
	 * @throws SQLException
	 */
	public WxcpCourseVo courseDetail2Tabs(int course_id) throws SQLException{
		StringBuffer sql = new StringBuffer("");
		sql.append(" select oc.id as course_id,ocm.description as summary,oc.course_outline as course_outline,");
		sql.append(" group_concat(ot.name) as teacher_name,ot.id as teacher_id,ot.description as teacher_decs,group_concat(ot.head_img) as teacher_head_img");//group_concat(ot.name) 列转行
		sql.append(" from oe_course oc,course_r_lecturer ct,oe_lecturer ot,oe_course_mobile ocm ");
		sql.append(" where oc.id=ct.course_id and ct.lecturer_id=ot.id and oc.id=ocm.course_id and ot.role_type='1' and ocm.course_id=? ");
		sql.append(" group by course_id order by ct.create_time asc");
		Object[] params = {course_id};
		WxcpCourseVo vo1 =super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(WxcpCourseVo.class),params);
		if(vo1!=null){
			String thead = vo1.getTeacher_head_img();
			if(thead!=null && !"".equals(thead)){
				vo1.setTeacher_head_img(thead.split(",")[0]);
			}
			String tname = vo1.getTeacher_name();
			if(tname!=null && !"".equals(tname)){
				String[] tnames = tname.split(",");
				if(tnames!=null && tnames.length>0){
					if(tnames.length==1){
						vo1.setTeacher_name(tnames[0]);
					}else{
						vo1.setTeacher_name(tnames[0]+"等");
					}
				}else{
					vo1.setTeacher_name("暂无讲师");
				}
			}else{
				vo1.setTeacher_name("暂无讲师");
			}
			return vo1;
		}else{
			StringBuffer sql2 = new StringBuffer("select oc.id as course_id,ocm.description as summary,oc.course_outline as course_outline ");
			sql2.append(" from oe_course oc,oe_course_mobile ocm where oc.id=ocm.course_id and ocm.course_id=?");
			Object[] params2 = {course_id};
			WxcpCourseVo vo2 =super.query(JdbcUtil.getCurrentConnection(), sql2.toString(), new BeanHandler<>(WxcpCourseVo.class),params2);
			return vo2;
		}
		
	}
	
	/***
	 * 订单课程详细信息
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	public WxcpCourseVo courseOrderDetail(int course_id) throws SQLException{
		StringBuffer sql = new StringBuffer("");
		sql.append(" select oc.id as course_id,oc.grade_name as course_name,");
		sql.append("oc.current_price as current_price,oc.course_length as course_length,");
		sql.append("group_concat(ot.name) as teacher_name ");//,group_concat(ot.id) as teacher_id //group_concat(ot.name) 列转行
		sql.append(" from oe_course oc,course_r_lecturer ct,oe_lecturer ot,oe_course_mobile ocm ");
		sql.append(" where oc.id=ct.course_id and ct.lecturer_id=ot.id and oc.id=ocm.course_id and ot.role_type='1' and ocm.course_id=? ");
		sql.append(" group by course_id order by ct.create_time asc");
		Object[] params = {course_id};
		WxcpCourseVo vo1 = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(WxcpCourseVo.class),params);
		if(vo1!=null){
			String courselength = vo1.getCourse_length();
			if(courselength!=null && !"".equals(courselength)){
				String[] cl = courselength.split("\\.");
				if(cl!=null && cl.length>0){
					if("00".equals(cl[1]) || "00"==cl[1]){
						vo1.setCourse_length(cl[0]);
					}else{
						if(cl[1].endsWith("0")){
							vo1.setCourse_length(courselength.substring(0, courselength.length()-1));
						}
					}
				}
			}
			String tname = vo1.getTeacher_name();
			if(tname!=null && !"".equals(tname)){
				String[] tnames = tname.split(",");
				if(tnames!=null && tnames.length>0){
					if(tnames.length==1){
						vo1.setTeacher_name(tnames[0]);
					}else{
						vo1.setTeacher_name(tnames[0]+"等");
					}
				}else{
					vo1.setTeacher_name("暂无讲师");
				}
			}else{
				vo1.setTeacher_name("暂无讲师");
			}
			return vo1;
		}else{
			StringBuffer sql2 = new StringBuffer(" select oc.id as course_id,oc.grade_name as course_name,oc.original_cost as original_cost,");
			sql2.append("oc.current_price as current_price,oc.course_length as course_length,oc.smallimg_path as smallimg_path,");
			sql2.append("oc.learnd_count as learnd_count from oe_course oc,oe_course_mobile ocm where oc.id=ocm.course_id and ocm.course_id=?");
			Object[] params2 = {course_id};
			WxcpCourseVo vo2 = super.query(JdbcUtil.getCurrentConnection(), sql2.toString(), new BeanHandler<>(WxcpCourseVo.class),params2);
			String courselength = vo2.getCourse_length();
			if(courselength!=null && !"".equals(courselength)){
				String[] cl = courselength.split("\\.");
				if(cl!=null && cl.length>0){
					if("00".equals(cl[1]) || "00"==cl[1]){
						vo2.setCourse_length(cl[0]);
					}else{
						if(cl[1].endsWith("0")){
							vo2.setCourse_length(courselength.substring(0, courselength.length()-1));
						}
					}
				}
			}
			return vo2;
		}
	}
	
	/***
	 * 课程试看
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	public List<ChapterVo> videoTrailers(int course_id) throws SQLException{
		//查课下所有知识点
		String sq = "select c.id as chapter_id,c.name as chapter_name from oe_chapter c "
				+ " where c.level=4 and c.course_id =? order by sort";
		List<ChapterVo> chapters =
				super.query(JdbcUtil.getCurrentConnection(),sq,new BeanListHandler<>(ChapterVo.class),course_id);
		//查课下所有试学视频
		sq = "select v.chapter_id,v.id as v_id,v.video_id as video_id,v.name as video_name from oe_video v "
				+ " where v.is_delete=0 and v.status=1 and v.is_try_learn=1 and v.course_id=? order by sort";
		List<VideoVo> videos =
				super.query(JdbcUtil.getCurrentConnection(),sq, new BeanListHandler<>(VideoVo.class),course_id);
		//循环组装
		for (ChapterVo chapter : chapters) {
			List<VideoVo> children = new ArrayList<VideoVo>();
			for (VideoVo video : videos) {
				if (chapter.getChapter_id().equals(video.getChapter_id())) {
					children.add(video);
				}
			}
			chapter.setVideos(children);
		}
		//删除空的知识点
		for (int i = 0; i < chapters.size(); i++) {
			if (chapters.get(i).getVideos().size() <= 0) {
				chapters.remove(i);
				i--;
			}
		}
		return chapters;
	}
	
	/***
	 * 个人中心 我的课程
	 * @param user_id
	 * @return
	 * @throws SQLException
	 */
	public List<MyCourseVo> myCourses(String user_id, int number, int pageSize) throws SQLException{
		String courseId ="191"; //Init.getProperty("bxg.course_id"); 刘涛注释
		StringBuffer sql = new StringBuffer(" ");
		sql.append("SELECT t1.course_id AS course_id,oc.grade_name AS course_name,ocm.img_url AS smallimg_path,");
		sql.append(" GROUP_CONCAT(ot.name) AS teacher_name,DATE_FORMAT(TIMESTAMPADD(YEAR,1,t1.create_time),'%Y-%m-%d') AS course_expiry_date");
		sql.append(" FROM (SELECT od.course_id,oo.create_time FROM oe_order oo,oe_order_detail od WHERE oo.user_id =? AND oo.id = od.order_id AND oo.order_status=1) AS t1");
		sql.append(" LEFT JOIN oe_course_mobile ocm ON ocm.course_id = t1.course_id");
		sql.append(" LEFT JOIN oe_course oc ON oc.id=ocm.course_id");
		sql.append(" LEFT JOIN course_r_lecturer ct ON t1.course_id = ct.course_id");
		sql.append(" LEFT JOIN oe_lecturer ot ON ct.lecturer_id = ot.id");
		sql.append(" GROUP BY t1.course_id ORDER BY t1.create_time desc");
		sql.append(" LIMIT " + number+","+pageSize);
		
		Object[] params = {user_id};
		List<MyCourseVo> myCourses = super.query(JdbcUtil.getCurrentConnection(),sql.toString(), new BeanListHandler<>(MyCourseVo.class), params);
		if(myCourses!=null && myCourses.size()>0){
			for(MyCourseVo vo1 : myCourses){
				String cn = vo1.getCourse_id();
				if(cn.equals(courseId)){
					vo1.setCourse_expiry_date("永久");
				}
				String tname = vo1.getTeacher_name();
				if(tname!=null && !"".equals(tname)){
					String[] tnames = tname.split(",");
					if(tnames!=null && tnames.length>0){
						if(tnames.length==1){
							vo1.setTeacher_name(tnames[0]);
						}else{
							vo1.setTeacher_name(tnames[0]+"等");
						}
					}else{
						vo1.setTeacher_name("暂无讲师");
					}
				}else{
					vo1.setTeacher_name("暂无讲师");
				}
			}
		}
		return myCourses;
	}

	public List<ChapterVo> videoLook(int course_id) throws SQLException {
		//查课下所有知识点
		// oe_chapter 章/节/知识点表
				String sq = "select c.id as chapter_id,c.name as chapter_name from oe_chapter c "
						+ " where c.level=4 and c.course_id =? order by sort";
				List<ChapterVo> chapters =
						super.query(JdbcUtil.getCurrentConnection(),sq,new BeanListHandler<>(ChapterVo.class),course_id);
				//查课下所有试学视频
				//oe_video 课程视频
				sq = "select v.chapter_id,v.id as v_id,v.video_id as video_id,v.name as video_name from oe_video v "
						+ " where v.is_delete=0 and v.status=1 and v.course_id=? order by sort";
				List<VideoVo> videos =
						super.query(JdbcUtil.getCurrentConnection(),sq, new BeanListHandler<>(VideoVo.class),course_id);
				//循环组装
				for (ChapterVo chapter : chapters) {
					List<VideoVo> children = new ArrayList<VideoVo>();
					for (VideoVo video : videos) {
						if (chapter.getChapter_id().equals(video.getChapter_id())) {
							children.add(video);
						}
					}
					chapter.setVideos(children);
				}
				//删除空的知识点
				for (int i = 0; i < chapters.size(); i++) {
					if (chapters.get(i).getVideos().size() <= 0) {
						chapters.remove(i);
						i--;
					}
				}
				return chapters;
	}
	/**
	 * Description：查找课程下的第一个视频id，因为默认播放的是第一个视频啦
	 * @param course_id
	 * @return
	 * @throws SQLException
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Map<String,String> getVideoFirst(int course_id) throws SQLException {
		// TODO Auto-generated method stub
		String videoId = null;
		String vId = null;
		Map<String,String> map = new HashMap<String, String>();
		/**
		 * 查找所有课程下的所有知识点
		 */
		String sq = "select c.id as chapter_id from oe_chapter c "
				+ " where c.level=4 and c.course_id =? order by sort";
		List<ChapterVo> chapters =
				super.query(JdbcUtil.getCurrentConnection(),sq,new BeanListHandler<>(ChapterVo.class),course_id);
		/**
		 * 查找知识点下的视频，找到最近的一个。找到就ok，
		 */
		for (ChapterVo chapter : chapters) {
			String chapter_id = chapter.getChapter_id();
			sq = "select v.video_id as video_id,v.id as v_id from oe_video v "
					+ " where v.is_delete=0 and v.status=1 and v.course_id=? and v.chapter_id =? order by sort";
			List<VideoVo> videos = super.query(JdbcUtil.getCurrentConnection(),sq, new BeanListHandler<>(VideoVo.class),course_id,chapter_id);
			for (VideoVo video : videos) {
				videoId =video.getVideo_id();
				vId =video.getV_id();
				map.put("chapterId",chapter_id);
				map.put("videoId",videoId);
				map.put("vId",vId);
				break;
			}
			if(videoId!=null){
				break;
			}
		}
		return map;
	}
	public List<CourseLecturVo> offLineClass(int number, int pageSize, 
			String queryParam) throws SQLException {
		StringBuffer all = new StringBuffer("");
		all.append("select  oc.smallimg_path as smallImgPath,oc.id ");
		all.append(" from oe_course as oc INNER JOIN oe_user ou on(ou.id=oc.user_lecturer_id)  ");
		all.append(" where oc.is_delete=0 and oc.status=1 and oc.type = 3 ");//and oc.is_free=0 oc.course_type=1 and
		//房间编号/主播/课程
//		if(queryParam!=null && !"".equals(queryParam) && !"null".equals(queryParam)){
//			all.append(" and ("); 
//			/*all.append(" ou.room_number like '%"+ queryParam + "%'"); 
//			all.append(" or "); */
//			all.append(" ou.name like '%"+ queryParam + "%'"); 
//			all.append(" or "); 
//			all.append(" oc.grade_name like '%"+ queryParam + "%')"); 
//		}
//		all.append(" order by learndCount desc");
		return super.queryPage(JdbcUtil.getCurrentConnection(),all.toString(),number,pageSize,CourseLecturVo.class);
	}
	/**
	 * 
	 * Description：传递一个课程id组成的 字符串。  然后判断这些课程是否失效或者不存在
	 * @param courseId
	 * @param userId
	 * @return
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @throws SQLException 
	 *
	 */
	public boolean  queryOrderIsExit(String courseIds, String userId) throws SQLException {
		// TODO Auto-generated method stub
		boolean falg = true;
		Integer csl = courseIds.split(",").length;
		//判断是直播课程是是点播课程。
		String sql ="select count(*) as c from oe_course as oc  where oc.is_delete=0 and oc.status=1 and oc.id in ("+courseIds+")" ;
		Map<String,Object> map = super.query(JdbcUtil.getCurrentConnection(),sql, new MapHandler());
		if(null != map && map.get("c")!=null){
			if(csl == Integer.parseInt(map.get("c").toString())){
				falg = true;
			}else{
				falg = false;
			}
		}else{
			falg = false;
		}
		return falg;
	}

	
}
