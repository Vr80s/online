package com.xczhihui.bxg.online.manager.user.service;

import java.util.List;
import java.util.Set;

import com.xczhihui.bxg.common.support.domain.SystemVariate;
import com.xczhihui.bxg.online.common.domain.Resource;
import com.xczhihui.bxg.online.manager.utils.ZtreeVo;

/**
 * 资源和权限接口
 * 
 * @author Haicheng Jiang
 *
 */
public interface PermResourceService {

	/**
	 * 添加一个资源(权限)
	 * 
	 * @param resource
	 */
	public void addResource(Resource resource);

	/**
	 * 删除一个资源(权限)
	 * 
	 * @param resource
	 */
	public void deleteResource(Resource resource);

	/**
	 * 给一个角色添加资源(权限)
	 * 
	 * @param roleId
	 * @param resourceId
	 */
	public void addRoleResource(String roleId, String resourceId);

	/**
	 * 删除角色的一个资源(权限)
	 * 
	 * @param roleId
	 * @param resourceId
	 */
	public void deleteRoleResource(String roleId, String resourceId);

	/**
	 * 根据用户id获取用户拥有的权限字符串(resoruce:perm，如：question:list)。
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> findPermissions(String userId);

	/**
	 * 根据类型查找资源(权限)
	 * 
	 * @param type
	 *            如：菜单，按钮等。
	 * @return
	 */
	public List<Resource> findResourcesByType(String type);
	
	/**
	 * 根据类型，查询用户拥有权限的资源。
	 * 
	 * @param userId
	 * @param type
	 *            如：后台菜单，按钮等。
	 * @return
	 */
	public List<Resource> findPermitResources(String userId, String type);
	
	/**
	 * 判断权限使用是否在使用。
	 * 
	 * @param resourceId
	 * @return
	 */
	public boolean isUsing(String resourceId);

	/**
	 * 修改资源信息
	 * 
	 * @param resource
	 */
	public void updateResource(Resource resource);

	/**
	 * 批量删除一批资源
	 * 
	 * @param ids
	 */
	public void deleteResources(List<String> ids);

	/**
	 * 添加权限资源，及相对应的缺省子资源。
	 * 
	 * @param resource
	 * @param actions
	 */
	public void addResources(Resource resource);

	/**
	 * 获取层级关系的资源列表，组织成树状结构。
	 * 
	 * @param type
	 *            资源类型，null表示所有
	 * @param isValid
	 *            是否只要有效的资源，null表示所有
	 * @return
	 */
	public List<Resource> getHierarchyResources(String type, Boolean isValid);

	/**
	 * 获取权限资源相关的系统变量(数据字典)。
	 * 
	 * @return
	 */
	public List<SystemVariate> getResourceTypes();

	/**
	 * 将所有有效的权限资源，组装成tree。
	 * 
	 * @return
	 */
	public List<ZtreeVo> getResourcesTree();

	/**
	 * 将角色拥有的权限资源，组装成tree。
	 * 
	 * @return
	 */
	public List<ZtreeVo> findRoleResourceTree(String roleId);
}
