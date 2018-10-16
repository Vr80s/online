package com.xczhihui.medical.constitution.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2018-10-15
 */
@Data
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

	@TableField(exist = false)
	private Integer score;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
