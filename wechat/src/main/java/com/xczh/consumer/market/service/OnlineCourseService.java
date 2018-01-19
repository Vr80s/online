package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 课程service
 * ClassName: OnlineCourseService.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
public interface OnlineCourseService {

	/**
	 * 直播列表页信息
	 * Description：
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<CourseLecturVo> findLiveListInfo() throws SQLException;
	/**
	 * 根据课程id得到直播详情
	 * Description：
	 * @param course_id
	 * @return
	 * @throws SQLException
	 * @return CourseLecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	CourseLecturVo liveDetailsByCourseId(int course_id, String userId)throws SQLException;
	/**
	 * 是否购买此课程
	 * Description：
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	ResponseObject courseIsBuy(OnlineUser user, int course_id);
    /**
     * 是否密码验证过此课程
     * Description：
     * @param user
     * @param course_id
     * @return
     * @return ResponseObject
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
	ResponseObject courseIsPwd(OnlineUser user, int course_id);
	/**
	 *
	 * Description：确认密码
	 * @param user
	 * @param course_id
	 * @param course_pwd
	 * @return
	 * @throws SQLException
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	ResponseObject saveCoursePwdAndConfirm(OnlineUser user, int course_id, String course_pwd)throws SQLException;
	/**
	 * Description：判断是否需要认证密码
	 * @param user
	 * @param course_id
	 * @return
	 * @return Object
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	ResponseObject courseIsConfirmPwd(OnlineUser user, int course_id)throws SQLException;
	/**
	 * Description：用户中心视频数据分页请求
	 * @param lecturerId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 * @return List<CourseLecturVo>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	List<CourseLecturVo> liveAndBunchAndAudio(String lecturerId, Integer pageNumber, 
			Integer pageSize,Integer type)throws SQLException;
	/**
	 *
	 * Description：用户中心视频数 --》但是这里请求的是课程数
	 * @param lecturerId
	 * @return
	 * @throws SQLException
	 * @return Integer
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Integer liveAndBunchAndAudioCount(String lecturerId) throws SQLException;
	/**
	 * 增加预约信息
	 * Description：
	 * @param user
	 * @param mobile
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @param course_id
	 *
	 */
	ResponseObject addSubscribeInfo(OnlineUser user, String mobile, Integer course_id)throws SQLException;
	/**
	 *
	 * Description：得到当前你预约人数
	 * @param courseId
	 * @return
	 * @throws SQLException
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Integer selectSubscribeInfoNumberCourse(Integer courseId)
			throws SQLException;

	/**
	 *
	 * Description：判断是否已经预约了，如果预约了，就不管了
	 * @param courseId
	 * @return
	 * @throws SQLException
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Integer selectSubscribeInfoIs(Integer courseId, String userId)
			throws SQLException;

	/**
	 * 判断这个课程类型和得到用户id
	 * Description：
	 * @param parseInt
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Integer getIsCouseType(int parseInt)throws SQLException;
	Map<String, Object> shareLink(int courseId, int type) throws SQLException;
	OnlineUser saveWxInfoByCode(String access_token, String openid,
                                UserCenterAPI userCenterAPI)throws Exception ;
	/**
	 * 修改浏览直播次数
	 * Description：
	 * @param courseId
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	void updateBrowseSum(Integer courseId)throws SQLException;

	CourseLecturVo courseStatusList(int course_id, String userId) throws SQLException;
	ResponseObject courseStatus(OnlineUser user, int course_id)
			throws SQLException;

	CourseLecturVo get(int course_id)
			throws SQLException;
	/**
	 * Description：模糊查找直播课程，需要按赠送的礼物多少来判断
	 * @param i
	 * @param j
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @return List<CourseLecturVo>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<CourseLecturVo> findLiveListByQueryKey(int i, int j, String queryParam) throws SQLException;
	/**
	 * 
	 * Description：h5分享后跳转
	 * @param parseInt
	 * @param type
	 * @return
	 * @throws SQLException
	 * @return CourseLecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	CourseLecturVo h5ShareAfter(int parseInt, Integer type)throws SQLException;
	void addLiveExamineData()throws SQLException;
	/**
	 * 
	 * Description：分享跳转
	 * @param parseInt
	 * @return
	 * @throws SQLException
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Map<String, Object> shareJump(int parseInt)throws SQLException;
	/**
	 * 判断这个主播有没有正在直播的课程
	 * Description：
	 * @param lecturerId
	 * @return
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	Map<String, String> teacherIsLive(String lecturerId)throws SQLException;

	/**
	 * 直播间打赏金额
	 * @param id
	 * @return
	 */
	String sumMoneyLive(String id);
	String getlecturerIdByCourseId(Integer courseId)
			throws SQLException;
	/**
	 * 
	 * Description：修改直播源类型为app直播
	 * @param courseId
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	void updateLiveSourceType(String courseId)throws SQLException ;
	
}
