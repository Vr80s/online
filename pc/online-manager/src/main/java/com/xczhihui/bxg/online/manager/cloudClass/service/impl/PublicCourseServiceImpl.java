package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseSubscribeDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.PublicCourseDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.LecturerService;
import com.xczhihui.bxg.online.manager.cloudClass.service.PublicCourseService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.ChangeCallbackVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.user.service.OnlineUserService;
import com.xczhihui.bxg.online.manager.utils.subscribe.Subscribe;
import com.xczhihui.bxg.online.manager.vhall.VhallUtil;
import com.xczhihui.bxg.online.manager.vhall.bean.Webinar;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("publicCourseServiceImpl")
public class PublicCourseServiceImpl extends OnlineBaseServiceImpl implements PublicCourseService {
	 @Autowired
    private LecturerService lecturerService;
    @Autowired
    private PublicCourseDao publicCourseDao;
    @Autowired
    private CourseService courseService; 
    @Autowired
    private CourseSubscribeDao courseSubscribeDao;

	@Autowired
	private OnlineUserService  onlineUserService;
	@Autowired
	private CourseDao  courseDao;
	
	@Value("${ENV_FLAG}")
	private String envFlag;
	@Value("${LIVE_VHALL_USER_ID}")
	private String liveVhallUserId;
	@Value("${vhall_callback_url}")
	String vhall_callback_url;
	@Value("${vhall_private_key}")
	String vhall_private_key;
    
