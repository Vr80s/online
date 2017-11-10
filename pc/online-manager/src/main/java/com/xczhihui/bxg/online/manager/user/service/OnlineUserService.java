package com.xczhihui.bxg.online.manager.user.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.OnlineUser;

/**
 * 网站用户
 * 
 * @author Haicheng Jiang
 */
public interface OnlineUserService {
	/**
	 * 按条件分页查询用户
	 * @param lastLoginIp
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param lastLoginTimeStart
	 * @param lastLoginTimeEnd
	 * @param searchName
	 * @param status
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<OnlineUser> findUserPage(String lastLoginIp, String createTimeStart, String createTimeEnd,
			String lastLoginTimeStart, String lastLoginTimeEnd, String searchName, Integer status, Integer menuId, int pageNumber,
			int pageSize);

	/**
	 * 禁用/启用
	 * @param loginName
	 * @param delete
	 */
	public void updateUserStatus(String loginName,int status);

	/**
	 * 设置学科权限
	 * 
	 * @param OnlineUser
	 * @return
	 */
	public void updateMenuForUser(OnlineUser entity);

	/**
	 * 查找所有的讲师
	 * Description：
	 * @return
	 * @return List<Map<String,Object>>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	List<Map<String, Object>> getAllUserLecturer();

	/**
	 * 设置此用户为讲师
	 * Description：
	 * @param loginName
	 * @param status
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @param description 
	 *
	 */
	void updateUserLecturer(String loginName, int status, String description);

	public OnlineUser getOnlineUserByUserId(String userId);

	
}
