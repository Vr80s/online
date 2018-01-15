package com.xczh.consumer.market.service.impl;

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
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.SmsUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.wxpay.util.WeihouInterfacesListUtil;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
	public ResponseObject checkCode(String mobile, String code, String vtype) throws Exception {
		if(null == mobile){
			return ResponseObject.newErrorResponseObject("手机号不能为空! ");
		}
		if(null == code){
			return ResponseObject.newErrorResponseObject("验证码不能为空! ");
		}
		initSystemVariate();
		/**
		 * 如果vtype类型等于1 的话，证明是注册，用户肯定是不存在了
		 * 如果vtype等于1 的话,就需要判断用户是否存在啦
		 */
		if(vtype!=null && "2".equals(vtype)){
			//在用户重新获取登陆对象
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
	public OnlineUser addUser(String mobile, String username, String shareCode, String password) throws Exception {
		OnlineUser u = new OnlineUser();
		//保存本地库
		u.setId(UUID.randomUUID().toString().replace("-", ""));
		u.setLoginName(mobile);
		u.setMobile(mobile);
		u.setStatus(0);
		u.setCreateTime(new Date());
		u.setDelete(false);
		u.setName(mobile);   //初次登录设置默认名为：手机号
		u.setSmallHeadPhoto(returnOpenidUri+"/web/images/defaultHead/" + (int) (Math.random() * 20 + 1)+".png");
		u.setVisitSum(0);
		u.setStayTime(0);
		u.setUserType(0);
		u.setOrigin("online");
		u.setMenuId(-1);
		
		u.setPassword(password);
		
		u.setSex(OnlineUser.SEX_UNKNOWN);
		u.setCreateTime(new Date());
		u.setType(1);
		//分销，设置上级
		if (shareCode != null && !"".equals(shareCode)) {
			List<Map<String, Object>> parent = onlineUserDao.query
					(JdbcUtil.getCurrentConnection(),"select id from oe_user where share_code = ?", new MapListHandler(),shareCode);
			if (parent.size() > 0) {
				u.setParentId(parent.get(0).get("id").toString());
			}
		}
		//String uuid = UUID.randomUUID().toString().replace("-", "");
		String weihouUserId = WeihouInterfacesListUtil.createUser(u.getId(),WeihouInterfacesListUtil.MOREN,mobile, u.getSmallHeadPhoto());
		u.setVhallId(weihouUserId);  //微吼id
		//u.setVhallName(u.getId());  //微吼名字
		u.setVhallName(mobile);
		u.setVhallPass(WeihouInterfacesListUtil.MOREN);    //微吼密码
		onlineUserDao.addOnlineUser(u);
		return u;
	}

	//20170711---yuruixin
	@Override
	public String addMessage(String username, String vtype) throws Exception{
		
		try {
			initSystemVariate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"1".equals(vtype) && !"2".equals(vtype)) {
			throw new RuntimeException ("动态码类型错误！1为注册，2为找回密码");
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
		if ("1".equals(vtype) && iu != null) {
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
		if ("2".equals(vtype) && (o == null || iu == null)) {
			return  "用户不存在！";
		}
		/**
		 * 重置密码，根据用户中心判断用户是否被禁用
		 */
		if ("2".equals(vtype) && iu.getStatus() == -1) {
			return "用户已禁用！";
		}
		
		// 产生随机6位动态码
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
		return "发送成功！";
	}
	
	private void sendPhone(String phone, String vcode,String vtype){
		if (!"1".equals(vtype) && !"2".equals(vtype)) {
			throw new RuntimeException("动态码类型错误！1为注册，2为找回密码");
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
	public ResponseObject addPhoneRegistByAppH5(HttpServletRequest req, String password,
			String mobile,String vtype)
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
		//注册过后删除这个被记录的验证码
		List<VerificationCode> lists = onlineUserDao.getListVerificationCode(mobile,vtype);
		if(null != lists && lists.size() > 0){
			onlineUserDao.deleteVerificationCodeById(lists.get(0).getId());
		}
		/**
		 * 为用户初始化一条代币记录
		 */
		userCoinService.saveUserCoin(user.getId());
		return ResponseObject.newSuccessResponseObject(user);
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
	 * 验证3 和 4 的动态码
	 */
	@Override
	public ResponseObject changeMobileCheckCode(String mobile, String code, String vtype) throws Exception {
		if(null == mobile){
			return ResponseObject.newErrorResponseObject("手机号不能为空! ");
		}
		if(null == code){
			return ResponseObject.newErrorResponseObject("验证码不能为空! ");
		}
		initSystemVariate();
		/**
		 * 如果vtype类型等于1 的话，证明是注册，用户肯定是不存在了
		 * 如果vtype等于1 的话,就需要判断用户是否存在啦
		 * 
		 * 如果vtype类型等于3 的话，证明是注册，用户肯定是不存在了
		 * 如果vtype等于3的话,就需要判断用户是否存在啦
		 */
		if(vtype!=null && "3".equals(vtype)){
			//在用户重新获取登陆对象
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
	public String changeMobileSendCode(String username, String vtype) throws Exception{
		
		try {
			initSystemVariate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!"3".equals(vtype) && !"4".equals(vtype)) {
			throw new RuntimeException ("动态码类型错误！3为本手机，4为要更换的手机");
		}
		
		if(vtype!=null && "3".equals(vtype)){
			//在用户重新获取登陆对象
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
		}else if(vtype!=null && "4".equals(vtype)){
			//在用户重新获取登陆对象
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
		//为了方便测试战时不做这个测试了
		sendPhoneCheck(username, vcode, vtype);
		return "发送成功！";
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
	private void sendPhoneCheck(String phone, String vcode,String vtype){
		if (!"3".equals(vtype) && !"4".equals(vtype)) {
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
			throw new RuntimeException ("发送动态码失败！");
		}
	}
	
	
	public static void main(String[] args) {
		String vcode = String.valueOf(ThreadLocalRandom.current().nextInt(1000,10000));
		System.out.println(vcode);
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
			String password, String mobile, String vtype, String appUniqueId) throws Exception {
		
		//手机
		ItcastUser iu = userCenterAPI.getUser(mobile);
		OnlineUser user = onlineUserDao.findUserByLoginName(mobile);
		if(iu == null){
		   //向用户中心注册
//			userCenterAPI.regist(mobile, password, "", UserSex.UNKNOWN, null,
//					mobile, UserType.STUDENT, UserOrigin.ONLINE, UserStatus.NORMAL);
			userCenterAPI.update(mobile,"",0, null, mobile,0, 0);
			userCenterAPI.updatePassword(appUniqueId,"123456",password);
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
		u.setSmallHeadPhoto(returnOpenidUri+"/web/images/defaultHead/" + (int) (Math.random() * 20 + 1)+".png");
		
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
		//u.setSmallHeadPhoto(returnOpenidUri+"/web/images/defaultHead/" + (int) (Math.random() * 20 + 1)+".png");
		u.setSmallHeadPhoto(returnOpenidUri+"/web/images/defaultHead/yx_mr.png");
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
	
}
