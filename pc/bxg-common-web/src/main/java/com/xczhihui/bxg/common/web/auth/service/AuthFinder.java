package com.xczhihui.bxg.common.web.auth.service;

import java.util.Set;

import com.xczhihui.bxg.common.support.domain.BxgUser;

/**
 * 给认证和授权提供接口。
 * 
 * @author liyong
 *
 */
public interface AuthFinder {

	/**
	 * 根据登录名查找用户，业务系统可以返回从BxgUser继承的用户类。
	 * 
	 * @param loginName
	 * @return
	 */
	public BxgUser findUserByLoginName(String loginName);

	/**
	 * 查找用户拥有的角色
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> findRoles(String userId);

	/**
	 * 查找用户拥有的权限
	 * 
	 * @return
	 */
	public Set<String> findPermissions(String userId);

}
