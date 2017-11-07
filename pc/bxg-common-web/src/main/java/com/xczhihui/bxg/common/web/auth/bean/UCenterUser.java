package com.xczhihui.bxg.common.web.auth.bean;

import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.user.center.bean.ItcastUser;
import com.xczhihui.user.center.bean.UserStatus;

/**
 * 如果业务系统没有自己的用户系统（使用用户中心的用户），可以用这个用户。
 * 
 * @author liyong
 *
 */
public class UCenterUser extends BxgUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static UCenterUser valueOf(ItcastUser itcastUser) {
		UCenterUser user = new UCenterUser();
		user.setLoginName(itcastUser.getLoginName());
		user.setName(itcastUser.getNikeName());
		user.setPassword("");
		user.setSex(itcastUser.getSex());
		user.setId(String.valueOf(itcastUser.getId()));
		user.setMobile(itcastUser.getMobile());
		user.setEmail(itcastUser.getEmail());
		user.setDelete(itcastUser.getStatus() == UserStatus.DISABLE.getValue());
		user.setCreateTime(itcastUser.getRegistDate());
		user.setCreatePerson("");
		return user;
	}
}
