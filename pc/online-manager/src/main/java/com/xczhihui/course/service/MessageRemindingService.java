package com.xczhihui.course.service;

import java.util.List;

import com.xczhihui.bxg.online.common.domain.Course;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/9 0009-下午 1:43<br>
 */
public interface MessageRemindingService {
    void saveCourseMessageReminding(Course course, String key);

    void deleteCourseMessageReminding(Course course, String key);

    List<Course> getCourseMessageRemindingList(String key);

    void liveCourseMessageReminding();

    void offlineCourseMessageReminding();
}
