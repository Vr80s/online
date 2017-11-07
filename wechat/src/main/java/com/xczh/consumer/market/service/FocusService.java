package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.wxpay.entity.FocusVo;

import java.sql.SQLException;
import java.util.List;

/**
 * 关注service
 * ClassName: FocusService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月15日<br>
 */
public interface FocusService {
    /**
     * Description：增加关注信息
     * @param onlineUser
     * @param onlineLecturer
     * @param course_id
     * @throws SQLException
     * @return void
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	void addFocusInfo(OnlineUser onlineUser, OnlineUser onlineLecturer, int course_id)throws SQLException;
	/***
	 * 我的粉丝
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	List<FocusVo> findMyFans(String userId, Integer pageNumber, Integer pageSize) throws SQLException;
	/***
	 * 我的关注（关注的人）
	 * @param course_id
	 * @return
	 * @throws SQLException
	 */
	List<FocusVo> findmyFocus(String userId, Integer pageNumber, Integer pageSize) throws SQLException;
	/**
	 * 
	 * Description：讲师下的粉丝总数
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Integer findMyFansCount(String userId) throws SQLException;
	/**
	 * Description：我关注的讲师总数
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	Integer findMyFocusCount(String userId) throws SQLException;

	/**
	 * 我是否已经关注了这个讲师  0 未关注 1已关注
	 * Description：
	 * @param userId
	 * @param lecturerId
	 * @return
	 * @throws SQLException
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Integer myIsFourslecturer(String userId, String lecturerId)
			throws SQLException;
	/**
	 * 
	 * Description：取消关注
	 * @param lecturerId  讲师id
	 * @param id  用户id
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	ResponseObject cancelFocus(String lecturerId, String id)throws SQLException;
}
