package com.xczhihui.bxg.online.manager.user.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.online.common.domain.OnlineUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;
import com.xczhihui.bxg.online.manager.utils.Group;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;

/**
 * 用户DAO
 * 
 * @author Haicheng Jiang
 *
 */
@Repository("userDao")
public class UserDao extends HibernateDao<User> {

	/**
	 * 根据用户ID删除用户角色关联。
	 * 
	 * @param userId
	 * @return
	 */
	public int deleteUserRolesByUserId(String userId) {
		String sql = "delete from user_role where user_id=?";
		return this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, userId);
	}

	/**
	 * 根据角色代码查找用户。
	 * 
	 * @param roleCode
	 * @return
	 */
	public List<User> findUsersByRole(String roleCode) {
		String sql = "select u.* from user u, user_role ur, role r "
				+ "where u.id=ur.user_id and ur.role_id=r.id and r.code=:roleCode";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("roleCode", roleCode);
		RowMapper<User> mapper = BeanPropertyRowMapper.newInstance(User.class);
		return this.getNamedParameterJdbcTemplate().query(sql, params, mapper);
	}

	/**
	 * 根据用户名查找用户。
	 * @param userName
	 * @return
	 */
	public List<OnlineUser> findUsersByLikeUserName(String userName) {
		String sql = "select u.* from oe_user u where 1=1 and login_name like :name ";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", "%"+userName+"%");
		RowMapper<OnlineUser> mapper = BeanPropertyRowMapper.newInstance(OnlineUser.class);
		return this.getNamedParameterJdbcTemplate().query(sql, params, mapper);
	}


	/**
	 * 添加一个用户
	 * 
	 * @param user
	 */
	public void addUser(User user) {
		user.setCreateTime(new Date());
		this.save(user);
	}

	/**
	 * 删除一个用户
	 * 
	 * @param user
	 */
	public void deleteUser(User user) {
		this.delete(user);
	}

	/**
	 * 根据登录名获取老师
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return this.findOneEntitiyByProperty(User.class, "loginName", loginName);
	}

	/**
	 * 查询用户信息。
	 * 
	 * @param isDelete
	 * @param roleId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<User> findUserPage(Boolean isDelete, String roleId,String searchName, int pageNumber, int pageSize) {
		String sql = "select id,login_name,password,mobile,email,name,create_person,create_time,"
				+ "is_delete,sex,qq,education,identity,description from user ";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.hasText(roleId)) {
			sql += " left join user_role on user.id = user_role.user_id where user_role.role_id=:roleId";
			paramMap.put("roleId", roleId);
		}else{
			sql+=" where 1=1 ";
		}
		if (searchName != null && !"".equals(searchName.trim())) {
			sql += " and (name like :searchName or login_name like :searchName) ";
			paramMap.put("searchName", "%"+searchName.trim()+"%");
		}
		if (isDelete != null) {
			if (StringUtils.hasText(roleId)) {
				sql += " and is_delete=:isDelete";
			} else {
				sql += " and is_delete=:isDelete";
			}
			paramMap.put("isDelete", isDelete);
		}
		sql += " order by create_time desc";
		return this.findPageBySQL(sql, paramMap, User.class, pageNumber, pageSize);
	}

	public PageVo findTeacherPage(Groups groups, PageVo page) {
		Group group = groups.findByName("roleId");
		String roleId = null;
		if (group != null) {
			roleId = group.getPropertyValue1().toString();
		}
		String sql = "select id,login_name,password,mobile,email,name,create_person,create_time,"
				+ "is_delete,sex,qq,education,identity,description from user ";
		if (StringUtils.hasText(roleId)) {
			sql += " left join user_role on user.id = user_role.user_id";
			groups.Add("user_role.role_id", roleId);
		}
		groups.Remove(group);
		page = super.findPageByGroups(groups, page, sql);
		return page;
	}
}
