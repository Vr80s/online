package com.xczhihui.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xczhihui.user.service.PermResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.xczhihui.common.support.domain.SystemVariate;
import com.xczhihui.common.support.service.SystemVariateService;
import com.xczhihui.bxg.online.common.domain.Resource;
import com.xczhihui.user.dao.PermResourceDao;
import com.xczhihui.user.dao.RoleDao;
import com.xczhihui.user.service.ResourceTreeHelper;
import com.xczhihui.utils.ZtreeVo;

@Service("permResourceService")
public class PermResourceServiceImpl implements PermResourceService {

	private PermResourceDao resourceDao;

	private RoleDao roleDao;

	private SystemVariateService systemVariateService;

	@Override
	public void addResource(Resource resource) {
		Resource res = this.resourceDao.findResource(resource.getType(),
				resource.getPermission());
		if (res != null) {
			throw new IllegalArgumentException("权限代码+类型不能重复");
		}
		resource.setCreateTime(new Date());
		this.resourceDao.save(resource);
	}

	@Override
	public void deleteResource(Resource resource) {
		this.resourceDao.delete(resource);
		this.resourceDao.deleteRoleResources(resource.getId());
	}

	@Override
	public void addRoleResource(String roleId, String resourceId) {
		this.resourceDao.addRoleResource(roleId, resourceId);
	}

	@Override
	public void deleteRoleResource(String roleId, String resourceId) {
		this.resourceDao.deleteRoleResource(roleId, resourceId);
	}

	@Override
	public Set<String> findPermissions(String userId) {
		Set<String> roleIds = this.roleDao.getRoleIds(userId);
		return this.resourceDao.getResourcePermissions(roleIds);
	}

	@Override
	public List<Resource> findResourcesByType(String type) {
		return this.resourceDao.findEntitiesByProperty(Resource.class, "type",
				type);
	}

	@Override
	public List<Resource> findPermitResources(String userId, String type) {
		Set<String> perms = this.findPermissions(userId);
		Set<String> types = new HashSet<>();
		types.add(type);
		List<Resource> resources = this.resourceDao.findResources(types, perms);
		return resources;
	}

	@Override
	public void addResources(Resource resource) {
		resource.setCreateTime(new Date());
		this.resourceDao.save(resource);
	}

	void addDefaultChild(Resource resource) {
		Resource updateRes = new Resource();
		updateRes.setCreatePerson(resource.getCreatePerson());
		updateRes.setCreateTime(resource.getCreateTime());
		updateRes.setDelete(resource.isDelete());
		updateRes.setDisplayOrder(0);
		updateRes.setParentId(resource.getId());
		updateRes.setName("修改" + resource.getName());
		updateRes.setDescription("修改" + resource.getDescription());
		updateRes.setPermission(resource.getPermission() + ":update");
		this.resourceDao.save(updateRes);

		Resource queryRes = new Resource();
		queryRes.setCreatePerson(resource.getCreatePerson());
		queryRes.setCreateTime(resource.getCreateTime());
		queryRes.setDelete(resource.isDelete());
		queryRes.setDisplayOrder(0);
		queryRes.setParentId(resource.getId());
		queryRes.setName("查询" + resource.getName());
		queryRes.setDescription("查询" + resource.getDescription());
		queryRes.setPermission(resource.getPermission() + ":query");
		this.resourceDao.save(queryRes);
	}

	@Override
	public void updateResource(Resource resource) {
		Resource old = this.resourceDao.get(resource.getId(), Resource.class);

		old.setDescription(resource.getDescription());
		old.setDisplayOrder(resource.getDisplayOrder());
		old.setName(resource.getName());
		old.setParentId(resource.getParentId());
		old.setPermission(resource.getPermission());
		old.setType(resource.getType());
		old.setUrl(resource.getUrl());
		old.setIcon(resource.getIcon());

		if (old.isDelete() != resource.isDelete()) {
			old.setDelete(resource.isDelete());
			if (old.isDelete()) {
				old.setCreateTime(new Date());
			}

			updateIsDelete(old.isDelete(), old.getId(), old.getCreateTime());

			if (!old.isDelete()) {
				updateParent(old.getParentId());
			}

		}

		this.resourceDao.update(old);
	}

	/**
	 * 级联修改父级为启用状态。
	 * 
	 * @param id
	 *            当前权限的id,根据此id修改父类的状态
	 * @return
	 */
	private void updateParent(String id) {

		Resource r = resourceDao.get(id, Resource.class);
		if (r != null && r.isDelete()) {
			r.setDelete(false);
			this.resourceDao.update(r);
			if (r.getParentId() != null) {
				updateParent(r.getParentId());
			}
		}

	}

