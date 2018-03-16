package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.dao.OLCourseMapper;
import com.xczh.consumer.market.dao.OnlineLecturerMapper;
import com.xczh.consumer.market.service.MenuService;
import com.xczh.consumer.market.service.OLCourseServiceI;
import com.xczh.consumer.market.service.OnlineCourseService;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.vo.ChapterVo;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.DateUtil;
import com.xczh.consumer.market.vo.MenuVo;
import com.xczh.consumer.market.vo.MyCourseVo;
import com.xczh.consumer.market.vo.WxcpCourseVo;
import com.xczh.consumer.market.vo.WxcpOeMenuVo;
import com.xczh.consumer.market.wxpay.typeutil.StringUtil;
import com.xczhihui.wechat.course.model.OfflineCity;
import com.xczhihui.wechat.course.service.IOfflineCityService;

@Service
public class OLCourseServiceImpl implements OLCourseServiceI {

	@Autowired
	private OLCourseMapper wxcpCourseDao;
	@Autowired
	private OnlineLecturerMapper onlineLecturerMapper;

	@Autowired
	private OnlineCourseService onlineCourseService;
	
	@Autowired
	private MenuService menuService;

	@Autowired
	private IOfflineCityService offlineCityService;
	
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
//		if(courseLecturVo!=null){
//			 Map<String,String> map = wxcpCourseDao.getVideoFirst(course_id);
//			 courseLecturVo.setChapterId(map.get("chapterId"));
//			 courseLecturVo.setDirectId(map.get("videoId"));
//			 courseLecturVo.setvId(map.get("vId"));
//		}
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
	public List<CourseLecturVo> offLineClassList(List<OfflineCity> cityList) throws SQLException {

		String strsql="(select  o.id,o.grade_name as gradeName,o.current_price*10 as currentPrice,4 as type,"
				+" o.smallimg_path as smallImgPath,o.lecturer as name,o.address as address,"
				+" o.city as city,DATE_FORMAT(o.start_time,'%m.%d') as startDateStr,"
				+"IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = o.id),0) + IFNULL(o.default_student_count, 0) learndCount,"
				+"if(date_sub(date_format(o.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff," //是否截止
				+" o.collection as collection,"
				+" o.is_free as watchState,"
				+" o.is_recommend,o.start_time as startTime,"
				+"'全国课程' as note "
				+" from oe_course o "
				+" WHERE o.is_delete=0 and o.status=1 and o.type = 3   "
				+" ORDER BY o.recommend_sort desc,o.start_time DESC LIMIT 0,6) ";

		if(cityList.size()>0){
			strsql+= " union all ";
		}
		int i = 0;
		for (OfflineCity offlineCity : cityList) {
				i++;
			strsql+="(select  o.id,o.grade_name as gradeName,o.current_price*10 as currentPrice,4 as type,"
					+" o.smallimg_path as smallImgPath,o.lecturer as name,o.address as address,"
					+" o.city as city,DATE_FORMAT(o.start_time,'%m.%d') as startDateStr,"
					+"IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = o.id),0) + IFNULL(o.default_student_count, 0) learndCount,"
					+"if(date_sub(date_format(o.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff," //是否截止
					+" o.collection as collection,"
					+" o.is_free as watchState,"
					+" o.is_recommend,o.start_time as startTime,"
					+" o.city as note "
					+" from oe_course o "
					+" WHERE o.is_delete=0 and o.status=1 and o.type = 3  and o.city = '"+offlineCity.getCityName()+"' "
					+" ORDER BY o.recommend_sort desc,o.start_time DESC LIMIT 0,4)";

				if(i < cityList.size()){
					strsql+= " union all ";
				}
		}
		return wxcpCourseDao.query(JdbcUtil.getCurrentConnection(),strsql,new BeanListHandler<>(CourseLecturVo.class));
	}
	@Override
	public List<CourseLecturVo> offLineClassListBySort(List<OfflineCity> cityList) throws SQLException {

		String strsql="(select  oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,4 as type, "
				+" oc.smallimg_path as smallImgPath,oc.lecturer as name,oc.address as address,"
				+" oc.city as city,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,"
				+"IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0) + IFNULL(oc.default_student_count, 0) learndCount,"
				+"if(date_sub(date_format(oc.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff," //是否截止
				+" oc.collection as collection,"
				+"'全国课程' as note "
				+" from oe_course oc "
				+"where  oc.is_delete=0 and oc.status=1 and oc.type = 3 "
				+" order by oc.start_time asc  limit 0,6)";
		if(cityList.size()>0){
			strsql+= " union all ";
		}
		int i = 0;
		for (OfflineCity offlineCity : cityList) {
			i++;
			strsql+="(select  oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,4 as type, "
					+"oc.smallimg_path as smallImgPath,oc.lecturer as name,oc.address as address,oc.city as city,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,"
					+"IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0) + IFNULL(oc.default_student_count, 0) learndCount,"
					+"if(date_sub(date_format(oc.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff," //是否截止
					+" oc.collection as collection,"
					+" oc.city as note "
					+" from oe_course oc "
					+"where  oc.is_delete=0 and oc.status=1 and oc.type = 3 "
					+" and oc.city = '"+offlineCity.getCityName()+"'"
					+" order by oc.start_time asc  limit 0,4)";
			if(i < cityList.size()){
				strsql+= " union all ";
			}
		}
		return wxcpCourseDao.query(JdbcUtil.getCurrentConnection(),strsql,new BeanListHandler<>(CourseLecturVo.class));
	}
	
