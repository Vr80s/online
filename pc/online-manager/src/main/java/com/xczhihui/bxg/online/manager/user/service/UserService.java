package com.xczhihui.bxg.online.manager.user.service;

import java.util.List;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.User;
import com.xczhihui.bxg.online.manager.common.web.UserVo;
import com.xczhihui.bxg.online.manager.utils.Groups;
import com.xczhihui.bxg.online.manager.utils.PageVo;

/**
 * 用户接口
 * 
 * @author Haicheng Jiang
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
	public List<User> findUsersByRole(String roleCode);

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(String id);

	/**
	 * 根据登陆名获取用户
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName);

	/**
	 * 添加一个用户
	 * 
	 * @param user
	 */
	public void addUser(User user);

	/**
	 * 删除一个用户
	 * 
	 * @param user
	 */
	public void deleteUser(User user);

	/**
	 * 按分页查询用户
	 * 
	 * @param isDelete
	 * @param roleId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<User> findUserPage(Boolean isDelete, String roleId,String searchName, int pageNumber, int pageSize);

	/**
	 * 根据条件分页获取用户信息。
	 * 
	 * @param groups
	 * @param pageVo
	 * @return
	 */
	public PageVo findPageByGroups(Groups groups, PageVo pageVo);

	/**
	 * 根据ID软删除一个用户
	 * 
	 * @param id
	 */
	public User deleteUserLogic(String id);

	/**
	 * 返回所有用户
	 * @return
	 */
	public List<UserVo> findAll();
	/**
	 * 根据ID除一个用户
	 * 
	 * @param id
	 */
	public User deleteUser(String id);

	/**
	 * 批量软删除一批用户
	 * 
	 * @param ids
	 */
	public void deleteUsersLogic(List<String> ids);

	/**
	 * 批量删除一批用户
	 * 
	 * @param ids
	 */
	public void deleteUsers(List<String> ids);

	/**
	 * 重置用户密码
	 * 
	 * @param loginName
	 */
	public void updateUserPassword(String loginName, String password);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public User updateUser(User user);
	/**
	 * 班级下的所有用户
	 * 
	 * @param gradeIds
	 */
	List<UserVo> findAllUserByGrade(String gradeIds);

	public void saveUserCoin();
}
