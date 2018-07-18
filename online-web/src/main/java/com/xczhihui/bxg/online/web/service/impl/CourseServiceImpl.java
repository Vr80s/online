package com.xczhihui.bxg.online.web.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.solr.client.solrj.SolrServerException;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.CollectionCourseApply;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.CourseException;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.bxg.online.web.dao.CourseDao;
import com.xczhihui.bxg.online.web.dao.CourseSubscribeDao;
import com.xczhihui.bxg.online.web.dao.ScoreTypeDao;
import com.xczhihui.bxg.online.web.service.CourseService;
import com.xczhihui.bxg.online.web.vo.CourseApplyVo;
import com.xczhihui.bxg.online.web.vo.CourseDescriptionVo;
import com.xczhihui.bxg.online.web.vo.CourseLecturVo;
import com.xczhihui.bxg.online.web.vo.CourseVo;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.util.enums.ApplyStatus;
import com.xczhihui.course.service.ICourseSolrService;
import com.xczhihui.medical.anchor.model.CourseApplyInfo;
import com.xczhihui.medical.anchor.service.ICourseApplyService;
import com.xczhihui.medical.exception.AnchorWorkException;

/**
 * CourseServiceImpl:课程业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class CourseServiceImpl extends OnlineBaseServiceImpl implements CourseService {

    private static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    private CourseDao coursedao;
    @Autowired
    private CourseSubscribeDao courseSubscribeDao;
    @Autowired
    private ScoreTypeDao scoreTypeDao;
    
    @Autowired
    private ICourseApplyService courseApplyService;
    @Autowired
    private ICourseSolrService courseSolrService;
    

    @Override
    public List<ScoreType> findAllScoreType() {
        return scoreTypeDao.findAll();
    }

    @Override
    public Page<CourseLecturVo> courseList(Integer menuType, Integer menuId, String couseTypeId, String multimediaType, String isFree, String orderType, String orderBy, Integer pageNumber, Integer pageSize) {
        //获取当前课程列表数据
        Page<CourseLecturVo> page = coursedao.listZyktCourse(menuType, menuId, couseTypeId, multimediaType, isFree, orderType, orderBy, pageNumber, pageSize);
        return page;
    }

    /**
     * 根据菜单编号，查找对应的课程列表
     *
     * @param menuId     学科ID号
     * @param pageNumber 当前是第几页，默认1
     * @param pageSize   每页显示多少行，默认20
     * @return Example 分页列表
     */
    @Override
    public Page<CourseLecturVo> getCourseAndLecturerlist(Integer type, Integer menuId, String couseTypeId, Integer pageNumber, Integer pageSize) {
        //获取当前课程列表数据
        Page<CourseLecturVo> page = coursedao.getCourseAndLecturerlist(type, menuId, couseTypeId, pageNumber, pageSize);
        return page;
    }

    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     *
     * @param userId       用户ID
     * @param courseStatus 课程状态：0:付费  1:免费
     * @param pageNumber   当前是第几页，默认1
     * @param pageSize     每页显示多少行，默认20
     */
    @Override
    public Page<CourseVo> findUserCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
        return coursedao.findUserCoursePage(userId, courseStatus, pageNumber, pageSize);
    }

    /**
     * 查找用户报名后的所有的课程 (根据付费以及免费条件搜索)
     *
     * @param userId       用户ID
     * @param courseStatus 课程状态：0:付费  1:免费
     * @param pageNumber   当前是第几页，默认1
     * @param pageSize     每页显示多少行，默认20
     */
    @Override
    public Page<CourseVo> findUserPublicCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
        return coursedao.findUserPublicCoursePage(userId, courseStatus, pageNumber, pageSize);
    }

    /**
     * 查找用户报名后的所有线下的课程 (根据付费以及免费条件搜索)
     *
     * @param userId       用户ID
     * @param courseStatus 课程状态：0:付费  1:免费
     * @param pageNumber   当前是第几页，默认1
     * @param pageSize     每页显示多少行，默认20
     */
    @Override
    public Page<CourseVo> findUserRealCoursePage(String userId, Integer courseStatus, Integer pageNumber, Integer pageSize) {
        return coursedao.findUserRealCoursePage(userId, courseStatus, pageNumber, pageSize);
    }

    /**
     * 根据课程ID号，查找对应的课程对象
     *
     * @param courseId 课程id
     */
    @Override
    public CourseApplyVo getCourseApplyByCourseId(Integer courseId) {
        return coursedao.getCourseApplyByCourseId(courseId);
    }


    /**
     * 根据课程ID号，查找对应的课程对象
     *
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @Override
    public CourseVo getCourseById(Integer courseId, String path, HttpServletRequest request, OnlineUser ou) throws IOException {
        //根据当前课程ID，查找对应的课程信息
        CourseVo courseVo = coursedao.getCourseById(courseId, request);
        File f = new File(path + File.separator + "/course_common_problem.html");
        courseVo.setCommonProblem(FileUtil.readAsString(f));
        return courseVo;
    }

    /**
     * 根据公开课程ID号，查找对应的课程对象
     *
     * @param courseId 课程id
     * @return Example 分页列表
     */
    @Override
    public CourseVo getOpenCourseById(Integer courseId, String ispreview, HttpServletRequest request) {
        //根据当前课程ID，查找对应的课程信息
        CourseVo courseVo = coursedao.getOpenCourseById(courseId, ispreview, request);
        //如果此课程存在,再根据课程ID 查找此课程下的老师
        if (courseVo != null) {
            OnlineUser onlineUser = coursedao.getLecturer(courseVo.getUserLecturerId());
            courseVo.setTeacherName(onlineUser.getName());
        }
        return courseVo;
    }


    /**
     * 查找推荐课程信息
     *
     * @param courseId 课程id
     * @return
     */
    @Override
    public List<CourseVo> getCourseByCourseId(Integer courseId, Integer num) {
        return coursedao.getCourseByCourseId(courseId, num);
    }


    /**
     * 博文答详情页面的推荐课程
     *
     * @param menuId 学科ID号
     * @return 推荐课程集合 courseLecturVos
     */
    @Override
    public List<CourseLecturVo> getRecommendedCourse(Integer menuId) {
        List<CourseLecturVo> clvs = coursedao.getRecommendedCourse(menuId);
        return clvs;
    }


    /**
     * 首页推荐课程信息
     *
     * @return
     */
    @Override
    public List<CourseLecturVo> getRecommendCourse(Integer num) {
        List<CourseLecturVo> clvs = coursedao.getRecommendCourse(num);
        return clvs;
    }

    @Override
    public List<CourseDescriptionVo> getCourseDescriptionsByCourseId(Boolean isPreview, String courseId) {
        return coursedao.getCourseDescriptionsByCourseId(isPreview, courseId);
    }

    @Override
    public CourseDescriptionVo getCourseDescriptionById(String id, String courseId) {
        return coursedao.getCourseDescriptionsById(id, courseId);
    }

    /**
     * 课程订单 根据课程id产生订单
     *
     * @param courseId 课程id号
     * @return 返回对应的课程对象
     */
    @Override
    public CourseVo findCourseOrderById(Integer courseId) {
        return coursedao.findCourseOrderById(courseId);
    }

    /**
     * 购买课程时，进行检测此订单关联的课程是否下架以及是否购买
     */
    @Override
    public String checkCouseInfo(String orderId) {
        coursedao.checkCouseInfo(orderId);
        return "开始报名";
    }

    @Override
    public ResponseObject insertSubscribe(String userId, String mobile, Integer courseId) throws ClientException {
        boolean isSubscribe = courseSubscribeDao.isSubscribe(userId, courseId);
        if (isSubscribe) {
            return ResponseObject.newErrorResponseObject("您已经预约过了！");
        }
        courseSubscribeDao.insert(userId, mobile, courseId);
        CourseVo courseVo = this.findOpenCourseById(courseId);
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH时mm分");
        String start = sdf.format(courseVo.getStart_time());
        SmsUtil.sendSmsSubscribe(mobile, courseVo.getCourseName(), start, null, true);
        return ResponseObject.newSuccessResponseObject("预约成功！");
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
            if ((now.getTime() - startTime.getTime()) > 1000 * 60 * 30) {
                course.setStatus("0");
                dao.update(course);
                CourseException courseException = new CourseException();
                courseException.setCourseId(course.getId());
                courseException.setDeleted(false);
                courseException.setType(1);
                courseException.setCreateTime(new Date());
                dao.save(courseException);
                logger.info("课程id{}的直播课程由于超时未发起直播，被下架并插入课程异常表中", course.getId());
                try {
                    courseSolrService.initCourseSolrDataById(course.getId());
                } catch (IOException e) {
                    logger.error(e.getMessage());
                } catch (SolrServerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<CourseVo> getCoursesByCollectionId(Integer collectionId) {
        return coursedao.getCoursesByCollectionId(collectionId);
    }

    @Override
    public List<CourseVo> getCoursesRecommendByType(Integer type) {
        List<CourseVo> list = coursedao.getCoursesRecommendByType(type);
        List<CourseVo> courseList = new ArrayList<CourseVo>();
        for (CourseVo c : list) {
            if (c.getRecommendSort() > 0) {
                courseList.add(c);
            }
        }
        return courseList;
    }

    @Override
    public Course findByApplyId(String applyId) {
        return coursedao.getCourseByApplyId(applyId);
    }

    @Override
    public void saveCollectionCourse(CourseApplyInfo courseApplyInfo) {
        
        this.saveCollectionCourse4Lock(courseApplyInfo.getUserId(), courseApplyInfo);
    }

    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "saveCollectionCourse4Lock")
    public void saveCollectionCourse4Lock(String userId, CourseApplyInfo courseApplyInfo) {
        
        CourseApplyInfo collectionApplyInfo = courseApplyService.selectCourseApplyById(courseApplyInfo.getUserId(), courseApplyInfo.getId());
        if (collectionApplyInfo == null) {
            throw new AnchorWorkException("专辑不存在");
        }
        
        if (collectionApplyInfo != null && !collectionApplyInfo.getStatus().equals(ApplyStatus.PASS.getCode())) {
            throw new AnchorWorkException("此专辑未审核通过,不能直接添加子课程！");
        }
        
        
        //查看是否存在此课程
        Course collection = this.findByApplyId(courseApplyInfo.getId()+"");
        if (collection == null) {
            throw new AnchorWorkException("未正确找到审核过后的课程！");
        }
        
        for (CourseApplyInfo applyInfo : courseApplyInfo.getCourseApplyInfos()) {
           
            CollectionCourseApply collectionCourseApply = new CollectionCourseApply();
            collectionCourseApply.setCourseApplyId(applyInfo.getId());
            collectionCourseApply.setCollectionApplyId(courseApplyInfo.getId());
            collectionCourseApply.setCollectionCourseSort(applyInfo.getCollectionCourseSort());
            dao.save(collectionCourseApply);
            
            /**
             * 查看是否审核通过
             */
            CourseApplyInfo cai = courseApplyService.selectById(applyInfo.getId());
            if(cai == null){
                throw new AnchorWorkException("未正确找到正确的子课程！");
            }
            Course course = null;
            if(cai.getStatus()!=null && cai.getStatus().equals(ApplyStatus.UNTREATED.getCode())) { //没有通过
                //让其审核通过
                course = savePassCourse(cai);
            
            }else if(cai.getStatus()!=null && cai.getStatus().equals(ApplyStatus.PASS.getCode())){  //通过    
                course = coursedao.getCourseByApplyId(cai.getId()+"");
            }
            
            if(course ==null || course.getId() ==null ) {
                throw new AnchorWorkException("此课程审核状态有误！");
            }
            
            // 保存专辑-课程关系
            String sql = "INSERT INTO collection_course(collection_id,course_id,create_time,collection_course_sort) "
                    + " VALUES (:cId,:courseId,now(),:collectionCourseSort)";
            Map<String, Integer> paramMap = new HashMap<String, Integer>();
            paramMap.put("cId", collection.getId());
            paramMap.put("courseId", course.getId());
            paramMap.put("collectionCourseSort", course.getCollectionCourseSort());
            dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
            
        }
    }

    private Course savePassCourse(CourseApplyInfo cai) {
        // TODO Auto-generated method stub
        
        //更改课程审核状态
        cai.setStatus(ApplyStatus.PASS.getCode());
        cai.setReviewPerson(null); 
        cai.setReviewTime(new Date());
        courseApplyService.updateById(cai);
        
        //同步到课程表
        return saveCourseApply2course(cai);
    }
    
    private Course saveCourseApply2course(CourseApplyInfo courseApply) {
        
        //之前是否存在此课程
        Course course =  coursedao.getCourseByApplyId(courseApply.getOldApplyInfoId()!=null ? courseApply.getOldApplyInfoId()+"":null);
        
        //验证课程名
        if(course!=null) {
            //修改
            String hqlPre = "from Course where gradeName = ? and id != ? ";
            Course courseName = dao.findByHQLOne(hqlPre, 
                    new Object[]{courseApply.getTitle(),course.getId()});
            if(courseName!=null) {
                throw new RuntimeException("课程名更改下吧！");
            }
        }else {
            String hqlPre = "from Course where gradeName = ?";
            Course courseName = dao.findByHQLOne(hqlPre, 
                    new Object[]{courseApply.getTitle()});
            if(courseName!=null) {
                throw new RuntimeException("课程名更改下吧！");
            }
        }
        
        // 当课程存在密码时，设置的当前价格失效，改为0.0
        if (courseApply.getPassword() != null && !"".equals(courseApply.getPassword().trim())) {
            courseApply.setPrice(0.0);
        }
        // 课程名称
        course.setGradeName(courseApply.getTitle());
        // 课程副标题
        course.setSubtitle(courseApply.getSubtitle());
        // 学科id
        course.setMenuId(Integer.valueOf(courseApply.getCourseMenu()));
        // 课程时长
        course.setCourseLength(courseApply.getCourseLength());
        // 原价格
        if(courseApply.getOriginalCost() != null){
            course.setOriginalCost(courseApply.getOriginalCost());
        }
        // 现价格
        course.setCurrentPrice(courseApply.getPrice());
        // 推荐值
        course.setRecommendSort(0);

        if (0 == course.getCurrentPrice()) {
            // 免费
            course.setIsFree(true);
        } else {
            // 付费
            course.setIsFree(false);
        }
        // 请填写一个基数，统计的时候加上这个基数
        course.setLearndCount(0);
        
        // 当前登录人
        //course.setCreatePerson(ManagerUserUtil.getUsername());

        // 课程介绍
        course.setCourseDetail(courseApply.getCourseDetail());

        // 增加密码和老师
        course.setCoursePwd(courseApply.getPassword());
        course.setUserLecturerId(courseApply.getUserId() + "");
        course.setType(courseApply.getCourseForm());

        // zhuwenbao-2018-01-09 设置课程的展示图
        course.setSmallImgPath(courseApply.getImgPath());

        course.setCourseOutline(courseApply.getCourseOutline());
        course.setLecturer(courseApply.getLecturer());
        course.setLecturerDescription(courseApply.getLecturerDescription());

        course.setLiveSource(2);
        course.setSort(0);

        course.setStatus("0");
//        if (course.getType() == CourseForm.OFFLINE.getCode()) {
//            // 线下课程
//            course.setAddress(courseApply.getAddress());
//            course.setStartTime(courseApply.getStartTime());
//            course.setEndTime(courseApply.getEndTime());
//            course.setCity(courseApply.getCity());
//            
//            //添加城市管理  不可能是线下班吧
//            //courseService.addCourseCity(course.getCity());
//            
//            
//        } else if (course.getType() == CourseForm.LIVE.getCode()) {
//            course.setStartTime(courseApply.getStartTime());
//            if (StringUtils.isBlank(course.getDirectId())) {
//                String webinarId = createWebinar(course);
//                course.setDirectId(webinarId);
//                // 将直播课设置为预告
//                course.setLiveStatus(2);
//            } else {
//                updateWebinar(course);
//            }
//        } else if (course.getType() == CourseForm.VOD.getCode()) {
//            // yuruixin-2017-08-16
//            // 课程资源
//            course.setMultimediaType(courseApply.getMultimediaType());
//            course.setDirectId(courseApply.getCourseResource());
//        }
        
        course.setMultimediaType(courseApply.getMultimediaType());
        course.setDirectId(courseApply.getCourseResource());
        
        course.setIsRecommend(0);
        course.setClassRatedNum(0);
        course.setApplyId(courseApply.getId());
        course.setCollectionCourseSort(courseApply.getCollectionCourseSort());
        course.setCollection(courseApply.getCollection());
        course.setCourseNumber(courseApply.getCourseNumber());

        course.setClientType(courseApply.getClientType());
        if (course.getId() != null) {
            // 若course有id，说明该申请来自一个已经审核通过的课程，则更新
            dao.update(course);
            //cacheService.delete(LIVE_COURSE_REMIND_LAST_TIME_KEY + RedisCacheKey.REDIS_SPLIT_CHAR + course.getId());
        
        } else {
            // 当前时间
            course.setCreateTime(new Date());
            dao.save(course);
            
        }
        
        //发送消息，暂时不发送啦
        //savecourseMessageReminding(course);
        return course;
    }
    
}
