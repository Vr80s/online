package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.WxcpClientUser;
import com.xczh.consumer.market.dao.WxcpClientUserMapper;
import com.xczh.consumer.market.service.WxcpClientUserService;
import com.xczh.consumer.market.vo.CodeUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 
 * Client端用户表
 * 
 **/
@Service
public class WxcpClientUserServiceImpl implements WxcpClientUserService {

	@Autowired
	private WxcpClientUserMapper wxcpClientUserMapper;
	//注入用户中心远程service
	@Autowired
	private UserCenterAPI userCenterApi;
	
	@Override
	public WxcpClientUser getWxcpClientUser(String clientId) throws Exception {
		return wxcpClientUserMapper.getWxcpClientUser(clientId);
	}

	@Override
	public void updateWxcpClientUser(WxcpClientUser user) throws Exception {
		wxcpClientUserMapper.updateWxcpClientUser(user);
	}
	
	/**
	 * 根据用户手机号查找用户
	 * @param user_mobile
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public WxcpClientUser getWxcpClientUserByMobile(String user_mobile) throws SQLException{
		return wxcpClientUserMapper.getWxcpClientUserByMobile(user_mobile);
	}

	/**
	 * 根据openid查找用户
	 * @return
	 * @throws SQLException 
	 */
	@Override
    public WxcpClientUser getWxcpClientUserByOpenid(String openid) throws SQLException {
		return wxcpClientUserMapper.getWxcpClientUserByOpenid(openid);
	}
	
	/**
	 * 添加
	 **/
	@Override
	public int insert( WxcpClientUser user ) throws SQLException {
		return wxcpClientUserMapper.insert(user);
	}
	
	/**
	 * 更新；
	 **/
	@Override
    public int update(WxcpClientUser user ) throws SQLException {
		return wxcpClientUserMapper.update(user);
	}
	/**
	 * 根据用户查找用户信息
	 * @return
	 * @throws SQLException 
	 */
	@Override
    public List<WxcpClientUser> getCenterUserInfo(WxcpClientUser wxcpClientUser) throws SQLException {
		return wxcpClientUserMapper.getCenterUserInfo(wxcpClientUser);
	}
	/**
	 * 用户中心查询注册用户
	 * @param mobile
	 * @param nike_name
	 * @throws Exception
	 */
	@Override
    public void saveClientUserForUserCenter(String mobile, String nike_name)throws Exception {
		WxcpClientUser wUser = wxcpClientUserMapper.getCenterUserByUserName(mobile);
		if(null == wUser){
			ItcastUser iUser = userCenterApi.getUser(mobile);
			if(null == iUser){
				userCenterApi.regist(mobile,"123456", nike_name, UserSex.UNKNOWN, "",
						mobile, UserType.COMMON, UserOrigin.ONLINE, UserStatus.NORMAL);
			}
			wUser = new WxcpClientUser();
			wUser.setUser_id(UUID.randomUUID().toString().replace("-", ""));
			wUser.setUser_name(mobile);
			wUser.setUser_mobile(mobile);
			wUser.setUser_nick_name(nike_name);
			wUser.setUser_status("1");
			wUser.setUser_sex("2");//用户性别  0表示女性  1表示男性 2表示未知
			wUser.setUser_password(CodeUtil.encodePassword("123456",null));//??wUser.setUser_password(CodeUtil.encodePassword("123456",mobile));//默认密码123456
			wUser.setOpenname("");
			wUser.setOpenid("");			
			wUser.setWx_public_id(WxPayConst.gzh_appid);
			wUser.setWx_public_name(WxPayConst.appid4name);
			wUser.setCreate_time(new Date());
			wUser.setUser_email("");
			wUser.setUser_identifyId("");
			wxcpClientUserMapper.insert(wUser);
		}else{
			ItcastUser iUser = userCenterApi.getUser(wUser.getUser_mobile());
			if(null == iUser){
				userCenterApi.regist(wUser.getUser_name(),"123456", wUser.getUser_nick_name()
						,UserSex.parse(Integer.valueOf(wUser.getUser_sex())),wUser.getUser_email(),
						wUser.getUser_mobile(), UserType.COMMON, UserOrigin.ONLINE, UserStatus.NORMAL);
			}
		}
	}
}