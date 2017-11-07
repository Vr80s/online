package com.xczhihui.bxg.online.manager.exam.vo;

import com.xczhihui.bxg.online.common.base.vo.OnlineBaseVo;

public class ExamPaperQuestion extends OnlineBaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	//'试卷id'
	private String paperId;
	// '题型，0单选、1多选、2判断、3填空、4简答'
	private Integer questionType;
	//'题干（富文本）'
	private String questionHead;
	//'选项'
	private String options;
	//'选项图片'
	private String optionsPicture;
	//附件
	private String attachmentUrl;
	//难度等级，A简单，B一般，C困难
	private String difficulty;
	//答案(富文本）
	private String answer;
	//答案解析(富文本）
	private String solution;
	//题目本身分数
	private String questionScore;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPaperId() {
		return paperId;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	public Integer getQuestionType() {
		return questionType;
	}
	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}
	public String getQuestionHead() {
		return questionHead;
	}
	public void setQuestionHead(String questionHead) {
		this.questionHead = questionHead;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getOptionsPicture() {
		return optionsPicture;
	}
	public void setOptionsPicture(String optionsPicture) {
		this.optionsPicture = optionsPicture;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
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
	public String getQuestionScore() {
		return questionScore;
	}
	public void setQuestionScore(String questionScore) {
		this.questionScore = questionScore;
	}
	
	
}
