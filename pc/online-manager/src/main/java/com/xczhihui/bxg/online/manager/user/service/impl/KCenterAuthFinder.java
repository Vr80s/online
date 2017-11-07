package com.xczhihui.bxg.online.manager.user.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xczhihui.bxg.common.web.auth.service.AuthFinder;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.user.service.PermResourceService;
import com.xczhihui.bxg.online.manager.user.service.RoleService;
import com.xczhihui.bxg.online.manager.user.service.UserService;

/**
 * 
 * @author Haicheng Jiang
 *
 */
public class KCenterAuthFinder implements AuthFinder {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private RoleService roleService;

	private PermResourceService resourceService;

	private UserService userService;

	public Set<String> findRoles(String userId) {
		return this.roleService.findRoleCodes(userId);
	}

	public Set<String> findPermissions(String userId) {
		return this.resourceService.findPermissions(userId);
	}

	@Override
	public User findUserByLoginName(String loginName) {
		User user = this.userService.getUserByLoginName(loginName);
		String msg = user != null ? "and found" : "but not found";
		logger.info("{} user center login success, {} kcenter user!", loginName, msg);
		return user;
	}

	@Autowired
	public void setTeacherService(UserService teacherService) {
		this.userService = teacherService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setResourceService(PermResourceService resourceService) {
		this.resourceService = resourceService;
	}
}
