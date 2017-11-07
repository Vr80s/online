package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.dao.WxcpClientUserWxMappingMapper;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * 
 * 微信信息表；
 * 
 **/
@Service
public class WxcpClientUserWxMappingServiceImpl implements WxcpClientUserWxMappingService {
	
	@Autowired
	private WxcpClientUserWxMappingMapper wxcpClientUserWxMappingMapper;
	
	@Autowired
	private OnlineUserService onlineUserService;

	/**
	 * 根据用户id，查询微信信息
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingByUserId(String userId) throws SQLException {
		return wxcpClientUserWxMappingMapper.getWxcpClientUserWxMappingByUserId(userId);
	}
	
	/**
	 * Description：
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return WxcpClientUserWxMapping
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@Override
	public WxcpClientUserWxMapping getWxMappingByUserIdOrUnionId(String userId) throws SQLException {
		OnlineUser ou = onlineUserService.findUserById(userId);
		return wxcpClientUserWxMappingMapper.getWxcpClientUserByUnionId(ou.getUnionId());
	}

	/**
	 * 根据opendId 查找 微信信息表
	 * @param openId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingByOpenId(String openId) throws SQLException {
		return wxcpClientUserWxMappingMapper.getWxcpClientUserWxMappingByOpenId(openId);
	}

	/**
	 * 根据opendId 查找 微信信息表
	 * @param openId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public WxcpClientUserWxMapping getWxcpClientUserWxMappingInfo(String client_id, String openid, String wx_public_id) throws SQLException {
		return wxcpClientUserWxMappingMapper.getWxcpClientUserWxMappingInfo(client_id,openid,wx_public_id);
	}
	
	/**
	 * 添加
	 **/
	@Override
	public int insert( WxcpClientUserWxMapping record ) throws SQLException {
		return wxcpClientUserWxMappingMapper.insert(record);
	}
	
	/**
	 * 
	 * 更新；
	 * 
	 **/
	@Override
	public int update( WxcpClientUserWxMapping record ) throws SQLException {
		return wxcpClientUserWxMappingMapper.update(record);
	}
	
	/**
	 * 根据unionid 查找 微信信息表
	 * @param openId
	 * @return
	 * @throws SQLException
	 */
	@Override
	public WxcpClientUserWxMapping getWxcpClientUserByUnionId(String unionId) throws SQLException {
		return wxcpClientUserWxMappingMapper.getWxcpClientUserByUnionId(unionId);
	}
	

}