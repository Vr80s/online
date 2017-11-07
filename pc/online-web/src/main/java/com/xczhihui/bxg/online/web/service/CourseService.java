package com.xczhihui.bxg.online.web.service;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.api.vo.*;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.CourseDescriptionVo;
import com.xczhihui.bxg.online.web.vo.CourseLecturVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.bxg.online.web.vo.CriticizeVo;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 *   CourseService1:课程业务层接口类
 *  @author Rongcai Kang
 */
public interface CourseService {


    public List<ScoreType> findAllScoreType();




    public Page<CourseLecturVo>  courseList(Integer menuType, Integer menuId, String couseTypeId,String multimediaType,String isFree,String orderType,String orderBy, Integer pageNumber, Integer pageSize);


    /**
     * 根据菜单编号，查找对应的课程列表
     * @param menuId 菜单编号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize 每页显示多少行，默认20
     * @return Example 分页列表
     */
    public Page<CourseLecturVo>  getCourseAndLecturerlist( Integer type,Integer menuId,String couseTypeId, Integer pageNumber, Integer pageSize);




    /**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     * @param ispreview 是否为预览，1为预览
     * @return Example 分页列表
     */
    public CourseVo getCourseById(Integer courseId,String ispreview,HttpServletRequest request);

    public Page<CourseVo> findUserRealCoursePage(String userId,Integer courseStatus, Integer pageNumber, Integer pageSize);

    /**
     * 返回用户课程中所有菜单
     * @param userId
     * @return
     */
    public Page<CourseVo> findUserCoursePage(String userId,Integer courseStatus,Integer pageNumber, Integer pageSize);

    public Page<CourseVo> findUserPublicCoursePage(String userId,Integer courseStatus,Integer pageNumber, Integer pageSize);


    /**
     * 根据课程ID号，查找对应的课程对象
     * @param courseId 课程id
     * @return Example 分页列表
     */
    public CourseApplyVo getCourseApplyByCourseId( Integer courseId);


    /**
     * 查找推荐课程信息
     * @param courseId 课程id
     * @return
     */
    public List<CourseVo> getCourseByCourseId( Integer courseId,Integer num) ;

    /**
     * 博文答详情页面的推荐课程
     *
     * @param menuId      学科ID号
     * @return  推荐课程集合 courseLecturVos
     */
    public List<CourseLecturVo>  getRecommendedCourse(Integer menuId);


    /**
     * 首页推荐课程信息
     * @return
     */
    public List<CourseLecturVo> getRecommendCourse (Integer num);

    /**
     * 根据课程Id查询课程介绍列表数据
     * @param isPreview
     * @param courseId
     * @return
     */
    public List<CourseDescriptionVo> getCourseDescriptionsByCourseId(Boolean isPreview,String courseId);

    /**
     * 根据课程Id查询课程介绍列表数据
     * @param id
     * @return
     */
    public CourseDescriptionVo getCourseDescriptionById(String id,String courseId);


    /**
     * 报名后课程详情也接口，根据课程id查找对应课程
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
    public  CourseVo   findEnterCourseDetail(HttpServletRequest request,Integer  courseId);

    /**
     * 课程订单 根据课程id产生订单
     * @param courseId  课程id号
     * @return 返回对应的课程对象
     */
    public  CourseVo   findCourseOrderById(Integer  courseId);

    /**
     * 购买课程时，进行检测此订单关联的课程是否下架以及是否购买
     * @param orderNo 订单号
     */
    public   String   checkCouseInfo(String  orderNo);


    /**
     * 获取当前课程下学员评价
     * @param courseId 课程id
     * @return
     */
    public  Page<CriticizeVo>  findStudentCriticize(Integer courseId, Integer pageNumber, Integer pageSize);


    /**
     * 获取好评的数量
     * @param courseId 课程ID
     * @return
     */
    public Integer getGoodCriticizSum(Integer courseId);

    /**
     * 获取课程目录
     * @param courseId
     * @return
     */
    public List<Map<String, Object>> getCourseCatalog(Integer courseId);




	public Object getOpenCourseById(Integer courserId, String ispreview,HttpServletRequest request);




	public CourseVo getCourseByCourseId(Integer courseId);




	void updateSentById(Integer id);

    /**
     * 预约
     * @param userId
     * @param mobile
     * @param courseId
     */
    ResponseObject insertSubscribe(String userId, String mobile, Integer courseId) throws ClientException ;




	public String getCoursesPage(Integer courseId);


}
