package com.xczhihui.utils.subscribe;

import java.util.List;
import java.util.TimerTask;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.course.dao.CourseSubscribeDao;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.course.vo.CourseSubscribeVo;

/**
 * ClassName: RemindTask.java <br>
 * Description: 定时给预约直播视频的用户发送短信<br>
 * Create by: name：yuxin <br>
 * email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年8月12日<br>
 */
public class RemindTask extends TimerTask {

    private int courseId;

    private String version;

    private CourseSubscribeDao courseSubscribeDao;

    private CourseService courseService;

    public RemindTask(int courseId, String version,
                      CourseService courseService, CourseSubscribeDao courseSubscribeDao) {
        this.courseId = courseId;
        this.version = version;
        this.courseSubscribeDao = courseSubscribeDao;
        this.courseService = courseService;
    }

    @Override
    public void run() {

        Course course = courseService.findOpenCourseById(courseId, version);
        if (course == null) {
            return;
        }
        long m = Math.abs(course.getStartTime().getTime()
                - System.currentTimeMillis())
                / (1000 * 60) + 1;
        if (!course.isSent()) {
            List<CourseSubscribeVo> courseSubscribeList = courseSubscribeDao
                    .getCourseSubscribeByCourseId(courseId);

            for (CourseSubscribeVo courseSubscribeVo : courseSubscribeList) {
                try {
                    SmsUtil.sendSmsSubscribe(courseSubscribeVo.getPhone(),
                            course.getGradeName(), null, m + "", false);
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            // 提醒短信送完结束，更新课程对应的直播状态
            courseService.updateSentById(course.getId());

        }
    }
}