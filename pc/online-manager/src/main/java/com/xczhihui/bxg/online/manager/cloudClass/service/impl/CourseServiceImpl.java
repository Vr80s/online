package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.CoursePreview;
import com.xczhihui.bxg.online.common.domain.Grade;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.common.domain.TeachMethod;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseSubscribeDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.LecturerService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseLecturVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LecturerVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.utils.subscribe.Subscribe;

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
    @Value("${ENV_FLAG}")
	private String envFlag;
    
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
    public List<CourseLecturVo>  getCourseAndLecturerlist(Integer number, Integer courseType,Integer pageNumber, Integer pageSize) {

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
		if(type != null) sql = sql + " and type = "+type;
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
		//当课程存在密码时，设置的当前价格失效，改为0.0
		if(courseVo.getCoursePwd()!=null && !"".equals(courseVo.getCoursePwd().trim())){
			courseVo.setCurrentPrice(0.0);
		}
		Course course = new Course();
		course.setGradeName(courseVo.getCourseName()); //课程名称
		course.setClassTemplate(courseVo.getClassTemplate()); //课程名称模板mv
		course.setMenuId(courseVo.getMenuId()); //学科id
		course.setCourseTypeId(courseVo.getCourseTypeId()); //课程类别id
		course.setCourseType(courseVo.getCourseType()); //授课方式
		course.setCourseLength(courseVo.getCourseLength()); //课程时长
		course.setOriginalCost(courseVo.getOriginalCost()); //原价格
		course.setCurrentPrice(courseVo.getCurrentPrice()); //现价格
		if(/*0==course.getOriginalCost()&&*/0==course.getCurrentPrice()){
			course.setIsFree(true); //免费
		}else{
			course.setIsFree(false); //收费
		}
		course.setSort(sort); //排序
		//course.setType(1);
		course.setLearndCount(courseVo.getLearndCount()); //请填写一个基数，统计的时候加上这个基数
		course.setCreatePerson(UserHolder.getCurrentUser().getLoginName()); //当前登录人
		course.setCreateTime(new Date()); //当前时间
		course.setStatus('0'+""); //状态
		course.setDescription(courseVo.getDescription());//课程描述
		course.setCloudClassroom(courseVo.getCloudClassroom());//云课堂连接
		course.setIsRecommend(courseVo.getIsRecommend());//
		course.setRecommendSort(courseVo.getRecommendSort());//
		course.setQqno(courseVo.getQqno());
		course.setDescriptionShow(0);
		course.setDefaultStudentCount(courseVo.getDefaultStudentCount());
		//yuruixin-2017-08-16
		course.setMultimediaType(courseVo.getMultimediaType());
		if(courseVo.getServiceType()==1){//判断添加职业课/微课
			course.setClassRatedNum(courseVo.getClassRatedNum());
			course.setServiceType(1);
			course.setGradeQQ(courseVo.getGradeQQ());
			course.setDefaultStudentCount(courseVo.getDefaultStudentCount());
			if(!course.isFree()){
				//添加微课，自动为微课创建一个报名中的班级
				String savesql=" insert into oe_grade (create_person,create_time,is_delete,course_id,name,qqno,status,sort,grade_status,student_amount,default_student_count) " +
						" values ('"+course.getCreatePerson()+"',now(),0,(select AUTO_INCREMENT courseId FROM information_schema.TABLES WHERE  TABLE_NAME ='oe_course' and TABLE_SCHEMA='online' ),'"+course.getClassTemplate()+"1期',"+course.getGradeQQ()+",1,1,1,"+course.getClassRatedNum()+"," + course.getDefaultStudentCount()+")";
				dao.getNamedParameterJdbcTemplate().update(savesql, params);
			}
		}else{//添加职业课
			course.setClassRatedNum(0);
			course.setServiceType(0);
		}
		//增加密码和老师
		course.setCoursePwd(courseVo.getCoursePwd());
		course.setUserLecturerId(courseVo.getUserLecturerId());
		course.setOnlineCourse(courseVo.getOnlineCourse());
		course.setAddress(courseVo.getAddress());
		if(course.getOnlineCourse() == 1){
			course.setStartTime(courseVo.getStartTime());
			course.setEndTime(courseVo.getEndTime());
		}
		dao.save(course);
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
		String sql = "SELECT oc.id as id,oc.grade_name as courseName,oc.class_template as classTemplate,om.name as xMenuName,st.name as scoreTypeName,oc.multimedia_type multimediaType,oc.start_time startTime,oc.end_time endTime,oc.address,"
				+ "tm.name as teachMethodName,oc.course_length as courseLength,oc.learnd_count as learndCount,oc.course_pwd as coursePwd,oc.grade_qq gradeQQ,oc.default_student_count defaultStudentCount,"
				+ "oc.create_time as createTime,oc.status as status ,oc.is_free as isFree,oc.original_cost as originalCost,"
				+ "oc.current_price as currentPrice,oc.description as description ,oc.cloud_classroom as cloudClassroom ,"
				+ "oc.menu_id as menuId,oc.course_type_id as courseTypeId,oc.courseType as courseType,oc.qqno,oc.grade_student_sum as classRatedNum,oc.user_lecturer_id as userLecturerId FROM oe_course oc "
				+ "LEFT JOIN oe_menu om ON om.id = oc.menu_id LEFT JOIN score_type st ON st.id = oc.course_type_id "
				+ "LEFT JOIN teach_method tm ON tm.id = oc.courseType WHERE oc.id = :courseId";
		
//		String sql="select c.learnd_count as learndCount , c.grade_name as courseName ,c.bigimg_path as bigImgPath,c.detailimg_path as detailImgPath,c.smallimg_path as smallingPath "
//				+ ",c.menu_number as menuNameSecond,c.live_time as liveTime ,c.graduate_time as graduateTime "
//				+ ",c.lecturer_Id as lecturerId ,c.description as description,l.`name` as lecturerName from oe_course c, oe_lecturer l "
//				+ "where c.lecturer_Id = l.id AND c.id=:courseId ";
		List<CourseVo> courseVoList=dao.findEntitiesByJdbc(CourseVo.class, sql, paramMap);
//		if(courseVoList!=null&&courseVoList.size()>0){
//			String menuNumberSecond = courseVoList.get(0).getMenuNameSecond();
//			String menuNumberFirst= courseVoList.get(0).getMenuNameSecond().substring(0, 4);
//			paramMap.put("menuNumberSecond", menuNumberSecond);
//			paramMap.put("menuNumberFirst", menuNumberFirst);
//			paramMap.put("lecturerId", courseVoList.get(0).getLecturerId());
//			String sqloeMenuSecound = "SELECT name as name from oe_menu where number =:menuNumberSecond";
//			List<MenuVo> MenuVoList = dao.findEntitiesByJdbc(MenuVo.class, sqloeMenuSecound, paramMap);
//			courseVoList.get(0).setMenuNameSecond(MenuVoList.get(0).getName());
			
//			if(MenuVoList!=null&&MenuVoList.size()>0){
//				String sqloeMenuFirst="SELECT name as name from oe_menu where level =1 AND number =:menuNumberFirst";
//				List<MenuVo> MenuVoListFist = dao.findEntitiesByJdbc(MenuVo.class, sqloeMenuFirst, paramMap);
//				courseVoList.get(0).setMenuName(MenuVoListFist.get(0).getName());
//			}
//		}
		sql = "select sum(IFNULL(t.default_student_count,0)) from oe_grade t where t.course_id = ?";
		courseVoList.get(0).setLearndCount(courseDao.queryForInt(sql, new Object[]{id}));//累计默认报名人数
		return courseVoList;
	}


	@Override
	public void updateCourse(CourseVo courseVo) {
		Course course = dao.findOneEntitiyByProperty(Course.class, "id", courseVo.getId());
		//当课程存在密码时，设置的当前价格失效，改为0.0
		if(courseVo.getCoursePwd()!=null && !"".equals(courseVo.getCoursePwd().trim())){
			courseVo.setCurrentPrice(0.0);
		}

		course.setGradeName(courseVo.getCourseName()); //课程名称
		course.setClassTemplate(courseVo.getClassTemplate()); //课程名称模板
		course.setMenuId(courseVo.getMenuId()); //学科的id
		course.setCourseTypeId(courseVo.getCourseTypeId()); //课程类别id
		course.setCourseType(courseVo.getCourseType()); //授课方式id
		course.setCourseLength(courseVo.getCourseLength()); //课程时长
		//course.setIsFree(courseVo.getIsFree()); //是否免费
		course.setOriginalCost(courseVo.getOriginalCost()); //原价格
		course.setCurrentPrice(courseVo.getCurrentPrice()); //现价格
		course.setMultimediaType(courseVo.getMultimediaType());
		//增加密码和老师
		course.setCoursePwd(courseVo.getCoursePwd());
		course.setUserLecturerId(courseVo.getUserLecturerId());
		course.setAddress(courseVo.getAddress());
		course.setDefaultStudentCount(courseVo.getDefaultStudentCount());
		
//		if(0==course.getOriginalCost()&&0==course.getCurrentPrice()){
		if(0==course.getCurrentPrice()){
			course.setIsFree(true); //免费
		}else{
			course.setIsFree(false); //收费
			if(course.getServiceType()==1){ //微课自动建班级
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("courseId",course.getId());
				String sql="select id from oe_grade where is_delete=0 and grade_status = 1 and course_id =:courseId and curriculum_time is null  and "+
						"  ifnull(student_count+default_student_count,0)< student_amount order by create_time  limit 1 ";
				List<Map<String, Object>> grades = dao.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
				//存在报名未结束的班级
				if (grades.size() <=0) {
					//查看上一个班级是第几期，要在这期上面加1
					sql="select count(id)+1 number,(select AUTO_INCREMENT gradeId FROM information_schema.TABLES WHERE  TABLE_NAME ='oe_grade' and TABLE_SCHEMA='online' ) id  from oe_grade  where course_id=:courseId";
					List<Map<String, Object>> gradeInfos= dao.getNamedParameterJdbcTemplate().queryForList(sql,paramMap);
					Map<String, Object> gradeInfo=gradeInfos.get(0);
					Integer default_count=Integer.valueOf(gradeInfo.get("number").toString())==1 ? course.getDefaultStudentCount() : 0;
					//修改微课，自动为微课创建一个报名中的班级
					String savesql=" insert into oe_grade (create_person,create_time,is_delete,course_id,name,qqno,status,sort,grade_status,student_amount,default_student_count) " +
							" values ('"+course.getCreatePerson()+"',now(),0,"+course.getId()+",'"+course.getClassTemplate()+gradeInfo.get("number")+"期',"+courseVo.getGradeQQ()+",1,1,1,"+course.getClassRatedNum()+"," + default_count+")";
					dao.getNamedParameterJdbcTemplate().update(savesql, paramMap);
				}
			}
		}
		//course.setCourseDescribe(courseVo.getCourseDescribe()); //课程简介
		course.setDescription(courseVo.getDescription());//课程描述
		course.setCloudClassroom(courseVo.getCloudClassroom());//云课堂连接
		course.setQqno(courseVo.getQqno());
		course.setLearndCount(courseVo.getLearndCount());
		course.setClassRatedNum(courseVo.getClassRatedNum());//班级额定人数
		course.setGradeQQ(courseVo.getGradeQQ());
		course.setDefaultStudentCount(courseVo.getDefaultStudentCount());
		course.setOnlineCourse(courseVo.getOnlineCourse());
		course.setStartTime(courseVo.getStartTime());
		course.setEndTime(courseVo.getEndTime());
		dao.update(course);
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
         
         String hqlNext="from Course where sort > (select sort from Course where id= ? ) and type is null  and isDelete=0 order by sort asc";
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
		
		String hqlNext="from Course where sort > (select sort from Course where id= ? ) and type is null and online_course=1 and isDelete=0 order by sort asc";
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
         String hqlNext="from Course where sort < (select sort from Course where id= ? ) and type is null  and isDelete=0 order by sort desc";
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
		String hqlNext="from Course where sort < (select sort from Course where id= ? ) and type is null and online_course=1 and isDelete=0 order by sort desc";
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
			String courseOutline, String commonProblem) {
		Course c = courseDao.findOneEntitiyByProperty(Course.class, "id", Integer.valueOf(courseId));
		c.setSmallImgPath(smallImgPath);
		c.setBigImgPath(smallImgPath);
		c.setDetailImgPath(detailImgPath);
		c.setCourseDetail(courseDetail);
		c.setCourseOutline(courseOutline);
		c.setCommonProblem(commonProblem);
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
				if(imgArr.length>1)
					img1 = imgArr[1];
				if(imgArr.length>2)
					img2 = imgArr[2];
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
		if (courseDetail == null) courseDetail = "";
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
		if(isRecommend == 1)//如果是要推荐 那么就验证 推荐数量是否大于4
		{
			//校验是否被引用
			String hqlPre="from Course where isDelete=0 and isRecommend = 1";
			List<Course> list= dao.findByHQL(hqlPre);
			if(list.size() > 0){//只有原来大于0才执行
//				List<Course> list2 = new ArrayList<Course>();
				
					for(int i = 0;i<ids.length;i++)
					{
						int j = 0;
						Iterator<Course> iterator = list.iterator();
						while(iterator.hasNext()){//剔除本次推荐的与已经推荐的重复的
							
							Course course = iterator.next();
							if(course.getId() == Integer.parseInt(ids[i])){//如果存在就把他剔除掉从list中
	//							list2.add(course);
								j =1;
							}
						}
						
						if(j == 0){
							System.out.println(" ["+i+"]"+ids[i]);
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
            if((list.size()+ids2.size()) > 4){
            	return false;
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
	public void updateSortUpRec(Integer id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getRecommendSort();
         
         String hqlNext="from Course where recommendSort < (select recommendSort from Course where id= ? )  and isDelete=0 and isRecommend = 1 order by recommendSort desc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getRecommendSort();
         
         coursePre.setRecommendSort(courseNextSort);
         courseNext.setRecommendSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
		
	}

	@Override
	public void updateSortDownRec(Integer id) {
		// TODO Auto-generated method stub
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getRecommendSort();
         String hqlNext="from Course where recommendSort > (select recommendSort from Course where id= ? ) and isRecommend = 1  and isDelete=0 order by recommendSort asc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getRecommendSort();
         
         coursePre.setRecommendSort(courseNextSort);
         courseNext.setRecommendSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
	}

	
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
		if(course != null && version != null && version.equals(course.getVersion()) && !course.isDelete() && "1".equals(course.getStatus()))
			return  course;
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

}
