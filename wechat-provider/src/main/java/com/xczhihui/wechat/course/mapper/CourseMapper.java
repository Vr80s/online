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

	CourseLecturVo selectLecturerRecentCourse(@Param("userId")String userId);

	List<CourseLecturVo> selectLecturerAllCourse(@Param("page")Page<CourseLecturVo> page,@Param("userId")String id);
	
	List<CourseLecturVo>   selectUserConsoleCourse(@Param("userId")String userId);
	
	List<CourseLecturVo>   selectMenuTypeAndRandCourse(@Param("page") Page<CourseLecturVo> page,@Param("courseId")Integer courseId);

	/**
	 * Description：我的课程  包含审批未审批的
	 * @param page
	 * @param userId
	 * @param courseFrom
	 * @param multimediaType
	 * @return
	 * @return List<CourseLecturVo>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	List<CourseLecturVo> selectAppCourseApplyPage(Page<CourseLecturVo> page,String userId, Integer courseFrom, Integer multimediaType);
	
}