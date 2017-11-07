//package com.xczhihui.bxg.user.center.controller;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.xczhihui.bxg.user.center.service.UserCenterAPI;
//import com.xczhihui.bxg.user.center.util.UserCenterResult;
//import com.xczhihui.user.center.bean.Token;
//import com.xczhihui.user.center.bean.TokenExpires;
//import com.xczhihui.user.center.bean.UserOrigin;
//import com.xczhihui.user.center.bean.UserSex;
//import com.xczhihui.user.center.bean.UserStatus;
//import com.xczhihui.user.center.bean.UserType;
///**
// * 用户中心
// * @author Haicheng Jiang
// */
//@RestController
//@RequestMapping("/user")
//public class UserCenterController  {
//	@Autowired
//	private UserCenterAPI service;
//	/**
//	 * 注册一个用户，会对明文密码MD5；password属性必须是明文。
//	 * @param loginName
//	 * @param password
//	 * @param nikeName
//	 * @param sex
//	 * @param email
//	 * @param mobile
//	 * @param type 用户类型，UserType.COMMON/UserType.STUDENT/UserType.TEACHER
//	 * @param origin 用户来源(从什么地方注册)，UserOrigin.BXG/UserType.ASK……
//	 * @param status 0正常（对应isDelete=false），-1禁用（对应isDelete=true）
//	 * @return
//	 */
//	@RequestMapping(value = "regist",method=RequestMethod.POST)
//    public UserCenterResult regist(
//			String loginName,
//			String password,
//			String nikeName,
//			Integer sex,
//			String email,
//			String mobile,
//			Integer type,
//			String origin,
//			Integer status){
//		try {
//			Token token = service.regist(loginName, password, nikeName, UserSex.parse(sex), email, mobile, 
//					UserType.parse(type), UserOrigin.valueOf(origin), UserStatus.parse(status));
//			return UserCenterResult.successResult(token);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return UserCenterResult.failResult(e.getMessage());
//		}
//    }
//	/**
//	 * 物理删除用户
//	 * @param loginName
//	 * @return
//	 */
//	@RequestMapping(value = "deleteUser",method=RequestMethod.POST)
//	public UserCenterResult deleteUser(String loginName){
//		try {
//			return UserCenterResult.successResult(service.deleteUser(loginName));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 逻辑删除用户
//	 * @param loginName
//	 * @return
//	 */
//	@RequestMapping(value = "deleteUserLogic",method=RequestMethod.POST)
//	public UserCenterResult deleteUserLogic(String loginName){
//		try {
//			return UserCenterResult.successResult(service.deleteUserLogic(loginName));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 更新用户
//	 * @param loginName
//	 * @param nikeName
//	 * @param sex
//	 * @param email
//	 * @param mobile
//	 * @param type
//	 * @param status
//	 * @return
//	 */
//	@RequestMapping(value = "update",method=RequestMethod.POST)
//	public UserCenterResult update(String loginName,String nikeName,int sex,String email,String mobile,int type,int status){
//		try {
//			return UserCenterResult.successResult(service.update(loginName, nikeName, sex, email, mobile, type, status));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 修改用户中心用户状态
//	 * @param loginName
//	 * @param status 0正常，-1禁用，详见#UserStatus
//	 * @return
//	 */
//	@RequestMapping(value = "updateStatus",method=RequestMethod.POST)
//	public UserCenterResult updateStatus(String loginName,int status){
//		try {
//			service.updateStatus(loginName, status);
//			return UserCenterResult.successResult("操作成功！");
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 修改密码；密码值必须是明文。如果是重置密码，oldPassword可以不传，传了会校验原密码是否正确。
//	 * @param loginName
//	 * @param oldPassword  原密码
//	 * @param newPassword  新密码
//	 * @return
//	 */
//	@RequestMapping(value = "updatePassword",method=RequestMethod.POST)
//	public UserCenterResult updatePassword(String loginName,String oldPassword, String newPassword){
//		try {
//			service.updatePassword(loginName, oldPassword, newPassword);
//			return UserCenterResult.successResult("操作成功！");
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 修改登录名。
//	 * @param oldLoginName 原来的登录名。
//	 * @param newLoginName  新的登录名。
//	 * @return
//	 */
//	@RequestMapping(value = "updateLoginName",method=RequestMethod.POST)
//	public UserCenterResult updateLoginName(String oldLoginName, String newLoginName){
//		try {
//			service.updateLoginName(oldLoginName, newLoginName);
//			return UserCenterResult.successResult("操作成功！");
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 登录
//	 * @param loginName 登录名
//	 * @param password 明文密码
//	 * @return
//	 */
//	@RequestMapping(value = "login",method=RequestMethod.POST)
//    public UserCenterResult login(String loginName, String password){
//		try {
//			return UserCenterResult.successResult(service.login(loginName, password));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 根据ID获取一个用户
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value = "getUserById",method=RequestMethod.POST)
//	public UserCenterResult getUserById(int id){
//		try {
//			return UserCenterResult.successResult(service.getUser(id));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 根据ID获取一批用户
//	 * @param ids
//	 * @return
//	 */
//	@RequestMapping(value = "getUsersByIds",method=RequestMethod.POST)
//	public UserCenterResult getUsersByIds(String ids){
//		try {
//			Set<Integer> ids_ = new HashSet<Integer>();
//			if (StringUtils.hasText(ids)) {
//				String[] idsarray = ids.split(",");
//				for (String id : idsarray) {
//					ids_.add(Integer.valueOf(id));
//				}
//			}
//			return UserCenterResult.successResult(service.getUsersByIds(ids_));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 根据登录名查找用户
//	 * @param loginName
//	 * @return
//	 */
//	@RequestMapping(value = "getUserByLoginName",method=RequestMethod.POST)
//	public UserCenterResult getUserByLoginName(String loginName){
//		try {
//			return UserCenterResult.successResult(service.getUser(loginName));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 根据登录名获取一批用户
//	 * @param loginNames
//	 * @return
//	 */
//	@RequestMapping(value = "getUsersByLoginNames",method=RequestMethod.POST)
//	public UserCenterResult getUsersByLoginNames(String loginNames){
//		try {
//			Set<String> ids_ = new HashSet<String>();
//			if (StringUtils.hasText(loginNames)) {
//				String[] nameArray = loginNames.split(",");
//				for (String name : nameArray) {
//					ids_.add(name);
//				}
//			}
//			return UserCenterResult.successResult(service.getUsersByLoginNames(ids_));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 延长票的有效期，一天。
//	 * @param ticket
//	 * @return
//	 */
//	@RequestMapping(value = "reflushTicketDay",method=RequestMethod.POST)
//	public UserCenterResult reflushTicketDay(String ticket){
//		try {
//			return UserCenterResult.successResult(service.reflushTicket(ticket,TokenExpires.Day));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 延长票的有效期，一小时。
//	 * @return
//	 */
//	@RequestMapping(value = "reflushTicketHour",method=RequestMethod.POST)
//	public UserCenterResult reflushTicketHour(String ticket){
//		try {
//			return UserCenterResult.successResult(service.reflushTicket(ticket,TokenExpires.Hour));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 校验票是否有效
//	 * @param ticket
//	 * @return ticket无效返回NULL
//	 */
//	@RequestMapping(value = "validateTicket",method=RequestMethod.POST)
//	public UserCenterResult validateTicket(String ticket){
//		try {
//			return UserCenterResult.successResult(service.validateTicket(ticket));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//	/**
//	 * 销毁票
//	 * @param ticket
//	 * @return true:销毁成功；false:票无效。
//	 */
//	@RequestMapping(value = "destoryTicket",method=RequestMethod.POST)
//	public UserCenterResult destoryTicket(String ticket){
//		try {
//			return UserCenterResult.successResult(service.destoryTicket(ticket));
//		} catch (Exception e) {
//			return UserCenterResult.failResult(e.getMessage());
//		}
//	}
//}
