package com.xczhihui.mobile.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.ScoreType;
import com.xczhihui.course.vo.CourseVo;

public interface MobileCourseService {

	List<Menu> getfirstMenus();

	List<ScoreType> getScoreType();
	
	Page<CourseVo> findCoursePage(CourseVo searchVo, int currentPage, int pageSize);

	List<CourseVo> findCourseById(Integer id);

	/**
	 * 获得课程详情
	 * @param courseId
	 * @return
	 */
	public Map<String,String> getCourseDetail(String courseId);

	void updateCourseDetail(String courseId, String smallImgPath, Object object, String courseDetail,
			String courseOutline, String commonProblem);

	void addPreview(String courseId, String smallImgPath, Object object, String courseDetail, String courseOutline,
			String commonProblem);

	
	

}
