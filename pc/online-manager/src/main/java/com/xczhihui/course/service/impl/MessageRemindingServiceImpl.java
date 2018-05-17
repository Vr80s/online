package com.xczhihui.course.service.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.support.service.impl.RedisCacheService;
import com.xczhihui.common.util.TimeUtil;
import com.xczhihui.constant.RedisKeyConstant;
import com.xczhihui.course.dao.CourseDao;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.service.MessageRemindingService;

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

    @Value("${sms.live.course.remind.code}")
    private String sendLiveRemindCode;
    @Value("${sms.offline.course.remind.code}")
    private String sendOfflineRemindCode;
    @Value("${weixin.course.remind.code}")
    private String weixinTemplateMessageRemindCode;

    CacheService cacheService;

    @Autowired
    CourseDao courseDao;
    @Autowired
    private ICommonMessageService commonMessageService;

    @Value("${env.flag}")
    private String envFlag;

    MessageRemindingServiceImpl() {
        cacheService = new RedisCacheService();
    }

    @Override
    public void saveCourseMessageReminding(Course course, String redisKey) {
        cacheService.set(redisKey + course.getId(), course);
    }

    @Override
    public void deleteCourseMessageReminding(Course course, String redisKey) {
        cacheService.delete(redisKey + course.getId());
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
        List<Course> courseMessageRemindingList = getCourseMessageRemindingList(RedisKeyConstant.OFFLINE_COURSE_REMIND_KEY);
        Date today = new Date();
        for (Course course : courseMessageRemindingList) {
            //之前的课程未提醒,需移除掉
            if (course.getStartTime() != null && today.after(course.getStartTime())) {
                deleteCourseMessageReminding(course, RedisKeyConstant.OFFLINE_COURSE_REMIND_KEY);
            } else {
                //开始时间是明天
                if (TimeUtil.isTomorrow(course.getStartTime())) {
                    List<OnlineUser> users = getUsersByCourseId(course.getId());
                    String courseName = course.getGradeName();
                    String address = course.getAddress();
                    String time = TimeUtil.getHourMonth(course.getStartTime());
                    for (OnlineUser user : users) {
                        String commonContent = MessageFormat.format(WEB_OFFLINE_COURSE_REMIND, courseName, time, address);
                        BaseMessage baseMessage = new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                                .buildWeb(commonContent)
                                .buildAppPush(MessageFormat.format(APP_PUSH_OFFLINE_COURSE_REMIND, courseName, time, address))
                                .buildWeixin(weixinTemplateMessageRemindCode, ImmutableMap.of("first", commonContent, "keyword1", courseName, "keyword2", time, "remark", "点击查看"))
                                .buildSms(sendLiveRemindCode, ImmutableMap.of("courseName", courseName, "time", time, "address", address))
                                .detailId(String.valueOf(course.getId()))
                                .build(user.getId(), RouteTypeEnum.COURSE_DETAIL_PAGE, null);
                        commonMessageService.saveMessage(baseMessage);
                    }

                }
            }
        }
    }

    @Override
    public void liveCourseMessageReminding() {
        List<Course> courseMessageRemindingList = getCourseMessageRemindingList(RedisKeyConstant.LIVE_COURSE_REMIND_KEY);
        for (Course course : courseMessageRemindingList) {
            if (course.getStartTime() != null) {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(course.getStartTime());
                Instant startTime = course.getStartTime().toInstant();
                Instant now = Instant.now();
                long seconds = Duration.between(now, startTime).getSeconds();
                if (seconds <= (60 * 10 + 30)) {
                    long minute = seconds / 60;
                    if (minute <= 0) {
                        loggger.info("课程提醒未发送");
                    } else {
                        //发送提醒
                        List<OnlineUser> users = getUsersByCourseId(course.getId());
                        for (OnlineUser user : users) {
//                            if (!"dev".equals(envFlag)) {
                                String courseName = course.getGradeName();
                                String lecturer = course.getLecturer();
                                String commonContent = MessageFormat.format(WEB_LIVE_COURSE_REMIND, lecturer, courseName, minute);
                                commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.COURSE.getVal())
                                        .buildWeb(commonContent)
                                        .buildAppPush(MessageFormat.format(APP_PUSH_LIVE_COURSE_REMIND, lecturer, courseName, minute))
                                        .buildWeixin(weixinTemplateMessageRemindCode, ImmutableMap.of("first", commonContent, "keyword1", courseName, "keyword", time, "remark", "点击查看"))
                                        .buildSms(sendLiveRemindCode, ImmutableMap.of("courseName", courseName, "lecturer", lecturer, "minute", String.valueOf(minute)))
                                        .detailId(String.valueOf(course.getId()))
                                        .build(user.getId(), RouteTypeEnum.COURSE_DETAIL_PAGE, null)
                                );
//                            } else {
//                                loggger.info("开发环境，不发送提示短信");
//                            }
                        }
                    }
                    deleteCourseMessageReminding(course, RedisKeyConstant.LIVE_COURSE_REMIND_KEY);
                }
            }
        }
    }

    private List<OnlineUser> getUsersByCourseId(Integer id) {
        return courseDao.getUsersByCourseId(id);
    }
}
