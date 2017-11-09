package com.xczhihui.bxg.online.manager.cloudClass.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.LiveAppealInfo;
import com.xczhihui.bxg.online.common.domain.LiveExamineInfo;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.cloudClass.vo.ChangeCallbackVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LiveAppealInfoVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.LiveExamineInfoVo;

public interface ExamineCourseService {

	public List<Menu> getMenus();

	
	public String updateWebinar(Course entity);
	
	public String createWebinar(Course entity);

	public String reCreateWebinar(CourseVo entity);

	void updateLiveStatus(ChangeCallbackVo changeCallbackVo);

	public Page<LiveExamineInfoVo> findCoursePage(
			LiveExamineInfoVo liveExamineInfoVo, int currentPage, int pageSize);

	public void updateCourse(LiveExamineInfoVo liveExamineInfoVo);

	void synchronizingCourse(LiveExamineInfo le);


	public String updateApply(String id);


	public LiveExamineInfo findExamineById(String id);

   
	public Page<LiveExamineInfoVo> findAppealListPage(
			LiveExamineInfoVo liveExamineInfoVo, int currentPage, int pageSize);


	public void updateBohuiApply(LiveAppealInfoVo lai);


	public void deletes(String[] _ids);


	public void deletesAppeal(String[] _ids);


	LiveAppealInfo findAppealInfoById(Integer id);

}
