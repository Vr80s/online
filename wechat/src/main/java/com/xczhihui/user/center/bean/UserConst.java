package com.xczhihui.user.center.bean;

/**
 * 用户相关的一些常量定义。 用UserOrigin,UserSex,UserStatus,UserType替代。
 * 
 * @author liyong
 */
@Deprecated
final public class UserConst {
	/**
	 * 熊猫中医
	 */
	public static final String ORIGIN_BXG = "bxg";
	/**
	 * 问答精灵
	 */
	public static final String ORIGIN_ASK = "ask";

	/**
	 * 双元课堂
	 */
	public static final String ORIGIN_DUAL = "dual";

	/**
	 * 用户中心
	 */
	public static final String ORIGIN_UCENTER = "ucenter";

	/**
	 * 堂播虎客户端
	 */
	public static final String ORIGIN_TBH = "tbh";

	/**
	 * 女
	 */
	public static final int SEX_FEMALE = 0;
	/**
	 * 男
	 */
	public static final int SEX_MALE = 1;
	/**
	 * 未知
	 */
	public static final int SEX_UNKNOWN = 3;

	/**
	 * 正常用户
	 */
	public static final int STATUS_NORMAL = 0;

	/**
	 * 被封禁的用户
	 */
	public static final int STATUS_DISABLE = -1;

	/**
	 * 普通的注册用户
	 */
	public static final int TYPE_COMMON = 0;

	/**
	 * 学生
	 */
	public static final int TYPE_STUDENT = 1;

	/**
	 * 老师
	 */
	public static final int TYPE_TEACHER = 2;

	/**
	 * 判断来源是否合法。
	 * 
	 * @param origin
	 * @return
	 */
	public static boolean isValid(String origin) {
		if (ORIGIN_BXG.equals(origin) || ORIGIN_DUAL.equals(origin) || ORIGIN_ASK.equals(origin)
				|| ORIGIN_UCENTER.equals(origin) || ORIGIN_TBH.equals(origin)) {
			return true;
		}
		return false;
	}
}
