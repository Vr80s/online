package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.CourseMapper;
import com.xczhihui.wechat.course.model.Course;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.vo.CourseLecturVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper,Course> implements ICourseService {

	@Autowired
	private CourseMapper iCourseMapper;
	
	@Override
    public Page<CourseLecturVo> selectCoursePage(Page<CourseLecturVo> page) {
		List<CourseLecturVo> records = iCourseMapper.selectCoursePage(page);
		return   page.setRecords(records);
	}

	@Override
	public CourseLecturVo selectCourseDetailsById(Integer courseId) {
		return iCourseMapper.selectCourseDetailsById(courseId);
	}

	@Override
	public List<CourseLecturVo> selectLearningCourseListByUserId(String id) {
		List<CourseLecturVo> listAll = iCourseMapper.selectLearningCourseListByUserId(id);
		return listAll;
	}
	
	@Override
    public Integer selectMyFreeCourseListCount(String id) {
		return   iCourseMapper.selectMyFreeCourseListCount(id);
	}
	
	@Override
    public Page<CourseLecturVo> selectMyFreeCourseList(Page<CourseLecturVo> page,String id) {
		List<CourseLecturVo> records = iCourseMapper.selectMyFreeCourseList(page,id);
		
		return   page.setRecords(records);
	}

	@Override
	public List<CourseLecturVo> selectCoursesByCollectionId(Integer collectionId) {
		List<CourseLecturVo> courses = iCourseMapper.selectCoursesByCollectionId(collectionId);
		return courses;
	}

	@Override
	public CourseLecturVo selectLecturerRecentCourse(String lecturerId) {
		return iCourseMapper.selectLecturerRecentCourse(lecturerId);
	}

	@Override
	public Page<CourseLecturVo> selectLecturerAllCourse(
			Page<CourseLecturVo> page, String lecturerId) {
		List<CourseLecturVo> records = iCourseMapper.selectLecturerAllCourse(page,lecturerId);
		return   page.setRecords(records);
	}

	@Override
	public List<CourseLecturVo> selectUserConsoleCourse(String id) {
		return iCourseMapper.selectUserConsoleCourse(id);
	}
	
	@Override
	public Page<CourseLecturVo> selectMenuTypeAndRandCourse(Page<CourseLecturVo> page,Integer menuId){
		List<CourseLecturVo> records = iCourseMapper.selectMenuTypeAndRandCourse(page,menuId);
		return   page.setRecords(records);
	}

	@Override
	public Page<CourseLecturVo> selectAppCourseApplyPage(Page<CourseLecturVo> page, String userId, Integer courseForm,Integer multimediaType) {
		List<CourseLecturVo> records = iCourseMapper.selectAppCourseApplyPage(page, userId,courseForm,multimediaType);
		page.setRecords(records);
		return   page.setRecords(records);
	}

	@Override
	public CourseLecturVo selectCourseMiddleDetailsById(Integer courseId) {
		
		return iCourseMapper.selectCourseMidileDetailsById(courseId);
	}

	@Override
	public List<CourseLecturVo> selectUserConsoleCourseLiveByPage(
			Page<CourseLecturVo> page, String userId) {
//		List<CourseLecturVo> records = iCourseMapper.selectUserConsoleCourseLiveByPage(page,userId);
//		page.setRecords(records);
		return  iCourseMapper.selectUserConsoleCourseLiveByPage(page,userId);
	}

	@Override
	public List<CourseLecturVo> listenCourseList() {
		return iCourseMapper.listenCourseList();
	}

	@Override
	public List<CourseLecturVo> findLiveListInfo() {
		return iCourseMapper.findLiveListInfo();
	}

	@Override
	public CourseLecturVo selectUserCurrentCourseStatus(Integer courseId,
			String userId) {
		return iCourseMapper.selectUserCurrentCourseStatus(courseId,userId);
	}

	@Override
	public CourseLecturVo selectCurrentCourseStatus(Integer courseId) {
		return iCourseMapper.selectCurrentCourseStatus(courseId);
	}

	@Override
	public String selectCourseDescription(Integer type,String typeId) {
		return iCourseMapper.selectCourseDescription(type,typeId);
	}


}
