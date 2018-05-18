package com.xczhihui.bxg.online.web.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.CourseSubscribeDao;
import com.xczhihui.bxg.online.web.dao.ScoreTypeDao;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.vo.*;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *   CourseServiceImpl:课程业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class CourseServiceImpl  extends OnlineBaseServiceImpl implements CourseService {

    @Autowired
    private CourseDao coursedao;

    @Autowired
    private CourseSubscribeDao courseSubscribeDao;

    @Autowired
    private ScoreTypeDao scoreTypeDao;

    private static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Override
    public List<ScoreType> findAllScoreType() {
        return scoreTypeDao.findAll();
    }

    @Override
    public Page<CourseLecturVo> courseList(Integer menuType, Integer menuId, String couseTypeId, String multimediaType, String isFree, String orderType, String orderBy, Integer pageNumber, Integer pageSize) {
        //获取当前课程列表数据
        Page<CourseLecturVo>  page = coursedao.listZyktCourse(menuType,menuId,couseTypeId,multimediaType,isFree,orderType,orderBy,pageNumber,pageSize);
        return  page;
    }

    /**
     * 根据菜单编号，查找对应的课程列表
     * @param menuId 学科ID号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize 每页显示多少行，默认20
     * @return Example 分页列表
     */
    @Override
    public   Page<CourseLecturVo>  getCourseAndLecturerlist(Integer type, Integer menuId, String couseTypeId, Integer pageNumber, Integer pageSize){
        //获取当前课程列表数据
        Page<CourseLecturVo>  page = coursedao.getCourseAndLecturerlist(type,menuId, couseTypeId,  pageNumber,  pageSize);
        return  page;
    }


    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    @Override
    public Page<CourseVo> findUserCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
        return coursedao.findUserCoursePage(userId, courseStatus, pageNumber, pageSize);
    }
    
    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    @Override
    public Page<CourseVo> findUserPublicCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
    	return coursedao.findUserPublicCoursePage(userId, courseStatus, pageNumber, pageSize);
    }
    
    /**
     * 查找用户报名后的所有线下的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    @Override
    public Page<CourseVo> findUserRealCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
    	return coursedao.findUserRealCoursePage(userId, courseStatus, pageNumber, pageSize);
    }

    /**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     */
    @Override
    public CourseApplyVo getCourseApplyByCourseId(Integer courseId) {
        return coursedao.getCourseApplyByCourseId(courseId);
    }


    /**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @Override
    public CourseVo getCourseById( Integer courseId,String path,HttpServletRequest request,OnlineUser ou) throws IOException {
        //根据当前课程ID，查找对应的课程信息
        CourseVo courseVo =  coursedao.getCourseById(courseId,request);
        File f = new File(path+File.separator+"/course_common_problem.html");
        courseVo.setCommonProblem(FileUtil.readAsString(f));
        
        //20180508 杨宣 需求变动
        //如果此课程存在,再根据课程ID 查找此课程下的老师
//        if(courseVo != null){
//            OnlineUser onlineUser = coursedao.getLecturer(courseVo.getUserLecturerId());
//            if(onlineUser!=null){
//                // 20170105---杨宣,这里不这样搞了
////                if(ou!=null && courseVo.getUserLecturerId().equals(ou.getId())){
////                    courseVo.setSelfCourse(true);
////                }
//            }
//        }
        return  courseVo;
    }
    
    /**
     * 根据公开课程ID号，查找对应的课程对象
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @Override
    public CourseVo getOpenCourseById( Integer courseId,String ispreview,HttpServletRequest request) {
    	//根据当前课程ID，查找对应的课程信息
    	CourseVo courseVo =  coursedao.getOpenCourseById(courseId,ispreview,request);
    	//如果此课程存在,再根据课程ID 查找此课程下的老师
    	if(courseVo != null){
    		OnlineUser onlineUser = coursedao.getLecturer(courseVo.getUserLecturerId());
    		courseVo.setTeacherName(onlineUser.getName());
    	}
    	
    	
    	return  courseVo;
    }


    /**
     * 查找推荐课程信息
     * @param courseId 课程id
     * @return
     */
    @Override
    public List<CourseVo> getCourseByCourseId(Integer courseId, Integer num) {
        return  coursedao.getCourseByCourseId(courseId, num);
    }


    /**
     * 博文答详情页面的推荐课程
     *
     * @param menuId      学科ID号
     * @return  推荐课程集合 courseLecturVos
     */
    @Override
    public List<CourseLecturVo>  getRecommendedCourse(Integer menuId) {
        List<CourseLecturVo> clvs=  coursedao.getRecommendedCourse(menuId);
        return clvs;
    }


    /**
     * 首页推荐课程信息
     * @return
     */
    @Override
    public List<CourseLecturVo> getRecommendCourse (Integer num){
            List<CourseLecturVo> clvs=  coursedao.getRecommendCourse(num);
           return  clvs;
    }

    @Override
    public List<CourseDescriptionVo> getCourseDescriptionsByCourseId(Boolean isPreview,String courseId) {
        return coursedao.getCourseDescriptionsByCourseId(isPreview,courseId);
    }

    @Override
    public CourseDescriptionVo getCourseDescriptionById(String id,String courseId) {
        return coursedao.getCourseDescriptionsById(id,courseId);
    }



    /**
     * 报名后课程详情也接口，根据课程id查找对应课程
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
    @Override
    public  CourseVo   findEnterCourseDetail(HttpServletRequest request, Integer  courseId){
        return coursedao.findEnterCourseDetail(request,courseId);
    }

    /**
     * 课程订单 根据课程id产生订单
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
    @Override
    public  CourseVo   findCourseOrderById(Integer  courseId){
        return coursedao.findCourseOrderById(courseId);
    }

    /**
     * 购买课程时，进行检测此订单关联的课程是否下架以及是否购买
     */
    @Override
    public  String  checkCouseInfo(String  orderId){
          coursedao.checkCouseInfo(orderId);
          return "开始报名";
    }

    /**
     * 获取课程目录
     * @param courseId
     * @return
     */
    @Override
    public List<Map<String, Object>> getCourseCatalog(Integer courseId) {
       return coursedao.getCourseCatalog(courseId);
    }


	/**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @Override
    public CourseVo getCourseByCourseId(Integer courseId) {
        //根据当前课程ID，查找对应的课程信息
        CourseVo courseVo =  coursedao.findCourseOrderById(courseId);
        return  courseVo;
    }
    
    @Override
    public void updateSentById(Integer id) {
    	coursedao.updateSentById(id);
    }

    @Override
    public ResponseObject insertSubscribe(String userId, String mobile, Integer courseId) throws ClientException {
        boolean isSubscribe=courseSubscribeDao.isSubscribe(userId,courseId);
        if(isSubscribe){
          return  ResponseObject.newErrorResponseObject("您已经预约过了！");
        }
        courseSubscribeDao.insert(userId,mobile,courseId);
        CourseVo courseVo =this.findOpenCourseById(courseId);
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH时mm分");
        String start = sdf.format(courseVo.getStart_time());
        SmsUtil.sendSmsSubscribe(mobile, courseVo.getCourseName(), start, null, true);
       return  ResponseObject.newSuccessResponseObject("预约成功！");
    }

	private CourseVo findOpenCourseById(Integer courseId) {
		return coursedao.findCourseOrderById(courseId);
	}

    @Override
    public void updateCourseException() {
        List<Course> liveCourse = coursedao.getLiveCourse();
        for (Course course : liveCourse) {
            Date startTime = course.getStartTime();
            Date now = new Date();
            if((now.getTime()-startTime.getTime())>1000*60*30){
                course.setStatus("0");
                dao.update(course);
                CourseException courseException = new CourseException();
                courseException.setCourseId(course.getId());
                courseException.setDeleted(false);
                courseException.setType(1);
                courseException.setCreateTime(new Date());
                dao.save(courseException);
                logger.info("课程id{}的直播课程由于超时未发起直播，被下架并插入课程异常表中",course.getId());
            }
        }
    }

    @Override
    public List<CourseVo> getCoursesByCollectionId(Integer collectionId) {
        return  coursedao.getCoursesByCollectionId(collectionId);
    }

    @Override
    public List<CourseVo> getCoursesRecommendByType(Integer type) {
        List<CourseVo> list = coursedao.getCoursesRecommendByType(type);
        List<CourseVo> courseList =new ArrayList<CourseVo>();
        for(CourseVo c:list){
            if(c.getRecommendSort()>0){
                courseList.add(c);
            }
        }
        return courseList;
    }

}
