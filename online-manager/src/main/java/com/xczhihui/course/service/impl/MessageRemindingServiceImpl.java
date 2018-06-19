package com.xczhihui.course.service.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.support.service.impl.RedisCacheService;
import com.xczhihui.common.util.DateUtil;
import com.xczhihui.common.util.RedisCacheKey;
import com.xczhihui.common.util.TimeUtil;
import com.xczhihui.course.dao.CollectionCourseApplyUpdateDateDao;
import com.xczhihui.course.dao.CourseDao;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.MessageRemindingService;
import com.xczhihui.course.util.CourseUtil;
import com.xczhihui.course.util.TextStyleUtil;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/9 0009-下午 1:43<br>
 */
@Service
public class MessageRemindingServiceImpl implements MessageRemindingService {

    private Logger loggger = LoggerFactory.getLogger(this.getClass());

    private static final String APP_PUSH_LIVE_COURSE_REMIND = "{0}老师叫你去上课了！您报名的《{1}》直播还有{2}分钟就要开始了，别忘了准时观看";
    private static final String WEB_LIVE_COURSE_REMIND = "【上课提醒】{0}老师叫你去上课了！您报名的《{1}》直播还有{2}分钟就要开始了，别忘了准时观看";

    private static final String APP_PUSH_OFFLINE_COURSE_REMIND = "您报名的《{0}》将于明天{1}在{2}开始，别忘了准时参加！";
    private static final String WEB_OFFLINE_COURSE_REMIND = "【上课提醒】您报名的《{0}》将于明天{1}在{2}开始，别忘了准时参加！";

    private static final String APP_PUSH_COLLECTION_COURSE_REMIND = "您的专辑课程《{0}》需要更新啦~更新时间为每{1}";
    private static final String WEB_COLLECTION_COURSE_REMIND = "【课程更新提示】您的专辑课程《{0}》需要更新啦~更新时间为每{1}";

    @Value("${sms.live.course.remind.code}")
    private String sendLiveRemindCode;
    @Value("${sms.offline.course.remind.code}")
    private String sendOfflineRemindCode;
    @Value("${sms.update.course.remind.code}")
    private String sendCourseUpdateRemindCode;
    @Value("${weixin.course.remind.code}")
    private String weixinTemplateMessageRemindCode;

    @Value("${mobile.domain}")
    private String mobileDomain;

    CacheService cacheService;

    @Autowired
    CourseDao courseDao;
    @Autowired
    private CollectionCourseApplyUpdateDateDao collectionCourseApplyUpdateDateDao;
    @Autowired
    private ICommonMessageService commonMessageService;

    @Value("${env.flag}")
    private String envFlag;

    MessageRemindingServiceImpl() {
        cacheService = new RedisCacheService();
    }

    @Override
    public void saveCourseMessageReminding(Course course, String redisKey) {
        cacheService.set(redisKey + RedisCacheKey.REDIS_SPLIT_CHAR + course.getId(), course);
    }

    @Override
    public void deleteCourseMessageReminding(Course course, String redisKey) {
        cacheService.delete(redisKey + RedisCacheKey.REDIS_SPLIT_CHAR + course.getId());
    }

    @Override
    public List<Course> getCourseMessageRemindingList(String redisKey) {
        Set<String> keys = cacheService.getKeys(redisKey);
        List<Course> courses = new ArrayList<>();
        for (String key : keys) {
            Course course = cacheService.get(key);
            courses.add(course);
        }
        return courses;
    }

    @Override
    public void offlineCourseMessageReminding() {
        List<Course> courseMessageRemindingList = getCourseMessageRemindingList(RedisCacheKey.OFFLINE_COURSE_REMIND_KEY);
        Date today = new Date();
        for (Course course : courseMessageRemindingList) {
            //之前的课程未提醒,需移除掉
            if (course.getStartTime() != null && today.after(course.getStartTime())) {
                deleteCourseMessageReminding(course, RedisCacheKey.OFFLINE_COURSE_REMIND_KEY);
            } else {
                //开始时间是明天
                if (TimeUtil.isTomorrow(course.getStartTime())) {
                    List<OnlineUser> users = getUsersByCourseId(course.getId());
                    String courseName = course.getGradeName();
                    String address = course.getAddress();
                    String time = TimeUtil.getHourMonth(course.getStartTime());
                    Map<String, String> smsParams = new HashMap<>(3);
                    smsParams.put("courseName", courseName);
                    smsParams.put("time", time);
                    smsParams.put("address", address);

                    String commonContent = MessageFormat.format(WEB_OFFLINE_COURSE_REMIND, courseName, time, address);
                    Map<String, String> weixinParams = new HashMap<>(4);
                    weixinParams.put("first", TextStyleUtil.clearStyle(commonContent));
                    weixinParams.put("keyword1", courseName);
                    weixinParams.put("keyword2", TimeUtil.getYearMonthDayHHmm(course.getStartTime()));
                    weixinParams.put("remark", "点击查看");
                    for (OnlineUser user : users) {
                        BaseMessage baseMessage = new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                                .buildWeb(commonContent)
                                .buildAppPush(MessageFormat.format(APP_PUSH_OFFLINE_COURSE_REMIND, courseName, time, address))
                                .buildWeixin(weixinTemplateMessageRemindCode, weixinParams)
                                .buildSms(sendOfflineRemindCode, smsParams)
                                .detailId(String.valueOf(course.getId()))
                                .build(user.getId(), CourseUtil.getRouteType(course.getCollection(), course.getType()), null);
                        commonMessageService.saveMessage(baseMessage);
                    }

                }
            }
        }
    }

