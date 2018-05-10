package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.xczhihui.common.support.domain.BasicEntity;
/**
 *  视屏实体类
 * @author yxd
 */
@Entity
@Table(name = "oe_question")
public class QuesStore extends BasicEntity implements Serializable{
	/**
	 * 课程体系Id
	 */
	@Column(name="course_id", length = 32)
	private String courseId;
	/**
	 * 知识点id
	 */
	@Transient
	private String chapterId;
	/**
	 * 知识点名称
	 */
	@Column(name="chapter_name", length = 32)
	private String chapterName;
	@Transient
	private String chapterCount;
	/**
	 * 题目类型
	 */
	@Column(name="question_type", columnDefinition="char(1)")
	private Integer questionType;
	/**
	 * 题干
	 */
	@Column(name="question_head", columnDefinition="longtext")
	private String questionHead;
	/**
	 * 题干纯文本
	 */
	@Column(name="question_head_text", columnDefinition="longtext")
	private String questionHeadText;
	
	/**
	 * 题目选项
	 */
	@Column(name="options", columnDefinition="longtext")
	private String options;
	/**
	 * 选项图片地址
	 */
	@Column(name="options_picture")
	private String optionsPicture;
	/**
	 * 附件地址（素材等）
	 */
	@Column(name="attachment_url")
	private String attachmentUrl;
	/**
	 * 难度系数
	 */
	@Column(name="difficulty", columnDefinition="char(1)")
	private String difficulty;
	/**
	 * 答案(富文本）
	 */
	@Column(name="answer", columnDefinition="longtext")
	private String answer;
	/**
	 * 答案(纯文本）
	 */
	@Column(name="answer_text", columnDefinition="longtext")
	private String answerText;
	/**
	 * 答案解析(富文本）
	 */
	@Column(name="solution", columnDefinition="longtext")
	private String solution;
	/**
	 * 答案解析(纯文本）
	 */
	@Column(name="solution_text", columnDefinition="longtext")
	private String solutionText;
	/**
	 * 状态，1启用，0禁用
	 */
	@Column(name="status")
	private Integer status;
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
	public String getQuestionHeadText() {
		return questionHeadText;
	}
	public void setQuestionHeadText(String questionHeadText) {
		this.questionHeadText = questionHeadText;
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
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getSolutionText() {
		return solutionText;
	}
	public void setSolutionText(String solutionText) {
		this.solutionText = solutionText;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public String getChapterCount() {
		return chapterCount;
	}
	public void setChapterCount(String chapterCount) {
		this.chapterCount = chapterCount;
	}
	
	
	
}
