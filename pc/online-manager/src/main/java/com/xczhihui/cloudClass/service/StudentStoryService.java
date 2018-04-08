package com.xczhihui.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.StudentStory;

public interface StudentStoryService {
	/**
	 * 根据条件分页获取角色信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
    public Page<StudentStory> findStudentStoryPage(StudentStory studentStory,  int pageNumber, int pageSize);
    
    public void addStudentStory(StudentStory studentStory);

	public void deletes(String[] ids);

	public StudentStory findCourseById(String id);

	public void updateStudentStory(StudentStory studentStory);
}
