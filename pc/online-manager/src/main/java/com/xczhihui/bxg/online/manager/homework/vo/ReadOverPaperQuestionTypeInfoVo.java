package com.xczhihui.bxg.online.manager.homework.vo;

import java.util.List;

/**
 * 大类题型的信息VO类
 */
public class ReadOverPaperQuestionTypeInfoVo {

	/**
	 * 试题信息集合
	 */
	private List<UserPaperQuestionVo> questionInfoVos;

	/**
	 * 试题类型编号
	 */
	private Integer questionTypeNumber;

	/**
	 * 当前类型所有题的满分
	 */
	private Double totleScore;

	/**
	 * 当前类型所有题的得分
	 */
	private Double score;

	/**
	 * 错误题数
	 */
	private Integer errorQuestions;

	/**
	 * 正确题数
	 */
	private Integer correctQuestions;

	public List<UserPaperQuestionVo> getQuestionInfoVos() {
		return questionInfoVos;
	}

	public void setQuestionInfoVos(List<UserPaperQuestionVo> questionInfoVos) {
		this.questionInfoVos = questionInfoVos;
	}

	public Integer getQuestionTypeNumber() {
		return questionTypeNumber;
	}

	public void setQuestionTypeNumber(Integer questionTypeNumber) {
		this.questionTypeNumber = questionTypeNumber;
	}

	public Double getTotleScore() {
		return totleScore;
	}

	public void setTotleScore(Double totleScore) {
		this.totleScore = totleScore;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getErrorQuestions() {
		return errorQuestions;
	}

	public void setErrorQuestions(Integer errorQuestions) {
		this.errorQuestions = errorQuestions;
	}

	public Integer getCorrectQuestions() {
		return correctQuestions;
	}

	public void setCorrectQuestions(Integer correctQuestions) {
		this.correctQuestions = correctQuestions;
	}

}
