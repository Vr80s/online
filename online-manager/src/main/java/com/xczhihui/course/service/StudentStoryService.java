package com.xczhihui.course.service;

import com.xczhihui.bxg.online.common.domain.StudentStory;
import com.xczhihui.common.util.bean.Page;

public interface StudentStoryService {
    /**
     * 根据条件分页获取角色信息。
     *
     * @param groups
     * @param pageVo
     * @return
     */
    public Page<StudentStory> findStudentStoryPage(StudentStory studentStory,
                                                   int pageNumber, int pageSize);

    public void addStudentStory(StudentStory studentStory);

    public void deletes(String[] ids);

    public StudentStory findCourseById(String id);

    public void updateStudentStory(StudentStory studentStory);
}
