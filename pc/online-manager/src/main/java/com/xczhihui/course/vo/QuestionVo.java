package com.xczhihui.course.vo;

import java.util.Date;

import com.xczhihui.bxg.common.util.DateUtil;

/**
 * @author snow
 */
public class QuestionVo {
	private String id;
	private String ksystemId;
	private String courseId;
	private String qKeyword;
	private Integer questionType;
	private String questionHead;
	private String questionHeadText;
	private String options;
	private String difficulty;
	private Date createTime;
	private String answer;
	private String solution;
	private String solutionText;
	private String status;
	private String inputPerson;
	private String author;
	private Integer questionNo;
	private Integer relatedKponitNumber;
	private String kpointNames;
	private String knowledgeNames;
	private String chapterId;
	private String usetimes;
	private String righttimes;
	private boolean isUse;
	private String lablename;
	private String labelIds;
	private String degreeDifficulty;
	private int manageReferencedCount;
	private int teacherReferncedCount;
	private String attachmentUrl;
	private String optionsPicture;

	// wgw加 组卷的时候 用来临时存放每题的分数
	private String questionScore;
	private String kpointIds;// 知识点ID
	private String questionId;// 知识点ID
	private String chapterName;// 知识点名称
	private String fileName;// 答案附件名称

	public boolean isUse() {
		return isUse;
	}

	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}

	public String getKpointNames() {
		return kpointNames;
	}

	public void setKpointNames(String kpointNames) {
		this.kpointNames = kpointNames;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKsystemId() {
		return ksystemId;
	}

	public void setKsystemId(String ksystemId) {
		this.ksystemId = ksystemId;
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

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		setCreateTimeStr();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInputPerson() {
		return inputPerson;
	}

	public void setInputPerson(String inputPerson) {
		this.inputPerson = inputPerson;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(Integer questionNo) {
		this.questionNo = questionNo;
	}

	public Integer getRelatedKponitNumber() {
		return relatedKponitNumber;
	}

	public void setRelatedKponitNumber(Integer relatedKponitNumber) {
		this.relatedKponitNumber = relatedKponitNumber;
	}

	private String createTimeStr = "";

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr() {
		if (createTime != null) {
			createTimeStr = DateUtil
					.formatDate(createTime, DateUtil.FORMAT_DAY);
		}
	}

	public String getQuestionHeadText() {
		return questionHeadText;
	}

	public void setQuestionHeadText(String questionHeadText) {
		this.questionHeadText = questionHeadText;
	}

	public String getqKeyword() {
		return qKeyword;
	}

	public void setqKeyword(String qKeyword) {
		this.qKeyword = qKeyword;
	}

	public String getUsetimes() {
		return usetimes;
	}

	public void setUsetimes(String usetimes) {
		this.usetimes = usetimes;
	}

	public String getRighttimes() {
		return righttimes;
	}

	public void setRighttimes(String righttimes) {
		this.righttimes = righttimes;
	}

	public String getLablename() {
		return lablename;
	}

	public void setLablename(String lablename) {
		this.lablename = lablename;
	}

	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}

	public String getDegreeDifficulty() {
		return degreeDifficulty;
	}

	public void setDegreeDifficulty(String degreeDifficulty) {
		this.degreeDifficulty = degreeDifficulty;
	}

	public int getManageReferencedCount() {
		return manageReferencedCount;
	}

	public void setManageReferencedCount(int manageReferencedCount) {
		this.manageReferencedCount = manageReferencedCount;
	}

	public int getTeacherReferncedCount() {
		return teacherReferncedCount;
	}

	public void setTeacherReferncedCount(int teacherReferncedCount) {
		this.teacherReferncedCount = teacherReferncedCount;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}

	public String getSolutionText() {
		return solutionText;
	}

	public void setSolutionText(String solutionText) {
		this.solutionText = solutionText;
	}

	public String getKnowledgeNames() {
		return knowledgeNames;
	}

	public void setKnowledgeNames(String knowledgeNames) {
		this.knowledgeNames = knowledgeNames;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public String getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(String questionScore) {
		this.questionScore = questionScore;
	}

	public String getOptionsPicture() {
		return optionsPicture;
	}

	public void setOptionsPicture(String optionsPicture) {
		this.optionsPicture = optionsPicture;
	}

	public String getKpointIds() {
		return kpointIds;
	}

	public void setKpointIds(String kpointIds) {
		this.kpointIds = kpointIds;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}