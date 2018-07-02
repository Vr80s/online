package com.xczhihui.course.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.ChangeCallbackVo;
import com.xczhihui.course.vo.CourseVo;

public interface PublicCourseService {

    public List<Menu> getMenus();

    public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
                                         int pageSize);

    public List<Lecturer> getTeacher(Integer menuId);

    public void addCourse(CourseVo courseVo);

    public void updateCourse(CourseVo courseVo);

    public void updateSortUp(Integer id);

    public void updateSortDown(Integer id);

    public String updateWebinar(Course entity);

    public String createWebinar(Course entity);

    public String reCreateWebinar(CourseVo entity);

    public void updateCourseDirectId(Course course)
            throws IllegalAccessException, InvocationTargetException;

    Integer updateLiveStatus(ChangeCallbackVo changeCallbackVo);

    /**
     * Description：通过申请id得到课程id
     *
     * @param id
     * @return Course
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    Course findCourseVoByLiveExanmineId(Integer id);

    public Course findCourseVoByLiveExanmineId(String id);
}
