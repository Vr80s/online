package com.xczhihui.bxg.common.util.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 题型枚举
 * 
 * @author liyong
 *
 */
public enum QuestionType {

	TYPE_SINGLE_CHOICE(0, "单选题"),

	TYPE_MULTIPLE_CHOICE(1, "多选题"),

	TYPE_TRUE_FALSE(2, "判断题"),

	TYPE_GAP_FILLING(3, "填空题"),

	TYPE_SHORT_ANSWER(4, "简答题"),

	TYPE_CODE(5, "代码题"),
	
	TYPE_PRACTICAL(6, "实操题"),
	
	TYPE_ATTACHMENT(7, "附件题");

	private int type;

	private String desc;

	private static Map<Integer, QuestionType> qts = new HashMap<Integer, QuestionType>();

	static {
		for (QuestionType qt : QuestionType.values()) {
			qts.put(qt.getType(), qt);
		}
	}

	/**
	 * 根据整型类型获取枚举对象。
	 * 
	 * @param type
	 * @return
	 */
	public static QuestionType valueOf(int type) {
		return qts.get(type);
	}

	private QuestionType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	/**
	 * 类型
	 * 
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * 描述
	 * 
	 * @return
	 */
	public String getDesc() {
		return desc;
	}
}
