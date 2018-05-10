package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
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
	 * 验证码检验
	 * @param mobile
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public ResponseObject checkCode(String mobile, String code, Integer vtype)throws Exception;
	/**
	 * 往本地库写用户
	 * @param mobile
	 * @param username
	 * @param shareCode 上级的分享码
	 * @return
	 * @throws Exception
	 */
	public OnlineUser addUser(String mobile, String userName,String shareCode, String password) throws Exception;
	
	/**
	 * 
	 * Description：发送短信验证码
	 * @param username
	 * @param vtype
	 * @return
	 * @throws Exception
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	String addMessage(String username, Integer vtype)throws Exception;
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
	public OnlineUser addPhoneRegistByAppH5(HttpServletRequest req,
                                                String password, String mobile, Integer vtype)throws Exception;
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
	 * 通过用户id更改 用户所对应的微吼信息
	 * @param weihouId
	 * @param password
	 * @param userName
	 * @param id
	 * @throws SQLException
	 */
	public void updateVhallIdOnlineUser(String weihouId, String password, String userName,
                                        String id) throws SQLException;
	
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
			Integer vtype) throws Exception;
	/**
	 * Description：给 3和4 发送验证码
	 * @param o
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	String changeMobileSendCode(String username, Integer vtype) throws Exception;
	/**
	 * Description：更新登录名（手机号）
	 * @param o
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public void updateUserLoginName(OnlineUser o)throws SQLException;

	
	void updateOnlineUserAddPwdAndUserName(OnlineUser ou) throws Exception;
	/**
	 * Description：查找主播信息
	 * @param lecturerId
	 * @return
	 * @throws SQLException
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public Map<String, Object> findHostById(String lecturerId)throws SQLException;
	
	public void emptyAccount(String userName)throws SQLException;
	/**
	 * 
	 * Description：验证手机号是否已经注册
	 * @param username
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void verifyPhone(String username)throws SQLException;
}