    @Override
    public void collectionUpdateRemind() {
        List<Course> courseMessageRemindingList = getCourseMessageRemindingList(RedisCacheKey.COLLECTION_COURSE_REMIND_KEY);
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        Integer day = calendar.get(Calendar.DAY_OF_WEEK);
        //周几
        day = day == 1 ? 7 : day - 1;
        for (Course course : courseMessageRemindingList) {
            List<Integer> dates = collectionCourseApplyUpdateDateDao.getUpdateDatesByCollectionId(course.getId());
            if (dates.isEmpty()) {
                deleteCourseMessageReminding(course, RedisCacheKey.COLLECTION_COURSE_REMIND_KEY);
            } else if (dates.contains(day)) {
                List<OnlineUser> users = getUsersByCourseId(course.getId());
                String courseName = course.getGradeName();
                String address = course.getAddress();
                String dateStr = dates.stream().map(DateUtil::getDayOfWeek).collect(Collectors.joining(","));
                Map<String, String> params = new HashMap<>(1);
                params.put("courseName", courseName);
                for (OnlineUser user : users) {
                    String commonContent = MessageFormat.format(WEB_COLLECTION_COURSE_REMIND, courseName, dateStr);
                    BaseMessage baseMessage = new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                            .buildWeb(commonContent)
                            .buildAppPush(MessageFormat.format(APP_PUSH_COLLECTION_COURSE_REMIND, courseName, dateStr))
                            .buildSms(sendCourseUpdateRemindCode, params)
                            .detailId(String.valueOf(course.getId()))
                            .build(user.getId(), RouteTypeEnum.ANCHOR_WORK_TABLE_PAGE, null);
                    commonMessageService.saveMessage(baseMessage);
                }
            }
        }
    }

    @Override
    public void liveCourseMessageReminding() {
        List<Course> courseMessageRemindingList = getCourseMessageRemindingList(RedisCacheKey.LIVE_COURSE_REMIND_KEY);
        for (Course course : courseMessageRemindingList) {
            if (course.getStartTime() != null) {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(course.getStartTime());
                Instant startTime = course.getStartTime().toInstant();
                Instant now = Instant.now();
                long seconds = Duration.between(now, startTime).getSeconds();
                if (seconds <= (60 * 10 + 30)) {
                    long minute = seconds / 60;
                    if (minute <= 0) {
                        loggger.info("课程{}提醒未发送,开播时间{}", course.getId(), course.getStartTime());
                        deleteCourseMessageReminding(course, RedisCacheKey.LIVE_COURSE_REMIND_KEY);
                    } else {
                        //发送提醒
                        List<OnlineUser> users = getUsersByCourseId(course.getId());
                        String courseName = course.getGradeName();
                        String lecturer = course.getLecturer();
                        RouteTypeEnum routeType = CourseUtil.getRouteType(course.getCollection(), course.getType());
                        String commonContent = MessageFormat.format(WEB_LIVE_COURSE_REMIND, lecturer, courseName, minute);
                        Map<String, String> params = new HashMap<>(4);
                        params.put("courseName", courseName);
                        params.put("lecture", lecturer);
                        params.put("minute", String.valueOf(minute));
                        params.put("code", String.valueOf(course.getId()));
                        Map<String, String> weixinParams = new HashMap<>(4);
                        weixinParams.put("first", TextStyleUtil.clearStyle(commonContent));
                        weixinParams.put("keyword1", courseName);
                        weixinParams.put("keyword2", time);
                        weixinParams.put("remark", "点击查看");
                        OnlineUser onlineUser = new OnlineUser();
                        for (OnlineUser user : users) {
                            commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                                    .buildWeb(commonContent)
                                    .buildAppPush(MessageFormat.format(APP_PUSH_LIVE_COURSE_REMIND, lecturer, courseName, minute))
                                    .buildWeixin(weixinTemplateMessageRemindCode, weixinParams)
                                    .buildSms(sendLiveRemindCode, params)
                                    .detailId(String.valueOf(course.getId()))
                                    .build(user.getId(), routeType, null)
                            );
                        }
                    }
                    deleteCourseMessageReminding(course, RedisCacheKey.LIVE_COURSE_REMIND_KEY);
                }
            }
        }
    }

    private List<OnlineUser> getUsersByCourseId(Integer id) {
        return courseDao.getUsersByCourseId(id);
    }
}
