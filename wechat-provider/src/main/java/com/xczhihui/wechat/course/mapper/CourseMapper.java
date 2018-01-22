package com.xczhihui.wechat.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.Course;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface CourseMapper extends BaseMapper<Course> {

     //关注数
	 List<CourseLecturVo> selectCoursePage(@Param("page") Page<CourseLecturVo> page);

	 //粉丝数
	 CourseLecturVo selectCourseById(@Param("courseId") Integer  courseId);
	 
	 CourseLecturVo selectCourseDetailsById(@Param("courseId") Integer  courseId);
	 
	 List<CourseLecturVo> selectLearningCourseListByUserId(@Param("userId")String userId);

	Integer selectMyFreeCourseListCount(@Param("userId")String userId);

	List<CourseLecturVo> selectMyFreeCourseList(@Param("page")Page<CourseLecturVo> page,@Param("userId")String id);
	
	List<CourseLecturVo> selectCoursesByCollectionId(Integer collectionId);
}