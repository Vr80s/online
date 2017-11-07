package com.xczhihui.user.center.bean;

/**
 * 用户状态
 * 
 * @author liyong
 *
 */
public enum UserStatus {
	/**
	 * 正常用户
	 */
	NORMAL(0),

	/**
	 * 被封禁的用户
	 */
	DISABLE(-1);

	private int value;

	private UserStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	/**
	 * 用户状态是否合法
	 * @param status
	 * @return
	 */
	public static boolean isValid(int status) {
		for(UserStatus o : UserStatus.values()){
			if(o.getValue() == status){
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
	public static UserStatus parse(int value){
		for(UserStatus o : UserStatus.values()){
			if(o.getValue() == value){
				return UserStatus.valueOf(o.toString());
			}
		}
		return UserStatus.valueOf("NORMAL");
	}
	/**
	 * 用户的逻辑删除转换成状态
	 * @param delete
	 * @return
	 */
	public static UserStatus deleteToStatus(boolean delete){
		if (delete) {
			return UserStatus.DISABLE;
		} else {
			return UserStatus.NORMAL;
		}
	}

}
