package com.xczhihui.bxg.online.manager.user.service;

import java.util.List;
import java.util.Map;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.WechatUser;

/**
 * 网站用户
 * 
 * @author Haicheng Jiang
 */
public interface WechatUserService {
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
	public Page<WechatUser> findUserPage(String nickname,
			String subscribeStartTime,
			String subscribeEndTime,
			String qr_scene,
			int pageNumber,
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
	 * @param WechatUser
	 * @return
	 */
	public void updateMenuForUser(WechatUser entity);

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

	public WechatUser getWechatUserByUserId(String userId);

	public List<Map<String, Object>> getAllCourseName();

    void updateUserLogin(String userId, String loginName);
}
