package com.xczhihui.course.service.impl;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableMap;
import com.xczhihui.common.util.EmailUtil;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.*;
import com.xczhihui.common.util.vhallyun.MessageService;
import com.xczhihui.common.util.vhallyun.VideoService;
import com.xczhihui.course.consts.MultiUrlHelper;
import com.xczhihui.course.exception.CourseException;
import com.xczhihui.course.mapper.CourseMapper;
import com.xczhihui.course.mapper.CriticizeMapper;
import com.xczhihui.course.mapper.FocusMapper;
import com.xczhihui.course.model.Course;
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

    @Autowired
    private CourseMapper iCourseMapper;

    @Autowired
    private CriticizeMapper criticizeMapper;

    @Autowired
    private FocusMapper focusMapper;


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
                
                //如果是付费课程，如果不是专辑的话，那么就查看是否属于其中一个专辑。
                
                if(CourseType.VIDEO.getId() == cv.getType()
                		|| CourseType.AUDIO.getId() == cv.getType()) {
                	Map<String,Object> collectionHint = iCourseMapper.selectTheirCollection(courseId);
                	
                	cv.setCollectionHint(collectionHint);
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
        if(!course.getRecord()) {
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
                    startOrEnd = "start_time";
                    course.setLiveStatus(1);
                    type = VhallCustomMessageType.LIVE_START.getCode();
                    // -- 》app端发起的直播
                    if(MultiUrlHelper.URL_TYPE_APP.equals(clientType)) {
                    	course.setLiveSourceType(true);
                    }
                    //直播状况
                    course.setLiveCase(LiveCaseType.NORMAL_LIVE.getCode());
                    
                    break;
                case STOP_EVENT:
                    startOrEnd = "end_time";
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

            if (StringUtils.isNotBlank(startOrEnd)) {
                Integer maxRecord = iCourseMapper.maxRecordCount(course.getDirectId());
                maxRecord = maxRecord == null ? 1 : maxRecord + 1;
                Date startTime = null;
                Date endTime = null;
                if (event.equals(LiveStatusEvent.START.getName())) {
                    startTime = new Date();
                } else if (event.equals(LiveStatusEvent.STOP.getName())) {
                    endTime = new Date();
                }
                iCourseMapper.insertRecordLiveTime(startTime, endTime, course.getId(), course.getDirectId(), maxRecord);
            }
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
}