	/**
	 * 级联修改 禁用启用状态。
	 * 
	 * @param isDelete
	 *            状态
	 * @param id
	 *            当前资源的id,根据此id修改子类的状态
	 */
	private void updateIsDelete(boolean isDelete, String id, Date updateTime) {
		List<String> ids = null;
		if (isDelete) {
			ids = resourceDao.getValidChildIds(id);

			if (CollectionUtils.isEmpty(ids)) {
				return;
			}

			resourceDao.updateDelete(ids, 1, updateTime);
			resourceDao.deleteLogics(Resource.class, ids);
		} else {
			ids = resourceDao.getChildIdsByTime(id, updateTime);
			if (CollectionUtils.isEmpty(ids)) {
				return;
			}
			resourceDao.updateDelete(ids, 0, updateTime);
		}

		if (!CollectionUtils.isEmpty(ids)) {
			for (String childId : ids) {
				updateIsDelete(isDelete, childId, updateTime);
			}
		}

	}

	@Override
	public void deleteResources(List<String> ids) {
		if (ids == null || ids.size() < 1) {
			return;
		}
		for (String id : ids) {
			this.resourceDao.delete(id, Resource.class);
			this.resourceDao.deleteRoleResources(id);
			this.deleteAllChild(id);
		}
	}

	private void deleteAllChild(String id) {
		List<String> ids = this.resourceDao.getChildIds(id);
		if (ids != null && ids.size() > 0) {
			for (String pid : ids) {
				this.deleteAllChild(pid);
				this.resourceDao.delete(pid, Resource.class);
				this.resourceDao.deleteRoleResources(pid);
			}
		}
	}

	@Override
	public List<Resource> getHierarchyResources(String type, Boolean isValid) {
		List<Resource> resources = this.resourceDao
				.findResources(type, isValid);
		this.popResourceDesc(resources);
		return ResourceTreeHelper.genHierarchyTree(resources);
	}

	@Override
	public List<SystemVariate> getResourceTypes() {
		return this.systemVariateService
				.getSystemVariatesByName("resource_type");
	}

	@Override
	public boolean isUsing(String resourceId) {
		List<String> roleIds = this.resourceDao.findRoleIds(resourceId);
		if (roleIds == null || roleIds.size() < 1) {
			return false;
		}
		return true;
	}

	@Override
	public List<ZtreeVo> getResourcesTree() {
		List<ZtreeVo> ztreeVos = new ArrayList<ZtreeVo>();
		List<Resource> resources = this.getHierarchyResources(null, true);
		if (resources != null) {
			for (Resource resource : resources) {
				ZtreeVo ztreeVo = this.constructionZtreeVo(resource, false);
				ztreeVos.add(ztreeVo);
			}
		}
		return ztreeVos;
	}

	@Override
	public List<ZtreeVo> findRoleResourceTree(String roleId) {
		List<ZtreeVo> ztreeVos = new ArrayList<ZtreeVo>();
		List<Resource> resources = this.findRoleResources(roleId);
		if (resources != null) {
			for (Resource resource : resources) {
				ZtreeVo ztreeVo = this.constructionZtreeVo(resource, true);
				ztreeVos.add(ztreeVo);
			}
		}
		return ztreeVos;
	}

	private List<Resource> findRoleResources(String roleId) {
		List<String> resIds = this.resourceDao.findRoleResourceIds(roleId);
		if (resIds == null || resIds.size() < 1) {
			return null;
		}
		List<Resource> resources = this.resourceDao
				.findValidResources(new HashSet<>(resIds));
		return ResourceTreeHelper.genHierarchyTree(resources);
	}

	private ZtreeVo constructionZtreeVo(Resource resource, boolean nocheck) {
		ZtreeVo ztreeVo = new ZtreeVo();
		ztreeVo.setId(resource.getId());
		ztreeVo.setName(resource.getName());
		if (StringUtils.hasText(resource.getParentId())) {
			ztreeVo.setIsParent(false);
		} else {
			ztreeVo.setIsParent(true);
		}
		ztreeVo.setOpen(true);
		ztreeVo.setNocheck(nocheck);

		List<Resource> children = resource.getChildren();
		if (children != null) {
			List<ZtreeVo> ztreeChildren = new ArrayList<ZtreeVo>();
			for (Resource res : children) {
				ZtreeVo z = this.constructionZtreeVo(res, nocheck);
				ztreeChildren.add(z);
			}
			ztreeVo.setChildren(ztreeChildren);
		}
		return ztreeVo;
	}

	private void popResourceDesc(List<Resource> resources) {
		Map<String, String> vars = this.getResourceTypeDescMap();
		if (resources != null && resources.size() > 0) {
			for (Resource res : resources) {
				String desc = vars.get(res.getType());
				res.setTypeDesc(desc);
			}
		}
	}

	private Map<String, String> getResourceTypeDescMap() {
		List<SystemVariate> vars = this.getResourceTypes();
		Map<String, String> map = new HashMap<>();
		for (SystemVariate var : vars) {
			map.put(var.getValue(), var.getDescription());
		}
		return map;
	}

	@Autowired
	public void setSystemVariateService(
			SystemVariateService systemVariateService) {
		this.systemVariateService = systemVariateService;
	}

	@Autowired
	public void setResourceDao(PermResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
