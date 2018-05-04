package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.SystemVariate;
import com.xczh.consumer.market.bean.VerificationCode;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.dao.WxcpClientUserWxMappingMapper;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.CookieUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SmsUtil;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.common.util.enums.SMSCode;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;

@Service
public class OnlineUserServiceImpl implements OnlineUserService {
	@Autowired
	public OnlineUserMapper onlineUserDao;
	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private UserCoinService userCoinService;
	
	@Value("${returnOpenidUri}")
	private String returnOpenidUri;
	
	@Value("${webdomain}")
	private String webdomain;
	
	@Autowired
	public WxcpClientUserWxMappingMapper wxcpClientUserWxMappingMapper;
	//数据字典
	private Map<String, String> attrs = new HashMap<String, String>();
	
	
	@Override
	public OnlineUser findUserById(String id) throws SQLException {
		return onlineUserDao.findUserById(id);
	}
	@Override
	public OnlineUser findUserByLoginName(String loginName) throws SQLException {
		return onlineUserDao.findUserByLoginName(loginName);
	}

	@Override
	public void addOnlineUser(OnlineUser user) throws SQLException {
		onlineUserDao.addOnlineUser(user);
	}

	@Override
	public ResponseObject checkCode(String mobile, String code,Integer  vtype) throws Exception {
		if(null == mobile){
			return ResponseObject.newErrorResponseObject("手机号不能为空! ");
		}
		if(null == code){
			return ResponseObject.newErrorResponseObject("验证码不能为空! ");
		}
		initSystemVariate();
		/**
		 * 如果短信验证类型是忘记密码的话，通过手机号查询是否存在此用户
		 */
		if(vtype!=null && SMSCode.FORGOT_PASSWORD.getCode() == vtype){
			//在用户重新获取登录对象
			ItcastUser iu = userCenterAPI.getUser(mobile);
			if(iu == null){
				return ResponseObject.newErrorResponseObject("用户不存在！");
			}
			OnlineUser o = onlineUserDao.findUserByLoginName(mobile);
			if (o != null) {
				if (o.isDelete() || o.getStatus() == -1){
					return ResponseObject.newErrorResponseObject("用户已禁用");
				}
			}	
		}
		/**
		 * 通过手机号和短信验证码类型查找 是否存在此条信息
		 */
		List<VerificationCode>  codes = onlineUserDao.getListVerificationCode(mobile,vtype);
		
		if (codes == null || codes.size() <= 0) {
			return ResponseObject.newErrorResponseObject("动态码不正确! ");
		}
		if(!codes.get(0).getVcode().equals(code)){
			return ResponseObject.newErrorResponseObject("动态码不正确！");
		}
		
		if (new Date().getTime() - codes.get(0).getCreateTime().getTime() > 
			1000 * 60 * Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			
			return ResponseObject.newErrorResponseObject("动态码超时，请重新发送！");
		}
		return ResponseObject.newSuccessResponseObject("动态码正确！");
	}
	
	
	public void initSystemVariate() throws Exception{
		//查数据字典
		List<SystemVariate> lst = onlineUserDao.getListSystemVariate();
		for (SystemVariate systemVariate : lst) {
			attrs.put(systemVariate.getName(), systemVariate.getValue());
		}
	}
	
	
	@Override
	public OnlineUser addUser(String mobile, String userName,
			String origin, String password) throws Exception {
		
		OnlineUser u = new OnlineUser();
		//保存本地库
		u.setId(UUID.randomUUID().toString().replace("-", ""));
		u.setLoginName(mobile);
		u.setMobile(mobile);
		u.setStatus(0);
		u.setCreateTime(new Date());
		u.setDelete(false);
		u.setName(mobile);   //初次登录设置默认名为：手机号
		u.setSmallHeadPhoto(webdomain+"/web/images/defaultHead/18.png");
		u.setVisitSum(0);
		u.setStayTime(0);
		u.setUserType(0);
		u.setOrigin(origin);
		u.setMenuId(-1);
		u.setPassword(password);
		u.setSex(OnlineUser.SEX_UNKNOWN);
		u.setCreateTime(new Date());
		u.setType(1);
		
		/**
		 * 创建微吼信息
		 */
		String weihouUserId = WeihouInterfacesListUtil.createUser(u.getId(),
				WeihouInterfacesListUtil.MOREN,mobile, 
				u.getSmallHeadPhoto(),mobile);
		if(StringUtils.isNotBlank(weihouUserId)) {
			u.setVhallId(weihouUserId);  //微吼id
			u.setVhallName(mobile);
			u.setVhallPass(WeihouInterfacesListUtil.MOREN);    //微吼密码
		}
		onlineUserDao.addOnlineUser(u);
		return u;
	}

