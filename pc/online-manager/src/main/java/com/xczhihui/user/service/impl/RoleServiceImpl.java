package com.xczhihui.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xczhihui.utils.Groups;
import com.xczhihui.utils.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.domain.Role;
import com.xczhihui.user.dao.RoleDao;
import com.xczhihui.user.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;

	@Override
	public List<Role> getAllValidRoles() {
		return this.roleDao.findEntitiesByProperty(Role.class, "isDelete",
				false);
	}

	@Override
	public void addRole(Role role) {
		role.setCreateTime(new Date());
		this.roleDao.save(role);
	}

	@Override
	public void deleteRole(Role role) {
		this.roleDao.delete(role);
		this.roleDao.deleteUserRolesByRoleId(role.getId());
	}

	@Override
	public void addUserRole(String userId, String roleId) {
		this.roleDao.addUserRole(userId, roleId);
	}

	@Override
	public void deleteUserRole(String userId, String roleId) {
		this.roleDao.deleteUserRole(userId, roleId);
	}

	@Override
	public Set<String> findRoleCodes(String userId) {
		return this.roleDao.getRoleCodes(userId);
	}

	@Override
	public Role getRoleByCode(String code) {
		return this.roleDao.findOneEntitiyByProperty(Role.class, "code", code);
	}

	@Override
	public Map<String, Set<String>> getUserRoles(Set<String> userIds) {
		Map<String, Set<String>> userRoles = new HashMap<>();
		if (userIds != null && userIds.size() > 0) {
			List<Map<String, Object>> roles = this.roleDao
					.getUserRoles(userIds);
			for (Map<String, Object> role : roles) {
				String uId = role.get("userId").toString();
				// String rId = role.get("roleId").toString();
				String rDesc = role.get("roleName").toString();
				Set<String> rs = userRoles.get(uId);
				if (rs == null) {
					rs = new HashSet<>();
					userRoles.put(uId, rs);
				}
				rs.add(rDesc);
			}
		}
		return userRoles;
	}

	@Override
	public Role getRole(String id) {
		return this.roleDao.get(id, Role.class);
	}

	@Override
	public void deleteRoleLogic(String id) {
		this.roleDao.deleteLogic(id, Role.class);
	}

	@Override
	public void deleteRole(String id) {
		this.roleDao.delete(id, Role.class);
		this.updateRoleResources(id, null);
		this.roleDao.deleteUserRolesByRoleId(id);
	}

	@Override
	public void deleteRolesLogic(List<String> ids) {
		this.roleDao.deleteLogics(Role.class, ids);
	}

	@Override
	public void deleteRoles(List<String> ids) {
		if (ids == null || ids.size() < 1) {
			return;
		}
		for (String id : ids) {
			this.deleteRole(id);
			this.updateRoleResources(id, null);
		}
	}

	@Override
	public void updateRole(Role role) {
		Role oldRole = this.roleDao.get(role.getId(), Role.class);
		this.checkRoleCode(oldRole, role);
		oldRole.setDelete(role.isDelete());
		oldRole.setName(role.getName());
		oldRole.setDescription(role.getDescription());
		this.roleDao.update(oldRole);
	}

	private void checkRoleCode(Role oldRole, Role role) {
		if (!oldRole.getCode().equals(role.getCode())) {
			Role r = this.getRoleByCode(role.getCode());
			if (r != null) {
				throw new IllegalArgumentException("角色代码已经存在");
			}
		}
	}

	@Override
	public List<Role> getAllRoles() {
		return this.roleDao.getAll(Role.class);
	}

	@Override
	public PageVo findPageByGroups(Groups groups, PageVo page) {
		String sql = "select id,is_delete as isDelete,create_person as createPerson,create_time as createTime,name,description,code from role";
		page = roleDao.findPageByGroups(groups, page, sql);
		return page;
	}

	@Override
	public void updateUserRoles(Set<String> userIds, Set<String> roleIds) {
		this.roleDao.deleteUserRoles(userIds);
		if (roleIds != null && roleIds.size() > 0) {
			this.roleDao.addUserRoles(userIds, roleIds);
		}
	}

	@Override
	public void updateRoleResources(String roleId, Set<String> resourceIds) {
		this.roleDao.deleteRoleResources(roleId);
		if (resourceIds != null && resourceIds.size() > 0) {
			this.roleDao.addRoleResources(roleId, resourceIds);
		}
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public Set<String> getUserRoleIds(String userIds) {
		Set<String> rs = new HashSet<String>();
		Set<String> s1 = new HashSet<String>();
		s1.add(userIds);
		List<Map<String, Object>> roles = this.roleDao.getUserRoles(s1);
		for (Map<String, Object> role : roles) {
			String rId = role.get("roleId").toString();
			rs.add(rId);
		}
		return rs;
	}
}
