package com.xczhihui.barrier.vo;

/**
 * @Author Fudong.Sun【】
 * @Date 2016/12/22 14:36
 */
public class ExamPaperVo {
	/**
	 * 题型
	 */
	private Integer question_type;
	/**
	 * 题干
	 */
	private String question_head;
	/**
	 * 选项
	 */
	private String options;
	/**
	 * 选项图片
	 */
	private String options_picture;
	/**
	 * 答案
	 */
	private String answer;
	/**
	 * 答案解析
	 */
	private String solution;
	/**
	 * 答题结果
	 */
	private Integer is_right;
	/**
	 * 我的答案
	 */
	private String my_answer;
	/**
	 * 题目本身分数
	 */
	private Integer question_score;

	public Integer getQuestion_score() {
		return question_score;
	}

	public void setQuestion_score(Integer question_score) {
		this.question_score = question_score;
	}

	public Integer getQuestion_type() {
		return question_type;
	}

	public void setQuestion_type(Integer question_type) {
		this.question_type = question_type;
	}

	public String getQuestion_head() {
		return question_head;
	}

	public void setQuestion_head(String question_head) {
		this.question_head = question_head;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getOptions_picture() {
		return options_picture;
	}

	public void setOptions_picture(String options_picture) {
		this.options_picture = options_picture;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Integer getIs_right() {
		return is_right;
	}

	public void setIs_right(Integer is_right) {
		this.is_right = is_right;
	}

	public String getMy_answer() {
		return my_answer;
	}

	public void setMy_answer(String my_answer) {
		this.my_answer = my_answer;
	}
}
