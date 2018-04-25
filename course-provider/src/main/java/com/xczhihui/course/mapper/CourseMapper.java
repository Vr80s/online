package com.xczhihui.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.Course;
import com.xczhihui.course.vo.CourseLecturVo;

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
	List<CourseLecturVo> selectAppCourseApplyPage(@Param("page") Page<CourseLecturVo> page,
			@Param("userId") String userId, @Param("courseForm") Integer courseForm,@Param("multimediaType") Integer multimediaType);

	CourseLecturVo selectCourseMidileDetailsById(Integer courseId);
	
	
	List<CourseLecturVo> selectUserConsoleCourseLiveByPage(@Param("page") Page<CourseLecturVo> page,@Param("userId")String userId);

	List<CourseLecturVo> listenCourseList();
	
	List<CourseLecturVo> findLiveListInfo();
	/**
	 * 	
	 * Description：用户当前课程状态   User current course status. 用户登录了  
	 * @param courseId
	 * @param id
	 * @return
	 * @return CourseLecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	CourseLecturVo selectUserCurrentCourseStatus(@Param("courseId")Integer courseId,@Param("userId")String userId);
	/**
	 * 
	 * Description：课程状态   User current course status. 用户没有登录  
	 * @param courseId
	 * @return
	 * @return CourseLecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	CourseLecturVo selectCurrentCourseStatus(@Param("courseId")Integer courseId);
    /**
     *  
     * Description：根据type类型查找不同类型的富文本内容
     * @param type
     * @param typeId
     * @return
     * @return String
     * @author name：yangxuan <br>email: 15936216273@163.com
     *
     */
	String selectCourseDescription(@Param("type")Integer type,@Param("typeId")String typeId);

	List<Integer> selectCourseIdByCollectionId(Integer courseId);
}