package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.ResponseObject;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OnlineUserService {
	/**
	 * 根据登录名查找用户
	 * @param loginName
	 * @return
	 */
	public OnlineUser findUserByLoginName(String loginName) throws SQLException;
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public OnlineUser findUserById(String id) throws SQLException;
	/**
	 * 添加用户
	 * @param user
	 * @throws SQLException
	 */
	public void addOnlineUser(OnlineUser user)throws SQLException;
	/**
	 * 手机提交注册
	 * @param password
	 */
	public ResponseObject addPhoneRegist(HttpServletRequest req,
										 String password, String mobile, String openId)throws Exception;
	/**
	 * 验证码检验
	 * @param mobile
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public ResponseObject checkCode(String mobile, String code, String vtype)throws Exception;
	/**
	 * 更新用户share_code
	 * @param user
	 * @throws SQLException
	 */
	public void updateOnlineUser(OnlineUser user)throws SQLException;
	/**
	 * 往本地库写用户
	 * @param mobile
	 * @param username
	 * @param shareCode 上级的分享码
	 * @return
	 * @throws Exception
	 */
	public OnlineUser addUser(String mobile, String username, String shareCode, String password) throws Exception;
	String addMessage(String username, String vtype)throws Exception;
	/**
	 * Description：h5、app注册功能
	 * @param req
	 * @param password
	 * @param mobile
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public ResponseObject addPhoneRegistByAppH5(HttpServletRequest req,
                                                String password, String mobile, String vtype)throws Exception;
	/**
	 *
	 * Description：修改用户中心数据接口
	 * @param user
	 * @throws SQLException
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void updateUserCenterData(OnlineUser user, Map<String, String> map)throws SQLException;
	/**
	 *
	 * Description：通过用户id得到房间号、用户id、用户头像
	 * @param lecturerId
	 * @return
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public Map<String, Object> findUserRoomNumberById(String lecturerId) throws SQLException ;
	/**
	 * 热门主播
	 * Description：
	 * @param queryKey
	 * @return
	 * @throws SQLException
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<Map<String, Object>> findHotHostByQueryKey(String queryKey)
			throws SQLException;
	public void updateVhallIdOnlineUser(String weihouId, String password, String userName,
                                        String id) throws SQLException;
	public OnlineUser findOnlineUserByUnionid(String unionid_)throws SQLException;
	/**
	 * 通过用户id得到此用户是否是讲师，以及讲师的房间号
	 * Description：
	 * @param id
	 * @return
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Map<String, Object> getUserIsTeacher(String id)throws SQLException;
	public void updateUserUnionidByid(OnlineUser ou1)throws SQLException;
	
	/**
	 * Description：验证 3和4的 输入的验证码是否正确
	 * @param mobile
	 * @param code
	 * @param vtype
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	ResponseObject changeMobileCheckCode(String mobile, String code,
			String vtype) throws Exception;
	/**
	 * Description：给 3和4 发送验证码
	 * @param o
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	String changeMobileSendCode(String username, String vtype) throws Exception;
	/**
	 * Description：更新登录名（手机号）
	 * @param o
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public void updateUserLoginName(OnlineUser o)throws SQLException;
	/**
	 * Description：判断这个用户是不是讲师
	 * @param userId
	 * @return
	 * @return OnlineUser
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Map<String,Object> judgeUserIsTeacher(String userId)throws SQLException;
	/**
	 * Description：更新用户信息。通过微信
	 * @param ou
	 * @throws SQLException
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public void updateOnlineUserByWeixinInfo(OnlineUser ou, OnlineUser ouNew)throws SQLException;
}