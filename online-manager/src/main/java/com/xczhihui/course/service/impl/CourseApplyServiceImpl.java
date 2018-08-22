package com.xczhihui.course.service.impl;

import static com.xczhihui.common.util.redis.key.RedisCacheKey.LIVE_COURSE_REMIND_LAST_TIME_KEY;

import java.text.MessageFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.common.support.cc.util.CCUtils;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.support.service.XcRedisCacheService;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.*;
import com.xczhihui.common.util.vhallyun.ChannelService;
import com.xczhihui.common.util.vhallyun.RoomService;
import com.xczhihui.course.dao.CourseApplyDao;
import com.xczhihui.course.dao.CourseDao;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.CourseApplyService;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.MessageRemindingService;
import com.xczhihui.course.util.TextStyleUtil;
import com.xczhihui.medical.enrol.service.EnrolService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.vhall.VhallUtil;
import com.xczhihui.vhall.bean.Webinar;

/**
 * CourseServiceImpl:课程业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class CourseApplyServiceImpl extends OnlineBaseServiceImpl implements
        CourseApplyService {
    /**
     * 直播课程审核通过文案
     */
    private static final String SMS_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            "您申请的{0}课程《{1}》已通过系统审核，可以上架啦！您可登录www.ipandatcm.com中主播工作台进行操作";
    private static final String APP_PUSH_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            "您申请的{0}课程《{1}》已通过系统审核，可以上架啦！";
    private static final String APP_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            "【课程审核通知】您申请的{0}课程" + "《{1}》已通过系统审核," + TextStyleUtil.LEFT_TAG + "去看看>>" + TextStyleUtil.RIGHT_TAG;
    private static final String WEB_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            APP_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS;
    private static final String WEIXIN_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            "【课程审核通知】您申请的{0}课程" + "《{1}》已通过系统审核";


    private static final String SMS_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            "您申请的{0}课程《{1}》已通过系统审核，可以上架啦！您可登录www.ipandatcm.com中主播工作台进行操作";
    private static final String APP_PUSH_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            "您申请的{0}课程《{1}》已通过系统审核，可以上架啦！";
    private static final String APP_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            "【课程审核通知】您申请的{0}课程《{1}》已通过系统审核，可以上架啦！";
    private static final String WEB_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            APP_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS;
    private static final String WEIXIN_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS =
            "【课程审核通知】您申请的{0}课程《{1}》已通过系统审核，可以上架啦！";

    private static final String APP_PUSH_COURSE_APPLY_FAIL_MESSAGE_TIPS =
            "您申请的{0}课程《{1}》未通过系统审核，原因：{2}。如有疑问请联系客服0898-32881934。";
    private static final String APP_COURSE_APPLY_FAIL_MESSAGE_TIPS =
            "【课程审核通知】您申请的{0}课程《{1}》未通过系统审核，" +
                    "原因：{2}。如有疑问请联系客服0898-32881934。";
    private static final String WEB_COURSE_APPLY_FAIL_MESSAGE_TIPS =
            APP_COURSE_APPLY_FAIL_MESSAGE_TIPS;
    private static final String WEIXIN_COURSE_APPLY_FAIL_MESSAGE_TIPS =
            "【课程审核通知】很遗憾，您发布的<<{0}>>课程未通过系统审核, 原因：{1}。如有疑问请联系客服0898-32881934。";
    @Value("${vhall.callback.url}")
    String vhall_callback_url;
    @Value("${vhall.private.key}")
    String vhall_private_key;
    @Value("${sms.live.course.apply.pass.code}")
    private String liveCourseApplyPassCode;
    @Value("${sms.not.live.course.apply.pass.code}")
    private String notLiveCourseApplyPassCode;
    @Value("${sms.course.apply.not.pass.code}")
    private String courseApplyNotPassCode;
    @Value("${weixin.course.admin.apply.code}")
    private String weixinCourseApplyCode;
    @Autowired
    private CourseApplyDao courseApplyDao;
    @Autowired
    private CourseService courseService;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private AnchorService anchorService;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CCUtils ccUtils;
    @Autowired
    private MessageRemindingService messageRemindingService;
    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private EnrolService enrolService;
    @Value("${vhall.user.id}")
    private String liveVhallUserId;
    @Autowired
    private XcRedisCacheService xcRedisCacheService;
    
    @Override
    public Page<CourseApplyInfo> findCoursePage(
            CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize) {
        Page<CourseApplyInfo> page = courseApplyDao.findCloudClassCoursePage(
                courseApplyInfo, pageNumber, pageSize);
        return page;
    }

    @Override
    public CourseApplyInfo findCourseApplyById(Integer id) {
        CourseApplyInfo courseApply = courseApplyDao
                .findCourseApplyAndMenuById(id);
        courseApply.setPrice(courseApply.getPrice() * 10);
        if (!courseApply.getCollection()
                && courseApply.getCourseForm() == CourseForm.VOD.getCode()) {
            String audioStr = "";
            if (courseApply.getMultimediaType() == Multimedia.AUDIO.getCode()) {
                audioStr = "_2";
            }

            String playCode = ccUtils.getPlayCode(courseApply.getCourseResource(), audioStr, "600", "490");

            courseApply.setPlayCode(playCode);
        }
        if (courseApply.getCollection()) {
            List<CourseApplyInfo> courseApplyInfos = courseApplyDao
                    .getCourseByCollectionId(courseApply.getId());
            courseApply.setCourseApplyInfoList(courseApplyInfos);
        }
        if ((courseApply.getStatus() == ApplyStatus.NOT_PASS.getCode())
                && courseApply.getDismissal() != null) {
            courseApply.setDismissalText(CourseDismissal
                    .getDismissal(courseApply.getDismissal()));
        }
        return courseApply;
    }

    @Override
    public void savePass(Integer courseApplyId, String createPerson) throws Exception {
        CourseApplyInfo courseApply = courseApplyDao.findCourseApplyById(courseApplyId);
        if (courseApply.getStatus() != ApplyStatus.UNTREATED.getCode()) {
            throw new RuntimeException("课程已被他人审核");
        }
        if (courseApply.getIsDelete()) {
            throw new RuntimeException("该课程申请被主播重新发起");
        }
        Course course = savePassCourse(courseApply, createPerson);
        // 对于专辑，通过时，所有课程都通过
        if (courseApply.getCollection()) {
            deleteCollectionCourseByCollectionId(course.getId());
            List<CourseApplyInfo> courseApplyInfos = courseApplyDao.getCourseDeatilsByCollectionId(courseApply.getId());
            for (int i = 0; i < courseApplyInfos.size(); i++) {
                if (courseApplyInfos.get(i).getStatus() != 1) {
                    throw new RuntimeException("专辑中包含未通过课程，无法通过审核");
                }
                Course subCourse = getCourseByApplyId(courseApplyInfos.get(i));
                course.setCollectionCourseSort(courseApplyInfos.get(i)
                        .getCollectionCourseSort());
                // 保存专辑-课程关系
                saveCollectionCourse(course, subCourse);
            }
            //如果专辑课程已经更新完，需要标识
            if (course.getCourseNumber() != null && Integer.compare(courseApplyInfos.size(), course.getCourseNumber()) >= 0) {
                courseDao.update(course);
                
                xcRedisCacheService.deleteCourseMessageReminding(course.buildCourseMessage(), RedisCacheKey.COLLECTION_COURSE_REMIND_KEY);
            } else {
            	xcRedisCacheService.saveCourseMessageReminding(course.buildCourseMessage(), RedisCacheKey.COLLECTION_COURSE_REMIND_KEY);
            }
            saveCollectionUpdateCollectionId(courseApplyId, course.getId());
        }
        sendCoursePassMessage(course, createPerson);
    }

    private void saveCollectionUpdateCollectionId(Integer collectionApplyId, Integer collectionId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("collectionApplyId", collectionApplyId).addValue("collectionId", collectionId);
        this.getDao().getNamedParameterJdbcTemplate()
                .update("UPDATE collection_course_apply_update_date" +
                        " SET collection_id = :collectionId" +
                        " WHERE collection_apply_id = :collectionApplyId", mapSqlParameterSource);
    }

    /**
     * 课程审核通过消息
     *
     * @param course
     * @param createPerson
     */
    private void sendCoursePassMessage(Course course, String createPerson) {
        try {
            String userId = course.getUserLecturerId();
            String typeText;
            boolean isLiveCourse = false;
            String title = course.getGradeName();
            Integer courseType = course.getType();
            RouteTypeEnum routeTypeEnum;

            if (course.getCollection() != null && course.getCollection()) {
                routeTypeEnum = RouteTypeEnum.COLLECTION_COURSE_LIST_ONLY_WEB;
                typeText = "专辑";
            } else {
                if (courseType.equals(1)) {
                    typeText = "直播";
                    isLiveCourse = true;
                    routeTypeEnum = RouteTypeEnum.LIVE_COURSE_LIST;
                } else {
                    if (courseType.equals(2)) {
                        if (course.getMultimediaType() == 1) {
                            typeText = "视频";
                            routeTypeEnum = RouteTypeEnum.VIDEO_COURSE_LIST_ONLY_WEB;
                        } else {
                            typeText = "音频";
                            routeTypeEnum = RouteTypeEnum.AUDIO_COURSE_LIST_ONLY_WEB;
                        }
                    } else {
                        typeText = "线下";
                        routeTypeEnum = RouteTypeEnum.OFFLINE_COURSE_LIST_ONLY_WEB;
                    }
                }
            }

            String content = MessageFormat.format(isLiveCourse ? WEB_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS : WEB_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS, typeText, title);
            Map<String, String> params = new HashMap<>();
            params.put("type", typeText);
            params.put("courseName", title);

            Map<String, String> weixinParams = new HashMap<>(3);
            weixinParams.put("first", MessageFormat.format(isLiveCourse ? WEIXIN_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS : WEIXIN_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS, typeText, title));
            weixinParams.put("keyword1", title);
            weixinParams.put("keyword2", "审核通过");
            weixinParams.put("remark", "");
            
            commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                    .buildWeb(content)
                    .buildAppPush(MessageFormat.format(isLiveCourse ? APP_PUSH_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS : APP_PUSH_NOT_LIVE_COURSE_APPLY_SUCCESS_MESSAGE_TIPS, typeText, title))
                    .buildSms(isLiveCourse ? liveCourseApplyPassCode : notLiveCourseApplyPassCode, params)
                    .buildWeixin(weixinCourseApplyCode, weixinParams)
                    .detailId(String.valueOf(course.getId()))
                    .build(userId, routeTypeEnum, createPerson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCollectionCourseByCollectionId(Integer collectionId) {
        String sql = "DELETE FROM `collection_course` WHERE collection_id=:collectionId";
        Map<String, Integer> paramMap = new HashMap<String, Integer>();
        paramMap.put("collectionId", collectionId);
        dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    }

    private Course getCourseByApplyId(CourseApplyInfo courseApplyInfo) {
        DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
        dc.add(Restrictions.eq("applyId", courseApplyInfo.getId()));
        Course course = dao.findEntity(dc);
        return course;
    }

    private void saveCollectionCourse(Course collection, Course course) {
        String sql = "INSERT INTO collection_course(collection_id,course_id,create_time,collection_course_sort) "
                + " VALUES (:cId,:courseId,now(),:collectionCourseSort)";
        Map<String, Integer> paramMap = new HashMap<String, Integer>();
        paramMap.put("cId", collection.getId());
        paramMap.put("courseId", course.getId());
        paramMap.put("collectionCourseSort", course.getCollectionCourseSort());

        dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
    }

    @Override
    public void saveNotPass(CourseApplyInfo courseApplyInfo, String userId) {
        CourseApplyInfo courseApply = courseApplyDao
                .findCourseApplyById(courseApplyInfo.getId());
        if (courseApply.getStatus() != ApplyStatus.UNTREATED.getCode()) {
            throw new RuntimeException("课程已被他人审核");
        }
        if (courseApply.getIsDelete()) {
            throw new RuntimeException("该课程申请被主播重新发起");
        }
        courseApply.setDismissal(courseApplyInfo.getDismissal());
        courseApply.setDismissalRemark(courseApplyInfo.getDismissalRemark());
        saveNotPassCourse(courseApply, userId);
        sendCourseNotPassMessage(courseApply, userId);
    }

    private void sendCourseNotPassMessage(CourseApplyInfo courseApplyInfo, String createPerson) {
        String n = "";
        String title = courseApplyInfo.getTitle();
        Integer courseForm = courseApplyInfo.getCourseForm();
        RouteTypeEnum routeTypeEnum = RouteTypeEnum.NONE;
        String reason = CourseDismissal.getDismissal(courseApplyInfo.getDismissal());
        String detailReason = reason + (StringUtils.isNotBlank(courseApplyInfo.getDismissalRemark()) ? ("," + courseApplyInfo.getDismissalRemark()) : "");
        if (courseApplyInfo.getCollection() != null && courseApplyInfo.getCollection()) {
            routeTypeEnum = RouteTypeEnum.COLLECTION_COURSE_LIST_ONLY_WEB;
            n = "专辑";
        } else {
            if (courseForm.equals(1)) {
                routeTypeEnum = RouteTypeEnum.LIVE_COURSE_LIST_ONLY_WEB;
                n = "直播";
            } else if (courseForm.equals(2)) {
                if (courseApplyInfo.getMultimediaType().equals(1)) {
                    routeTypeEnum = RouteTypeEnum.VIDEO_COURSE_LIST_ONLY_WEB;
                    n = "视频";
                } else {
                    routeTypeEnum = RouteTypeEnum.AUDIO_COURSE_LIST_ONLY_WEB;
                    n = "音频";
                }
            } else if (courseForm.equals(3)) {
                routeTypeEnum = RouteTypeEnum.OFFLINE_COURSE_LIST_ONLY_WEB;
                n = "线下";
            }
        }

        String content = MessageFormat.format(WEB_COURSE_APPLY_FAIL_MESSAGE_TIPS, n, title, detailReason);
        Map<String, String> params = new HashMap<>(2);
        params.put("type", n);
        params.put("courseName", title);

        Map<String, String> weixinParams = new HashMap<>(4);
        weixinParams.put("first", MessageFormat.format(WEIXIN_COURSE_APPLY_FAIL_MESSAGE_TIPS, title, detailReason));
        weixinParams.put("keyword1", title);
        weixinParams.put("keyword2", "未通过审核");
        weixinParams.put("remark", "请按要求修改后再提交申请，感谢您的支持。");
        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                .buildWeb(content)
                .buildSms(courseApplyNotPassCode, params)
                .buildAppPush(MessageFormat.format(APP_PUSH_COURSE_APPLY_FAIL_MESSAGE_TIPS, n, title, detailReason))
                .buildWeixin(weixinCourseApplyCode, weixinParams)
                .build(courseApplyInfo.getUserId(), routeTypeEnum, createPerson)
        );
    }

    @Override
    public Page<CourseApplyResource> findCourseApplyResourcePage(
            CourseApplyResource courseApplyResource, int currentPage,
            int pageSize) {
        Page<CourseApplyResource> page = courseApplyDao
                .findCourseApplyResourcePage(courseApplyResource, currentPage,
                        pageSize);
        for (CourseApplyResource car : page.getItems()) {
            String audioStr = "";
            if (car.getMultimediaType() == 2) {
                audioStr = "_2";
            }

            String playCode = ccUtils.getPlayCode(car.getResource(), audioStr, "600", "490");

            car.setPlayCode(playCode);
        }
        return page;
    }

    @Override
    public void deleteOrRecoveryCourseApplyResource(
            Integer courseApplyResourceId, Boolean delete) {
        DetachedCriteria dc = DetachedCriteria
                .forClass(CourseApplyResource.class);
        dc.add(Restrictions.eq("id", courseApplyResourceId));
        CourseApplyResource courseApplyResource = dao.findEntity(dc);
        courseApplyResource.setDeleted(delete);
        dao.update(courseApplyResource);
    }

    @Override
    public Page<CourseApplyInfo> findCoursePageByUserId(
            CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize) {
        Page<CourseApplyInfo> page = courseApplyDao.findCoursePageByUserId(
                courseApplyInfo, pageNumber, pageSize);
        return page;
    }

    @Override
    public void updateRecommendSort(Integer id, Integer recommendSort) {
        String hqlPre = "from CourseAnchor where id = ?";
        CourseAnchor courseAnchor = dao.findByHQLOne(hqlPre,
                new Object[]{id});
        if (courseAnchor != null) {
            courseAnchor.setRecommendSort(recommendSort);
            dao.update(courseAnchor);
        }
    }

    private void saveNotPassCourse(CourseApplyInfo courseApply, String userId) {
        courseApply.setStatus(ApplyStatus.NOT_PASS.getCode());
        courseApply.setReviewPerson(userId);
        courseApply.setReviewTime(new Date());
        dao.update(courseApply);
    }

    public Course savePassCourse(CourseApplyInfo courseApply, String userId) throws Exception {
        courseApply.setStatus(ApplyStatus.PASS.getCode());
        courseApply.setReviewPerson(userId);
        courseApply.setReviewTime(new Date());
        dao.update(courseApply);

        return saveCourseApply2course(courseApply);

    }

    private Course saveCourseApply2course(CourseApplyInfo courseApply) throws Exception {
        Course course = getCourse4Apply(courseApply.getOldApplyInfoId());
        courseService.checkName(null, courseApply.getTitle(), courseApply.getOldApplyInfoId());
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
        course.setCreatePerson(ManagerUserUtil.getUsername());

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
        course.setMultimediaType(courseApply.getMultimediaType());
        course.setStatus("0");
        if (course.getType() == CourseForm.OFFLINE.getCode()) {
            // 线下课程
            course.setAddress(courseApply.getAddress());
            course.setStartTime(courseApply.getStartTime());
            course.setEndTime(courseApply.getEndTime());
            course.setCity(courseApply.getCity());
            // 添加城市管理
            courseService.addCourseCity(course.getCity());
        } else if (course.getType() == CourseForm.LIVE.getCode()) {
            course.setStartTime(courseApply.getStartTime());
            if (course.getRecord() == null) {
                course.setRecord(true);
            }
            if(course.getMultimediaType()==Multimedia.VIDEO.getCode()){
                if (StringUtils.isBlank(course.getDirectId())) {
                    course.setDirectId(RoomService.create());
                    // 将直播课设置为预告
                    course.setLiveStatus(2);
                }
                if (StringUtils.isBlank(course.getChannelId())) {
                    course.setChannelId(ChannelService.create());
                }
            }else{
                if (StringUtils.isBlank(course.getChannelId())) {
                    String channelId = ChannelService.create();
                    course.setChannelId(channelId);
                    // 将直播课设置为预告
                    course.setLiveStatus(2);
                }
            }
        } else if (course.getType() == CourseForm.VOD.getCode()) {
            // yuruixin-2017-08-16
            // 课程资源
            course.setMultimediaType(courseApply.getMultimediaType());
            course.setDirectId(courseApply.getCourseResource());
        }
        course.setIsRecommend(0);
        course.setClassRatedNum(0);
        course.setApplyId(courseApply.getId());
        course.setCollectionCourseSort(courseApply.getCollectionCourseSort());
        course.setCollection(courseApply.getCollection());
        course.setCourseNumber(courseApply.getCourseNumber());

        course.setClientType(courseApply.getClientType());
        course.setTeaching(courseApply.getTeaching());

        if (course.getId() != null) {
            // 若course有id，说明该申请来自一个已经审核通过的课程，则更新
            dao.update(course);
            cacheService.delete(LIVE_COURSE_REMIND_LAST_TIME_KEY + RedisCacheKey.REDIS_SPLIT_CHAR + course.getId());
        } else {
            // 当前时间
            course.setCreateTime(new Date());
            dao.save(course);
            if(course.getTeaching()){
                enrolService.saveCourseTeaching4Init(course.getId(),course.getUserLecturerId());
            }
        }
        savecourseMessageReminding(course);
        return course;
    }

    private Course getCourse4Apply(Integer oldApplyInfoId) {
        if(oldApplyInfoId==null){
            return new Course();
        }
        List<Integer> applyIdList = new ArrayList<>();
        getOldApplyIds(oldApplyInfoId,applyIdList);
        String applyIds = StringUtils.join(applyIdList,",");
        String courseTypeSql = "from Course oc where oc.applyId in (:applyIds)";
        Map<String, Object> params = new HashMap<String, Object>();
        List<Course> courses = (List<Course>)dao.getHibernateTemplate().findByNamedParam(courseTypeSql, "applyIds", applyIdList);
        if(courses.size()>1){
            throw new RuntimeException("数据有误");
        }else if(courses.size()==1){
            return courses.get(0);
        }
        return new Course();
    }

    private List<Integer> getOldApplyIds(Integer oldApplyInfoId, List<Integer> applyIdList) {
        if(oldApplyInfoId!=null && oldApplyInfoId !=0){
            applyIdList.add(oldApplyInfoId);
            String sql = "SELECT cai.old_apply_info_id FROM `course_apply_info` cai WHERE cai.id = ?";
            Integer oai = dao.queryForInt(sql, oldApplyInfoId);
            getOldApplyIds(oai,applyIdList);
        }
        return applyIdList;
    }

    /**
     * Description：创建一个直播活动 creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>
     * email: yuruixin@ixincheng.com
     * @Date: 下午 8:52 2018/1/22 0022
     **/
    public String createWebinar(Course entity) {
        Webinar webinar = new Webinar();
        webinar.setSubject(entity.getGradeName());
        webinar.setIntroduction(entity.getDescription());
        Date start = entity.getStartTime();
        String start_time = start.getTime() + "";
        start_time = start_time.substring(0, start_time.length() - 3);
        webinar.setStart_time(start_time);
        CourseAnchor courseAnchor = anchorService
                .findCourseAnchorByUserId(entity.getUserLecturerId());
        webinar.setHost(courseAnchor.getName());
        Integer directSeeding = entity.getDirectSeeding();
        if (directSeeding == null) {
            directSeeding = 3;
        }
        webinar.setLayout(directSeeding.toString());
        OnlineUser u = onlineUserService.getOnlineUserByUserId(entity
                .getUserLecturerId());
        webinar.setUser_id(u.getVhallId());
        String webinarId = VhallUtil.createWebinar(webinar);

        VhallUtil.setActiveImage(webinarId,
                VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
        VhallUtil.setCallbackUrl(webinarId, vhall_callback_url,
                vhall_private_key);
        return webinarId;
    }

    public String updateWebinar(Course entity) {
        //更新封面
        VhallUtil.setActiveImage(entity.getDirectId(), VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
        Webinar webinar = new Webinar();
        webinar.setId(entity.getDirectId() + "");
        webinar.setSubject(entity.getGradeName());
        webinar.setIntroduction(entity.getDescription());
        Date start = entity.getStartTime();
        String start_time = start.getTime() + "";
        start_time = start_time.substring(0, start_time.length() - 3);
        webinar.setStart_time(start_time);
        webinar.setHost(entity.getLecturer());
        webinar.setLayout(entity.getDirectSeeding() + "");
        return VhallUtil.updateWebinar(webinar);
    }

    void savecourseMessageReminding(Course course) {
    	System.out.println(xcRedisCacheService);
        if (course.getType().equals(CourseForm.LIVE.getCode())) {
        	course.buildCourseMessage();
        	xcRedisCacheService.saveCourseMessageReminding(course.buildCourseMessage(), RedisCacheKey.LIVE_COURSE_REMIND_KEY);
        } else if (course.getType().equals(CourseForm.OFFLINE.getCode())) {
        	xcRedisCacheService.saveCourseMessageReminding(course.buildCourseMessage(), RedisCacheKey.OFFLINE_COURSE_REMIND_KEY);
        }
    }
}
