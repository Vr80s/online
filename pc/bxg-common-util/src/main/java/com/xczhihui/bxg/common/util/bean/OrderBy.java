package com.xczhihui.bxg.common.util.bean;

import java.io.Serializable;

/**
 * 查询排序
 * 
 * @author liyong
 *
 */
public class OrderBy implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int TYPE_ASC = 0;

	public static final int TYPE_DESC = 1;
	
	/**
	 * 排序的列名
	 */
	private String name;
	
	/**
	 * 排序类型，见：TYPE_*
	 */
	private int type;

	/**
	 * 按属性正序排列
	 * 
	 * @param name
	 */
	public OrderBy(String name) {
		this(name, TYPE_ASC);
	}

	/**
	 * 按属性名排序
	 * 
	 * @param name
	 * @param type
	 *            TYPE_ASC TYPE_DESC
	 */
	public OrderBy(String name, int type) {
		this.name = name;
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
}
