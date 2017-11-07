package com.xczhihui.bxg.common.support.dao;

/**
 * 关系条件
 * 
 * @author liyong
 *
 */
public enum CriteriaRelation {
	/**
	 * =
	 */
	EQ,

	/**
	 * >
	 */
	GT,

	/**
	 * >=
	 */
	GE,

	/**
	 * <
	 */
	LT,

	/**
	 * <=
	 */
	LE,

	/**
	 * !=
	 */
	NE,

	/**
	 * like
	 */
	LIKE;

	private CriteriaRelation() {
	}
}
