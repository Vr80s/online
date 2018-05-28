package com.xczhihui.user.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xczhihui.common.dao.HibernateDao;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.online.common.domain.Role;

/**
 * 角色DAO
 * 
 * @author Haicheng Jiang
 *
 */
@Repository("roleDao")
public class RoleDao extends HibernateDao<Role> {

	/**
	 * 获取用户拥有的角色ID
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> getRoleIds(String userId) {
		String sql = "select r.id from user_role ur, role r "
				+ "where ur.role_id=r.id and ur.user_id=?";
		List<String> roles = this.getNamedParameterJdbcTemplate()
				.getJdbcOperations().queryForList(sql, String.class, userId);
		return new HashSet<String>(roles);
	}

	/**
	 * 获取用户拥有的角色代码。
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> getRoleCodes(String userId) {
		String sql = "select r.code from user_role ur, role r "
				+ "where ur.role_id=r.id and ur.user_id=?";
		List<String> roles = this.getNamedParameterJdbcTemplate()
				.getJdbcOperations().queryForList(sql, String.class, userId);
		return new HashSet<String>(roles);
	}

	/**
	 * 添加用户和角色的关联
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void addUserRole(String userId, String roleId) {
		String sql = "insert into user_role(user_id, role_id) values(?, ?)";
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, userId, roleId);
	}

	/**
	 * 删除用户和角色的关联
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void deleteUserRole(String userId, String roleId) {
		String sql = "delete from user_role where user_id=? and role_id=?";
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, userId, roleId);
	}

	/**
	 * 根据角色ID删除用户角色关联。
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteUserRolesByRoleId(String roleId) {
		String sql = "delete from user_role where role_id=?";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, roleId);
	}

	/**
	 * 返回一批用户的角色信息。
	 * 
	 * @param userIds
	 * @return
	 */
	public List<Map<String, Object>> getUserRoles(Set<String> userIds) {
		if (userIds == null || userIds.size() < 1) {
			return new ArrayList<>();
		}
		String sql = "select ur.user_id userId, r.id roleId, r.name roleName, r.code roleCode,"
				+ "r.description roleDescription from user_role ur, role r "
				+ "where ur.role_id=r.id and ur.user_id in (:userIds)";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("userIds", userIds);
		return this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
	}

	/**
	 * 删除一批用户的角色信息。
	 * 
	 * @param userIds
	 * @return
	 */
	public int deleteUserRoles(Set<String> userIds) {
		String sql = "delete from user_role where user_id in (:userIds)";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("userIds", userIds);
		return this.getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	/**
	 * 给一批用户加上同一组角色。
	 * 
	 * @param userIds
	 * @param roleIds
	 */
	public void addUserRoles(Set<String> userIds, Set<String> roleIds) {
		if (userIds == null || userIds.size() < 1 || roleIds == null
				|| roleIds.size() < 1) {
			return;
		}
		String sql = "insert into user_role(user_id, role_id) values(?, ?)";
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (String uid : userIds) {
			for (String rid : roleIds) {
				Object[] args = new Object[] { uid, rid };
				batchArgs.add(args);
			}
		}
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.batchUpdate(sql, batchArgs);
	}

	/**
	 * 删除角色对应的权限资源
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteRoleResources(String roleId) {
		String sql = "delete from role_resource where role_id=?";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, roleId);
	}

	/**
	 * 给一个角色添加一批权限资源。
	 * 
	 * @param roleId
	 * @param resourceIds
	 */
	public void addRoleResources(String roleId, Set<String> resourceIds) {
		if (!StringUtils.hasText(roleId) || resourceIds == null
				|| resourceIds.size() < 1) {
			return;
		}
		String sql = "insert into role_resource(role_id, resource_id) values(?, ?)";
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for (String rid : resourceIds) {
			Object[] args = new Object[] { roleId, rid };
			batchArgs.add(args);
		}
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.batchUpdate(sql, batchArgs);
	}
}
