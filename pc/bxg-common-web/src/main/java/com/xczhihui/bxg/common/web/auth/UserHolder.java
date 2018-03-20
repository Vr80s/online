package com.xczhihui.bxg.common.web.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xczhihui.bxg.common.support.domain.BxgUser;

/**
 * 放置当前登录用户。BxgUser可以向下转型成AuthFinder.findUserByLoginName返回的类型；
 * 业务系统的用户类需要从BxgUser继承。
 * 
 * @author liyong
 *
 */
public class UserHolder {

	static final Logger logger = LoggerFactory.getLogger(UserHolder.class);

	private static final ThreadLocal<BxgUser> CURRENT_USER = new ThreadLocal<BxgUser>();

	/**
	 * 修改当前用户(一般在登录或退出时调用)
	 * 
	 * @param user
	 */
	public static void setCurrentUser(BxgUser user) {
		CURRENT_USER.set(user);
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 如果未登录返回null
	 */
	public static BxgUser getCurrentUser() {
		return CURRENT_USER.get();
	}

	/**
	 * 获取当前用户
	 * 
	 * @exception 当前用户不存在抛出RuntimeException
	 * @return
	 */
	public static BxgUser getRequireCurrentUser() {
		BxgUser user = CURRENT_USER.get();
		if (user == null) {
			throw new RuntimeException("Current user is null.");
		}
		return user;
	}
}
