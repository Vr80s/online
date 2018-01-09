package com.xczhihui.bxg.online.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.bxg.common.util.SmsUtil;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.CourseSubscribeDao;
import com.xczhihui.bxg.online.web.dao.ScoreTypeDao;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.service.LecturerService;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.CourseDescriptionVo;
import com.xczhihui.bxg.online.web.vo.CourseLecturVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.bxg.online.web.vo.LecturVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;

/**
 *   CourseServiceImpl:课程业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class CourseServiceImpl  extends OnlineBaseServiceImpl implements CourseService {

    @Autowired
    private CourseDao coursedao;

    @Autowired
    private LecturerService service;

    @Autowired
    private CourseSubscribeDao courseSubscribeDao;

    @Autowired
    private ScoreTypeDao scoreTypeDao;

    @Override
    public List<ScoreType> findAllScoreType() {
        return scoreTypeDao.findAll();
    }

    @Override
    public Page<CourseLecturVo> courseList(Integer menuType, Integer menuId, String couseTypeId, String multimediaType, String isFree, String orderType, String orderBy, Integer pageNumber, Integer pageSize) {
        //获取当前课程列表数据
        Page<CourseLecturVo>  page = coursedao.listZyktCourse(menuType,menuId,couseTypeId,multimediaType,isFree,orderType,orderBy,pageNumber,pageSize);
        //循环课程,根据课程ID号查找当前课程对应的讲师,只要两个讲师
        if(!CollectionUtils.isEmpty(page.getItems())){
            for (CourseLecturVo courseLecturVo :page.getItems()) {
                String name = "暂无讲师";
                OnlineUser onlineUser = coursedao.getLecturer(courseLecturVo.getUserLecturerId());
                if(onlineUser!=null)name=onlineUser.getName();
                courseLecturVo.setName(name);
            }
        }

        return  page;
    }

    /**
     * 根据菜单编号，查找对应的课程列表
     * @param menuId 学科ID号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize 每页显示多少行，默认20
     * @return Example 分页列表
     */
    public   Page<CourseLecturVo>  getCourseAndLecturerlist( Integer type,Integer menuId,String couseTypeId, Integer pageNumber, Integer pageSize){
        //获取当前课程列表数据
        Page<CourseLecturVo>  page = coursedao.getCourseAndLecturerlist(type,menuId, couseTypeId,  pageNumber,  pageSize);
        //循环课程,根据课程ID号查找当前课程对应的讲师,只要两个讲师
        if(!CollectionUtils.isEmpty(page.getItems())){
            for (CourseLecturVo courseLecturVo :page.getItems()) {
            	String name = "暂无讲师";
            	OnlineUser onlineUser = coursedao.getLecturer(courseLecturVo.getUserLecturerId());
            	if(onlineUser!=null)name=onlineUser.getName();
            	courseLecturVo.setName(name);
            }
        }

        return  page;
    }


    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    public Page<CourseVo> findUserCoursePage(String userId,Integer courseStatus, Integer pageNumber, Integer pageSize) {
        return coursedao.findUserCoursePage(userId, courseStatus, pageNumber, pageSize);
    }
    
    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    public Page<CourseVo> findUserPublicCoursePage(String userId,Integer courseStatus, Integer pageNumber, Integer pageSize) {
    	return coursedao.findUserPublicCoursePage(userId, courseStatus, pageNumber, pageSize);
    }
    
    /**
     * 查找用户报名后的所有线下的课程 (根据付费以及免费条件搜索)
     * @param userId     用户ID
     * @param courseStatus   课程状态：0:付费  1:免费
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     */
    public Page<CourseVo> findUserRealCoursePage(String userId,Integer courseStatus, Integer pageNumber, Integer pageSize) {
    	return coursedao.findUserRealCoursePage(userId, courseStatus, pageNumber, pageSize);
    }

    /**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     */
    public CourseApplyVo getCourseApplyByCourseId(Integer courseId) {
        return coursedao.getCourseApplyByCourseId(courseId);
    }


    /**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @Override
    public CourseVo getCourseById( Integer courseId,String ispreview,HttpServletRequest request,OnlineUser ou) {
        //根据当前课程ID，查找对应的课程信息
        CourseVo courseVo =  coursedao.getCourseById(courseId,ispreview,request);
        String  names="";
        //如果此课程存在,再根据课程ID 查找此课程下的老师
        if(courseVo != null){
        	String name = "暂无讲师";
        	String teacherDescription="";
        	OnlineUser onlineUser = coursedao.getLecturer(courseVo.getUserLecturerId());
        	if(onlineUser!=null){
        		name=onlineUser.getName();
        		teacherDescription=onlineUser.getDescription();
        		
        		if(ou!=null && courseVo.getUserLecturerId().equals(ou.getId())){// 20170105---杨宣
            		courseVo.setSelfCourse(true);
            	}
        	}
        	courseVo.setTeacherName(name);
        	courseVo.setTeacherNames(name);
			courseVo.setTeacherDescription(teacherDescription);
        }
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
    public List<CourseVo> getCourseByCourseId( Integer courseId,Integer num) {
        return  coursedao.getCourseByCourseId(courseId, num);
    }


    /**
     * 博文答详情页面的推荐课程
     *
     * @param menuId      学科ID号
     * @return  推荐课程集合 courseLecturVos
     */
    public List<CourseLecturVo>  getRecommendedCourse(Integer menuId) {
        List<CourseLecturVo> clvs=  coursedao.getRecommendedCourse(menuId);
        //循环课程,根据课程ID号查找当前课程对应的讲师,只要两个讲师
        if(!CollectionUtils.isEmpty(clvs)){
            for (CourseLecturVo courseLecturVo :clvs) {
                List<LecturVo> lecturVos = service.findLecturerById(courseLecturVo.getId());
                if(!CollectionUtils.isEmpty(lecturVos) && lecturVos.size() == 1){
                    courseLecturVo.setName(lecturVos.get(0).getName());
                }else if(!CollectionUtils.isEmpty(lecturVos) && lecturVos.size() > 1) {
                    courseLecturVo.setName(lecturVos.get(0).getName()+"等");
                }
            }
        }
        return clvs;
    }


    /**
     * 首页推荐课程信息
     * @return
     */
    public List<CourseLecturVo> getRecommendCourse (Integer num){
            List<CourseLecturVo> clvs=  coursedao.getRecommendCourse(num);
            //循环课程,根据课程ID号查找当前课程对应的讲师,只要两个讲师
            if(!CollectionUtils.isEmpty(clvs)){
                for (CourseLecturVo courseLecturVo :clvs) {
                    List<LecturVo> lecturVos = service.findLecturerById(courseLecturVo.getId());
                    if(!CollectionUtils.isEmpty(lecturVos) && lecturVos.size() == 1){
                        courseLecturVo.setName(lecturVos.get(0).getName());
                    }else if(!CollectionUtils.isEmpty(lecturVos) && lecturVos.size() > 1) {
                        courseLecturVo.setName(lecturVos.get(0).getName()+"等");
                    }
                }
            }
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
    public  CourseVo   findEnterCourseDetail(HttpServletRequest request,Integer  courseId){
        return coursedao.findEnterCourseDetail(request,courseId);
    }

    /**
     * 课程订单 根据课程id产生订单
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
    public  CourseVo   findCourseOrderById(Integer  courseId){
        return coursedao.findCourseOrderById(courseId);
    }

    /**
     * 购买课程时，进行检测此订单关联的课程是否下架以及是否购买
     */
    public  String   checkCouseInfo(String  orderId){
          coursedao.checkCouseInfo(orderId);
          return "开始报名";
    }


    /**
     * 获取当前课程下学员评价
     * @param courseId 课程id
     * @return
     */
    public  Page<CriticizeVo>   findStudentCriticize(Integer courseId, Integer pageNumber, Integer pageSize){
        return  coursedao.findStudentCriticize(courseId, pageNumber,  pageSize);
    }


    /**
     * 获取好评的数量
     * @param courseId 课程ID
     * @return
     */
    public Integer getGoodCriticizSum(Integer courseId) {
       return coursedao.getGoodCriticizSum(courseId);
    }


    /**
     * 获取课程目录
     * @param courseId
     * @return
     */
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
	public String getCoursesPage(Integer courseId) {
        CourseVo cv = coursedao.findCourseOrderById(courseId);
		if(cv==null)return "";
		if(cv.getType()!=null && cv.getType()==1){
			if("1".equals(cv.getCoursePwd())){
				return "encryptOpenCourseDetailPage.html";//加密直播
			}else if(cv.isFree()){
				return "freeOpenCourseDetailPage.html";//免费直播
			}else{
				return "payOpenCourseDetailPage.html";//付费直播
			}
		}else{
			if("1".equals(cv.getCoursePwd())){
				return "encryptCourseDetailPage.html";//加密直播
			}else if(cv.isFree()||Double.valueOf(cv.getCurrentPrice())==0){
				return "freeCourseDetailPage.html";//免费直播
			}else{
				return "payCourseDetailPage.html";//付费直播
			}
		}
	}

}
