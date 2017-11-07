package com.xczhihui.bxg.online.manager.exam.vo;

public class ExamPaperQuestionVo{
	
	private String id;
	private String paperId;
    private Integer questionType;
    private Integer questionHead;
    private Integer options;
    private String optionsPicture;
    private String attachmentUrl;
    private String difficulty;
    private Integer answer;
    private Integer solution;
    private Integer questionScore;

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
	public Integer getQuestionHead() {
		return questionHead;
	}
	public void setQuestionHead(Integer questionHead) {
		this.questionHead = questionHead;
	}
	public Integer getOptions() {
		return options;
	}
	public void setOptions(Integer options) {
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
	public Integer getAnswer() {
		return answer;
	}
	public void setAnswer(Integer answer) {
		this.answer = answer;
	}
	public Integer getSolution() {
		return solution;
	}
	public void setSolution(Integer solution) {
		this.solution = solution;
	}
	public Integer getQuestionScore() {
		return questionScore;
	}
	public void setQuestionScore(Integer questionScore) {
		this.questionScore = questionScore;
	}
}
