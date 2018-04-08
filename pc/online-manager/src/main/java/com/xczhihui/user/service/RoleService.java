package com.xczhihui.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xczhihui.bxg.online.common.domain.Role;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.PageVo;

/**
 * 用户角色
 * 
 * @author Haicheng Jiang
 *
 */
public interface RoleService {

	/**
	 * 获取所有的有效的角色
	 * 
	 * @return
	 */
	public List<Role> getAllValidRoles();

	/**
	 * 添加角色
	 * 
	 * @param role
	 */
	public void addRole(Role role);

	/**
	 * 删除角色
	 * 
	 * @param role
	 */
	public void deleteRole(Role role);

	/**
	 * 给用户添加一个角色
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void addUserRole(String userId, String roleId);

	/**
	 * 删除用户的一个角色
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void deleteUserRole(String userId, String roleId);

	/**
	 * 根据用户id获取用户拥有的角色代码。
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> findRoleCodes(String userId);
	
	/**
	 * 根据角色代码查找角色
	 * 
	 * @param code
	 * @return
	 */
	public Role getRoleByCode(String code);

	/**
	 * 获取一批用户的角色信息
	 * 
	 * @param userIds
	 * @return
	 */
	public Map<String, Set<String>> getUserRoles(Set<String> userIds);

	/**
	 * 获取一个角色信息
	 * 
	 * @param id
	 * @return
	 */
	public Role getRole(String id);

	/**
	 * 根据ID删除一个角色
	 * 
	 * @param id
	 */
	public void deleteRole(String id);

	/**
	 * 根据ID软删除一个角色
	 * 
	 * @param id
	 */
	public void deleteRoleLogic(String id);

	/**
	 * 批量删除角色
	 * 
	 * @param ids
	 */
	public void deleteRoles(List<String> ids);

	/**
	 * 批量软删除角色
	 * 
	 * @param ids
	 */
	public void deleteRolesLogic(List<String> ids);

	/**
	 * 更新角色信息
	 * 
	 * @param role
	 */
	public void updateRole(Role role);

	/**
	 * 获取所有的角色，包括无效的。
	 * 
	 * @return
	 */
	public List<Role> getAllRoles();

	/**
	 * 根据条件分页获取角色信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public PageVo findPageByGroups(Groups groups, PageVo pageVo);

	/**
	 * 更新一批用户的角色信息，删除原来的角色信息，添加新的角色信息。如果roleIds是null表示删除用户的角色信息。
	 * 
	 * @param userIds
	 * @param roleIds
	 */
	public void updateUserRoles(Set<String> userIds, Set<String> roleIds);

	/**
	 * 修改角色的权限。
	 * 
	 * @param roleId
	 * @param resourcesIds
	 *            null表示删除角色的权限信息。
	 */
	public void updateRoleResources(String roleId, Set<String> resourcesIds);
	
	/**
	 * 获取一个用户的角色的ID
	 * @return
	 */
	public Set<String> getUserRoleIds(String userId);
}
