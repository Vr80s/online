package com.xczhihui.medical.constitution.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
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
@TableName("medical_constitution_question_bank")
public class MedicalConstitutionQuestionBank extends Model<MedicalConstitutionQuestionBank> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 题号
     */
	private String no;
    /**
     * 题干
     */
	private String content;
    /**
     * 标签
     */
	private String tag;
	private String disease;
	private String symptom;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
