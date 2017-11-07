package com.xczhihui.user.center.bean;

/**
 * 用户类型
 * 
 * @author liyong
 *
 */
public enum UserType {
	/**
	 * 普通的注册用户
	 */
	COMMON(0),

	/**
	 * 学生
	 */
	STUDENT(1),

	/**
	 * 老师
	 */
	TEACHER(2);

	private int value;

	private UserType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	/**
	 * 用户类型是否合法
	 * @param type
	 * @return
	 */
	public static boolean isValid(int type) {
		for(UserType o : UserType.values()){
			if(o.getValue() == type){
				return true;
			}
		}
		return false;
	}
	/**
	 * 根据值获得对象
	 * @param value
	 * @return
	 */
	public static UserType parse(int value){
		for(UserType o : UserType.values()){
			if(o.getValue() == value){
				return UserType.valueOf(o.toString());
			}
		}
		return UserType.valueOf("COMMON");
	}
}
