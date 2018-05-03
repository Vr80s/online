package com.xczh.consumer.market.service;

import java.sql.SQLException;
import java.util.Map;

import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.vo.LecturVo;

/**
 * 课程service
 * ClassName: OnlineCourseService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
public interface OnlineCourseService {


	/**
	 * 修改浏览直播次数
	 * Description：
	 * @param courseId
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	void updateBrowseSum(Integer courseId)throws SQLException;

	
	/**
	 * 
	 * Description：修改直播源类型为app直播
	 * @param courseId
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	void updateLiveSourceType(Integer courseId)throws SQLException ;
	

	/**
	 *
	 * Description：课程分享
	 * @param courseId
	 * @return CourseLecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	CourseLecturVo courseShare(Integer courseId)
			throws SQLException;
	/**
	 *
	 * Description：主播分享
	 * @param lecturerId
	 * @return LecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	LecturVo lectureShare(String lecturerId)
			throws SQLException;
	
}
