package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpClientUser;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * Client端用户表
 * @author zhangshixiong
 * @date 2017-01-17
 **/
public interface WxcpClientUserService{
	
	/**
	 * 根据用户id查找用户
	 * @param clientId
	 * @return
	 * @throws SQLException 
	 */
	public WxcpClientUser getWxcpClientUser(String clientId) throws Exception;
	
	/**
	 * 更新用户信息
	 * @param user
	 * @throws SQLException
	 */
	public void updateWxcpClientUser(WxcpClientUser user)throws Exception;
	
	/**
	 * 根据用户手机号查找用户
	 * @param user_mobile
	 * @return
	 * @throws SQLException 
	 */
	public WxcpClientUser getWxcpClientUserByMobile(String user_mobile) throws SQLException;

	/**
	 * 根据openid查找用户
	 * @param user_mobile
	 * @return
	 * @throws SQLException 
	 */
	public WxcpClientUser getWxcpClientUserByOpenid(String openid) throws SQLException;
	
	/**
	 * 
	 * 添加
	 * 
	 **/
	public int insert(WxcpClientUser user) throws SQLException ;

	/**
	 *
	 * 更新；
	 *
	 **/
	public int update(WxcpClientUser user) throws SQLException ;

	/**
	 * 根据用户查找用户信息
	 * @param user_mobile
	 * @return
	 * @throws SQLException
	 */
	public List<WxcpClientUser> getCenterUserInfo(WxcpClientUser wxcpClientUser) throws SQLException;
	/**
	 * 用户中心查询注册用户
	 * @param mobile
	 * @param nike_name
	 * @throws Exception
	 */
	public void saveClientUserForUserCenter(String mobile, String nike_name)throws Exception;
}