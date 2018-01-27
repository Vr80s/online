package com.xczhihui.wechat.course.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.wechat.course.mapper.CourseMapper;
import com.xczhihui.wechat.course.model.Course;
import com.xczhihui.wechat.course.service.ICourseService;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

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
		// TODO Auto-generated method stub
		List<CourseLecturVo> records = iCourseMapper.selectCoursePage(page);
		return   page.setRecords(records);
	}

	@Override
	public CourseLecturVo selectCourseDetailsById(Integer courseId) {
		// TODO Auto-generated method stub
		return iCourseMapper.selectCourseDetailsById(courseId);
	}

	@Override
	public List<CourseLecturVo> selectLearningCourseListByUserId(String id) {
		// TODO Auto-generated method stub
		List<CourseLecturVo> listAll = iCourseMapper.selectLearningCourseListByUserId(id);
		return listAll;
	}
	
	@Override
    public Integer selectMyFreeCourseListCount(String id) {
		// TODO Auto-generated method stub
		return   iCourseMapper.selectMyFreeCourseListCount(id);
	}
	
	@Override
    public Page<CourseLecturVo> selectMyFreeCourseList(Page<CourseLecturVo> page,String id) {
		// TODO Auto-generated method stub
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
	};

}
