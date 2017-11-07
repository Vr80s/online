package com.xczhihui.bxg.common.util.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 题困难度
 * 
 * @author liyong
 *
 */
public enum QuestionDifficulty {

	LOW(0, "容易"),

	MEDIUM(1, "一般"),

	HIGH(2, "较难"),

	VERYHIGH(3, "难");

	private int level;

	private String desc;

	private static Map<Integer, QuestionDifficulty> qds = new HashMap<Integer, QuestionDifficulty>();

	static {
		for (QuestionDifficulty qd : QuestionDifficulty.values()) {
			qds.put(qd.getLevel(), qd);
		}
	}

	/**
	 * 根据困难度等级获取枚举对象。
	 * 
	 * @param level
	 * @return
	 */
	public static QuestionDifficulty valueOf(int level) {
		return qds.get(level);
	}

	private QuestionDifficulty(int level, String desc) {
		this.level = level;
		this.desc = desc;
	}

	/**
	 * 困难等级
	 * 
	 * @return
	 */
	public int getLevel() {
		return level;
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
