package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.common.enums.CourseForm;
import com.xczhihui.bxg.online.common.enums.Multimedia;
import com.xczhihui.bxg.online.common.utils.cc.bean.CategoryBean;
import com.xczhihui.bxg.online.common.utils.cc.config.Config;
import com.xczhihui.bxg.online.common.utils.cc.util.APIServiceFunction;
import com.xczhihui.bxg.online.common.utils.cc.util.CCUtils;

import com.xczhihui.bxg.online.manager.user.service.OnlineUserService;
import com.xczhihui.bxg.online.manager.utils.subscribe.Subscribe;
import com.xczhihui.bxg.online.manager.vhall.VhallUtil;
import com.xczhihui.bxg.online.manager.vhall.bean.Webinar;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseSubscribeDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.LecturerService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseLecturVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LecturerVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;

/**
 *   CourseServiceImpl:课程业务层接口实现类
 *   @author Rongcai Kang
 */
@Service("courseService")
public class CourseServiceImpl  extends OnlineBaseServiceImpl implements CourseService {

    @Autowired
    private LecturerService lecturerService;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseSubscribeDao courseSubscribeDao;
	//由后台配置的点播视频
    private static String CNAME = "video-on-demand-background";

	@Autowired
	private OnlineUserService onlineUserService;

	@Value("${ENV_FLAG}")
	private String envFlag;
	@Value("${LIVE_VHALL_USER_ID}")
	private String liveVhallUserId;
	@Value("${vhall_callback_url}")
	String vhall_callback_url;
	@Value("${vhall_private_key}")
	String vhall_private_key;
    
