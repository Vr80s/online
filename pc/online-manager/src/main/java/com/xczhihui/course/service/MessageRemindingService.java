package com.xczhihui.course.service;

import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.bxg.online.common.domain.Course;

import java.util.List;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/9 0009-下午 1:43<br>
 */
public interface MessageRemindingService {
    void saveCourseMessageReminding(Course course);

    void deleteCourseMessageReminding(Course course);

    List<Course> getCourseMessageRemindingList();

    void checkCourseMessageReminding() throws ClientException;
}
