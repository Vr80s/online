package com.xczhihui.course.vo;

/**
 * 题目选项图片关系对应类
 * 
 * @author snow
 * @date 2015年12月15日
 */
public class QuestionOptionPicture {

	/**
	 * 题目ID
	 */
	private String questionId;
	/**
	 * 题目类型
	 */
	private Integer questionType;
	/**
	 * 选项名称
	 */
	private String optionName;

	/**
	 * 附件ID
	 */
	private String attachmentId;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

}