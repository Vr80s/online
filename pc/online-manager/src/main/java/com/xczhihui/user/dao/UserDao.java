package com.xczhihui.user.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.utils.Groups;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.utils.Group;
import com.xczhihui.utils.PageVo;

/**
 * 用户DAO
 *
 * @author Haicheng Jiang
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
        String sql = "DELETE FROM user_role WHERE user_id=?";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, userId);
    }

    /**
     * 根据角色代码查找用户。
     *
     * @param roleCode
     * @return
     */
    public List<User> findUsersByRole(String roleCode) {
        String sql = "SELECT u.* FROM user u, user_role ur, role r "
                + "WHERE u.id=ur.user_id AND ur.role_id=r.id AND r.code=:roleCode";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("roleCode", roleCode);
        RowMapper<User> mapper = BeanPropertyRowMapper.newInstance(User.class);
        return this.getNamedParameterJdbcTemplate().query(sql, params, mapper);
    }

    /**
     * 根据用户名查找用户。
     *
     * @param userName
     * @return
     */
    public List<OnlineUser> findUsersByLikeUserName(String userName) {
        String sql = "SELECT u.* FROM oe_user u WHERE 1=1 AND login_name LIKE :name ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", "%" + userName + "%");
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
    public Page<User> findUserPage(Boolean isDelete, String roleId, String searchName, int pageNumber, int pageSize) {
        String sql = "select id,login_name,password,mobile,email,name,create_person,create_time,"
                + "is_delete,sex,qq,education,identity,description from user ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (StringUtils.hasText(roleId)) {
            sql += " left join user_role on user.id = user_role.user_id where user_role.role_id=:roleId";
            paramMap.put("roleId", roleId);
        } else {
            sql += " where 1=1 ";
        }
        if (searchName != null && !"".equals(searchName.trim())) {
            sql += " and (name like :searchName or login_name like :searchName) ";
            paramMap.put("searchName", "%" + searchName.trim() + "%");
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

    /**
     * 根据userId修改isLecturer
     *
     * @author zhuwenbao
     */
    public void updateIsLecturerById(int isLecturer, String userId) {
        String sql = "UPDATE oe_user SET is_lecturer = ? WHERE id = ?";
        this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql, isLecturer, userId);
    }

    public void markDeleted(String id) {
        String sql = "UPDATE user SET is_delete = TRUE WHERE id = :id";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        this.getNamedParameterJdbcTemplate().update(sql, mapSqlParameterSource);
    }
}
