package com.xczhihui.user.service;

import java.util.List;
import java.util.Set;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.common.web.UserVo;
import com.xczhihui.utils.Groups;
import com.xczhihui.utils.PageVo;

/**
 * 用户接口
 *
 */
public interface UserService {

	/**
	 * 根据角色代码查找用户。
	 *
	 * @param roleCode
	 *            角色代码，如：admin,manager
	 * @return
	 */
	List<User> findUsersByRole(String roleCode);

	/**
	 * 根据ID获取用户
	 *
	 * @param id
	 * @return
	 */
	User getUserById(String id);

	/**
	 * 根据登录名获取用户
	 *
	 * @param loginName
	 * @return
	 */
	User getUserByLoginName(String loginName);

	Set<String> findRoles(String userId);

	Set<String> findPermissions(String userId);

	/**
	 * 添加一个用户
	 *
	 * @param user
	 */
	void addUser(User user);

	/**
	 * 删除一个用户
	 *
	 * @param user
	 */
	void deleteUser(User user);

	/**
	 * 按分页查询用户
	 *
	 * @param isDelete
	 * @param roleId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page<User> findUserPage(Boolean isDelete, String roleId, String searchName,
			int pageNumber, int pageSize);

	/**
	 * 根据条件分页获取用户信息。
	 *
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	PageVo findPageByGroups(Groups groups, PageVo pageVo);

	/**
	 * 根据ID软删除一个用户
	 *
	 * @param id
	 */
	void deleteUserLogic(String id);

	/**
	 * 返回所有用户
	 *
	 * @return
	 */
	List<UserVo> findAll();

	/**
	 * 根据ID除一个用户
	 *
	 * @param id
	 */
	void deleteUser(String id);

	/**
	 * 批量软删除一批用户
	 *
	 * @param ids
	 */
	void deleteUsersLogic(List<String> ids);

	/**
	 * 批量删除一批用户
	 *
	 * @param ids
	 */
	void deleteUsers(List<String> ids);

	/**
	 * 重置用户密码
	 *
	 * @param loginName
	 */
	void updateUserPassword(String loginName, String password);

	/**
	 * 更新用户信息
	 *
	 * @param user
	 */
	User updateUser(User user);

	/**
	 * 班级下的所有用户
	 *
	 * @param gradeIds
	 */
	List<UserVo> findAllUserByGrade(String gradeIds);

	void saveUserCoin();
}
