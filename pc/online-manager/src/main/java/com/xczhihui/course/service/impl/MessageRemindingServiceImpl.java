package com.xczhihui.course.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.support.service.CacheService;
import com.xczhihui.common.support.service.impl.RedisCacheService;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.course.dao.CourseDao;
import com.xczhihui.course.service.MessageRemindingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/9 0009-下午 1:43<br>
 */
@Service
public class MessageRemindingServiceImpl implements MessageRemindingService {

    private Logger loggger = LoggerFactory.getLogger(this.getClass());
    private static String C_M_R_KEY = "c_m_r_";
    CacheService cacheService;

    @Autowired
    CourseDao courseDao;

    @Value("${env.flag}")
    private String envFlag;

    MessageRemindingServiceImpl(){
        cacheService = new RedisCacheService();
    }

    @Override
    public void saveCourseMessageReminding(Course course){
        cacheService.set(C_M_R_KEY+course.getId(),course);
    }

    @Override
    public void deleteCourseMessageReminding(Course course){
        cacheService.delete(C_M_R_KEY+course.getId());
    }

    @Override
    public List<Course> getCourseMessageRemindingList(){
        Set<String> keys = cacheService.getKeys(C_M_R_KEY);
        List<Course> courses = new ArrayList<>();
        for (String key : keys) {
            Course course = cacheService.get(key);
            courses.add(course);
        }
        return courses;
    }

    @Override
    public void checkCourseMessageReminding() {
        List<Course> courseMessageRemindingList = getCourseMessageRemindingList();
        for (Course course : courseMessageRemindingList) {
            if(course.getStartTime()!=null){
                Instant startTime = course.getStartTime().toInstant();
                Instant now = Instant.now();
                long seconds = Duration.between( now,startTime).getSeconds();
                if(seconds <=(60*10+30)){
                    long minute = seconds / 60;
                    if(minute <=0){
                        loggger.info("课程{}提醒未发送,开播时间{}",course.getId(),course.getStartTime());
                        deleteCourseMessageReminding(course);
                    }else{
                        //发送提醒
                        List<OnlineUser> users = getUsersByCourseId(course.getId());
                        for (OnlineUser user : users) {
                            String phone = user.getLoginName();
                            if (!"dev".equals(envFlag)) {
                                try {
                                    SmsUtil.sendSmsSubscribe(phone,
                                            course.getGradeName(), null, minute + "", false);
                                } catch (ClientException e) {
                                    loggger.error("课程{},手机号{}短信提醒发送失败",course.getId(),phone);
                                }
                            }else{
                                loggger.info("开发环境，不发送提示短信");
                            }
                        }
                        deleteCourseMessageReminding(course);
                    }
                }
            }
        }
    }

    private List<OnlineUser> getUsersByCourseId(Integer id) {
        return courseDao.getUsersByCourseId(id);
    }
}
