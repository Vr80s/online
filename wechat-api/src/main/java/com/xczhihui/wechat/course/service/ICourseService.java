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
	  public List<CourseLecturVo> selectLearningCourseListByUserId(String id);

	  List<CourseLecturVo> selectCoursesByCollectionId(Integer collectionId);
}