	@Override
	public List<CourseLecturVo> offLineClassListOld(int number, int pageSize) throws SQLException {

		String dateWhere = " if(date_sub(date_format(oc.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff ";//这个用来比较啦
		
		String dateWhereCutoff = " if(date_sub(date_format(oc.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) ";//这个用来比较啦
		
		String sql="( select  ou.small_head_photo headImg,oc.address,IFNULL(( SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id ), 0 ) + "
				+ "IFNULL(default_student_count, 0) learndCount,ou.name,oc.grade_name gradeName,oc.description,oc.current_price currentPrice,"
				+ "if(oc.course_pwd is not null,2,if(oc.is_free =0,1,0)) as watchState,4 as type,"
				+ "oc.is_free isFree,oc.smallimg_path as smallImgPath,oc.id,oc.start_time startTime,oc.end_time endTime, "
				+  dateWhere
				+ "from"
				+ " oe_course oc INNER JOIN oe_user ou on(ou.id=oc.user_lecturer_id) where  "
				+  dateWhereCutoff +" = 1 and"  //表示没有截止的
				+ " oc.is_delete=0 and oc.status=1 and oc.type = 3 ORDER BY oc.start_time )";
		
		sql +="  UNION all  ";
		
		sql += "( select  ou.small_head_photo headImg,oc.address,IFNULL(( SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id ), 0 ) + "
				+ "IFNULL(default_student_count, 0) learndCount,ou.name,oc.grade_name gradeName,oc.description,oc.current_price currentPrice,"
				+ "if(oc.course_pwd is not null,2,if(oc.is_free =0,1,0)) as watchState,4.as type,"
				+ "oc.is_free isFree,oc.smallimg_path as smallImgPath,oc.id,oc.start_time startTime,oc.end_time endTime, "
				+  dateWhere
				+ "from"
				+ " oe_course oc INNER JOIN oe_user ou on(ou.id=oc.user_lecturer_id) where  "
				+  dateWhereCutoff +" = 0 and"  //表示没有截止的
				+ " oc.is_delete=0 and oc.status=1 and oc.type = 3 ORDER BY oc.start_time desc)";	
		

		return wxcpCourseDao.queryPage(JdbcUtil.getCurrentConnection(),sql,number,pageSize,CourseLecturVo.class);
	}
	

