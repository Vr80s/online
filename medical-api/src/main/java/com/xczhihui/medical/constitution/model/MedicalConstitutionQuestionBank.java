package com.xczhihui.medical.constitution.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
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


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalConstitutionQuestionBank{" +
			", id=" + id +
			", no=" + no +
			", content=" + content +
			", tag=" + tag +
			"}";
	}
}
