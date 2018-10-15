package com.xczhihui.medical.constitution.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-10-15
 */
@TableName("medical_constitution_question_record_details")
public class MedicalConstitutionQuestionRecordDetails extends Model<MedicalConstitutionQuestionRecordDetails> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	@TableField("question_id")
	private Integer questionId;
    /**
     * 题号
     */
	@TableField("question_no")
	private String questionNo;
    /**
     * 答案
     */
	private String answer;
	@TableField("record_id")
	private Integer recordId;
	@TableField("create_time")
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalConstitutionQuestionRecordDetails{" +
			", id=" + id +
			", questionId=" + questionId +
			", questionNo=" + questionNo +
			", answer=" + answer +
			", recordId=" + recordId +
			", createTime=" + createTime +
			"}";
	}
}
