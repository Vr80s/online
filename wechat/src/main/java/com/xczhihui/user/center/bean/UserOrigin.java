package com.xczhihui.user.center.bean;

/**
 * 用户来源（来之哪个业务系统）
 * 
 * @author liyong
 *
 */
public enum UserOrigin {
	/**
	 * 院校系统
	 */
	BXG,

	/**
	 * 问答精灵
	 */
	ASK,

	/**
	 * 双元课堂
	 */
	DUAL,

	/**
	 * 用户中心
	 */
	UCENTER,

	/**
	 * 堂播虎客户端
	 */
	TBH,
	
	/**
	 * 在线
	 */
	ONLINE;
	
	/**
	 * 判断来源是否合法。
	 * 
	 * @param origin
	 * @return
	 */
	public static boolean isValid(String origin) {
		for(UserOrigin o : UserOrigin.values()){
			if(o.toString().equalsIgnoreCase(origin)){
				return true;
			}
		}
		return false;
	}
}