	@Override
	public CourseLecturVo offLineClassItem(Integer id,String userId) throws SQLException {

		String dateWhere = " if(date_sub(date_format(c.start_time,'%Y%m%d'),INTERVAL 1 DAY)>=date_format(now(),'%Y-%m-%d'),1,0) as cutoff ";//这个用来比较啦
		
		StringBuilder sql = new StringBuilder("");
		sql.append("select ou.description udescription,c.address,c.id,c.direct_Id as directId,c.grade_name as gradeName,ou.small_head_photo as headImg,ou.name as name,");
		sql.append("c.smallimg_path as smallImgPath,ou.room_number as roomNumber,c.start_time as startTime,c.end_time as endTime,");
		sql.append("c.description as description,ou.id as userId,"
				+ " c.original_cost as originalCost,c.current_price as currentPrice,4 as type,");  //课程简介
		sql.append("if(c.course_pwd is not null,2,if(c.is_free =0,1,0)) as watchState, ");  //课程简介

		sql.append(" (SELECT IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) ");
		sql.append(" + IFNULL(c.default_student_count, 0) + IFNULL(c.pv, 0)) as  learndCount,live_status as  lineState, ");

	    sql.append(dateWhere);
		
		sql.append(" from oe_course c,oe_user ou ");
		sql.append(" where  c.user_lecturer_id = ou.id and c.id = ?  and c.is_delete=0 and c.status = 1  and  c.type=3  ");
		Object[] params = {id};

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
	
	/***
	 * 直播搜索页面的接口调整
	 * @param
	 * @param
	 * @return
	 * @throws SQLException
	 */
	@Override
    public List<CourseLecturVo> recommendCourseList( List<MenuVo> listmv) throws SQLException{
		
		
		//学习人数、当前价格、课程类型、课程图片、讲师名、课程名字
		
		StringBuffer all = new StringBuffer("");
		all.append(" ( select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,oc.city as city,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
		all.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");    		//课程类型
		all.append(" if(oc.live_status = 2,if(DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now()<oc.start_time,4,");
		all.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now()<oc.start_time,5,oc.live_status)),oc.live_status) as  lineState ,");
		all.append(" if(oc.is_free =0,0,1) as watchState, ");	
		all.append(" oc.collection as collection, ");


		all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount,");								//学习人数
		all.append(" oc.start_time as startTime, ");
		all.append(" '精品课程' as note ");

		all.append(" from oe_course oc ,oe_user ou ");
		all.append(" where  oc.user_lecturer_id = ou.id and oc.is_delete=0 and oc.status=1 order by oc.recommend_sort desc  limit 0,6)");


		all.append("  union all ");
		
