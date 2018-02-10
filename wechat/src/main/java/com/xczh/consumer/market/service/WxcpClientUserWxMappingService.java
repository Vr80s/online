package com.xczh.consumer.market.service;

import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;

import java.sql.SQLException;

/**
 * 
 * 微信信息表；
 * @author zhangshixiong
 * @date 2017-01-17
 **/
public interface WxcpClientUserWxMappingService{
	
	/**
	 * 根据用户id，查询微信信息
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingByUserId(String userId) throws SQLException;
	/**
	 * 根据opendId 查找 微信信息表
	 * @param openId
	 * @return
	 * @throws SQLException
	 */
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingByOpenId(String openId) throws SQLException;
	
	/**
	 * 根据opendId 查找 微信信息表
	 * @param openId
	 * @return
	 * @throws SQLException
	 */
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingInfo(String client_id, String openid, String wx_public_id) throws SQLException ;

	/**
	 * 添加
	 **/
	public int insert(WxcpClientUserWxMapping record) throws SQLException ;


	public int update(WxcpClientUserWxMapping record) throws SQLException ;
	/**
	 * 根据unionid 查找 微信信息表
	 * @param openId
	 * @return
	 * @throws SQLException
	 */
	WxcpClientUserWxMapping getWxcpClientUserByUnionId(String unionId)
			throws SQLException;
	/**
	 * Description：先通过用户id得到unionid在通过uninonid得到微信信息
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return WxcpClientUserWxMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	WxcpClientUserWxMapping getWxMappingByUserIdOrUnionId(String userId)
			throws SQLException;
	/**
	 * 
	 * Description：通过unionId和用户id获取查看微信信息
	 * @param userId
	 * @param unionId
	 * @return
	 * @return WxcpClientUserWxMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingByUserIdAndUnionId(String userId,
			String unionId)throws SQLException;

}