	@Override
	public List<Menu> getMenus() {
		
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="SELECT id,name FROM oe_menu where is_delete = 0 and name <> '全部' and status=1 and yun_status = 1";
		dao.findEntitiesByJdbc(MenuVo.class, sql, params);
	    return dao.findEntitiesByJdbc(Menu.class, sql, params);
	}
	@Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,int pageSize) {
		Page<CourseVo> page = publicCourseDao.findCloudClassCoursePage(courseVo, pageNumber, pageSize);
    	return page;
	}
	@Override
	public List<Lecturer> getTeacher(Integer menuId) {
		String sql="select * from oe_lecturer where is_delete=0 and role_type =1 and menu_id ="+menuId;
		List<Lecturer>  Lecturers= dao.findEntitiesByJdbc(Lecturer.class,sql, new HashMap<String , Object>());
		return Lecturers;
		
	}
	@Override
	public void addCourse(CourseVo courseVo) {
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
		//当课程存在密码时，设置的当前价格失效，改为0.0 yuruixin20170819
		if(courseVo.getCoursePwd()!=null && !"".equals(courseVo.getCoursePwd().trim())){
			courseVo.setCurrentPrice(0.0);
		}
		Course entity= new Course();
		entity.setGradeName(courseVo.getCourseName()); //课程名称
		entity.setMenuId(courseVo.getMenuId()); //学科的id
		entity.setCourseLength(courseVo.getCourseLength()); //课程时长
		entity.setCreatePerson(UserHolder.getCurrentUser().getLoginName()); //当前登录人
		entity.setCreateTime(new Date()); //当前时间
		entity.setStatus('0' + ""); //状态
		entity.setSort(sort); //排序
		entity.setEssenceSort(essenceSort); //精品推荐排序
		entity.setStartTime(courseVo.getStartTime());//直播开始时间
		entity.setEndTime(courseVo.getEndTime());//直播结束时间
		
//		entity.setDirectId(courseVo.getDirectId());//直播间ID
		entity.setType(1);//课程分类 1:公开直播课
		entity.setSmallImgPath(courseVo.getSmallingPath());//课程展示图
		entity.setBigImgPath(courseVo.getSmallingPath());
		entity.setDescriptionShow(0);//'不展示(0)，展示（1）'
		entity.setDirectSeeding(courseVo.getDirectSeeding());//直播布局
		entity.setEndLineNumber(0);//结束时在线人数
		entity.setFlowersNumber(0);//鲜花数
		entity.setHighestNumberLine(0);//最高在线人数
		entity.setPv(0);//浏览数
		entity.setIsRecommend(0);//不推荐(0)，推荐（1）
		entity.setLearndCount(0);//已学人数
		entity.setDelete(false);//不删除
//		entity.setExternalLinks(courseVo.getExternalLinks());//链接
	
		entity.setClassRatedNum(courseVo.getClassRatedNum());//班级额定人数
		entity.setDefaultStudentCount(0);
		entity.setServiceType(courseVo.getServiceType());
		
		/*2017.08.10  yuruixin*/
		entity.setOriginalCost(courseVo.getOriginalCost());
		entity.setCurrentPrice(courseVo.getCurrentPrice());
		entity.setDescription(courseVo.getDescription());
		entity.setCourseType(courseVo.getCourseType());
		entity.setCourseTypeId(courseVo.getCourseTypeId());
		/*2017.08.10  yuruixin*/
		if (entity.getClassRatedNum() == null) {
			entity.setClassRatedNum(0);
		}
		if (entity.getServiceType() == null) {
			entity.setServiceType(0);
		}
//		if(0==entity.getOriginalCost()&&0==entity.getCurrentPrice()){
		if(0==entity.getCurrentPrice()){
			entity.setIsFree(true); //免费
		}else{
			entity.setIsFree(false);
		}
		
		/*entity.setLecturerId(courseVo.getLecturerId());//教师ID
		entity.setTeacherImgPath(courseVo.getTeacherImgPath());//教师头像
*/		
		entity.setUserLecturerId(courseVo.getUserLecturerId());
		//新增课程密码
		entity.setCoursePwd(courseVo.getCoursePwd());
		entity.setVersion(UUID.randomUUID().toString().replace("-",""));
		String webinarId = createWebinar(entity);
		
		entity.setDirectId(webinarId);
		entity.setLiveStatus(2);//将直播状态设为1
		dao.save(entity);
		Subscribe.setting(entity.getId(), courseService, courseSubscribeDao);
	}
	@Override
	public void updateCourse(CourseVo courseVo) {
		
		//当课程存在密码时，设置的当前价格失效，改为0.0 yuruixin20170819
		if(courseVo.getCoursePwd()!=null && !"".equals(courseVo.getCoursePwd().trim())){
			courseVo.setCurrentPrice(0.0);
		}
		Course  entity= dao.findOneEntitiyByProperty(Course.class, "id", courseVo.getId());
		entity.setSmallImgPath(courseVo.getSmallingPath());//课程展示图
		entity.setBigImgPath(courseVo.getSmallingPath());
		entity.setGradeName(courseVo.getCourseName()); //课程名称
		entity.setMenuId(courseVo.getMenuId()); //学科的id
		
		entity.setCourseLength(courseVo.getCourseLength()); //课程时长
		entity.setStartTime(courseVo.getStartTime());//直播开始时间
		entity.setEndTime(courseVo.getEndTime());//直播结束时间
		entity.setDirectSeeding(courseVo.getDirectSeeding());//直播方式
		entity.setDirectId(courseVo.getDirectId());//直播间ID
		entity.setExternalLinks(courseVo.getExternalLinks());//链接
		
		//修改  原来的讲师id变为现在的  userid
		entity.setUserLecturerId(courseVo.getUserLecturerId());//教师ID
//		entity.setLecturerId(courseVo.getLecturerId());//教师ID
		//entity.setTeacherImgPath(courseVo.getTeacherImgPath());//教师头像
		
		entity.setDefaultStudentCount(courseVo.getDefaultStudentCount());
		/*2017.08.10  yuruixin*/
		entity.setOriginalCost(courseVo.getOriginalCost());
		entity.setCurrentPrice(courseVo.getCurrentPrice());
		entity.setDescription(courseVo.getDescription());
//		if(0==entity.getOriginalCost()&&0==entity.getCurrentPrice()){
		if(0==entity.getCurrentPrice()){
			entity.setIsFree(true); //免费
		}else{
			entity.setIsFree(false);
		}
		//修改课程密码
		entity.setCoursePwd(courseVo.getCoursePwd());
		entity.setVersion(UUID.randomUUID().toString().replace("-",""));
		/*2017.08.10  yuruixin*/
		dao.update(entity);
		updateWebinar(entity);
		
		Subscribe.setting(courseVo.getId(), courseService, courseSubscribeDao);
	}
	@Override
	public void updateSortUp(Integer id) {
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getSort();
         
         String hqlNext="from Course where sort > (select sort from Course where id= ? )  and isDelete=0 and type=1 order by sort asc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getSort();
         
         coursePre.setSort(courseNextSort);
         courseNext.setSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
		
	}
	@Override
	public void updateSortDown(Integer id) {
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getSort();
         String hqlNext="from Course where sort < (select sort from Course where id= ? )  and isDelete=0 and type=1 order by sort desc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getSort();
         
         coursePre.setSort(courseNextSort);
         courseNext.setSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
	}
	
	/** 
	 * Description：创建直播间
	 * @param entity
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@Override
    public String createWebinar(Course entity) {
		Webinar webinar = new Webinar();
		webinar.setSubject(entity.getGradeName());
		webinar.setIntroduction(entity.getDescription());
		Date start = entity.getStartTime();
		String start_time = start.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		webinar.setStart_time(start_time);
		OnlineUser ou = getLecturer(entity.getUserLecturerId());
		String teacherName = null;
		if(ou!=null) {
            teacherName = ou.getName();
        }
		webinar.setHost(teacherName);
		webinar.setLayout(entity.getDirectSeeding().toString());
		OnlineUser u = onlineUserService.getOnlineUserByUserId(entity.getUserLecturerId());
		webinar.setUser_id(u.getVhallId());
//		if("dev".equals(envFlag)||"test".equals(envFlag)){
//			webinar.setUser_id(liveVhallUserId);
//		}else{
			
//		}
		String webinarId = VhallUtil.createWebinar(webinar);
		
		VhallUtil.setActiveImage(webinarId, VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
//		if("dev".equals(envFlag)||"test".equals(envFlag)){
		VhallUtil.setCallbackUrl(webinarId, vhall_callback_url, vhall_private_key);
//		}
		return webinarId;
	}
	
	public OnlineUser getLecturer(String userLecturerId) {
		DetachedCriteria dc = DetachedCriteria.forClass(OnlineUser.class);
		dc.add(Restrictions.eq("id", userLecturerId));
		OnlineUser user = dao.findEntity(dc);
		return user;
	}
	
	@Override
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
		OnlineUser ou = getLecturer(entity.getUserLecturerId());
		String teacherName = null;
		if(ou!=null) {
            teacherName = ou.getName();
        }
		webinar.setHost(teacherName);
		webinar.setLayout(entity.getDirectSeeding()+"");
		return VhallUtil.updateWebinar(webinar);
	}
	
	public static void main(String[] args) {
		System.out.println("\u6d3b\u52a8\u4e0d\u5b58\u5728");
	}
	
	@Override
	public void updateCourseDirectId(Course course) {
		publicCourseDao.updateCourseDirectId(course);
	}
	
	/** 
	 * Description：创建直播间
	 * @param entity
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@Override
    public String reCreateWebinar(CourseVo entity) {
		Webinar webinar = new Webinar();
		webinar.setSubject(entity.getCourseName());
		webinar.setIntroduction(entity.getDescription());
		Date start = entity.getStartTime();
		String start_time = start.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		webinar.setStart_time(start_time);
		OnlineUser ou = getLecturer(entity.getUserLecturerId());
		String teacherName = null;
		if(ou!=null) {
            teacherName = ou.getName();
        }
		webinar.setHost(teacherName);
		webinar.setLayout(ou.getDistrict());
		String webinarId = VhallUtil.createWebinar(webinar);
		
		VhallUtil.setActiveImage(webinarId, VhallUtil.downUrlImage(entity.getSmallimgPath(), "image"));
		return webinarId;
	}
	
	@Override
	public void updateLiveStatus(ChangeCallbackVo changeCallbackVo) {
		
		 String hql="from Course where direct_id = ?";
         Course course= dao.findByHQLOne(hql,new Object[] {changeCallbackVo.getWebinarId()});
         
         String startOrEnd ="";
         if(course!=null){
        	 switch (changeCallbackVo.getEvent()) {
        	 case "start":
        		 startOrEnd ="start_time";
        		course.setLiveStatus(1);
        		 break;
        	 case "stop":
        		 startOrEnd ="end_time";
        		 course.setLiveStatus(3);
        		 break;
        	 default:
        		 break;
        	 }
        	 dao.update(course);
        	 
        	if(startOrEnd!=""){
        		
        		String findSql = "select record_count  from oe_live_time_record where live_id = :live_id order by record_count desc limit 1";
        		Map<String,Object> find = new HashMap<String,Object>();
        		find.put("live_id", course.getDirectId());
        		List<Integer> list = dao.getNamedParameterJdbcTemplate().queryForList(findSql, find, Integer.class);

        		
        		Integer maxRecord = 0;
        		if(list!=null && list.size()>0){
        			maxRecord = list.get(0);
        			maxRecord ++;
        		}
        		
        		/**
        		 * 并且记录当前视频id开播的次数：
        		 */
        		String end ="insert into  oe_live_time_record (course_id,live_id,"+startOrEnd+",record_count)  "
        				+ "values (:course_id,:live_id,:"+startOrEnd+",:record_count)";
        		
    			Map<String,Object> paramsEnd=new HashMap<String,Object>();
    			paramsEnd.put("course_id", course.getId());
    			paramsEnd.put("live_id", course.getDirectId());
    			paramsEnd.put(""+startOrEnd+"", new Date());
    			paramsEnd.put("record_count", maxRecord);
    			dao.getNamedParameterJdbcTemplate().update(end, paramsEnd);
        	} 
         }
	}
	

	/* (non-Javadoc)
	 * @see com.xczhihui.bxg.online.manager.cloudClass.service.CourseService#initOpenCourseToSend()
	 */
	@Override
	public void saveOpenCourseToSend() {
		List<Course> courseList = courseDao.getOpenCourseToSend();
		for (Course course : courseList) {
			if(!"dev".equals(envFlag)){
				Subscribe.setting(course.getId(), courseService, courseSubscribeDao);
			}
		}
		System.out.println("======================初始化"+courseList.size()+"个直播课程预约======================");
	}
	
	
	@Override
	public Course findCourseVoByLiveExanmineId(Integer id) {
		 String hql="from Course where examine_id = ?";
         Course course= dao.findByHQLOne(hql,new Object[] {id});
         return course;
	}
	
	
	
}
