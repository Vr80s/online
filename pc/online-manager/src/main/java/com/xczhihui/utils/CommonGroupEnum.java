package com.xczhihui.utils;

/**
 * 
 * @author yuanziyang
 * @date 2016年4月5日 下午3:31:01
 */
public enum CommonGroupEnum {

	/**
	 * 学习方向
	 */
	TARGET("target"),
	/**
	 * 工作年限
	 */
	JOBYEARS("jobyears"),
	/**
	 * 
	 */
	URLRESET("urlreset"),
	/**
	 * 当前职业
	 */
	OCCUPATION("occupation"), URLINTER("urlinter"), MESINTER("mesinter");

	private String name;

	public String getName() {
		return name;
	}

	private CommonGroupEnum(String name) {
		this.name = name;
	}
}
