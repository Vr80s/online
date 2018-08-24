package com.xczhihui.course.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.xczhihui.common.support.lock.Lock;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.EmailUtil;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.CourseForm;
import com.xczhihui.common.util.enums.CourseType;
import com.xczhihui.common.util.enums.LiveCaseType;
import com.xczhihui.common.util.enums.LivePushStreamStatus;
import com.xczhihui.common.util.enums.LiveStatus;
import com.xczhihui.common.util.enums.LiveStatusEvent;
import com.xczhihui.common.util.enums.MessageTypeEnum;
import com.xczhihui.common.util.enums.Multimedia;
import com.xczhihui.common.util.enums.PayStatus;
import com.xczhihui.common.util.enums.PlayBackType;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.common.util.enums.VhallCustomMessageType;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.common.util.vhallyun.ChannelService;
import com.xczhihui.common.util.vhallyun.InteractionService;
import com.xczhihui.common.util.vhallyun.MessageService;
import com.xczhihui.common.util.vhallyun.RoomService;
import com.xczhihui.common.util.vhallyun.VideoService;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.exception.CourseException;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.mapper.CriticizeMapper;
import com.xczhihui.course.mapper.FocusMapper;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.ICourseService;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.ShareInfoVo;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    public static final String START_EVENT = "start";
    public static final String STOP_EVENT = "stop";

    

	private static final String WEB_TREATMENT_MESSAGE_TIPS = 
			"【熊猫中医】您已成功预约{0}医师{1}的远程诊疗，请做好诊前准备并及时登录熊猫中医平台以便{2}老师进行远程协助诊疗。";
	
	private static final String APP_TREATMENT_MESSAGE_TIPS = 
			"【熊猫中医】您已成功预约{0}医师{1}的远程诊疗，请做好诊前准备。";
    
    
    @Autowired
    private CourseMapper iCourseMapper;

    @Autowired
    private CriticizeMapper criticizeMapper;

    @Autowired
    private FocusMapper focusMapper;
    
    @Value("${weixin.course.remind.code}")
    private String weixinTemplateMessageRemindCode;
    
    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private CacheService cacheService;

    @Override
    public Page<CourseLecturVo> selectCoursePage(Page<CourseLecturVo> page) {
        List<CourseLecturVo> records = iCourseMapper.selectCoursePage(page);
        return page.setRecords(records);
    }

    @Override
    public CourseLecturVo selectCourseDetailsById(String userId, Integer courseId) {

        CourseLecturVo cv = iCourseMapper.selectCourseDetailsById(courseId);
        if (cv == null) {
            throw new CourseException("获取课程详情有误");
        }
        /**
         * 这里需要判断是否购买过了
         */
        if (userId != null) {
            // 是否关注
            Integer isFours = focusMapper.isFoursLecturer(userId, cv.getUserLecturerId());
            if (isFours != 0) {
                cv.setIsFocus(1);
            }
            Integer falg = criticizeMapper.hasCourse(courseId, userId);
            
            //如果是付费课程，判断这个课程是否已经被购买了
            if (cv.getWatchState() == 0) { // 付费课程    
                if (falg > 0) {
                    cv.setWatchState(2);
                }
            } else if (cv.getWatchState() == 1) { // 免费课程    如果是免费的  判断是否学习过
                if (falg > 0) {
                    cv.setLearning(1);
                }
            }
        }

        return cv;
    }

    @Override
    public List<Map<String, Object>> selectLearningCourseListByUserId(Integer pageSize, String id) {
        List<CourseLecturVo> listAll = iCourseMapper.selectLearningCourseListByUserId(pageSize, id);

        List<Map<String, Object>> mapCourseList = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapTj = new HashMap<String, Object>();
        Map<String, Object> mapNw = new HashMap<String, Object>();
        List<CourseLecturVo> listTj = new ArrayList<CourseLecturVo>();
        List<CourseLecturVo> listNw = new ArrayList<CourseLecturVo>();
        for (CourseLecturVo courseLecturVo : listAll) {
            if ("我的课程".equals(courseLecturVo.getNote())) {
                listTj.add(courseLecturVo);
            }
            if ("已结束课程".equals(courseLecturVo.getNote())) {
                listNw.add(courseLecturVo);
            }
        }
        mapTj.put("title", "我的课程");
        mapTj.put("courseList", listTj);

        mapNw.put("title", "已结束课程");
        mapNw.put("courseList", listNw);

        mapCourseList.add(mapTj);
        mapCourseList.add(mapNw);

        return mapCourseList;
    }

    @Override
    public Integer selectMyFreeCourseListCount(String id) {
        return iCourseMapper.selectMyFreeCourseListCount(id);
    }

    @Override
    public Page<CourseLecturVo> selectMyPurchasedCourseList(Page<CourseLecturVo> page, String id) {
        List<CourseLecturVo> records = iCourseMapper.selectMyPurchasedCourseList(page, id);

        return page.setRecords(records);
    }

    @Override
    public List<CourseLecturVo> selectCoursesByCollectionId(Integer collectionId) {
        List<CourseLecturVo> courses = iCourseMapper.selectCoursesByCollectionId(collectionId);
        return courses;
    }

    @Override
    public CourseLecturVo selectLecturerRecentCourse(String lecturerId) {
        return selectLecturerRecentCourse(lecturerId, false);
    }

    @Override
    public CourseLecturVo selectLecturerRecentCourse(String lecturerId, boolean onlyFreee) {
        return iCourseMapper.selectLecturerRecentCourse(lecturerId, onlyFreee);
    }

    @Override
    public Page<CourseLecturVo> selectLecturerAllCourse(Page<CourseLecturVo> page, String lecturerId) {
        return selectLecturerAllCourseByType(page, lecturerId, null, false);
    }

    @Override
    public Page<CourseLecturVo> selectLecturerAllCourseByType(Page<CourseLecturVo> page, String lecturerId,
                                                              Integer type, boolean onlyFree) {
        List<CourseLecturVo> records = iCourseMapper.selectLecturerAllCourseByType(page, lecturerId, type, onlyFree);
        return page.setRecords(records);
    }

    @Override
    public List<CourseLecturVo> selectUserConsoleCourse(String id) {
        return iCourseMapper.selectUserConsoleCourse(id);
    }

    @Override
    public Page<CourseLecturVo> selectMenuTypeAndRandCourse(Page<CourseLecturVo> page, Integer menuId) {
        List<CourseLecturVo> records = iCourseMapper.selectMenuTypeAndRandCourse(page, menuId);
        return page.setRecords(records);
    }

    @Override
    public Page<CourseLecturVo> selectAppCourseApplyPage(Page<CourseLecturVo> page, String userId, Integer courseForm, Integer multimediaType) {
        List<CourseLecturVo> records = iCourseMapper.selectAppCourseApplyPage(page, userId, courseForm, multimediaType);
        page.setRecords(records);
        return page.setRecords(records);
    }

    @Override
    public CourseLecturVo selectCourseMiddleDetailsById(String userId, Integer courseId) {

        CourseLecturVo cv = iCourseMapper.selectCourseMidileDetailsById(courseId);
        if (cv == null) {
            throw new CourseException("获取课程详情有误");
        }
        /**
         * 这里需要判断是否购买过了
         */
        if (userId != null) {
            // 是否关注
            Integer isFours = focusMapper.isFoursLecturer(userId, cv.getUserLecturerId());
            if (isFours != 0) {
                cv.setIsFocus(1);
            }
            Integer falg = criticizeMapper.hasCourse(courseId, userId);
            //如果是付费课程，判断这个课程是否已经被购买了
            if (cv.getWatchState() == 0) { // 付费课程     
                if (falg > 0) {
                    cv.setWatchState(2);
                }
                //如果是付费课程，如果不是专辑的话，那么就查看是否属于其中一个专辑。
                if(CourseType.VIDEO.getId() == cv.getType()
                		|| CourseType.AUDIO.getId() == cv.getType()) {
                	Map<String,Object> collectionHint = iCourseMapper.selectTheirCollection(courseId);
                	
                	cv.setCollectionHint(collectionHint);
                }
                
                //如果是免费的  判断是否学习过
            } else if (cv.getWatchState() == 1) { // 免费课程
                if (falg > 0) {
                    cv.setLearning(1);
                }
            }
        }
        
        
        
        
        return cv;
    }

    @Override
    public List<CourseLecturVo> selectUserConsoleCourseLiveByPage(
            Page<CourseLecturVo> page, String userId) {
        return iCourseMapper.selectUserConsoleCourseLiveByPage(page, userId);
    }

    @Override
    public List<CourseLecturVo> listenCourseList(boolean onlyFree) {
        return iCourseMapper.listenCourseList(onlyFree);
    }

    @Override
    public List<CourseLecturVo> listenCourseList() {
        return listenCourseList(false);
    }

    @Override
    public CourseLecturVo selectUserCurrentCourseStatus(Integer courseId,
                                                        String userId) {
        CourseLecturVo cv = iCourseMapper.selectUserCurrentCourseStatus(courseId, userId);
        return cv;
    }

    @Override
    public CourseLecturVo selectCurrentCourseStatus(Integer courseId) {

        CourseLecturVo cv = iCourseMapper.selectCurrentCourseStatus(courseId);
        return cv;
    }

    @Override
    public String selectCourseDescription(Integer type, String typeId) {

        return XzStringUtils.formatA(iCourseMapper.selectCourseDescription(type, typeId));
    }

    @Override
    public Page<CourseLecturVo> myCourseType(Page<CourseLecturVo> page, String userId, Integer type) {
        List<CourseLecturVo> list = iCourseMapper.myCourseType(page, userId, type);
        return page.setRecords(list);
    }

    @Override
    public List<Map<String, Object>> getLiveStatusList() {
        List<Map<String, Object>> list = LiveStatus.getLiveStatusList();
        return list;
    }

    @Override
    public List<Map<String, Object>> getPayStatusList() {
        List<Map<String, Object>> list = PayStatus.getPayStatusList();
        return list;
    }


    @Override
    public List<CourseLecturVo> selectCourseByLearndCount(Page<CourseLecturVo> page, Integer type) {
        return iCourseMapper.selectCourseByLearndCount(page, type);
    }

    @Override
    public List<Course> getAllCourseByStatus() {
        List<Course> list = iCourseMapper.getAllCourseByStatus();
        return list;
    }

    @Override
    public List<CourseLecturVo> selectRecommendSortAndRandCourse(Page<CourseLecturVo> page) {

        return iCourseMapper.selectRecommendSortAndRandCourse(page);
    }

    @Override
    public List<Course> findByIds(List<Integer> ids) {
        List<Course> courses = new ArrayList<>();
        for (Integer id : ids) {
            courses.add(iCourseMapper.selectById(id));
        }
        return courses;
    }

    @Override
    public List<Map<String, Object>> findByMenuIdExcludeId(Integer menuId, Integer courseId) {
        return iCourseMapper.selectByMenuIdExcludeSelf(menuId, courseId);
    }

    @Override
    public CourseLecturVo selectCourseStatusDeleteUserLecturerId(Integer courseId) {
        return iCourseMapper.selectCourseStatusDeleteUserLecturerId(courseId);
    }

    @Override
    @Transactional(readOnly = false)
    public String getLiveCourseUrl4Wechat(String userId, String courseId) {
        Integer lineStatus = iCourseMapper.getLineStatus(courseId);
        //直播中、直播结束、即将直播
        if (lineStatus == 1 || lineStatus == 3 || lineStatus == 4) {
            /**
             * 增加播放记录
             */
            return "/xcview/html/details.html?courseId=" + courseId;
        }
        return "/xcview/html/live_play.html?my_study=" + courseId;
    }

    @Override
    public Page<Map<String, Object>> selectOfflineCourseByAnchorId(String anchorId) {
        //TODO 当前需求列出所有线下课
        Page<Map<String, Object>> page = new Page<>(1, 1000);
        List<Map<String, Object>> results = iCourseMapper.selectOfflineCourseByAnchorId(page, anchorId);
        return page.setRecords(results);
    }

    @Override
    public Integer selectLiveCountByUserIdAndType(String userId, Integer type) {
        return iCourseMapper.selectLiveCountByUserIdAndType(userId, type);
    }

    @Override
    public List<Map<String, Object>> list(Integer type, String userId) {
        return iCourseMapper.selectCourseByType(type, userId);
    }

    @Override
    public ShareInfoVo selectShareInfoByType(Integer type, String id) {

        return iCourseMapper.selectShareInfoByType(type, id);
    }

    @Override
    public Course findSimpleInfoById(int id) {
        return iCourseMapper.findSimpleInfoById(id);
    }

    @Override
    public List<Map<String, Object>> doctorCourseList(String lecturerId, String userId, boolean onlyFreee) {

        List<Map<String, Object>> alllist = new ArrayList<Map<String, Object>>();

        List<CourseLecturVo> records = selectTeachingCoursesByUserId(new Page<CourseLecturVo>(1, 4), lecturerId,
                userId);
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("text", "跟师直播");
        map1.put("code", 5);
        map1.put("courseList", records);
        alllist.add(map1);

        List<CourseLecturVo> recordsLive = iCourseMapper.selectLecturerAllCourseByType(new Page<CourseLecturVo>(1, 6),
                lecturerId, CourseType.LIVE.getId(), onlyFreee);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("text", "直播课程");
        map.put("code", CourseType.LIVE.getId());
        map.put("courseList", recordsLive);

        alllist.add(map);

        return alllist;
    }

    @Override
    public List<CourseLecturVo> selectTeachingCoursesByUserId(Page<CourseLecturVo> page, String lecturerId, String userId) {
        //userId为医师的用户id
        List<CourseLecturVo> courses = iCourseMapper.selectTeachingCourse(page, lecturerId, userId);
        return courses;
    }

    @Override
    public boolean selectQualification4TeachingCourse(String userId, Integer courseId) {
        int count = iCourseMapper.selectQualification4TeachingCourse(userId, courseId);
        return count > 0;
    }

    @Override
    public CourseLecturVo selectDoctorLiveRoomRecentCourse(String userId, boolean onlyFreee) {
        return iCourseMapper.selectDoctorLiveRoomRecentCourse(userId, onlyFreee);
    }


    /**
     * 查看当前用户是否关注了主播以及是否购买了这个课程
     *
     * @param cv
     * @param accountIdOpt
     * @param courseId
     * @return
     */
    private CourseLecturVo assignFocusAndWatchState(CourseLecturVo cv, Optional<String> accountIdOpt, Integer courseId) {
        /**
         * 这里需要判断是否购买过了
         */
        if (accountIdOpt.isPresent()) {
            String accountId = accountIdOpt.get();
            // 是否关注
            Integer isFours = focusMapper.isFoursLecturer(accountId, cv.getUserLecturerId());
            if (isFours != 0) {
                cv.setIsFocus(1);
            }
            Integer falg = criticizeMapper.hasCourse(courseId, accountId);
            //如果是付费课程，判断这个课程是否已经被购买了
            if (cv.getWatchState() == 0) { // 付费课程
                if (falg > 0) {
                    cv.setWatchState(2);
                }
                //如果是免费的  判断是否学习过
            } else if (cv.getWatchState() == 1) { // 免费课程
                if (falg > 0) {
                    cv.setLearning(1);
                }
            }
        }
        return cv;
    }

    @Override
    public void updatePlayBackStatusByRecordId(String recordId, Integer status) {

        iCourseMapper.updatePlayBackStatusByRecordId(recordId, status);
    }

    @Override
    public void updatePlayBackStatusAndSendVahllYunMessageByRecordId(String recordId, Integer status) throws Exception {

    	/**
    	 * 回放是否生成成功
    	 */
    	iCourseMapper.updatePlayBackStatusByRecordId(recordId, status);
    	
    	CourseLecturVo course = iCourseMapper.selectCourseByRecordId(recordId);

       
        Integer type = VhallCustomMessageType.PLAYBACK_GENERATION_SECCESS.getCode();
        String message = "回放生成成功";
        //发送im消息
        if (status.equals(PlayBackType.GENERATION_SUCCESS.getCode())) {
            type = VhallCustomMessageType.PLAYBACK_GENERATION_SECCESS.getCode();
            message = "回放生成成功";
        } else if (status.equals(PlayBackType.GENERATION_FAILURE.getCode())) {
            type = VhallCustomMessageType.PLAYBACK_GENERATION_FAILURE.getCode();
            message = "回放生成失败";
            try {
            	String content = "未找到课程信息。";
                List<Course> courses = iCourseMapper.selectByMap(ImmutableMap.of("record_id", recordId));
                if (courses != null && courses.isEmpty()) {
                	content = "课程名："+courses.get(0).getGradeName()+",课程id:"+courses.get(0).getId()+",回放id:"+recordId;
                }
                //发送email ,报告错误  
                EmailUtil.sendExceptionMailBySSL("课程服务：回放生成失败", "回放生成失败。回放recordId = "+recordId,content);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        /*
         * 如果直播没有回放，不发送消息到客户端
         */
        if(course.getRecord()) {
            JSONObject job = new JSONObject();
            job.put("type", type);
            job.put("message", message);
            MessageService.sendMessage(MessageService.CustomBroadcast, job.toJSONString(), course.getChannelId());
        }
    }

    @Override
    public Integer updateCourseLiveStatus(String event, String roomId,String clientType) {
        List<Course> courses = iCourseMapper.selectByMap(ImmutableMap.of("direct_id", roomId));
        if (courses == null || courses.isEmpty()) {
            return null;
        }
        Course course = courses.get(0);

        String startOrEnd = "";
        Integer type = 0;
        if (course != null) {
            switch (event) {
                case START_EVENT:
                    course.setLiveStatus(1);
                    type = VhallCustomMessageType.LIVE_START.getCode();
                    // -- 》app端发起的直播
                    if(MultiUrlHelper.URL_TYPE_APP.equals(clientType)) {
                    	course.setLiveSourceType(true);
                    }
                    break;
                case STOP_EVENT:
                    course.setLiveStatus(3);
                    type = VhallCustomMessageType.LIVE_END.getCode();
                    Date startTime = course.getStartTime();
                    Date currentTime = new Date();
                    if (course.getChannelId() != null) {
                        try {
                            String recordId = VideoService.createRecord(course.getDirectId(), null, null);
                            course.setRecordId(recordId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                default:
                    break;
            }
            // 更改直播开始结束时间,更改直播当前状态
            iCourseMapper.updateById(course);
            // 发送直播开始通知广播
            liveStatusUpdateNotice(course.getChannelId(), type);
            return course.getId();
        }
        return null;
    }

    public void liveStatusUpdateNotice(String channelId, Integer type) {
        Map<String, Object> body = new HashMap<>(2);
        body.put("type", type);
        body.put("message", ImmutableMap.of("content", "", "headImg", "", "username", "", "role", ""));
        try {
            MessageService.sendMessage(MessageService.CustomBroadcast, JSONObject.toJSONString(body), channelId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer updateCourseLiveCase(String channelId) {
        List<Course> courses = iCourseMapper.selectByMap(ImmutableMap.of("channel_id", channelId));
        if (courses == null || courses.isEmpty()) {
            return null;
        }
        Course course = courses.get(0);
        course.setLiveCase(LiveCaseType.EXIT_BUT_NOT_END.getCode());
        return iCourseMapper.updateById(course);
    }

    @Override
    public Integer findLiveStatusByDirectId(String directId) {
        return iCourseMapper.selectCourseLiveStatusByDirectId(directId);
    }

    @Override
    public Integer getCourseLivePushStreamStatus(Integer courseId) {
        Course course = iCourseMapper.selectById(courseId);
        if (course == null) {
            throw new CourseException("课程数据不存在");
        }
        try {
            if (course.getDirectId() != null && !VideoService.isCanPushStream(course.getDirectId())) {
                return LivePushStreamStatus.PUSH_STREAM_ING.getCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LivePushStreamStatus.NON_PUSH_STREAM.getCode();
    }
    

	//需要医师名，需要诊疗时间
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Lock(lockName = "createTherapyLive", waitTime = 5, effectiveTime = 8)
	public Integer createTherapyLive(Integer lockId,Integer clientType,String accountId1) throws Exception {
		
    	//查看诊疗必要信息
		CourseLecturVo cv = iCourseMapper.selectTherapyLiveInfo(lockId);
		if(cv == null) {
			 throw new CourseException("课程数据有误");
		}
		
		Course course = new Course();
		//***医师的远程诊疗直播 yyyy/mm/dd 如有重复则加上编号（01,02,03….）。
		
		String gradeName = createTherapyGradeName(cv.getUserLecturerId(),cv.getDoctorName(),cv.getStartTime());
		course.setGradeName(gradeName);
        course.setAppointmentInfoId(lockId);
        //默认图
        course.setSmallImgPath("https://file.xczhihui.com/18821120655/9db25c52561d-9754170cc93c4169996f3ddc86ea30f91534824415642.png");
        //讲师的用户id
        course.setUserLecturerId(cv.getUserLecturerId());
        //媒体类型
        course.setMultimediaType(Multimedia.VIDEO.getCode());
        //直播课
        course.setType(CourseForm.LIVE.getCode());
        //回放
        course.setRecord(true);
        //常
        course.setCourseDetail(cv.getDescription());
        //讲师名字和讲师简介   --》默认把主播名字和主播介绍带过去
        course.setLecturer(cv.getHeir());
        course.setLecturerDescription(cv.getLecturerDescription());
        
        //预约时间
        course.setStartTime(cv.getStartTime());
        //结束时间
        course.setEndTime(cv.getEndTime());
        
        //客户端类型
        course.setClientType(clientType);
        //房间id
	    course.setDirectId(RoomService.create());
	    //渠道id
	    course.setChannelId(ChannelService.create());
	    //互动id
	    course.setInavId(InteractionService.create()); 
        
	     // 将直播课设置为预告
        course.setLiveStatus(2);	
        course.setSort(0);
        course.setStatus("1");
        // 推荐值
        course.setRecommendSort(0);
        // 请填写一个基数，统计的时候加上这个基数
        course.setLearndCount(0);
        course.setCurrentPrice(0d);
        course.setCreateTime(new Date());
        course.setDelete(false);
        course.setFree(true); //免费
        
        /*
		 * 保存审核信息
		 */
		iCourseMapper.insertCouserApplyInfo(course);
        /**
         * 保存课程信息
         */
        iCourseMapper.insert(course);
        
        /**
         * redis 缓存中增加数据，开播10分钟提醒。
         */
    	

        return course.getId();
	}

    @Override
    public Course selectById(Integer courseId) {
        return iCourseMapper.selectById(courseId);
    }

    @Override
    public void deleteCourseMessage(Integer courseId) {
        cacheService.delete(RedisCacheKey.OFFLINE_COURSE_REMIND_KEY + RedisCacheKey.REDIS_SPLIT_CHAR + courseId);
        cacheService.delete(RedisCacheKey.LIVE_COURSE_REMIND_KEY + RedisCacheKey.REDIS_SPLIT_CHAR + courseId);
        cacheService.delete(RedisCacheKey.COLLECTION_COURSE_REMIND_KEY + RedisCacheKey.REDIS_SPLIT_CHAR + courseId);
    }

    @Override
    public List<Course> listLiving() {
        return iCourseMapper.selectLivingCourse();
    }

    public void sendTherapyMessage(CourseLecturVo cv,String userId) throws Exception {
		
		/*
    	 * 1、发送短信提示
    	 */
        String content = MessageFormat.format(WEB_TREATMENT_MESSAGE_TIPS,cv.getDoctorName(),
        		DateUtil.treatmentTime(cv.getStartTime(), cv.getEndTime()), cv.getDoctorName());

        Map<String, String> params = new HashMap<>();
//        params.put("type", typeText);
//        params.put("courseName", title);
        
        
        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                 .buildAppPush(APP_TREATMENT_MESSAGE_TIPS)
                 .buildWeb(content)
                 //.buildSms(code, params) 需要配置下短信模板
                 .detailId(String.valueOf(cv.getId()))
                 .build(userId, RouteTypeEnum.APPOINTMENT_TREATMENT_INFO_PAGE, cv.getUserLecturerId()));
	}

	/**  
	 * <p>Title: createTherapyGradeName</p>  
	 * <p>Description: </p>  
	 * @param doctorName
	 * @param startTime
	 * @return  
	 */ 
	private String createTherapyGradeName(String userLecturerId,String doctorName, Date startTime) {
		//***医师的远程诊疗直播 yyyy/mm/dd 如有重复则加上编号（01,02,03….）。  
		String strGradeName = doctorName+"医师的远程诊疗直播"+DateUtil.formatDate(startTime,DateUtil.FORMAT_DAY);
		try {
			//编号
			String  numberStr   = iCourseMapper.selectDoctorCurrentDayTherapyNumber(startTime,userLecturerId);
			System.out.println(numberStr);
			if(numberStr!=null && XzStringUtils.isNumeric(numberStr)) {
				int number = Integer.parseInt(numberStr);
				if(number < 9) {
					numberStr = "0"+(number+1);
				}else if(number == 9) {
					numberStr = "10";
				}
				strGradeName +=numberStr;
			}else {
				strGradeName +="01";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strGradeName;
	}
	

}
