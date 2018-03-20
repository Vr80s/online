package com.xczh.consumer.market.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.SystemVariate;
import com.xczh.consumer.market.bean.VerificationCode;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.dao.WxcpClientUserWxMappingMapper;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.CookieUtil;
import com.xczh.consumer.market.utils.RandomUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SmsUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.wxpay.util.WeihouInterfacesListUtil;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.enums.SMSCode;
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
	
	//private String  password = RandomUtil.getCharAndNumr(6);
	
	
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
		if (new Date().getTime() - codes.get(0).getCreateTime().getTime() > 1000 * 60 * Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			return ResponseObject.newErrorResponseObject("动态码超时，请重新发送！");
		}
		return ResponseObject.newSuccessResponseObject("动态码正确！");
	}
	/**
	 * 微信端注册
	 */
	@Override
	public ResponseObject addPhoneRegist(HttpServletRequest req, String password,
			String mobile,String openId)
			throws Exception {
		
		WxcpClientUserWxMapping m = wxcpClientUserWxMappingMapper.getWxcpClientUserWxMappingByOpenId(openId);
		String username = "";
		if(null != m){
			username = m.getNickname();
		}
		//手机
		ItcastUser iu = userCenterAPI.getUser(mobile);
		OnlineUser user = onlineUserDao.findUserByLoginName(mobile);
		if(iu == null){
		   //向用户中心注册
			userCenterAPI.regist(mobile, password, username, UserSex.UNKNOWN, null,
					mobile, UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
		}
		if(null == user){
			String shareCode = CookieUtil.getCookieValue(req, "_usercode_");
			user = this.addUser(mobile, username,shareCode,password);
		}
		List<VerificationCode> lists = onlineUserDao.getListVerificationCode(mobile);
		if(null != lists && lists.size() > 0){
			onlineUserDao.deleteVerificationCodeById(lists.get(0).getId());
		}
		if(m != null){
			m.setClient_id(user.getId());
			wxcpClientUserWxMappingMapper.update(m);
		}
		/**
		 * 为用户初始化一条代币记录
		 */
		userCoinService.saveUserCoin(user.getId());
		return ResponseObject.newSuccessResponseObject(user);
	}
	
	public void initSystemVariate() throws Exception{
		//查数据字典
		List<SystemVariate> lst = onlineUserDao.getListSystemVariate();
		for (SystemVariate systemVariate : lst) {
			attrs.put(systemVariate.getName(), systemVariate.getValue());
		}
	}
	
	@Override
	public void updateOnlineUser(OnlineUser user) throws SQLException {
		onlineUserDao.updateOnlineUser(user);
	}
	
	@Override
	public OnlineUser addUser(String mobile, String username, 
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
		
		u.setVhallId(weihouUserId);  //微吼id
		u.setVhallName(mobile);
		u.setVhallPass(WeihouInterfacesListUtil.MOREN);    //微吼密码
		
		onlineUserDao.addOnlineUser(u);
		return u;
	}

	//20170711---yuruixin
	@Override
	public String addMessage(String username, Integer vtype) throws Exception{
		
		try {
			initSystemVariate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * 重置密码，根据本地库判断用户是否存在
		 */
		if ((SMSCode.FORGOT_PASSWORD.getCode() == vtype || SMSCode.WITHDRAWAL.getCode() == vtype)  && (o == null || iu == null)) {
			return  "用户不存在！";
		}
		/**
		 * 重置密码，根据用户中心判断用户是否被禁用
		 */
		if ((SMSCode.FORGOT_PASSWORD.getCode() == vtype || SMSCode.WITHDRAWAL.getCode() == vtype) && iu.getStatus() == -1) {
			return "用户已禁用！";
		}
		
		// 产生随机4位动态码
		String vcode = String.valueOf(ThreadLocalRandom.current().nextInt(1000,10000));
		
		//vcode = "1234";
		
		List<VerificationCode> codes = null;
		try {
			codes = onlineUserDao.getListVerificationCode(username,vtype);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (codes != null && codes.size() > 0) {// 如果存在历史动态码，按业务逻辑判断
				
			VerificationCode code = codes.get(0);
			
			if (new Date().getTime() - code.getCreateTime().getTime() < 1000 * Integer.valueOf(attrs.get("message_provider_interval_time"))) {
				//发送，判断邮箱还是手机
				if (username.contains("@")){
					return "同一邮箱两次发送间隔至少" + Integer.valueOf(attrs.get("message_provider_interval_time")) + "秒！";
				}else{
					return "同一手机号两次发送间隔至少" + Integer.valueOf(attrs.get("message_provider_interval_time")) + "秒！";
				}
			} else if (new Date().getTime() - code.getCreateTime().getTime() > 1000 * 60
					* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
				code.setVcode(vcode);
			} else {
				vcode = code.getVcode();
			}
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
				// TODO Auto-generated catch block
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
	public Map<String, Object> findUserRoomNumberById(String lecturerId) throws SQLException {
		return onlineUserDao.findUserRoomNumberById(lecturerId);
	}
	
	
	@Override
	public List<Map<String, Object>> findHotHostByQueryKey(String queryKey) throws SQLException {
		return onlineUserDao.findHotHostByQueryKey(queryKey);
	}
	@Override
	public void updateVhallIdOnlineUser(String weihouId, String password,String userName,
			String id) throws SQLException {
	    onlineUserDao.updateVhallIdOnlineUser(weihouId,password,userName,id);
	}
	@Override
	public OnlineUser findOnlineUserByUnionid(String unionid_)
			throws SQLException {
		return onlineUserDao.findOnlineUserByUnionid(unionid_);
	}
	@Override
	public Map<String, Object> getUserIsTeacher(String id) throws SQLException {
		return onlineUserDao.getUserIsTeacher(id);
	}
	@Override
	public void updateUserUnionidByid(OnlineUser ou1) throws SQLException {
		// TODO Auto-generated method stub
		onlineUserDao.updateUserUnionidByid(ou1);
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
			} else if (new Date().getTime() - code.getCreateTime().getTime() > 1000 * 60 * Integer.valueOf(attrs.get("message_provider_valid_time"))) {
				code.setVcode(vcode);
			} else {
				vcode = code.getVcode();
			}
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
	public Map<String,Object> judgeUserIsTeacher(String userId) throws SQLException {
		
	    Map<String,Object> map =  onlineUserDao.judgeUserIsTeacher(userId);
	    
	    
		return map;
	}
	@Override
	public void updateOnlineUserByWeixinInfo(OnlineUser ou, OnlineUser ouNew)
			throws SQLException {
		onlineUserDao.updateOnlineUserByWeixinInfo(ou,ouNew);
	}	

	@Override
	public Map<String, Object> getAppTouristRecord(String appOnlyOne)
			throws SQLException {
		return onlineUserDao.getAppTouristRecord(appOnlyOne);
	}	
	@Override
	public void saveAppTouristRecord(OnlineUser ou,String appOnlyOne)
			throws SQLException {
	    onlineUserDao.saveAppTouristRecord(ou,appOnlyOne);
	}
	@Override
	public ResponseObject updateIPhoneRegist(HttpServletRequest req,
			String password, String mobile, Integer vtype, String appUniqueId) throws Exception {
		
		//手机
		ItcastUser iu = userCenterAPI.getUser(mobile);
		OnlineUser user = onlineUserDao.findUserByLoginName(mobile);
		ItcastUser iuApple = userCenterAPI.getUser(appUniqueId);
		//如果此唯一id已经注册了，那么这个就是普通的注册
		if(iu == null && iuApple==null){
			//向用户中心注册
			return ResponseObject.newSuccessResponseObject(addPhoneRegistByAppH5(req,password,mobile,vtype));
		}
		
		if(iu == null){
		   //向用户中心注册
			userCenterAPI.update(appUniqueId,"",3, null, mobile,3, 3);
			userCenterAPI.updatePassword(appUniqueId,"123456",password);
			//更改登录名
			userCenterAPI.updateLoginName(appUniqueId,mobile);
		}
		if(null == user ){
			String shareCode = CookieUtil.getCookieValue(req, "_usercode_");
			user = this.updateUser(mobile, mobile,shareCode,password,appUniqueId);
		}
		//注册过后删除这个被记录的验证码
		List<VerificationCode> lists = onlineUserDao.getListVerificationCode(mobile,vtype);
		if(null != lists && lists.size() > 0){
			onlineUserDao.deleteVerificationCodeById(lists.get(0).getId());
		}
		return ResponseObject.newSuccessResponseObject(user);
		
	}	
	
	public OnlineUser updateUser(String mobile, String username,
			String shareCode, String password,String appUniqueId) throws Exception{
		
		//查询出这个用户
		Map<String, Object> map = onlineUserDao.getAppTouristRecord(appUniqueId);
		if(map == null){
			return null;
		}
		OnlineUser u = onlineUserDao.findUserById(map.get("userId").toString());
		//保存本地库
		u.setLoginName(mobile);
		u.setMobile(mobile);
		u.setName(mobile);
		u.setPassword(password);
		u.setSmallHeadPhoto(returnOpenidUri+"/web/images/defaultHead/18.png");
		onlineUserDao.updateOnlineUserAddPwdAndUserName(u);
		return u;
	}
	
	@Override
	public void updateOnlineUserAddPwdAndUserName(OnlineUser ou) throws Exception{
		onlineUserDao.updateOnlineUserAddPwdAndUserName(ou);
	}
	@Override
	public OnlineUser findUserByIdAndVhallNameInfo(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return onlineUserDao.findUserByIdAndVhallNameInfo(userId);
	}
	
	@Override
	public OnlineUser addYkUser(String appUniqueId) throws Exception {
		
		//String [] arr = "人参,人发,卜芥,儿茶,八角,丁香,刀豆,三七,三棱,干姜,干漆,广白,广角,广丹,大黄,大戟,大枣,大蒜,大蓟,小蓟,小麦,小蘖".split(",");
//		int index=(int)(Math.random()*arr.length);
		String name = "游客";
		String password = "123456";
		/**
		 * 因为要用到用户中心id了
		 */
		ItcastUser iu = userCenterAPI.getUser(appUniqueId);
		if(iu == null){
		   //向用户中心注册
			userCenterAPI.regist(appUniqueId, password, "", UserSex.UNKNOWN, null,
					appUniqueId, UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
			
			iu = userCenterAPI.getUser(appUniqueId);
		}
		OnlineUser u = new OnlineUser();
		
		//用户中心id  用于发送礼物等操作
		u.setUserCenterId(iu.getId());

		//保存本地库
		u.setId(UUID.randomUUID().toString().replace("-", ""));
		//u.setLoginName(name);
		//u.setMobile(mobile);
		u.setStatus(0);
		u.setCreateTime(new Date());
		u.setDelete(false);
		u.setName("");   //默认一个名字
		u.setSmallHeadPhoto(webdomain+"/web/images/defaultHead/18.png");
		u.setVisitSum(0);
		u.setStayTime(0);
		u.setUserType(0);
		u.setOrigin("apple_yk");
		u.setMenuId(-1);
		u.setPassword(iu.getPassword());
		u.setSex(OnlineUser.SEX_UNKNOWN);
		u.setType(1);
		//分销，设置上级
		String weihouUserId = WeihouInterfacesListUtil.createUser(
				u.getId(),
				WeihouInterfacesListUtil.MOREN,
				name,
				returnOpenidUri+"/web/images/defaultHead/" + (int) (Math.random() * 20 + 1)+".png");
		
		u.setVhallId(weihouUserId);  //微吼id
		u.setVhallName(name);
		u.setVhallPass(WeihouInterfacesListUtil.MOREN);    //微吼密码
		onlineUserDao.addOnlineUser(u);
		/**
		 * 为用户初始化一条代币记录
		 */
		userCoinService.saveUserCoin(u.getId());
		
		return u;
	}
	@Override
	public void updateAppleTourisrecord(String appUniqueId,Integer isReigs) throws SQLException {
		// TODO Auto-generated method stub
		onlineUserDao.updateAppleTourisrecord(appUniqueId,isReigs);
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
	public OnlineUser wechatCreateUserInfo(WxcpClientUserWxMapping wx) throws SQLException {
		// TODO Auto-generated method stub
		/**
		 * 用户中心注册
		 */
		com.xczh.consumer.market.utils.Token iu =  userCenterAPI.regist(wx.getUnionid(), 
				WeihouInterfacesListUtil.MOREN_USER_PASSWORD,
				wx.getNickname(),UserSex.parseWechat(wx.getSex()), null,
				null, UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
			
		
		OnlineUser u = new OnlineUser();
		//用户中心id  用于发送礼物等操作
		u.setUserCenterId(iu.getUserId());
		//保存本地库
		u.setId(UUID.randomUUID().toString().replace("-", ""));
		u.setStatus(0);
		u.setCreateTime(new Date());
		u.setDelete(false);
		u.setLoginName(wx.getUnionid());
		u.setName(wx.getNickname());   						//微信用户名称
		u.setSmallHeadPhoto(wx.getHeadimgurl());//微信用户头像
		u.setVisitSum(0);
		u.setStayTime(0);
		u.setUserType(0);
		u.setOrigin("weixin_public");
		u.setMenuId(-1);
		u.setPassword(WeihouInterfacesListUtil.MOREN_USER_PASSWORD);
		u.setSex(UserSex.parseWechat(wx.getSex()).getValue()); //微信性别
		u.setType(1);
		
		/**
		 * 创建微吼信息
		 */
		String weihouUserId = WeihouInterfacesListUtil.createUser(
				u.getId(),
				WeihouInterfacesListUtil.MOREN,wx.getNickname(),
				wx.getHeadimgurl());
		u.setVhallId(weihouUserId);  //微吼id
		u.setVhallName(wx.getNickname());
		u.setVhallPass(WeihouInterfacesListUtil.MOREN);    //微吼密码
		
		onlineUserDao.addOnlineUser(u);
		
		
		/**
		 * 为用户初始化一条代币记录
		 */
		userCoinService.saveUserCoin(u.getId());
		return u;
	}
	@Override
	public void verifyPhone(String username) throws SQLException {
		// TODO Auto-generated method stub
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
