package com.xczhihui.user.center.bean;

/**
 * 用户性别
 * 
 * @author liyong
 *
 */
public enum UserSex {
	/**
	 * 女
	 */
	FEMALE(0),

	/**
	 * 男
	 */
	MALE(1),

	/**
	 * 未知
	 */
	UNKNOWN(2);
	
	private int value;

	private UserSex(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	/**
	 * 性别是否合法
	 * @param status
	 * @return
	 */
	public static boolean isValid(int sex) {
		for(UserSex o : UserSex.values()){
			if(o.getValue() == sex){
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
	public static UserSex parse(int value){
		for(UserSex o : UserSex.values()){
			if(o.getValue() == value){
				return UserSex.valueOf(o.toString());
			}
		}
		return UserSex.valueOf("UNKNOWN");
	}
}
