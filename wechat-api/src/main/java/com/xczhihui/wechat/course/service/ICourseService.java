package com.xczhihui.wechat.course.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.vo.CourseLecturVo;

public interface ICourseService {
	
	  public Page<CourseLecturVo> selectCoursePage(Page<CourseLecturVo> page);
	  

	  /**
	   * Description:通过课程id查找课程详细
	   * @param courseId
	   * @return
	   * @return CourseLecturVo
	   * @author name：yangxuan <br>email: 15936216273@163.com
	   */
	  public CourseLecturVo selectCourseDetailsById(Integer courseId);
	  

	  /**
	   * Description：学习中心中的课程
	   * @param id
	   * @return
	   * @return List<CourseLecturVo>
	   * @author name：yangxuan <br>email: 15936216273@163.com
	   */
	  public List<CourseLecturVo> selectLearningCourseListByUserId(String userId);
	  
	  /**
	   * 
	   * Description：查询用户已购买课程的数量
	   * @param id
	   * @return
	   * @return Integer
	   * @author name：yangxuan <br>email: 15936216273@163.com
	   *
	   */
	  public Integer selectMyFreeCourseListCount(String userId);

	  /**
	   * 
	   * Description：分页查询用于已购买课程列表
	   * @param page
	   * @param id
	   * @return
	   * @return Page<CourseLecturVo>
	   * @author name：yangxuan <br>email: 15936216273@163.com
	   *
	   */
	  Page<CourseLecturVo> selectMyFreeCourseList(Page<CourseLecturVo> page,
			String userId);
	  

	  List<CourseLecturVo> selectCoursesByCollectionId(Integer collectionId);

      /**
       * 
       * Description：查找此主播最近一次的课程
       * @param lecturerId
       * @return
       * @return CourseLecturVo
       * @author name：yangxuan <br>email: 15936216273@163.com
       *
       */
	  public CourseLecturVo selectLecturerRecentCourse(String lecturerId);

	  /**
	   * Description：根据主播id得到主播的所有课程，按照发布时间排序
	   * @param page
	   * @param lecturerId
	   * @return
	   * @return Page<CourseLecturVo>
	   * @author name：yangxuan <br>email: 15936216273@163.com
	   */
	  public Page<CourseLecturVo> selectLecturerAllCourse(Page<CourseLecturVo> page, String lecturerId);
}
