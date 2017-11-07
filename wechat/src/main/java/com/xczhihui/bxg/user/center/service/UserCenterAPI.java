package com.xczhihui.bxg.user.center.service;

import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczhihui.user.center.bean.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户中心API
 * @author Haicheng Jiang
 */
public interface UserCenterAPI {
	/**
	 * 注册一个用户，会对明文密码MD5；password属性必须是明文。
	 * @param loginName
	 * @param password
	 * @param nikeName
	 * @param sex
	 * @param email
	 * @param mobile
	 * @param type 用户类型，UserType.COMMON/UserType.STUDENT/UserType.TEACHER
	 * @param origin 用户来源(从什么地方注册)，UserOrigin.BXG/UserType.ASK……
	 * @param status 0正常（对应isDelete=false），-1禁用（对应isDelete=true）
	 * @return
	 */
	public Token regist(
            String loginName,
            String password,
            String nikeName,
            UserSex sex,
            String email,
            String mobile,
            UserType type,
            UserOrigin origin,
            UserStatus status);


	/**
	 * 批量导入的时候调用，批量增加用户
	 * @param users
	 */
	public void registBatch(List<List<Map<String, Object>>> users);

	/**
	 * 物理删除用户
	 * @param loginName
	 * @return 返回被删除的用户，如果没有找到对应的用户，返回NULL
	 */
	public ItcastUser deleteUser(String loginName);

	/**
	 * 逻辑删除用户
	 * @param loginName
	 * @return 返回被删除的用户，如果没有找到对应的用户，返回NULL
	 */
	public ItcastUser deleteUserLogic(String loginName);
	/**
	 * 更新用户信息；不会更新登录名、密码、用户来源（origin）。
	 * @param loginName
	 * @param nikeName
	 * @param sex
	 * @param email
	 * @param mobile
	 * @param type 用户类型，UserType.COMMON/UserType.STUDENT/UserType.TEACHER
	 * @param status 0正常（对应isDelete=false），-1禁用（对应isDelete=true）
	 * @return
	 */
	public ItcastUser update(String loginName, String nikeName, int sex, String email, String mobile, int type, int status);

	/**
	 * 修改用户中心用户状态
	 * @param loginName
	 * @param status 0正常，-1禁用，详见#UserStatus
	 */
	public void updateStatus(String loginName, int status);

	/**
	 * 修改密码；密码值必须是明文。如果是重置密码，oldPassword可以不传，传了会校验原密码是否正确。
	 * @param loginName
	 * @param oldPassword  原密码
	 * @param newPassword  新密码
	 * @return
	 */
	public void updatePassword(String loginName, String oldPassword, String newPassword);
	/**
	 * 修改登录名。
	 * @param oldLoginName 原来的登录名。
	 * @param newLoginName  新的登录名。
	 */
	public void updateLoginName(String oldLoginName, String newLoginName);
	/**
	 * 登录
	 * @param loginName 登录名
	 * @param password 明文密码
	 * @param tokenExpires 过期时间类型。
	 * @return
	 */
	public Token login(String loginName, String password, TokenExpires tokenExpires);
	/**
	 * 登录
	 * @param loginName 登录名
	 * @param password 明文密码
	 * @return
	 */
	public Token login(String loginName, String password);
	/**
	 * 根据ID获取一个用户
	 * @param id
	 * @return
	 */
	public ItcastUser getUser(int id);
	/**
	 * 根据ID获取一批用户
	 * @param ids
	 * @return
	 */
	public List<ItcastUser> getUsersByIds(Set<Integer> ids);
	/**
	 * 根据登录名查找用户
	 * @param loginName
	 * @return
	 */
	public ItcastUser getUser(String loginName);
	/**
	 * 根据登录名获取一批用户
	 * @param loginNames
	 * @return
	 */
	public List<ItcastUser> getUsersByLoginNames(Set<String> loginNames);
	/**
	 * 查询用户
	 * @param vo
	 * @return
	 */
	public TableVo getUsers(TableVo vo);
	/**
	 * 延长票的有效期。
	 * @param ticket
	 * @param tokenExpires 过期时间类型。
	 * @return
	 */
	public Token reflushTicket(String ticket, TokenExpires tokenExpires);
	/**
	 * 延长票的有效期。
	 * @param ticket
	 * @return
	 */
	public Token reflushTicket(String ticket);
	/**
	 * 校验票是否有效
	 * @param ticket
	 * @return ticket无效返回NULL
	 */
	public Token validateTicket(String ticket);
	/**
	 * 销毁票
	 * @param ticket
	 * @return true:销毁成功；false:票无效。
	 */
	public boolean destoryTicket(String ticket);

	public Token wechatLogin(String loginName) throws Exception;
	
	Token loginThirdPart(String loginName, String password,
						 TokenExpires tokenExpires) throws Exception;


	public boolean checkPassword(String loginName,String password);
}
