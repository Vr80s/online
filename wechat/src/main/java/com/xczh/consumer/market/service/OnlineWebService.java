package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczhihui.bxg.online.api.vo.CriticizeVo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OnlineWebService {

	void saveEntryVideo(Integer courseId, OnlineUser u) throws SQLException;

	List<Map<String, Object>> getUserCourse(Integer courseId, String userId)
			throws SQLException;

	/**
	 * 
	 * Description：判断此用户是否购买过这个课程，如果购买过返回true 如果没有购买返回false
	 * @param courseId
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Boolean
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Boolean getLiveUserCourse(Integer courseId, String userId)
			throws SQLException;
	
	/**
	 * 
	 * Description：判断此用户是否购买过这个课程，如果购买过返回true 如果没有购买返回false
	 * @param courseId
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Boolean
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Boolean getLiveUserCourseAndIsFree(CriticizeVo criticize)
			throws SQLException;

}
