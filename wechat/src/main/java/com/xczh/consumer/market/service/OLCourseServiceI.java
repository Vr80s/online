package com.xczh.consumer.market.service;

import com.xczh.consumer.market.vo.*;
import com.xczhihui.course.model.OfflineCity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OLCourseServiceI {

	/**
	 * 线下培训班列表
	 * Description：
	 * @return
	 * @return List<CourseLecturVo>
	 * @author name：liutao
	 *
	 */
	public List<CourseVo> offLineClassList( List<OfflineCity> cityList)throws SQLException;

	
	public List<CourseVo> recommendCourseList(List<MenuVo> listmv) throws SQLException;
	/**
	 * 
	 * Description：查询
	 * @param menuType
	 * @param courseType
	 * @param isFree
	 * @param queryKey
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return List<CourseLecturVo>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public List<CourseVo> queryAllCourse(String menuType,Integer lineState,
			Integer courseType, String isFree, String city,String queryKey,
			Integer pageNumber, Integer pageSize) throws SQLException;
	
}
