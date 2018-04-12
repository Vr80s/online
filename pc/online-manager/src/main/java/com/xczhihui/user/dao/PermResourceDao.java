package com.xczhihui.user.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.Resource;

/**
 * 资源(权限)DAO
 * 
 * @author Haicheng Jiang
 */
@Repository("permResourceDao")
public class PermResourceDao extends SimpleHibernateDao {

	/**
	 * 删除权限关联的资源。
	 * 
	 * @param resourceId
	 * @return
	 */
	public int deleteRoleResources(String resourceId) {
		String sql = "delete from role_resource where resource_id = ?";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, resourceId);
	}

	/**
	 * 根据类型和权限代码查资源。
	 * 
	 * @param type
	 * @param permission
	 * @return
	 */
	public Resource findResource(String type, String permission) {
		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		dc.add(Restrictions.eq("type", type));
		dc.add(Restrictions.eq("permission", permission));
		return this.findEntity(dc);
	}

	/**
	 * 添加角色和资源(权限)的关联
	 * 
	 * @param roleId
	 * @param resourceId
	 */
	public void addRoleResource(String roleId, String resourceId) {
		String sql = "insert into role_resource(role_id, resource_id) values(?, ?)";
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, roleId, resourceId);
	}

	/**
	 * 删除角色和资源(权限)的关联
	 * 
	 * @param roleId
	 * @param resourceId
	 */
	public void deleteRoleResource(String roleId, String resourceId) {
		String sql = "delete from role_resource where role_id=? and resource_id=?";
		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql, roleId, resourceId);
	}

	/**
	 * 根据一组角色ID获取这些角色拥有的资源(权限)
	 * 
	 * @param roleIds
	 * @return
	 */
	public Set<String> getResourcePermissions(Set<String> roleIds) {
		if (roleIds == null || roleIds.size() < 1) {
			return new HashSet<String>();
		}
		String sql = "select r.permission from role_resource rp, resource r "
				+ "where r.id=rp.resource_id and rp.role_id in (:roleIds)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("roleIds", roleIds);
		List<String> perms = this.getNamedParameterJdbcTemplate().queryForList(
				sql, params, String.class);
		return new HashSet<String>(perms);
	}

	/**
	 * 根据条件查找权限资源。
	 * 
	 * @param type
	 *            资源类型，null表示所有
	 * @param isValid
	 *            是否只要有效的资源，null表示所有
	 * @return
	 */
	public List<Resource> findResources(String type, Boolean isValid) {
		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		if (StringUtils.hasText(type)) {
			dc.add(Restrictions.eq("type", type));
		}
		if (isValid != null) {
			dc.add(Restrictions.eq("isDelete", !isValid));
		}
		return super.findEntities(dc);
	}

	/**
	 * 根据类型和一组权限代码查找有效的资源。
	 * 
	 * @param type
	 * @param permissions
	 * @return
	 */
	public List<Resource> findResources(Set<String> types,
			Set<String> permissions) {
		if (permissions == null || permissions.size() < 1 || types == null
				|| types.size() < 1) {
			return new ArrayList<>();
		}

		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		if (types.size() == 1) {
			dc.add(Restrictions.eq("type", types.iterator().next()));
		} else {
			dc.add(Restrictions.in("type", types));
		}
		dc.add(Restrictions.eq("isDelete", false));
		dc.add(Restrictions.in("permission", permissions));
		return this.findEntities(dc);
	}

	/**
	 * 根据角色ID查找，角色拥有的权限资源ID。
	 * 
	 * @param roleId
	 * @return
	 */
	public List<String> findRoleResourceIds(String roleId) {
		String sql = "select resource_id from role_resource where role_id=?";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList(sql, String.class, roleId);
	}

	/**
	 * 根据ID和有效性查找权限资源。
	 * 
	 * @param resourceIds
	 * @return
	 */
	public List<Resource> findValidResources(Set<String> resourceIds) {
		DetachedCriteria dc = DetachedCriteria.forClass(Resource.class);
		if (resourceIds != null && resourceIds.size() > 0) {
			dc.add(Restrictions.in("id", resourceIds));
		}
		dc.add(Restrictions.eq("isDelete", false));
		return super.findEntities(dc);
	}

	/**
	 * 获取子资源ID。
	 * 
	 * @param parentId
	 * @return
	 */
	public List<String> getChildIds(String parentId) {
		String sql = "select id from resource where parent_id=?";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList(sql, String.class, parentId);
	}

	/**
	 * 获取权限资源关联的角色ID
	 * 
	 * @param resourceId
	 * @return
	 */
	public List<String> findRoleIds(String resourceId) {
		String sql = "select role_id from role_resource where resource_id=?";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList(sql, String.class, resourceId);
	}

	/**
	 * 获取有效子资源ID。
	 * 
	 * @param parentId
	 * @return
	 */
	public List<String> getValidChildIds(String parentId) {
		String sql = "select id from resource where parent_id=? and is_delete=0";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList(sql, String.class, parentId);
	}

	/**
	 * 根据父id和创建时间获取有资源ID。
	 * 
	 * @param parentId
	 *            父id
	 * @param time
	 *            创建时间
	 * @return
	 */
	public List<String> getChildIdsByTime(String parentId, Date time) {
		String sql = "select id from resource where parent_id=? and create_time=?";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList(sql, String.class, parentId, time);
	}

	/**
	 * 根据ids 修改数据
	 * 
	 * @param ids
	 *            要修改的id数组
	 * @param delete
	 *            数据状态
	 * @param createTime
	 *            创建时间
	 * @return
	 * @author suyin
	 * @since 2016.04.18 16:31
	 */
	public void updateDelete(List<String> ids, int delete, Date createTime) {

		if (CollectionUtils.isEmpty(ids)) {
			return;
		}

		StringBuffer sql = new StringBuffer();

		ids.forEach(cid -> {
			if (cid != null) {
				sql.append(sql.length() == 0 ? "" : ",").append("'")
						.append(cid).append("'");
			}
		});

		sql.insert(0,
				"update resource set is_delete=? , create_time=? where id in ( ");
		sql.append(")");

		logger.info(sql.toString());

		this.getNamedParameterJdbcTemplate().getJdbcOperations()
				.update(sql.toString(), delete, createTime);

	}

}