    @Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo,  int pageNumber, int pageSize) {
    	Page<CourseVo> page = courseDao.findCloudClassCoursePage(courseVo, pageNumber, pageSize);
    	return page;
	
	}
    
    @Override
    public Page<CourseVo> findCourseRecPage(CourseVo courseVo,  int pageNumber, int pageSize) {
    	Page<CourseVo> page = courseDao.findCloudClassCourseRecPage(courseVo, pageNumber, pageSize);
    	return page;
    	
    }
    

    /**
     * 根据菜单编号，查找对应的课程列表
     * @param number 菜单编号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize 每页显示多少行，默认20
     * @return Example 分页列表
     */
    @Override
	public List<CourseLecturVo>  getCourseAndLecturerlist(Integer number, Integer courseType, Integer pageNumber, Integer pageSize) {

         List<CourseLecturVo> courseLecturVos=null;
         //先根据菜单编号获取相关课程信息
         List<Course>  courses=this.findCourseListByNumber("", number, courseType, pageNumber, pageSize);
         if(!CollectionUtils.isEmpty(courses)){
             courseLecturVos = new ArrayList<>();
             for (Course course : courses){
                  //根据教师ID号查找老师信息
             //     Lecturer lecturer = lecturerService.findLecturerById(course.getLecturerId());
                  CourseLecturVo courseLecturVo=new CourseLecturVo();
                  courseLecturVo.setId(course.getId());
                  courseLecturVo.setGradeName(course.getGradeName());
                  //courseLecturVo.setType(course.getType());
                  courseLecturVo.setCourseType(Integer.valueOf(course.getCourseType()));
                  courseLecturVo.setLiveTime(course.getLiveTime());
                  courseLecturVo.setSmallImgPath(course.getSmallImgPath());
                  courseLecturVo.setDetailImgPath(course.getDetailImgPath());
                  courseLecturVo.setBigImgPath(course.getBigImgPath());
                  courseLecturVo.setDescription(course.getDescription());
                  courseLecturVo.setCreateTime(course.getCreateTime());
                  courseLecturVo.setGraduateTime(course.getGraduateTime());
                  courseLecturVo.setCloudClassroom(course.getCloudClassroom());
                //  courseLecturVo.setName(lecturer.getName());
                  courseLecturVos.add(courseLecturVo);
             }
         }
        return courseLecturVos;
    }




    /**
     * 根据菜单编号，查找对应的课程列表
     * @param menuNumber 菜单编号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize 每页显示多少行，默认20
     * @return Example 分页列表
     */
    public List<Course>  findCourseListByNumber(String name,Integer menuNumber,Integer courseType, Integer pageNumber, Integer pageSize){
        pageNumber = pageNumber == null ? 1 : pageNumber;
        pageSize = pageSize == null ? 20 : pageSize;

        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuffer and = new StringBuffer();
        if (StringUtils.hasText(name)) {
            and.append(" and name like :name");
            paramMap.put("name", name);
        }
        String sql="";
        if( courseType!=0){
            sql="from Course where 1=1 and isDelete=0 and courseType="+courseType+ and + " order by  liveTime ";
        }else
        {
            sql="from Course where 1=1 and isDelete=0 and menuNumber like '"+menuNumber +"%'" + and + " order by  liveTime ";
        }
        Page<Course> page = dao.findPageByHQL(sql, paramMap, pageNumber, pageSize);
        return  this.sort(page.getItems());
       // return  page.getItems();
    }


    /**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @Override
    public CourseVo getCourseById( Integer courseId) {

        CourseVo courseVo=null;

        if(courseId != null){
            String hql="from Course where 1=1 and isDelete=0 and id = ?";
            Course course= dao.findByHQLOne(hql,new Object[] {courseId});
            if(course!=null){
                courseVo=new CourseVo();
                courseVo.setId(course.getId());
                courseVo.setCreateTime(course.getCreateTime());
                courseVo.setCloudClassroom(course.getCloudClassroom());
                courseVo.setBigImgPath(course.getBigImgPath());
                courseVo.setDescription(course.getDescription());
                courseVo.setDetailImgPath(course.getDetailImgPath());
                courseVo.setCourseName(course.getGradeName());
                courseVo.setQqno(course.getQqno());
                courseVo.setStatus(course.getStatus());
				courseVo.setGradeQQ(course.getGradeQQ());
				courseVo.setDefaultStudentCount(course.getDefaultStudentCount());
				courseVo.setDirectId(course.getDirectId());
				
				courseVo.setStartTime(course.getStartTime());
				courseVo.setUserLecturerId(course.getUserLecturerId());
				courseVo.setSmallimgPath(course.getSmallImgPath());
				courseVo.setCoursePwd(course.getCoursePwd());
            }
        }
        return courseVo;
    }

      public List<Course>  sort(List<Course> couVo){
          List<Course> firstVo=new ArrayList<Course>();
          List<Course> secondVo=new ArrayList<Course>();
          long currentTime = new Date().getTime();//毫秒
          for(Course vo :couVo){
               if(vo.getLiveTime().getTime()>currentTime){
                   firstVo.add(vo);
               }else{
                   secondVo.add(vo);
               }
          }
          for (Course course:secondVo){
              firstVo.add(course);
          }
          return  firstVo;
      }


	@Override
	public List<Menu> getfirstMenus(Integer type) {
		
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="SELECT id,name FROM oe_menu where is_delete = 0 and name <> '全部' and status=1 and yun_status = 1 ";
		if(type != null) {
            sql = sql + " and type = " + type;
        }
		dao.findEntitiesByJdbc(MenuVo.class, sql, params);
	    return dao.findEntitiesByJdbc(Menu.class, sql, params);
	}


	@Override
	public List<MenuVo> getsecoundMenus(String firstMenuNumber) {
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="SELECT s.id as number ,s.`name` as name from score_type  s WHERE s.id<>0 ";
		if(!"".equals(firstMenuNumber)&&firstMenuNumber!=null){
			params.put("firstMenuNumber", firstMenuNumber);
			sql="SELECT s.id as number ,s.`name` as name from menu_coursetype mc,score_type  s WHERE s.id<>0 and mc.course_type_id=s.id and mc.menu_id =:firstMenuNumber";
		}
		//String sql="SELECT number,name FROM oe_menu where name!='全部'and number<>:firstMenuNumber and SUBSTR(number FROM 1 FOR 4)=:firstMenuNumber";
		return dao.findEntitiesByJdbc(MenuVo.class, sql, params);
	}


	@Override
	public List<LecturerVo> getLecturers() {
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="SELECT id,name FROM oe_lecturer where is_delete=0 ";
		dao.findEntitiesByJdbc(MenuVo.class, sql, params);
	    return dao.findEntitiesByJdbc(LecturerVo.class, sql, params);
	}

    @Override
    public List<CourseVo> findByMenuId(String menuId,String courseTypeId) {
        String sql="select id,"
        		+ " grade_name as courseName,"
        		+ " class_template as classTemplate,"
        		+ " course_type courseType,"
        		+ " grade_student_sum gradeStudentSum,"
        		+ " (select count(1) from oe_plan_template opt where opt.course_id = oe_course.id ) teachingDays  "
        		+ " from oe_course where menu_id=:menuId and course_type = 0 ";//职业课
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("menuId",menuId);
		sql+=" and course_type_id=:courseTypeId ";
		params.put("courseTypeId",courseTypeId);
		sql+=" and is_delete=0 ";
        return dao.findEntitiesByJdbc(CourseVo.class, sql, params);
    }

	@Override
	public List<CourseVo> list(String courseType) {
		String sql="select *,grade_name as courseName from oe_course where is_delete=0 and status=1 ";
		Map<String,Object> params=new HashMap<String,Object>();
		if(courseType != null && !"".equals(courseType)){
			sql += " and course_type = :courseType ";
			params.put("courseType", courseType);
		}
		List<CourseVo> voList=dao.findEntitiesByJdbc(CourseVo.class, sql, params);
		return voList;
	}

	@Override
	public List<CourseVo> getCourselist(String search) {
		if("".equals(search)||null==search){
			String sql="select *,grade_name as courseName from oe_course where is_delete=0 ";
			Map<String,Object> params=new HashMap<String,Object>();
			List<CourseVo> voList=dao.findEntitiesByJdbc(CourseVo.class, sql, params);
			return voList;
		}
		String sql="select *,grade_name as courseName from oe_course where is_delete=0 and grade_name like '%"+search+"%'";
		Map<String,Object> params=new HashMap<String,Object>();
		List<CourseVo> voList=dao.findEntitiesByJdbc(CourseVo.class, sql, params);
		return voList;
	}

	@Override
	public void addCourse(CourseVo courseVo) {
		checkName(courseVo.getId(),courseVo.getCourseName());
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="SELECT IFNULL(MAX(sort),0) as sort FROM oe_course ";
		List<Course> temp = dao.findEntitiesByJdbc(Course.class, sql, params);
		int sort;
		if(temp.size()>0){
			 sort=temp.get(0).getSort().intValue()+1;
		}else{
			 sort=1;
		}
		//设置精品推荐排序
		String essenceSortsql="SELECT IFNULL(MAX(essence_sort),0) as essenceSort FROM oe_course ";
		List<Course> essenceSorttemp = dao.findEntitiesByJdbc(Course.class, essenceSortsql, params);
		int essenceSort;
		if(essenceSorttemp.size()>0){
			essenceSort=essenceSorttemp.get(0).getEssenceSort().intValue()+1;
		}else{
			essenceSort=1;
		}
		//当课程存在密码时，设置的当前价格失效，改为0.0
		if(courseVo.getCoursePwd()!=null && !"".equals(courseVo.getCoursePwd().trim())){
			courseVo.setCurrentPrice(0.0);
		}
		Course course = new Course();
		//课程名称
		course.setGradeName(courseVo.getCourseName());
		//课程名称模板mv//20180109  yuxin 将课程名作为模板
		course.setClassTemplate(courseVo.getCourseName());
		//学科id
		course.setMenuId(courseVo.getMenuId());
//		course.setCourseTypeId(courseVo.getCourseTypeId()); //课程类别id
		//授课方式
		course.setCourseType(courseVo.getCourseType());
		//课程时长
		course.setCourseLength(courseVo.getCourseLength());
		//原价格
		course.setOriginalCost(courseVo.getCurrentPrice());
		//现价格
		course.setCurrentPrice(courseVo.getCurrentPrice());
		if(0==course.getCurrentPrice()){
			//免费
			course.setIsFree(true);
		}else{
			//收费
			course.setIsFree(false);
		}
		//排序
		course.setSort(sort);
		course.setEssenceSort(essenceSort); //精品推荐排序
		course.setType(courseVo.getType());

		course.setLearndCount(0);
		//当前登录人
		course.setCreatePerson(courseVo.getCreatePerson());
		//当前时间
		course.setCreateTime(new Date());
		//状态
		course.setStatus("0");
		//课程描述  2018-01-20 yuxin 暂时弃用描述
//		course.setDescription(courseVo.getDescription());
		course.setIsRecommend(0);
		course.setRecommendSort(null);

		course.setDescriptionShow(0);
		//随机生成一个10-99数，统计的时候加上这个基数
		java.util.Random random=new java.util.Random();
		// 返回[0,100)集合中的整数，注意不包括10
		int result=random.nextInt(100);
		// +1后，[0,10)集合变为[52,152)集合，满足要求
		int defaultStudentCount = result+52;
		course.setDefaultStudentCount(defaultStudentCount);
		//yuruixin-2017-08-16
		course.setMultimediaType(courseVo.getMultimediaType());
		course.setClassRatedNum(0);
		course.setServiceType(0);
		//增加密码和老师
		course.setCoursePwd(courseVo.getCoursePwd());
		course.setUserLecturerId(courseVo.getUserLecturerId());
		course.setLecturer(courseVo.getLecturer());
//		course.setOnlineCourse(courseVo.getOnlineCourse());
		course.setPv(0);
		course.setApplyId(0);
		// zhuwenbao-2018-01-09 设置课程的展示图
		// findCourseById 是直接拿小图 getCourseDetail是从大图里拿 同时更新两个 防止两者数据不一样
		course.setSmallImgPath(courseVo.getSmallimgPath());
		course.setBigImgPath(courseVo.getSmallimgPath());
		if(course.getType() == CourseForm.OFFLINE.getCode()){
			course.setStartTime(courseVo.getStartTime());
			course.setEndTime(courseVo.getEndTime());
			course.setAddress(courseVo.getAddress());
			course.setCity(courseVo.getRealCitys());
		}else if(course.getType() == CourseForm.LIVE.getCode()){
			course.setMultimediaType(Multimedia.VIDEO.getCode());
			course.setStartTime(courseVo.getStartTime());
			//直播布局
			course.setDirectSeeding(courseVo.getDirectSeeding());
			course.setVersion(UUID.randomUUID().toString().replace("-",""));
			String webinarId = createWebinar(course);
			course.setDirectId(webinarId);
			//将直播状态设为2
			course.setLiveStatus(2);
		}

		course.setCollection(false);
		course.setSubtitle(courseVo.getSubtitle());

		dao.save(course);
		if(course.getType() == CourseForm.LIVE.getCode()){
			Subscribe.setting(course.getId(), this, courseSubscribeDao);
		}
	}

    @Override
    public List<CourseVo> listByMenuId(String menuId) {
        String sql="select *,grade_name as courseName from oe_course where is_delete=0 and status=1 and menu_id=:menuId";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("menuId", menuId);
        List<CourseVo> voList=dao.findEntitiesByJdbc(CourseVo.class, sql, params);
        return voList;
    }


    @Override
	public List<CourseVo> findCourseById(Integer id) {
		// TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("courseId", id);
//		String sql = "SELECT oc.id as id,oc.grade_name as courseName,oc.class_template as classTemplate,oc.subtitle,oc.lecturer,om.name as xMenuName,st.name as scoreTypeName,oc.multimedia_type multimediaType,oc.start_time startTime,oc.end_time endTime,oc.address,"
//				+ "tm.name as teachMethodName,oc.course_length as courseLength,oc.learnd_count as learndCount,oc.course_pwd as coursePwd,oc.grade_qq gradeQQ,oc.default_student_count defaultStudentCount,"
//				+ "oc.create_time as createTime,oc.status as status ,oc.is_free as isFree,oc.original_cost as originalCost,"
//				+ "oc.current_price as currentPrice,oc.description as description ,oc.cloud_classroom as cloudClassroom ,"
//				+ "oc.menu_id as menuId,oc.course_type_id as courseTypeId,oc.courseType as courseType,oc.qqno,oc.grade_student_sum as classRatedNum,oc.user_lecturer_id as userLecturerId,oc.smallimg_path as smallimgPath FROM oe_course oc "
//				+ "LEFT JOIN oe_menu om ON om.id = oc.menu_id LEFT JOIN score_type st ON st.id = oc.course_type_id "
//				+ "LEFT JOIN teach_method tm ON tm.id = oc.courseType WHERE oc.id = :courseId";
		String sql = "SELECT \n" +
				"  oc.id AS id,\n" +
				"  oc.grade_name AS courseName,\n" +
				"  oc.class_template AS classTemplate,\n" +
				"  oc.subtitle,\n" +
				"  oc.lecturer,\n" +
				"  om.name AS xMenuName,\n" +
				"  st.name AS scoreTypeName,\n" +
				"  oc.multimedia_type multimediaType,\n" +
				"  oc.start_time startTime,\n" +
				"  oc.end_time endTime,\n" +
				"  oc.address,\n" +
				"  oc.course_length AS courseLength,\n" +
				"  oc.learnd_count AS learndCount,\n" +
				"  oc.course_pwd AS coursePwd,\n" +
				"  oc.grade_qq gradeQQ,\n" +
				"  oc.default_student_count defaultStudentCount,\n" +
				"  oc.create_time AS createTime,\n" +
				"  oc.status AS STATUS,\n" +
				"  oc.is_free AS isFree,\n" +
				"  oc.original_cost AS originalCost,\n" +
				"  oc.current_price AS currentPrice,\n" +
				"  oc.description AS description,\n" +
				"  oc.cloud_classroom AS cloudClassroom,\n" +
				"  oc.menu_id AS menuId,\n" +
				"  oc.course_type_id AS courseTypeId,\n" +
				"  oc.courseType AS courseType,\n" +
				"  oc.qqno,\n" +
				"  oc.grade_student_sum AS classRatedNum,\n" +
				"  ou.name AS userLecturerId,\n" +
				"  oc.smallimg_path AS smallimgPath \n" +
				"FROM\n" +
				"  oe_course oc \n" +
				"  LEFT JOIN `oe_user` ou \n" +
				"  ON ou.id=oc.`user_lecturer_id`\n" +
				"  LEFT JOIN oe_menu om \n" +
				"    ON om.id = oc.menu_id \n" +
				"  LEFT JOIN score_type st \n" +
				"    ON st.id = oc.course_type_id \n" +
				"WHERE oc.id = :courseId ";
		List<CourseVo> courseVoList=dao.findEntitiesByJdbc(CourseVo.class, sql, paramMap);
		sql = "select sum(IFNULL(t.default_student_count,0)) from oe_grade t where t.course_id = ?";
		courseVoList.get(0).setLearndCount(courseDao.queryForInt(sql, new Object[]{id}));//累计默认报名人数
		return courseVoList;
	}


	@Override
	public void updateCourse(CourseVo courseVo) {
		checkName(courseVo.getId(),courseVo.getCourseName());

		Course course = dao.findOneEntitiyByProperty(Course.class, "id", courseVo.getId());
		//当课程存在密码时，设置的当前价格失效，改为0.0
		if(courseVo.getCoursePwd()!=null && !"".equals(courseVo.getCoursePwd().trim())){
			courseVo.setCurrentPrice(0.0);
		}

		//课程名称
		course.setGradeName(courseVo.getCourseName());
		//课程名称模板
		course.setClassTemplate(courseVo.getCourseName());
		//学科的id
		course.setMenuId(courseVo.getMenuId());
		//课程时长
		course.setCourseLength(courseVo.getCourseLength());
		//原价格
		course.setOriginalCost(courseVo.getCurrentPrice());
		//现价格
		course.setCurrentPrice(courseVo.getCurrentPrice());
		course.setMultimediaType(courseVo.getMultimediaType());
		//增加密码和老师
		course.setCoursePwd(courseVo.getCoursePwd());
		//作者禁止修改
//		course.setUserLecturerId(courseVo.getUserLecturerId());
		course.setAddress(courseVo.getAddress());
		course.setCity(courseVo.getRealCitys());
		
		course.setDefaultStudentCount(courseVo.getDefaultStudentCount());

		// findCourseById 是直接拿小图 getCourseDetail是从大图里拿 同时更新两个 防止两者数据不一样
		course.setSmallImgPath(courseVo.getSmallimgPath());
		course.setBigImgPath(courseVo.getSmallimgPath());

		if(0==course.getCurrentPrice()){
			course.setIsFree(true);
		}else{
			course.setIsFree(false);
		}
//		course.setLearndCount(courseVo.getLearndCount());
		course.setDefaultStudentCount(courseVo.getDefaultStudentCount());

		course.setSubtitle(courseVo.getSubtitle());
		course.setLecturer(courseVo.getLecturer());

		if(course.getType()==CourseForm.LIVE.getCode()){
			course.setStartTime(courseVo.getStartTime());
			course.setVersion(UUID.randomUUID().toString().replace("-",""));
		}else if(course.getType()==CourseForm.OFFLINE.getCode()){
			course.setStartTime(courseVo.getStartTime());
			course.setEndTime(courseVo.getEndTime());
		}

		dao.update(course);

		if(course.getType()==CourseForm.LIVE.getCode()){
			updateWebinar(course);
			Subscribe.setting(courseVo.getId(), this, courseSubscribeDao);
		}
	}

	/**
	 * Description：校验是否重名
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 5:44 2018/1/21 0021
	 **/
	@Override
	public void checkName(Integer id, String courseName) {
		List<Course> entitys= findByName(courseName);
		for(Course entity: entitys){
			if(id==null) {
				id = 0;
			}
			if(!entity.isDelete()&&entity.getId().intValue()!=id){
				throw new RuntimeException(courseName+":课程名称已存在！");
			}
		}
	}

	@Override
	public void updateRecImgPath(CourseVo courseVo) {
		Course course = dao.findOneEntitiyByProperty(Course.class, "id", courseVo.getId());
		course.setRecImgPath(courseVo.getRecImgPath());
		dao.update(course);
	}


	@Override
	public void updateStatus(Integer id) {
		// TODO Auto-generated method stub
		
		 String hql="from Course where 1=1 and isDelete=0 and id = ?";
         Course course= dao.findByHQLOne(hql, new Object[]{id});
         course.setReleaseTime(new Date());
         if(course.getStatus()!=null&&"1".equals(course.getStatus())){
        	 course.setStatus("0");
         }else{
        	 /*if(course.getType()!=null&&1==course.getType()){
        		 String hqlo ="from Course where  isDelete=0 and status =1 and type=1";
        		 List<Course> temps=dao.findByHQL(hqlo, new Object[] {});
        		 if(temps.size()>=4){
        				throw new RuntimeException ("只能启用4条数据！");
        		 }
        	 }*/
        	 course.setStatus("1");
         }
         
         dao.update(course);
	}
	@Override
	public void deleteCourseById(Integer id) {
		// TODO Auto-generated method stub
//		 String hql="update Course set isDelete = 1 where  id = ?";
//         Course course= dao.findByHQLOne(hql,new Object[] {id});
//         course.setDelete(true);
//         dao.update(course);
		//校验是否被引用
			String hqlPre="from Grade where  isDelete=0 and courseId = ?";
			Grade grade= dao.findByHQLOne(hqlPre,new Object[] {id.toString()});
            if(grade !=null){
            	throw new RuntimeException ("该数据被引用，无法删除！");
            }
   
         courseDao.deleteById(id);
	}
	
	@Override
	public void updateSortUp(Integer id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getSort();
         
         String hqlNext="from Course where sort > (select sort from Course where id= ? ) and type=2  and isDelete=0 order by sort asc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getSort();
         
         coursePre.setSort(courseNextSort);
         courseNext.setSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
         
         
	}
	
	@Override
	public void updateSortUpForReal(Integer id) {
		// TODO Auto-generated method stub
		String hqlPre="from Course where  isDelete=0 and id = ?";
		Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
		Integer coursePreSort=coursePre.getSort();
		
		String hqlNext="from Course where sort > (select sort from Course where id= ? ) and type=3 and isDelete=0 order by sort asc";
		Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
		Integer courseNextSort=courseNext.getSort();
		
		coursePre.setSort(courseNextSort);
		courseNext.setSort(coursePreSort);
		
		dao.update(coursePre);
		dao.update(courseNext);
		
		
	}


	@Override
	public void updateSortDown(Integer id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getSort();
         String hqlNext="from Course where sort < (select sort from Course where id= ? ) and type=2  and isDelete=0 order by sort desc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getSort();
         
         coursePre.setSort(courseNextSort);
         courseNext.setSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
		
	}
	
	@Override
	public void updateSortDownForReal(Integer id) {
		// TODO Auto-generated method stub
		String hqlPre="from Course where  isDelete=0 and id = ?";
		Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
		Integer coursePreSort=coursePre.getSort();
		String hqlNext="from Course where sort < (select sort from Course where id= ? ) and type = 3 and isDelete=0 order by sort desc";
		Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
		Integer courseNextSort=courseNext.getSort();
		
		coursePre.setSort(courseNextSort);
		courseNext.setSort(coursePreSort);
		
		dao.update(coursePre);
		dao.update(courseNext);
		
	}


	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		//校验是否被引用
		for(String id:ids){
			String hqlPre="from Grade where  isDelete=0 and courseId = ?";
			Grade grade= dao.findByHQLOne(hqlPre,new Object[] {id});
            if(grade !=null){
            	throw new RuntimeException ("该数据被引用，无法删除！");
            }
        }
		
		for(String id:ids){
			String hqlPre="from Course where  isDelete=0 and id = ?";
	        Course course= dao.findByHQLOne(hqlPre,new Object[] {Integer.valueOf(id)});
            if(course !=null){
            	 course.setDelete(true);
                 dao.update(course);
            }
        }
		
	}


	@Override
	public List<TeachMethod> getTeachMethod() {
		return courseDao.getTeachMethod();
	}


	@Override
	public List<Course> getCourse() {
		return courseDao.getCourse();
	}
	
	@Override
	public List<Grade> getGrade(String gradeIds) {
		return courseDao.getGrade(gradeIds);
	}

	@Override
	public void updateCourseDetail(String courseId, String smallImgPath, String detailImgPath, String courseDetail,
								   String courseOutline, String commonProblem, String lecturerDescription) {
		Course c = courseDao.findOneEntitiyByProperty(Course.class, "id", Integer.valueOf(courseId));

		// zhuwenbao 2018-01-09 在课程详情页面已经移除调smallImgPath选项 不加判断的话会将之前的smallImgPath设置为null
		if(smallImgPath != null && !"".equals(smallImgPath.trim())){
			c.setSmallImgPath(smallImgPath);
			c.setBigImgPath(smallImgPath);
		}
		c.setDetailImgPath(detailImgPath);
		c.setCourseDetail(courseDetail);
		c.setCourseOutline(courseOutline);
		c.setCommonProblem(commonProblem);
		c.setLecturerDescription(lecturerDescription);
		courseDao.update(c);
	}


	@Override
	public Map<String, String> getCourseDetail(String courseId) {
		Course c = courseDao.get(Integer.valueOf(courseId), Course.class);
		if (c != null) {
			Map<String, String> retn = new HashMap<String, String>();
			retn.put("detailImgPath",c.getDetailImgPath());
			retn.put("courseDetail", c.getCourseDetail());
			retn.put("courseOutline", c.getCourseOutline());
			retn.put("commonProblem", c.getCommonProblem());
			retn.put("lecturerDescription", c.getLecturerDescription());
			retn.put("gradeName", c.getGradeName());
			retn.put("descriptionShow", c.getDescriptionShow().toString());
			/*2017-08-14---yuruixin*/
			String imgStr = c.getBigImgPath();
			String img0 = null;
			String img1 = null;
			String img2 = null;
			if(imgStr != null){
				String[] imgArr = imgStr.split("dxg");
				img0 = imgArr[0];
				if(imgArr.length>1) {
                    img1 = imgArr[1];
                }
				if(imgArr.length>2) {
                    img2 = imgArr[2];
                }
			}
			retn.put("smallImgPath", img0);
			retn.put("smallImgPath1", img1);
			retn.put("smallImgPath2", img2);
			/*2017-08-14---yuruixin*/
			return retn;
		}
		return null;
	}


	@Override
	public List<ScoreType> getScoreType() {
		// TODO Auto-generated method stub
		return courseDao.getScoreType();
	}


	@Override
	public void addPreview(String courseId, String smallImgPath, String detailImgPath, String courseDetail,
			String courseOutline, String commonProblem) {
		if (courseDetail == null) {
            courseDetail = "";
        }
		CoursePreview c = courseDao.get(Integer.valueOf(courseId), CoursePreview.class);
		if(c != null){
			courseDao.getNamedParameterJdbcTemplate().getJdbcOperations().execute("delete from oe_course_preview where id = "+courseId);
		}
			courseDao.getNamedParameterJdbcTemplate().getJdbcOperations().execute("INSERT INTO oe_course_preview ( " +
																					"	id, " +
																					"	create_person, " +
																					"	create_time, " +
																					"	is_delete, " +
																					"	bigimg_path, " +
																					"	cloud_classroom, " +
																					"	description, " +
																					"	detailimg_path, " +
																					"	grade_name, " +
																					"	graduate_time, " +
																					"	live_time, " +
																					"	smallimg_path, " +
																					"	sort, " +
																					"	courseType, " +
																					"	STATUS, " +
																					"	learnd_count, " +
																					"	original_cost, " +
																					"	current_price, " +
																					"	course_length, " +
																					"	menu_id, " +
																					"	course_type_id, " +
																					"	is_free, " +
																					"	class_template, " +
																					"	course_detail, " +
																					"	course_outline, " +
																					"	common_problem, " +
																					"	lecturer_id, " +
																					"	is_recommend, " +
																					"	recommend_sort, " +
																					"	qqno, " +
																					"	description_show " +
																					") SELECT " +
																					"	id, " +
																					"	create_person, " +
																					"	create_time, " +
																					"	is_delete, " +
																					"	bigimg_path, " +
																					"	cloud_classroom, " +
																					"	description, " +
																					"	detailimg_path, " +
																					"	grade_name, " +
																					"	graduate_time, " +
																					"	live_time, " +
																					"	smallimg_path, " +
																					"	sort, " +
																					"	courseType, " +
																					"	1, " +
																					"	learnd_count, " +
																					"	original_cost, " +
																					"	current_price, " +
																					"	course_length, " +
																					"	menu_id, " +
																					"	course_type_id, " +
																					"	is_free, " +
																					"	class_template, " +
																					"	course_detail, " +
																					"	course_outline, " +
																					"	common_problem, " +
																					"	lecturer_id, " +
																					"	is_recommend, " +
																					"	recommend_sort, " +
																					"	qqno, " +
																					"	description_show " +
																					"FROM " +
																					"	oe_course where id="+courseId);
			courseDao.getNamedParameterJdbcTemplate().getJdbcOperations().
			update("update oe_course_preview set smallimg_path=?,bigimg_path=?,detailimg_path=?,course_detail=?,course_outline=?,common_problem=? where id=?",
					smallImgPath,smallImgPath,detailImgPath,courseDetail,courseOutline,commonProblem,Integer.valueOf(courseId));
	}


	@Override
	public boolean updateRec(String[] ids,int isRecommend) {
		// TODO Auto-generated method stub
		List<String> ids2 = new ArrayList();
		//如果是要推荐 那么就验证 推荐数量是否大于4
		if(isRecommend == 1){
			//校验是否被引用
			String hqlPre="from Course where isDelete=0 and isRecommend = 1";
			List<Course> list= dao.findByHQL(hqlPre);
			if(list.size() > 0){//只有原来大于0才执行
					for(int i = 0;i<ids.length;i++)
					{
						int j = 0;
						Iterator<Course> iterator = list.iterator();
						while(iterator.hasNext()){
							//剔除本次推荐的与已经推荐的重复的
							
							Course course = iterator.next();
							if(course.getId() == Integer.parseInt(ids[i])){//如果存在就把他剔除掉从list中
	//							list2.add(course);
								j =1;
							}
						}
						
						if(j == 0){
							ids2.add(ids[i]);
						}
					}
	
//				list.removeAll(list2);
			}else{
				for(int i=0;i<ids.length;i++)
				{
					ids2.add(ids[i]);
				}
			}
			//已经存在的数量 +  即将添加的数量
            if((list.size()+ids2.size()) > 12){
				//取消推荐数目限制
//            	return false;
            }
		}else{//如果是取消推荐
			for(int i=0;i<ids.length;i++)
			{
				ids2.add(ids[i]);
			}
		}
		
		String sql="select ifnull(min(recommend_sort),0) from oe_course where  is_delete=0 and is_recommend = 1";
		int i = dao.queryForInt(sql,null);//最小的排序
		
		for(String id:ids2){
			if(id == "" || id == null)
			{
				continue;
			}
			i = i -1;
			String hqlPre="from Course where  isDelete = 0 and id = ?";
	        Course course= dao.findByHQLOne(hqlPre,new Object[] {Integer.valueOf(id)});
            if(course !=null){
            	 course.setIsRecommend(isRecommend);
            	 course.setRecommendSort(i);
                 dao.update(course);
            }
        }
		return true;
	}
	@Override
	public boolean updateCityRec(String[] ids,int isRecommend) {
		// TODO Auto-generated method stub

		for(String id:ids){
			if(id == "" || id == null)
			{
				continue;
			}
			String hqlPre="from OffLineCity where  isDelete = 0 and id = ?";
			OffLineCity course= dao.findByHQLOne(hqlPre,new Object[] {Integer.valueOf(id)});
			if(course !=null){
				course.setIsRecommend(isRecommend);
				dao.update(course);
			}
		}
		return true;
	}

	@Override
	public void updateSortUpRec(Integer id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getRecommendSort();
         
         String hqlNext="from Course where recommendSort > (select recommendSort from Course where id= ? )  and isDelete=0 and online_course=1 and isRecommend = 1 order by recommendSort asc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getRecommendSort();
         
         coursePre.setRecommendSort(courseNextSort);
         courseNext.setRecommendSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
		
	}

	@Override
	public void updateCitySortUp(Integer id) {
		// TODO Auto-generated method stub
		String hqlPre="from OffLineCity where  isDelete=0 and id = ?";
		OffLineCity coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
		
		if(coursePre.getIsRecommend() == 0){
			throw new RuntimeException("排序只能选择推荐的线下课");
		}
		
		Integer coursePreSort=coursePre.getSort();
		String hqlNext="from OffLineCity where sort > (select sort from OffLineCity where id= ? )  and isDelete=0  and isRecommend = 1 order by sort asc";
		OffLineCity courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
		Integer courseNextSort=courseNext.getSort();

		coursePre.setSort(courseNextSort);
		courseNext.setSort(coursePreSort);

		dao.update(coursePre);
		dao.update(courseNext);

	}
	@Override
	public void updateCitySortDown(Integer id) {
		// TODO Auto-generated method stub
		String hqlPre="from OffLineCity where  isDelete=0 and id = ?";
		OffLineCity coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
		
		if(coursePre.getIsRecommend() == 0){
			throw new RuntimeException("排序只能选择推荐的线下课");
		}
		
		Integer coursePreSort=coursePre.getSort();
		
		String hqlNext="from OffLineCity where sort < (select sort from OffLineCity where id= ? ) and isRecommend = 1 and isDelete=0 order by sort desc";
		OffLineCity courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
		
		if(courseNext == null){
			throw new RuntimeException("此排序已是最小值了");
		}
		Integer courseNextSort=courseNext.getSort();

		coursePre.setSort(courseNextSort);
		courseNext.setSort(coursePreSort);

		dao.update(coursePre);
		dao.update(courseNext);
	}

	@Override
	public void updateSortDownRec(Integer id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getRecommendSort();
       
         String hqlNext="from Course where recommendSort < (select recommendSort from Course where id= ? ) and isRecommend = 1 and online_course=1 and isDelete=0 order by recommendSort desc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getRecommendSort();
         
         coursePre.setRecommendSort(courseNextSort);
         courseNext.setRecommendSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
	}

	
	@Override
	public List<Course> findByName(String name){
		List<Course> courses=dao.findEntitiesByProperty(Course.class, "gradeName", name);
		/*if(course!=null&&!course.isDelete()){
			return course;
		}else{
			return null;	
		}*/
		return courses;
		
	}

	@Override
	public void updateDescriptionShow(CourseVo courseVo) {
		 String hqlPre="from Course where id = ?";
         Course course= dao.findByHQLOne(hqlPre,new Object[] {courseVo.getId()});
         course.setDescriptionShow(courseVo.getDescriptionShow());
         dao.update(course);
	}

	@Override
	public Object lectereListByCourseIdAndRoleType(int roleType, String courseId) {
		// TODO Auto-generated method stub
		 Map<String,Object> params=new HashMap<String,Object>();
	        params.put("roleType", roleType);
	        params.put("courseId", courseId);
	        StringBuilder sql=new StringBuilder();
	        sql.append("SELECT " +
		        		"	ol.id, " +
		        		"	ol. name, " +
		        		"	CASE " +
		        		"WHEN grl.lecturer_id > 0 THEN " +
		        		"	1 " +
		        		"ELSE " +
		        		"	0 " +
		        		"END status " +
		        		"FROM " +
		        		"	oe_lecturer ol " +
		        		"LEFT JOIN course_r_lecturer grl ON ( " +
		        		"	ol.id = grl.lecturer_id " +
		        		"	AND grl.course_id =:courseId " +
		        		") " +
		        		"JOIN oe_course oc ON ( " +
		        		"	ol.menu_id = oc.menu_id " +
		        		"	AND oc.id =:courseId " +
		        		") " +
		        		"WHERE " +
		        		"	ol.role_type =:roleType " +
		        		" AND ol.is_delete = 0 ");

	        return  dao.getNamedParameterJdbcTemplate().queryForList(sql.toString(),params);
	}

	@Override
	public void saveTeachers(String gradeId, String courseId, String userName,
			List<String> roleTypes) {
		// TODO Auto-generated method stub
		Map<String,Object> param=new HashMap<String,Object>();
        String sql="delete from course_r_lecturer where course_id=:courseId ";
        param.put("courseId", courseId);
        dao.getNamedParameterJdbcTemplate().update(sql, param);
        if(roleTypes!=null&&roleTypes.size()>0)
        {
            for(String roleType1:roleTypes)
            {
                Map<String,Object> roleType1Param=new HashMap<String,Object>();
                roleType1Param.put("courseId",courseId);
                roleType1Param.put("lecturerId",roleType1);
                roleType1Param.put("createPerson",userName);
                roleType1Param.put("createTime",new Date());
                roleType1Param.put("id", UUID.randomUUID().toString().replace("-",""));
                String insertRoleType1="insert into course_r_lecturer(id,course_id,lecturer_id,is_delete,create_person,create_time) values(:id,:courseId,:lecturerId,0,:createPerson,:createTime) ";
                dao.getNamedParameterJdbcTemplate().update(insertRoleType1, roleType1Param);
            }
        }
	}

	@Override
	public Course findOpenCourseById(Integer id) {
		Course course=dao.findOneEntitiyByProperty(Course.class, "id", id);
		return  course;
	}
	
	@Override
	public Course findOpenCourseById(Integer id,String version) {
		Course course=dao.findOneEntitiyByProperty(Course.class, "id", id);
		if(course != null && version != null && version.equals(course.getVersion()) && !course.isDelete() && "1".equals(course.getStatus())) {
            return course;
        }
		return null;
	}

	@Override
	public void updateSentById(Integer id) {
		Course course = dao.findOneEntitiyByProperty(Course.class, "id", id);
		course.setSent(true);
		dao.update(course);
	}


	@Override
	public Course getPublicCourseById(Integer courseId) {
		String hql="from Course where 1=1 and isDelete=0 and id = ?";
        Course course= dao.findByHQLOne(hql, new Object[]{courseId});
		return course;
	}

	@Override
	public void deleteCourseByExamineId(Integer examineId, boolean falg) {
		String hqlPre="from Course where  examine_id = ?";
		Course course= dao.findByHQLOne(hqlPre,new Object[] {examineId});
		if(course !=null){
			course.setDelete(falg);
			dao.update(course);
		}
	}


	@Override
	public void updateCategoryInfo(String courseId) throws Exception {
		List<CategoryBean> cs = CCUtils.getAllCategories();
		createCourseCategories(courseId,cs);
		Thread.sleep(1000);
		cs = CCUtils.getAllCategories();
		createChapterCategories(courseId,cs);
	}

	@Override
	public String updateCourseVideoInfo(String id) {

		Map<String, String> vs = new HashMap<String, String>();
		Map<String, String> cs = new HashMap<String, String>();

		Map<String,Object> paramMap = new HashMap<String,Object>();
		Course c = dao.get(Integer.valueOf(id), Course.class);

		String msg = "";

		String sql = "select sp.id,sp.`name` from oe_course ke,oe_chapter zhang,oe_chapter jie,oe_chapter zsd,oe_video sp "+
				" where sp.chapter_id=zsd.id and zsd.parent_id=jie.id and jie.parent_id=zhang.id and zhang.parent_id=ke.id "+
				" and ke.id=:id and zhang.is_delete=0 and jie.is_delete=0 and zsd.is_delete=0 and sp.is_delete=0 and sp.video_id is null ";
		paramMap.put("id", id);

		List<Map<String, Object>> vsmp = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
		if (vsmp.size() <= 0) {
			return "ok";
		}

		for (Map<String, Object> map : vsmp) {
			vs.put(String.valueOf(map.get("id")),String.valueOf(map.get("name")));
		}


		List<String> categories = new ArrayList<String>();
		List<CategoryBean> allCategories = CCUtils.getAllCategories();
		for (CategoryBean categoryBean : allCategories) {
			if (categoryBean.getName().equals(c.getGradeName())) {
				List<CategoryBean> subs = categoryBean.getSubs();
				for (CategoryBean sub : subs) {
					categories.add(sub.getId());
				}
				break;
			}
		}
//		categories.clear();
//		categories.add("5C3F061265D9303B");
		for (String categoryid : categories) {
			for(int i=1; i<999999; i++){
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("categoryid", categoryid);
				paramsMap.put("userid", OnlineConfig.CC_USER_ID);
				paramsMap.put("num_per_page", "100");
				paramsMap.put("page", i+"");
				paramsMap.put("format", "json");
				long time = System.currentTimeMillis();
				String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,OnlineConfig.CC_API_KEY);
				String responsestr = APIServiceFunction.HttpRetrieve(Config.api_category_videos+"?" + requestURL);

				if (responsestr.contains("\"error\":")) {
					throw new RuntimeException("该课程有视频正在做转码处理<br>请过半小时之后再操作。");
				}

				Gson g = new GsonBuilder().create();
				Map<String, Object> mp = g.fromJson(responsestr, Map.class);
				Map<String, Object> root = (Map<String, Object>)mp.get("videos");
				ArrayList<Object> videos = (ArrayList<Object>)root.get("video");

				if (videos == null || videos.size() <= 0) {
					break;
				}

				for (Object object : videos) {
					Map<String, Object> video = (Map<String, Object>)object;

					String duration = video.get("duration").toString();
					double d = Double.valueOf(duration);
					String m = String.valueOf((int)d / 60);
					String s = String.valueOf((int)d % 60);
					m = m.length()==1 ? "0"+m : m;
					s = s.length()==1 ? "0"+s : s;
					String ms = m+":"+s;

					String vid = video.get("id").toString();
					String title = video.get("title").toString();

					if (cs.containsKey(title)) {
						double oldduration = Double.valueOf(cs.get(title).split("_#_")[2]);
						if (d > oldduration) {
							cs.put(title, vid+"_#_"+ms+"_#_"+duration);
						}
					} else {
						cs.put(title, vid+"_#_"+ms+"_#_"+duration);
					}
				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
			}
		}

		for(Map.Entry<String, String> video : vs.entrySet()){
			String vinfo = cs.get(video.getValue());
			if (vinfo != null) {
				String vid = vinfo.split("_#_")[0];
				String ms = vinfo.split("_#_")[1];
				sql = "update oe_video set video_id='"+vid+"',video_time='"+ms+"' where id='"+video.getKey()+"' ";
				dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
			} else{
				msg += (video.getValue()+"<br>");
			}
		}

		if (msg.length() > 0) {
			return "同步成功，但以下视频还未上传：<br>"+msg+"请使用客户端上传后再次同步";
		}
		return "ok";
	}

	public void createCourseCategories(String courseId,List<CategoryBean> cs) throws Exception{
		String name = null;
		List<Map<String, Object>> lst = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList("select o.grade_name from oe_course o where o.is_delete=0 and (o.type=2 or o.type=0) and id="+courseId);
		if (lst != null && lst.size() > 0) {
			name = lst.get(0).get("grade_name").toString();
		}

		boolean b = false;
		for (CategoryBean bean : cs) {
			if (name.equals(bean.getName())) {
				b = true;
				break;
			}
		}
		if (!b) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("userid", OnlineConfig.CC_USER_ID);
			paramsMap.put("name", name);
			paramsMap.put("format", "json");
			long time = System.currentTimeMillis();
			String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,OnlineConfig.CC_API_KEY);
			String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/category/create?" + requestURL);
			if (responsestr.contains("error")) {
				throw new RuntimeException("创建一级CC分类失败！");
			}
			Gson g = new GsonBuilder().create();
			Map<String, Object> mp = g.fromJson(responsestr, Map.class);
			Map<String, Object> category = (Map<String, Object>)mp.get("category");
		}
	}

	public void createChapterCategories(String courseId,List<CategoryBean> cs) throws Exception{

		List<Map<String, Object>> lst = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList("select o.grade_name,z.`name` from oe_course o,oe_chapter z "
						+ "where z.parent_id=o.id and z.`level`=2 and o.is_delete=0 and z.is_delete=0 and (o.type=2 or o.type=0) and o.id="+courseId);

		for (Map<String, Object> map : lst) {
			String grade_name = map.get("grade_name").toString();
			String name = map.get("name").toString();

			for (CategoryBean bean : cs) {
				if (grade_name.equals(bean.getName())) {
					boolean b = false;
					for (CategoryBean sub : bean.getSubs()) {
						if (sub.getName().equals(name)) {
							b = true;
							break;
						}
					}
					if (!b) {
						Map<String, String> paramsMap = new HashMap<String, String>();
						paramsMap.put("userid", OnlineConfig.CC_USER_ID);
						paramsMap.put("name", name);
						paramsMap.put("super_categoryid", bean.getId());
						paramsMap.put("format", "json");
						long time = System.currentTimeMillis();
						String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,OnlineConfig.CC_API_KEY);
						String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/category/create?" + requestURL);
						if (responsestr.contains("error")) {
							throw new RuntimeException("创建二级CC分类失败！");
						}
						Gson g = new GsonBuilder().create();
						Map<String, Object> mp = g.fromJson(responsestr, Map.class);
						Map<String, Object> category = (Map<String, Object>)mp.get("category");
						Thread.sleep(500);
					}
				}
			}
		}
	}

	@Override
	public String updateCourseVideo(String id) {

		Map<String, String> vs1 = new HashMap<String, String>();
		Map<String, String> cs = new HashMap<String, String>();

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Course c = dao.get(Integer.valueOf(id), Course.class);

		String msg = "";

		String sql = "SELECT oc.`grade_name` FROM `oe_course` oc WHERE oc.id=:id";
		paramMap.put("id", id);
		List<String> courseNames = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap,String.class);
		String courseName = courseNames.get(0);

		List<String> categories = new ArrayList<String>();
		List<CategoryBean> allCategories = CCUtils.getAllCategories();
		for (CategoryBean categoryBean : allCategories) {
			if (categoryBean.getName().equals(CNAME)) {
				categories.add(categoryBean.getId());
				break;
			}
		}
		for (String categoryid : categories) {
			for (int i = 1; i < 999999; i++) {
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("categoryid", categoryid);
				paramsMap.put("userid", OnlineConfig.CC_USER_ID);
				paramsMap.put("num_per_page", "100");
				paramsMap.put("page", i + "");
				paramsMap.put("format", "json");
				long time = System.currentTimeMillis();
				String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time, OnlineConfig.CC_API_KEY);
				String responsestr = APIServiceFunction.HttpRetrieve(Config.api_category_videos + "?" + requestURL);

				if (responsestr.contains("\"error\":")) {
					throw new RuntimeException("该课程有视频正在做转码处理<br>请过半小时之后再操作。");
				}

				Gson g = new GsonBuilder().create();
				Map<String, Object> mp = g.fromJson(responsestr, Map.class);
				Map<String, Object> root = (Map<String, Object>) mp.get("videos");
				ArrayList<Object> videos = (ArrayList<Object>) root.get("video");

				if (videos == null || videos.size() <= 0) {
					break;
				}

				for (Object object : videos) {
					Map<String, Object> video = (Map<String, Object>) object;

					String duration = video.get("duration").toString();
//					double d = Double.valueOf(duration);
//					int totalM = (int)d/(60);
//					int h = totalM/60;
//					double m = Double.valueOf(totalM-h*60) / 60;
//					DecimalFormat df   = new DecimalFormat("######0.00");
//					m = Double.valueOf(df.format(m));
					String hm = String.valueOf(Double.valueOf(duration).intValue()/60);
//					String hm = String.valueOf(h + m);

					String vid = video.get("id").toString();
					String title = video.get("title").toString();

//					if (cs.containsKey(title)) {
//						double oldduration = Double.valueOf(cs.get(title).split("_#_")[2]);
//						if (d > oldduration) {
//							cs.put(title, vid + "_#_" + hm + "_#_" + duration);
//						}
//					} else {
						cs.put(title, vid + "_#_" + hm + "_#_" + duration);
//					}
				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
				}
			}
		}

			String vinfo = cs.get(courseName);
			if (vinfo != null) {
				String vid = vinfo.split("_#_")[0];
				String ms = vinfo.split("_#_")[1];
				sql = "update oe_course set direct_id='" + vid + "',course_length='" + ms + "' where id=" + id + "";
				dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
			} else {
				msg += (courseName + "<br>");
			}

		if (msg.length() > 0) {
			return "以下视频还未上传：<br>" + msg + "请使用客户端上传后再次同步";
		}
		return "ok";
	}

	@Override
	public void addCourseCity(String city) {
		// TODO Auto-generated method stub
		/**
		 * 添加前，看存在此城市，如果存在那么就不添加
		 */
		if(!findCourseCityByName(city)){
			Map<String,Object> params1=new HashMap<String,Object>();
			String sql="SELECT IFNULL(MAX(sort),0) as sort FROM oe_offline_city ";
			List<OffLineCity> temp = dao.findEntitiesByJdbc(OffLineCity.class, sql, params1);
			int sort;
			if(temp.size()>0){
				sort=temp.get(0).getSort().intValue()+1;
			}else{
				sort=1;
			}
			User user = (User) UserHolder.getRequireCurrentUser(); 
			String savesql=" insert into oe_offline_city (create_person,create_time,city_name,sort)"+
					" values ('"+user.getCreatePerson()+"',now(),'"+city+"','"+sort+"')";
			 Map<String,Object> params=new HashMap<String,Object>();
			dao.getNamedParameterJdbcTemplate().update(savesql, params);
		}
	}
	
	@Override
	public Boolean findCourseCityByName(String city) {
		 Map<String,Object> params=new HashMap<String,Object>();
		 List<Integer>  list = dao.getNamedParameterJdbcTemplate().
				 queryForList("select count(*) as c from oe_offline_city o where  o.city_name = '"+city+"'",params,Integer.class);
		 System.out.println("cityList"+list.size());
		 if(list!=null && null!= list.get(0) && list.get(0)>0){
			 return true;
		 }
		 return false;
	}
	
	@Override
	public OffLineCity findCourseCityByName(Integer cityId) {
		String hqlPre="from OffLineCity where id  = '"+cityId+"'";;
		OffLineCity object =  dao.findByHQLOne(hqlPre,new Object[] {});
		return object;
	}
	
	@Override
	public void deleteCourseCityByName(String city) {
		String savesql=" delete from oe_offline_city where city_name = '"+city+"'";
		Map<String,Object> params=new HashMap<String,Object>();
		dao.getNamedParameterJdbcTemplate().update(savesql, params);
	}
	
	@Override
	public Page<OffLineCity>  getCourseCityList(OffLineCity searchVo,Integer pageNumber,Integer pageSize) {
		String sql="select  *  from  oe_offline_city where  is_delete = 0 and status = 1 ";
		//List<OffLineCity> list= dao.findByHQL(hqlPre);
		 if(searchVo.getCityName()!=null && !"".equals(searchVo.getCityName())){
			 sql+=" and city_name = '"+searchVo.getCityName()+"'";
		 }
		sql+=" order by is_recommend desc,sort desc ";
		Map<String,Object> params=new HashMap<String,Object>();
		Page<OffLineCity> courseVos = dao.findPageBySQL(sql, params, OffLineCity.class, pageNumber, pageSize);
		return courseVos;
	}

	@Override
	public void updateCourseCityStatus(Integer courseId) {
		// TODO Auto-generated method stub

		
		String hql="from Course where 1=1 and isDelete=0 and id = ?";
        Course course= dao.findByHQLOne(hql, new Object[]{courseId});
        
        Map<String,Object> params=new HashMap<String,Object>();
        /*
         * 课程中所有关于这个城市的
         */
        List<Integer>  list = dao.getNamedParameterJdbcTemplate().
				 queryForList("select count(*) as c from oe_course o where  o.status=1 and "
				 		+ "o.city = '"+course.getCity()+"'",params,Integer.class);
        
        Integer status = 0;
        if(list!=null && null!= list.get(0) && list.get(0)>0){
        	status = 1;
		}
        String savesql=" update  oe_offline_city  set status = '"+status+"' where city_name = '"+course.getCity()+"' ";
		dao.getNamedParameterJdbcTemplate().update(savesql, params);
	}

	@Override
	public void deleteCourseCityStatus(Integer courseId) {

		String hql="from Course where 1=1 and isDelete=0 and id = ?";
        Course course= dao.findByHQLOne(hql, new Object[]{courseId});
        
        Map<String,Object> params=new HashMap<String,Object>();
        
        //查出所有为禁用的
        List<Integer>  list = dao.getNamedParameterJdbcTemplate().
				 queryForList("select count(*) as c from oe_course o where o.is_delete=0  and "
				 		+ "o.city = '"+course.getCity()+"'",params,Integer.class);
        
        Integer status = 1;
        if(list!=null && null!= list.get(0) && list.get(0)>0){
        	status = 0;
		}
        String savesql=" update  oe_offline_city  set  is_delete= '"+status+"' where city_name = '"+course.getCity()+"' ";
		dao.getNamedParameterJdbcTemplate().update(savesql, params);
	}

	@Override
	public void updateCourseCity(OffLineCity offLineCity) {
		// TODO Auto-generated method stub
		String savesql=" update  oe_offline_city  set icon = '"+offLineCity.getIcon()+"' where id = '"+offLineCity.getId()+"' ";
		Map<String,Object> params=new HashMap<String,Object>();
		dao.getNamedParameterJdbcTemplate().update(savesql, params);
	}

	@Override
	public Course findCourseInfoById(Integer id) {
		Course course = dao.get(id, Course.class);
		if((course.getCollection()==null || !course.getCollection())&&course.getDirectId()!=null){
			String audioStr="";
			if(course.getMultimediaType()==2){
				audioStr = "_2";
			}
			String src = "https://p.bokecc.com/flash/single/"+ OnlineConfig.CC_USER_ID+"_"+course.getDirectId()+"_false_"+OnlineConfig.CC_PLAYER_ID+"_1"+audioStr+"/player.swf";
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String playCode = "";
			playCode+="<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ";
			playCode+="		codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" ";
			playCode+="		width=\"600\" ";
			playCode+="		height=\"490\" ";
			playCode+="		id=\""+uuid+"\">";
			playCode+="		<param name=\"movie\" value=\""+src+"\" />";
			playCode+="		<param name=\"allowFullScreen\" value=\"true\" />";
			playCode+="		<param name=\"allowScriptAccess\" value=\"always\" />";
			playCode+="		<param value=\"transparent\" name=\"wmode\" />";
			playCode+="		<embed src=\""+src+"\" ";
			playCode+="			width=\"600\" height=\"490\" name=\""+uuid+"\" allowFullScreen=\"true\" ";
			playCode+="			wmode=\"transparent\" allowScriptAccess=\"always\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" ";
			playCode+="			type=\"application/x-shockwave-flash\"/> ";
			playCode+="	</object>";
			course.setPlayCode(playCode);
		}

		if(course.getCollection()!=null && course.getCollection()){
			List<Course> courses = courseDao.getCourseByCollectionId(course.getId());
			course.setCourseInfoList(courses);
		}
		DetachedCriteria menudc = DetachedCriteria.forClass(Menu.class);
		menudc.add(Restrictions.eq("id", Integer.valueOf(course.getMenuId())));
		Menu menu = dao.findEntity(menudc);
		if(menu!=null){
			course.setCourseMenu(menu.getName());
		}
		return course;
	}

	public String createWebinar(Course entity) {
		Webinar webinar = new Webinar();
		webinar.setSubject(entity.getGradeName());
		webinar.setIntroduction(entity.getDescription());
		Date start = entity.getStartTime();
		String start_time = start.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		webinar.setStart_time(start_time);
		webinar.setHost(entity.getLecturer());
		webinar.setLayout(entity.getDirectSeeding().toString());
		OnlineUser u = onlineUserService.getOnlineUserByUserId(entity.getUserLecturerId());
		webinar.setUser_id(u.getVhallId());
		String webinarId = VhallUtil.createWebinar(webinar);

		VhallUtil.setActiveImage(webinarId, VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
		VhallUtil.setCallbackUrl(webinarId, vhall_callback_url, vhall_private_key);
		return webinarId;
	}

	public String updateWebinar(Course entity) {
		//更新封面
		VhallUtil.setActiveImage(entity.getDirectId(), VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
		Webinar webinar = new Webinar();
		webinar.setId(entity.getDirectId()+"");
		webinar.setSubject(entity.getGradeName());
		webinar.setIntroduction(entity.getDescription());
		Date start = entity.getStartTime();
		String start_time = start.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		webinar.setStart_time(start_time);
		webinar.setHost(entity.getLecturer());
		webinar.setLayout(entity.getDirectSeeding()+"");
		return VhallUtil.updateWebinar(webinar);
	}
}
