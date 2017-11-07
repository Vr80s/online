package com.xczhihui.bxg.online.manager.utils;

import java.util.Date;

/**
 * 与具体ORM实现无关的属性过滤条件封装类, 主要记录页面中简单的搜索过滤条件.
 * 
 * @author wufe
 */
public class PropertyFilter {

	/** 多个属性间OR关系的分隔符. */
	public static final String OR_SEPARATOR = "_OR_";

	/** 属性比较类型. */
public enum MatchType {
		AND,
		OR,
		NULL,
		NOTNULL,
		BETWEEN,
		IN,
		EQ,  //==EQuivalent with
		LIKE, //like '%value%'
		LIKESTART, //like 'value%'
		LT, //< Less than
		GT, //> Greater Than
		LE, //<= Less than or Equivalent with
		GE, //>= Greater than or Equivalent with 
		NOTIN,
		NE, MatchType; // Not Equivalent with !=
	}
public enum Type {
	INTEGER,
	LONG,
	FLOAT,
	STRING,
	DATE,
	DOUBLE,
	BOOLEAN
}

	/** 属性数据类型. */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		private PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private MatchType matchType = null;
	private Object matchValue = null;

	private Class<?> propertyClass = null;
	private String[] propertyNames = null;

	public PropertyFilter() {
	}

	
	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	/**
	 * 获取比较方式.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	/**
	 * 获取比较值.
	 */
	public Object getMatchValue() {
		return matchValue;
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * 获取唯一的比较属性名称.
	 */
	public String getPropertyName() {
		return propertyNames[0];
	}

	/**
	 * 是否比较多个属性.
	 */
	public boolean hasMultiProperties() {
		return (propertyNames.length > 1);
	}
}