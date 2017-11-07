package com.xczhihui.bxg.online.manager.user.service;

import com.xczhihui.bxg.online.common.domain.User;

/**
 * 个人中心相关接口。
 * 
 * @author Haicheng Jiang
 *
 */
public interface PersonService {
	/**
	 * 更新个人信息
	 * 
	 * @param oldUser
	 * @param newUser
	 */
	public void updatePersonInfo(User oldUser, User newUser);

	/**
	 * 修改头像
	 * 
	 * @param user
	 * @param image
	 */
	public void updateHeadPhoto(User user, byte[] image);

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @param newpassword
	 * @param oldpassword
	 */
	public void updatePassword(User user, String newpassword, String oldpassword);
}