		all.append(" ( select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,oc.city as city,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
		all.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");    		//课程类型
		all.append(" if(oc.live_status = 2,if(DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now()<oc.start_time,4,");
		all.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now()<oc.start_time,5,oc.live_status)),oc.live_status) as  lineState ,");
		all.append(" if(oc.is_free =0,0,1) as watchState, ");
		all.append(" oc.collection as collection, ");
		
		all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount,");								//学习人数
		all.append(" oc.start_time as startTime, ");
		all.append(" '最新课程' as note ");
		
		all.append(" from oe_course oc ,oe_user ou ");
		all.append(" where  oc.user_lecturer_id = ou.id and  oc.is_delete=0 and oc.status=1  order by  oc.release_time desc limit 0,6)");
		
		
		all.append("  union all ");
		
		
		int i = 0;
		for (MenuVo menuVo : listmv) {
			i++;
			all.append(" ( select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,oc.city as city,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
			all.append(" if(oc.type =3,4,IF(oc.type =1,3,if(oc.multimedia_type=1,1,2))) as type, ");    		//课程类型
			all.append(" if(oc.live_status = 2,if(DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now()<oc.start_time,4,");
			all.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now()<oc.start_time,5,oc.live_status)),oc.live_status) as  lineState ,");
			all.append(" if(oc.is_free =0,0,1) as watchState, ");
			all.append(" oc.collection as collection, ");
			
			all.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount,");								//学习人数
			all.append(" oc.start_time as startTime, ");
			all.append(" om.name as note ");
			all.append(" from oe_course oc, oe_menu om  ,oe_user ou ");
			all.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id	and oc.is_delete=0 and oc.status=1 ");
			all.append(" and om.id  = "+menuVo.getId());
			all.append("  order by oc.recommend_sort desc limit 0,4 ) ");
			
			if(i < listmv.size()){
				all.append("  union all ");
			}
		}
		System.out.println(all.toString());
		
		return wxcpCourseDao.query(JdbcUtil.getCurrentConnection(),all.toString(),new BeanListHandler<>(CourseLecturVo.class));
	}

	@Override
	public List<CourseLecturVo> queryAllCourse(String menuType,Integer lineState,
			Integer courseType, String isFree,String city, String queryKey,
			Integer pageNumber, Integer pageSize) throws SQLException {

	    pageNumber = pageNumber == null ? 1 : pageNumber;
		pageSize = pageSize == null ? 100000 : pageSize;
        StringBuffer  commonSql =new StringBuffer();
        //如果为模糊查询，排序规则为，课程名>分类>讲师名>课程,讲师简介
        if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
			commonSql.append("select * from ((");
		}
		commonSql.append(" select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
				+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,");
        commonSql.append(" if(oc.live_status=1,DATE_FORMAT(oc.start_time,'%H:%i'),");
		commonSql.append("if(oc.live_status=2,if(oc.start_time <= DATE_ADD(now(),INTERVAL 1 DAY) and oc.start_time > now(),");
        commonSql.append("DATE_FORMAT(oc.start_time,'%H:%i'),DATE_FORMAT(oc.start_time,'%m.%d')),DATE_FORMAT(oc.start_time,'%m.%d') )) as startDateStr,");
        commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
				+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
		commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
		commonSql.append(" oc.collection as collection, ");
		commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
		commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
		commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
		commonSql.append(" oc.city as city, ");//是否免费
		commonSql.append(" 1 as querySort, ");//排序
		commonSql.append(" oc.release_time, ");//上架/下架时间
		commonSql.append(" oc.recommend_sort as recommendSort, ");//推荐值
		//课程类型     音频、视频、直播、线下培训班   1 2 3 4
		commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
		commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
		commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");
		commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
		commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
				+ " oc.is_delete=0 and oc.status = 1   ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
			if(city.equals("其他")){
				Page<OfflineCity> OfflineCityPage = new Page<>();
				OfflineCityPage.setCurrent(1);
				OfflineCityPage.setSize(5);
				List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
				String citylist = " (";
				for(OfflineCity c : oclist){
					citylist+="'"+c.getCityName()+"',";
				}
				citylist = citylist.substring(0,citylist.length()-1);
				citylist+=") ";
				commonSql.append(" and oc.type =3 ");
				commonSql.append(" and oc.city not in "+citylist+"");
			}else if(city.equals("全国课程")){
				commonSql.append(" and oc.type =3 ");
			}else{
				commonSql.append(" and oc.city= '"+city+"'");
				commonSql.append(" and oc.type =3 ");
			}
		}

		if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
			commonSql.append(" and oc.is_free = '"+isFree+"'");
		}

		/**
		 * 直播中的状态 4:直播课程
		 */
		if(lineState!=null&&lineState!=4){
			commonSql.append(" and oc.live_status = '"+lineState+"'");
		}
		if(lineState!=null&&lineState==4){
			commonSql.append(" and oc.live_status = 2 ");
			commonSql.append(" and oc.start_time >= DATE_ADD(now(),INTERVAL 1 DAY) ");
		}
		/**
		 * 目前检索的是讲师名和课程id
		 */
		if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
			commonSql.append(" and ");
			commonSql.append(" oc.grade_name like '%"+ queryKey + "%' ");
		}
		if(courseType!=null) {
			if (courseType == 1 || courseType == 2) { //视频或者音频
				commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
			} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
				commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
			}
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
				!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
			commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
		}
		//判断是否有模糊查询
		if(org.apache.commons.lang.StringUtils.isBlank(queryKey)){
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)){
				//精品课程排序
				if(menuType.equals("goodCourse")){
					commonSql.append("  order by  oc.recommend_sort desc ");
					//最新课程排序
				}else if(menuType.equals("newCourse")){
					commonSql.append(" order by  oc.release_time desc ");
				}else{
					commonSql.append("  order by  oc.recommend_sort desc,oc.release_time desc ");
				}
			}else if(org.apache.commons.lang.StringUtils.isBlank(menuType)&&courseType!=null&&courseType==4){
				commonSql.append("  order by  oc.recommend_sort desc,recent ");
			}else if(org.apache.commons.lang.StringUtils.isBlank(menuType)&&courseType!=null&&courseType==3){
				commonSql.append("  order by  oc.recommend_sort desc,oc.start_time desc ");
			}else{
				commonSql.append("  order by  oc.recommend_sort desc,oc.release_time desc ");
			}
		}else{
			commonSql.append(") ");
			commonSql.append(" union ");

			commonSql.append(" (select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
			commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
			commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
			commonSql.append(" oc.collection as collection, ");
			commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
			commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
			commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
			commonSql.append(" oc.city as city, ");//是否免费
			commonSql.append(" 2 as querySort, ");//排序
			commonSql.append(" oc.release_time, ");//上架/下架时间
			commonSql.append(" oc.recommend_sort as recommendSort, ");//推荐值
			//课程类型     音频、视频、直播、线下培训班   1 2 3 4
			commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
			commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
			commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");

			commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
			commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
					+ " oc.is_delete=0 and oc.status = 1   ");
			if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
				if(city.equals("其他")){
					Page<OfflineCity> OfflineCityPage = new Page<>();
					OfflineCityPage.setCurrent(1);
					OfflineCityPage.setSize(5);
					List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
					String citylist = " (";
					for(OfflineCity c : oclist){
						citylist+="'"+c.getCityName()+"',";
					}
					citylist = citylist.substring(0,citylist.length()-1);
					citylist+=") ";
					commonSql.append(" and oc.type =3 ");
					commonSql.append(" and oc.city not in "+citylist+"");
				}else if(city.equals("全国课程")){
					commonSql.append(" and oc.type =3 ");
				}else{
					commonSql.append(" and oc.city= '"+city+"'");
					commonSql.append(" and oc.type =3 ");
				}
			}

			if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
				commonSql.append(" and oc.is_free = '"+isFree+"'");
			}

			/**
			 * 直播中的状态 4:直播课程
			 */
			if(lineState!=null&&lineState!=4){
				commonSql.append(" and oc.live_status = '"+lineState+"'");
			}
			if(lineState!=null&&lineState==4){
				commonSql.append(" and oc.live_status = 2 ");
				commonSql.append(" and oc.start_time >= DATE_ADD(now(),INTERVAL 1 DAY) ");
			}
			/**
			 * 目前检索的是讲师名和课程id
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
				commonSql.append(" and ");
				commonSql.append(" oc.menu_id in (select id from oe_menu  where name like '%"+ queryKey + "%')  ");
			}
			if(courseType!=null) {
				if (courseType == 1 || courseType == 2) { //视频或者音频
					commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
				} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
					commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
				}
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
					!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
				commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
			}
			commonSql.append(") ");

			commonSql.append(" union ");

			commonSql.append(" (select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
			commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
			commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
			commonSql.append(" oc.collection as collection, ");
			commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
			commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
			commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
			commonSql.append(" oc.city as city, ");//是否免费
			commonSql.append(" 3 as querySort, ");//排序
			commonSql.append(" oc.release_time, ");//上架/下架时间
			commonSql.append(" oc.recommend_sort as recommendSort, ");//推荐值
			//课程类型     音频、视频、直播、线下培训班   1 2 3 4
			commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
			commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
			commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");

			commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
			commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
					+ " oc.is_delete=0 and oc.status = 1   ");
			if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
				if(city.equals("其他")){
					Page<OfflineCity> OfflineCityPage = new Page<>();
					OfflineCityPage.setCurrent(1);
					OfflineCityPage.setSize(5);
					List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
					String citylist = " (";
					for(OfflineCity c : oclist){
						citylist+="'"+c.getCityName()+"',";
					}
					citylist = citylist.substring(0,citylist.length()-1);
					citylist+=") ";
					commonSql.append(" and oc.type =3 ");
					commonSql.append(" and oc.city not in "+citylist+"");
				}else if(city.equals("全国课程")){
					commonSql.append(" and oc.type =3 ");
				}else{
					commonSql.append(" and oc.city= '"+city+"'");
					commonSql.append(" and oc.type =3 ");
				}
			}

			if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
				commonSql.append(" and oc.is_free = '"+isFree+"'");
			}

			/**
			 * 直播中的状态 4:直播课程
			 */
			if(lineState!=null&&lineState!=4){
				commonSql.append(" and oc.live_status = '"+lineState+"'");
			}
			if(lineState!=null&&lineState==4){
				commonSql.append(" and oc.live_status = 2 ");
				commonSql.append(" and oc.start_time >= DATE_ADD(now(),INTERVAL 1 DAY) ");
			}
			/**
			 * 目前检索的是讲师名和课程id
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
				commonSql.append(" and ");
				commonSql.append(" oc.lecturer like '%"+ queryKey + "%' ");
			}
			if(courseType!=null) {
				if (courseType == 1 || courseType == 2) { //视频或者音频
					commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
				} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
					commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
				}
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
					!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
				commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
			}

			commonSql.append(") ");

			commonSql.append(" union ");

			commonSql.append(" (select oc.id,oc.grade_name as gradeName,oc.current_price*10 as currentPrice,"
					+ "oc.smallimg_path as smallImgPath,oc.lecturer as name,DATE_FORMAT(oc.start_time,'%m.%d') as startDateStr,");
			commonSql.append(" IFNULL((SELECT COUNT(*) FROM apply_r_grade_course WHERE course_id = oc.id),0)"
					+ "+IFNULL(oc.default_student_count, 0) learndCount, ");
			commonSql.append(" if(oc.is_free =0,0,1) as watchState, ");//是否免费
			commonSql.append(" oc.collection as collection, ");
			commonSql.append(" if(oc.live_status = 2,if((DATE_SUB(now(),INTERVAL 30 MINUTE) < oc.start_time and now() > oc.start_time ) or ");
			commonSql.append(" (DATE_ADD(now(),INTERVAL 10 MINUTE)>=oc.start_time and now() < oc.start_time ),4, ");
			commonSql.append(" if(DATE_ADD(now(),INTERVAL 2 HOUR)>=oc.start_time and now() < oc.start_time,5,oc.live_status)),oc.live_status) as lineState,");
			commonSql.append(" oc.city as city, ");//是否免费
			commonSql.append(" 4 as querySort, ");//排序
			commonSql.append(" oc.release_time, ");//上架/下架时间
			commonSql.append(" oc.recommend_sort as recommendSort, ");//推荐值
			//课程类型     音频、视频、直播、线下培训班   1 2 3 4
			commonSql.append(" if(oc.type =3,4,IF(oc.type = 1,3,if(oc.multimedia_type=1,1,2))) as type, ");
			commonSql.append(" oc.create_time,oc.is_recommend,oc.start_time as startTime,");
			commonSql.append(" ABS(timestampdiff(second,current_timestamp,oc.start_time)) as recent ");

			commonSql.append(" from oe_course oc,oe_menu as om ,oe_user ou ");
			commonSql.append(" where  oc.user_lecturer_id = ou.id and om.id = oc.menu_id  and "
					+ " oc.is_delete=0 and oc.status = 1   ");
			if(org.apache.commons.lang.StringUtils.isNotBlank(city)){
				if(city.equals("其他")){
					Page<OfflineCity> OfflineCityPage = new Page<>();
					OfflineCityPage.setCurrent(1);
					OfflineCityPage.setSize(5);
					List<OfflineCity> oclist = offlineCityService.selectOfflineCityPage(OfflineCityPage).getRecords();
					String citylist = " (";
					for(OfflineCity c : oclist){
						citylist+="'"+c.getCityName()+"',";
					}
					citylist = citylist.substring(0,citylist.length()-1);
					citylist+=") ";
					commonSql.append(" and oc.type =3 ");
					commonSql.append(" and oc.city not in "+citylist+"");
				}else if(city.equals("全国课程")){
					commonSql.append(" and oc.type =3 ");
				}else{
					commonSql.append(" and oc.city= '"+city+"'");
					commonSql.append(" and oc.type =3 ");
				}
			}

			if(org.apache.commons.lang.StringUtils.isNotBlank(isFree)){
				commonSql.append(" and oc.is_free = '"+isFree+"'");
			}

			/**
			 * 直播中的状态 4:直播课程
			 */
			if(lineState!=null&&lineState!=4){
				commonSql.append(" and oc.live_status = '"+lineState+"'");
			}
			if(lineState!=null&&lineState==4){
				commonSql.append(" and oc.live_status = 2 ");
				commonSql.append(" and oc.start_time >= DATE_ADD(now(),INTERVAL 1 DAY) ");
			}
			/**
			 * 目前检索的是讲师名和课程id
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(queryKey)){
				commonSql.append(" and (");
				commonSql.append(" oc.description like '%"+ queryKey + "%' ");
				commonSql.append(" or ");
				commonSql.append(" oc.course_detail like '%"+ queryKey + "%' ");
				commonSql.append(" or ");
				commonSql.append(" oc.lecturer_description like '%"+ queryKey + "%') ");
			}
			if(courseType!=null) {
				if (courseType == 1 || courseType == 2) { //视频或者音频
					commonSql.append(" and oc.multimedia_type = '" + courseType + "'");  //多媒体类型1视频2音频
				} else if (courseType == 3 || courseType == 4) { //直播  或者线下课程
					commonSql.append(" and " + (courseType == 3 ? " oc.type=1 " : " oc.type =3 "));
				}
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(menuType)&&
					!menuType.equals("goodCourse")&&!menuType.equals("newCourse")){
				commonSql.append(" AND oc.menu_id = '"+menuType+"' ");
			}
			commonSql.append(")) ooc");

			commonSql.append("  order by  ooc.querySort asc,recommendSort desc,ooc.release_time desc ");

		}

    	System.out.println("commonSql:"+commonSql.toString());
        return wxcpCourseDao.queryPage(JdbcUtil.getCurrentConnection(),commonSql.toString(),
        		pageNumber,pageSize,CourseLecturVo.class);
	}
	
	
	
}
