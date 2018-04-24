package com.xczhihui.bxg.online.common.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.xczhihui.common.support.domain.BasicEntity;

@Entity
@Table(name = "oe_barrier_strategy")
public class BarrierStrategy extends BasicEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "barrier_id")
	private String barrierId;
	
	@Column(name = "question_type")
    private String questionType;
	
	@Column(name = "question_sum")
    private Integer questionSum;
	
	@Column(name = "total_score")
    private Integer totalScore;
	
	@Column(name = "qustion_score")
    private Integer qustionScore;

	public String getBarrierId() {
		return barrierId;
	}
	public void setBarrierId(String barrierId) {
		this.barrierId = barrierId;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public Integer getQuestionSum() {
		return questionSum;
	}
	public void setQuestionSum(Integer questionSum) {
		this.questionSum = questionSum;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	public Integer getQustionScore() {
		return qustionScore;
	}
	public void setQustionScore(Integer qustionScore) {
		this.qustionScore = qustionScore;
	}
    
}
