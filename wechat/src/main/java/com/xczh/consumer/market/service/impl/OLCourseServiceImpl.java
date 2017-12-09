package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.dao.OLCourseMapper;
import com.xczh.consumer.market.dao.OnlineLecturerMapper;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.*;
import com.xczh.consumer.market.wxpay.typeutil.StringUtil;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class OLCourseServiceImpl implements OLCourseServiceI {

	@Autowired
	private OLCourseMapper wxcpCourseDao;
	@Autowired
	private OnlineLecturerMapper onlineLecturerMapper;

	@Autowired
	private OnlineCourseService onlineCourseService;
	
	/***
	 * 学科分类列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<WxcpOeMenuVo> categoryList() throws Exception {
		List<WxcpOeMenuVo> list = wxcpCourseDao.categoryList();
		return list;
	}

	/***
	 * 学科分类下课程列表
	 * 
	 * @param menu_id
	 * @param number
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<WxcpCourseVo> courseListByCategory(int menu_id, int number,
                                                   int pageSize) throws Exception {
		List<WxcpCourseVo> list = wxcpCourseDao.courseCategoryList(menu_id,
				number, pageSize);
		if (list != null && list.size() > 0) {
			for (WxcpCourseVo vo : list) {
				String timelong = vo.getCourse_length();
				if ("".equals(timelong) || timelong == null) {
					vo.setCourse_length("00:00");
				}

				Double price = vo.getCurrent_price();
				if (price == null) {
					vo.setCurrent_price(0.00);
				}

				Double orgin = vo.getOriginal_cost();
				if (orgin == null) {
					vo.setOriginal_cost(0.00);
				}

				String outline = vo.getCourse_outline();
				if ("".equals(outline) || outline == null) {
					vo.setCourse_outline("");
				}

				String su = vo.getSummary();
				if (StringUtil.isEmpty(su)) {
					vo.setSummary("");
				}

				String tdesc = vo.getTeacher_decs();
				if (StringUtil.isEmpty(tdesc)) {
					vo.setTeacher_decs("");
				}

				String tname = vo.getTeacher_name();
				if (StringUtil.isEmpty(tname)) {
					vo.setTeacher_name("");
				}

				Integer lcount = vo.getLearnd_count();
				if (lcount == null) {
					vo.setLearnd_count(0);
				}
			}
		}
		return list;
	}

	/***
	 * 课程详细信息
	 * 
	 * @param course_id
	 * @return
	 * @throws Exception
	 */
//	@Override
//	public WxcpCourseVo courseDetail1(int course_id, String user_id)
//			throws Exception {
//		WxcpCourseVo course = wxcpCourseDao.courseDetail1(course_id, user_id);
//		String tname = course.getTeacher_name();
//		if (StringUtil.isEmpty(tname)) {
//			course.setTeacher_name("");
//		}
//		Double price = course.getCurrent_price();
//		if (price == null) {
//			course.setCurrent_price(0.00);
//		}
//
//		Double orgin = course.getOriginal_cost();
//		if (orgin == null) {
//			course.setOriginal_cost(0.00);
//		}
//
//		return course;
//	}

	@Override
	public WxcpCourseVo courseDetail2(int course_id) throws Exception {
		WxcpCourseVo course = wxcpCourseDao.courseDetail2Tabs(course_id);
		String tname = course.getTeacher_name();
		if (StringUtil.isEmpty(tname)) {
			course.setTeacher_name("");
		}
		String tdesc = course.getTeacher_decs();
		if (StringUtil.isEmpty(tdesc)) {
			course.setTeacher_decs("");
		}

		String outline = course.getCourse_outline();
		if (StringUtil.isEmpty(outline)) {
			course.setCourse_outline("");
		}

		String sumy = course.getSummary();
		if (StringUtil.isEmpty(sumy)) {
			course.setSummary("");
		}

		String thead = course.getTeacher_head_img();
		if (StringUtil.isEmpty(thead)) {
			course.setTeacher_head_img("");
		}
		return course;
	}

	/***
	 * 课程试看
	 */
	@Override
	public List<ChapterVo> videoTrailersList(int course_id) throws Exception {
		List<ChapterVo> vos = wxcpCourseDao.videoTrailers(course_id);
		return vos;
	}

	/***
	 * 订单课程详细信息
	 */
	@Override
	public WxcpCourseVo courseOrderDetail(int course_id) throws Exception {
		WxcpCourseVo course = wxcpCourseDao.courseOrderDetail(course_id);
		String expiry_date = DateUtil.getNextYearDateString();
		course.setCourse_expiry_date(expiry_date);
		String tname = course.getTeacher_name();
		if (StringUtil.isEmpty(tname)) {
			course.setTeacher_name("");
		}
		return course;
	}

	@Override
	public List<MyCourseVo> myCourseList(String user_id, int number,
                                         int pageSize) throws Exception {
		List<MyCourseVo> mycourses = wxcpCourseDao.myCourses(user_id, number,
				pageSize);
		return mycourses;
	}

	@Override
	public List<ChapterVo> videoLookList(int course_id) throws Exception {
		List<ChapterVo> vos = wxcpCourseDao.videoLook(course_id);
		return vos;
	}

	/***
	 * 新的原型接口 --- 学科分类列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> categoryXCList() throws Exception {
		return  wxcpCourseDao.categoryXCList();
	}

	/***
	 * 学科分类下课程列表
	 * 
	 * @param menu_id
	 * @param number
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<CourseLecturVo> courseXCListByCategory(String menu_id, int number,
                                                       int pageSize, Integer multimedia_type) throws Exception {
		List<CourseLecturVo> list = wxcpCourseDao.courseCategoryXCList(menu_id,
				number, pageSize,multimedia_type);
		return list;
	}
	@Override
	public CourseLecturVo bunchDetailsByCourseId(int course_id) throws SQLException {
		// TODO Auto-generated method stub
		CourseLecturVo courseLecturVo = wxcpCourseDao.bunchDetailsByCourseId(course_id);
		//少了一个视频id
		//查课下所有知识点
		if(courseLecturVo!=null){
			 Map<String,String> map = wxcpCourseDao.getVideoFirst(course_id);
			 courseLecturVo.setChapterId(map.get("chapterId"));
			 courseLecturVo.setDirectId(map.get("videoId"));
			 courseLecturVo.setvId(map.get("vId"));
		}
		return courseLecturVo;
	}

	@Override
	public List<CourseLecturVo> courseCategoryXCList1(int number, int pageSize,
                                                      String queryParam) throws SQLException {
		return wxcpCourseDao.courseCategoryXCList1(number, pageSize, queryParam);
	}

	@Override
	public WxcpCourseVo courseDetail1(int course_id, String user_id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseLecturVo> offLineClass(String keyWord, int number,
			int pageSize)throws SQLException  {
		return wxcpCourseDao.offLineClass(number, pageSize, keyWord);
	}

	@Override
	public List<CourseLecturVo> offLineClassList(int number, int pageSize) throws SQLException {

		String dateWhere = " if(date_sub(date_format(oc.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff ";//这个用来比较啦
		
		String dateWhereCutoff = " if(date_sub(date_format(oc.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) ";//这个用来比较啦
		
		String sql="( select  ou.small_head_photo headImg,oc.address,IFNULL(( SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id ), 0 ) + "
				+ "IFNULL(default_student_count, 0) learndCount,ou.name,oc.grade_name gradeName,oc.description,oc.current_price currentPrice,"
				+ "if(oc.course_pwd is not null and oc.course_pwd !='',2,if(oc.is_free =0,1,0)) as watchState,"
				+ "oc.is_free isFree,oc.smallimg_path as smallImgPath,oc.id,oc.start_time startTime,oc.end_time endTime, "
				+  dateWhere
				+ "from"
				+ " oe_course oc INNER JOIN oe_user ou on(ou.id=oc.user_lecturer_id) where  "
				+  dateWhereCutoff +" = 1 and"  //表示没有截止的
				+ " oc.is_delete=0 and oc.status=1 and oc.online_course =1 ORDER BY oc.start_time )";
		
		sql +="  UNION all  ";
		
		sql += "( select  ou.small_head_photo headImg,oc.address,IFNULL(( SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id ), 0 ) + "
				+ "IFNULL(default_student_count, 0) learndCount,ou.name,oc.grade_name gradeName,oc.description,oc.current_price currentPrice,"
				+ "if(oc.course_pwd is not null and oc.course_pwd !='',2,if(oc.is_free =0,1,0)) as watchState,"
				+ "oc.is_free isFree,oc.smallimg_path as smallImgPath,oc.id,oc.start_time startTime,oc.end_time endTime, "
				+  dateWhere
				+ "from"
				+ " oe_course oc INNER JOIN oe_user ou on(ou.id=oc.user_lecturer_id) where  "
				+  dateWhereCutoff +" = 0 and"  //表示没有截止的
				+ " oc.is_delete=0 and oc.status=1 and oc.online_course =1 ORDER BY oc.start_time desc)";	
		

		return wxcpCourseDao.queryPage(JdbcUtil.getCurrentConnection(),sql,number,pageSize,CourseLecturVo.class);
	}

	@Override
	public CourseLecturVo offLineClassItem(Integer id,String userId) throws SQLException {

		String dateWhere = " if(date_sub(date_format(c.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff ";//这个用来比较啦
		
		StringBuilder sql = new StringBuilder("");
		sql.append("select ou.description udescription,c.address,c.id,c.direct_Id as directId,c.grade_name as gradeName,ou.small_head_photo as headImg,ou.name as name,");
		sql.append("c.smallimg_path as smallImgPath,ou.room_number as roomNumber,c.start_time as startTime,c.end_time as endTime,");
		sql.append("c.description as description,ou.id as userId,"
				+ " c.original_cost as originalCost,c.current_price as currentPrice,");  //课程简介
		sql.append("if(c.course_pwd is not null and c.course_pwd !='',2,if(c.is_free =0,1,0)) as watchState, ");  //课程简介

		sql.append(" (SELECT IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) ");
		sql.append(" + IFNULL(c.default_student_count, 0) + IFNULL(c.pv, 0)) as  learndCount,live_status as  lineState, ");

	    sql.append(dateWhere);
		
		sql.append(" from oe_course c,oe_user ou ");
		sql.append(" where  c.user_lecturer_id = ou.id and c.id = ?  and c.is_delete=0 and c.status = 1  and  c.online_course=1  ");
		Object[] params = {id};
		System.out.println(sql.toString());


		CourseLecturVo courseLecturVo = wxcpCourseDao.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(CourseLecturVo.class),params);
		/**
		 * 当前直播状态:  0 直播已结束   1 直播还未开始   2 点播
		 */
			/**
			 * 获取当前预约人数
			 */
		Integer subCount = onlineCourseService.selectSubscribeInfoNumberCourse(id);
		courseLecturVo.setCountSubscribe(subCount);
		/**
		 * 判断自己是否预约了
		 */
		if(userId!=null){
			Integer isSubscribe = onlineCourseService.selectSubscribeInfoIs(id,userId);
			if(isSubscribe == 0){//未预约
				courseLecturVo.setIsSubscribe(isSubscribe);
			}else{
				courseLecturVo.setIsSubscribe(1);
			}
		}
		return courseLecturVo;
	}
}