	//20170711---yuruixin
	@Override
	public String addMessage(String username, Integer vtype) throws Exception{
		
		try {
			initSystemVariate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (SMSCode.RETISTERED.getCode() !=vtype && SMSCode.FORGOT_PASSWORD.getCode()!=vtype &&
				SMSCode.WITHDRAWAL.getCode() != vtype ) {
			throw new RuntimeException ("动态码类型错误！1为注册，2为找回密码  5 为提现使用");
		}
		ItcastUser iu = null;
		try {
			iu = userCenterAPI.getUser(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 新注册，根据用户中心判断用户是否存在
		 */
		if (SMSCode.RETISTERED.getCode() == vtype && iu != null) {
			return  (String.format("该%s已注册，请直接登录！",username.contains("@") ? "邮箱" : "手机号"));
		}

		OnlineUser o = null;
		try {
			o = onlineUserDao.findUserByLoginName(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/**
		 * 重置密码，根据本地库判断用户是否存在
		 */
		if ((SMSCode.FORGOT_PASSWORD.getCode() == vtype ||
				SMSCode.WITHDRAWAL.getCode() == vtype)
				&& (o == null || iu == null)) {
			return  "用户不存在！";
		}
		/**
		 * 重置密码，根据用户中心判断用户是否被禁用
		 */
		if ((SMSCode.FORGOT_PASSWORD.getCode() == vtype || 
				SMSCode.WITHDRAWAL.getCode() == vtype) 
				&& (o.isDelete() || o.getStatus() == -1 )) {
			return "用户已禁用！";
		}
		//产生随机4位动态码
		String vcode = String.valueOf(ThreadLocalRandom.current().nextInt(1000,10000));
		List<VerificationCode> codes = null;
		try {
			codes = onlineUserDao.getListVerificationCode(username,vtype);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (codes != null && codes.size() > 0) {// 如果存在历史动态码，按业务逻辑判断
			VerificationCode code = codes.get(0);
			if (new Date().getTime() - code.getCreateTime().getTime() < 
					1000 * Integer.valueOf(attrs.get("message_provider_interval_time"))) {
				//发送，判断邮箱还是手机
				if (username.contains("@")){
					return "同一邮箱两次发送间隔至少" + Integer.valueOf(attrs.get("message_provider_interval_time")) + "秒！";
				}else{
					return "同一手机号两次发送间隔至少" + Integer.valueOf(attrs.get("message_provider_interval_time")) + "秒！";
				}
			} 
//			else if (new Date().getTime() - code.getCreateTime().getTime() > 1000 * 60 
//					* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
//				
//				code.setVcode(vcode);
//			} else {
//				vcode = code.getVcode();
//			}
			code.setVcode(vcode);
			code.setCreateTime(new Date());
			try {
				onlineUserDao.updateVerificationCode(code);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {// 不存在历史动态码，直接发送
			// 保持动态码信息
			VerificationCode c = new VerificationCode();
			c.setCreateTime(new Date());
			c.setDelete(false);
			c.setPhone(username);
			c.setVcode(vcode);
			c.setVtype(vtype);
			try {
				onlineUserDao.insertVerificationCode(c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		sendPhone(username, vcode, vtype);
		return "发送成功";
	}
	
	private void sendPhone(String phone, String vcode,Integer vtype){
		
		if (SMSCode.RETISTERED.getCode() !=vtype && SMSCode.FORGOT_PASSWORD.getCode()!=vtype &&
				SMSCode.WITHDRAWAL.getCode() != vtype ) {
			throw new RuntimeException ("动态码类型错误！1为注册，2为找回密码  5 为提现使用");
		}
		//判断发送结果
		SendSmsResponse response = null;
		try {
			response = SmsUtil.sendSms(phone, vcode);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		if (response==null || !"OK".equals(response.getCode())) {
			throw new RuntimeException ("发送动态码失败！");
		}
	}
	/**
	 * 微信端注册
	 */
	@Override
	public OnlineUser addPhoneRegistByAppH5(HttpServletRequest req, String password,
			String mobile,Integer vtype)
			throws Exception {
		//手机
		ItcastUser iu = userCenterAPI.getUser(mobile);
		OnlineUser user = onlineUserDao.findUserByLoginName(mobile);
		if(iu == null){
		   //向用户中心注册
			userCenterAPI.regist(mobile, password, "", UserSex.UNKNOWN, null,
					mobile, UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
		}
		if(null == user ){
			String shareCode = CookieUtil.getCookieValue(req, "_usercode_");
			user = this.addUser(mobile, "",shareCode,password);
		}
		/*
		 * 注册过后删除这个被记录的验证码
		 */
		List<VerificationCode> lists = onlineUserDao.getListVerificationCode(mobile,vtype);
		if(null != lists && lists.size() > 0){
			onlineUserDao.deleteVerificationCodeById(lists.get(0).getId());
		}
		/**
		 * 注册成功后初始化
		 * 为用户初始化一条代币记录
		 */
		userCoinService.saveUserCoin(user.getId());
		return user;
	}
	@Override
	public void updateUserCenterData(OnlineUser user, Map<String,String> map) throws SQLException {
		onlineUserDao.updateUserCenter(user,map);
	}
	
	
	@Override
	public void updateVhallIdOnlineUser(String weihouId, String password,String userName,
			String id) throws SQLException {
	    onlineUserDao.updateVhallIdOnlineUser(weihouId,password,userName,id);
	}
	
	/**
	 * 短信验证码验证
	 */
	@Override
	public ResponseObject changeMobileCheckCode(String mobile, String code, Integer vtype) throws Exception {
		if(null == mobile){
			return ResponseObject.newErrorResponseObject("手机号不能为空! ");
		}
		if(null == code){
			return ResponseObject.newErrorResponseObject("验证码不能为空! ");
		}
		initSystemVariate();
		
		//已经存在的手机号需要验证时候有效
		if(vtype!=null && SMSCode.OLD_PHONE.getCode() ==vtype &&  SMSCode.WITHDRAWAL.getCode() == vtype){
			
			//在用户重新获取登录对象
			ItcastUser iu = userCenterAPI.getUser(mobile);
			if(iu == null){
				return ResponseObject.newErrorResponseObject("用户不存在！");
			}
			OnlineUser o = onlineUserDao.findUserByLoginName(mobile);
			if (o != null) {
				if (o.isDelete() || o.getStatus() == -1){
					return ResponseObject.newErrorResponseObject("用户已禁用");
				}
			}	
		}
		//动态码验证
		List<VerificationCode>  codes = onlineUserDao.getListVerificationCode(mobile,vtype);
		if (codes == null || codes.size() <= 0) {
			return ResponseObject.newErrorResponseObject("动态码不正确! ");
		}
		if(!codes.get(0).getVcode().equals(code)){
			return ResponseObject.newErrorResponseObject("动态码不正确！");
		}
		if (new Date().getTime() - codes.get(0).getCreateTime().getTime() > 1000 * 60
				* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			return ResponseObject.newErrorResponseObject("动态码超时，请重新发送！");
		}
		return ResponseObject.newSuccessResponseObject("动态码正确！");
	}
	/**
	 * 给 3 和 4  发送动态码
	 */
	@Override
	public String changeMobileSendCode(String username, Integer vtype) throws Exception{
		try {
			initSystemVariate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (SMSCode.OLD_PHONE.getCode()!=vtype && SMSCode.NEW_PHONE.getCode()!=vtype ) {
			throw new RuntimeException ("动态码类型错误！3为本手机，4为要更换的手机");
		}
		if(vtype!=null && SMSCode.OLD_PHONE.getCode()==vtype){
			//在用户重新获取登录对象
			ItcastUser iu = userCenterAPI.getUser(username);
			if(iu == null){
				return "用户不存在！";
			}
			OnlineUser o = onlineUserDao.findUserByLoginName(username);
			if (o != null) {
				if (o.isDelete() || o.getStatus() == -1){
					return "用户已禁用";
				}
			}	
		}else if(vtype!=null && SMSCode.NEW_PHONE.getCode()==vtype){
			//在用户重新获取登录对象
			ItcastUser iu = userCenterAPI.getUser(username);
			if(iu!=null){
				return "此手机号已被绑定";
			}
			OnlineUser o = onlineUserDao.findUserByLoginName(username);
			if (o != null) {
				if (o.isDelete() || o.getStatus() == -1){
					return "用户已禁用";
				}
			}	
		}
		// 产生随机4位动态码
		String vcode = String.valueOf(ThreadLocalRandom.current().nextInt(1000,10000));
		
		//vcode = "1234";
		List<VerificationCode> codes = null;
		try {
			codes = onlineUserDao.getListVerificationCode(username,vtype);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (codes != null && codes.size() > 0) {// 如果存在历史动态码，按业务逻辑判断
				
			VerificationCode code = codes.get(0);
			
			//时间差
			Long  t_difference = new Date().getTime() - code.getCreateTime().getTime() ;
			
			//时间间隔
			
			if (new Date().getTime() - code.getCreateTime().getTime() < 1000 * Integer.valueOf(attrs.get("message_provider_interval_time"))) {
				//发送，判断邮箱还是手机
				if (username.contains("@")){
					return "同一邮箱两次发送间隔至少" + Integer.valueOf(attrs.get("message_provider_interval_time")) + "秒！";
				}else{
					return "同一手机号两次发送间隔至少" + Integer.valueOf(attrs.get("message_provider_interval_time")) + "秒！";
				}
			} 
//			如果十分钟内的话，发送的验证码都一样了。			
//			else if (new Date().getTime() - code.getCreateTime().getTime() > 1000 * 60 * Integer.valueOf(attrs.get("message_provider_valid_time"))) {
//				code.setVcode(vcode);
//			} else {
//				vcode = code.getVcode();
//			}
			code.setVcode(vcode);
			code.setCreateTime(new Date());
			try {
				onlineUserDao.updateVerificationCode(code);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {// 不存在历史动态码，直接发送
			// 保持动态码信息
			VerificationCode c = new VerificationCode();
			c.setCreateTime(new Date());
			c.setDelete(false);
			c.setPhone(username);
			c.setVcode(vcode);
			c.setVtype(vtype);
			try {
				onlineUserDao.insertVerificationCode(c);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		sendPhoneCheck(username, vcode, vtype);
		return "发送成功";
	}
	/**
	 * 
	 * Description：用户更换手机号使用的接口
	 * @param phone
	 * @param vcode
	 * @param vtype
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	private void sendPhoneCheck(String phone, String vcode,Integer vtype){
		if (SMSCode.OLD_PHONE.getCode()!=vtype && SMSCode.NEW_PHONE.getCode()!=vtype ) {
			throw new RuntimeException ("动态码类型错误！3为本手机，4为要更换的手机");
		}
		//判断发送结果
		SendSmsResponse response = null;
		try {
			response = SmsUtil.sendSms(phone, vcode);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		if (response==null || !"OK".equals(response.getCode())) {
			throw new RuntimeException ("发送失败");
		}
	}
	
	public static void main(String[] args) {
		String vcode = String.valueOf(ThreadLocalRandom.current().nextInt(1000,10000));
		System.out.println(vcode);
		double 	vcode1 = Math.random()*9000+1000;
		System.out.println(vcode1);
		System.out.println((int)((Math.random()*9+1)*1000));
	}
	
	
	@Override
	public void updateUserLoginName(OnlineUser o) throws SQLException {
		onlineUserDao.updateUserLoginName(o);
	}
	
	
	@Override
	public void updateOnlineUserAddPwdAndUserName(OnlineUser ou) throws Exception{
		onlineUserDao.updateOnlineUserAddPwdAndUserName(ou);
	}
	@Override
	public Map<String, Object> findHostById(String lecturerId)
			throws SQLException {
		return onlineUserDao.findHostById(lecturerId);
	}
	@Override
	public void emptyAccount(String userName) throws SQLException {
		onlineUserDao.emptyAccount(userName);
	}
	
	@Override
	public void verifyPhone(String username) throws SQLException {
		//在用户重新获取登录对象
		ItcastUser iu = userCenterAPI.getUser(username);
		if(iu == null){
			throw new RuntimeException ("用户不存在！");
		}
		OnlineUser o = onlineUserDao.findUserByLoginName(username);
		if (o != null) {
			if (o.isDelete() || o.getStatus() == -1){
				throw new RuntimeException ("用户已禁用！");
			}
		}	
	}
	
}
