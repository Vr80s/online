package com.xczhihui.bxg.common.web.auth.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.auth.bean.UCenterUser;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.ItcastUser;

/**
 * 基于用户中心的角色、权限实现。
 * 
 * @author liyong
 *
 */
public class UCenterAuthFinder implements AuthFinder {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserCenterAPI userCenterAPI;

	public Set<String> findRoles(String userId) {
		// TODO 从用户中心获取用户角色
		return new HashSet<>();
	}

	public Set<String> findPermissions(String userId) {
		// TODO 从用户中心获取用户权限
		return new HashSet<>();
	}

	@Override
	public BxgUser findUserByLoginName(String loginName) {
		ItcastUser u = this.userCenterAPI.getUser(loginName);
		BxgUser user = null;
		if (u != null) {
			user = UCenterUser.valueOf(u);
		}
		return user;
	}

}
