package com.xczhihui.medical.constitution.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("medical_constitution")
public class MedicalConstitution extends Model<MedicalConstitution> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 证型后台标签
     */
	@TableField("backstage_tag")
	private String backstageTag;
    /**
     * 证型前台标签
     */
	private String tag;
    /**
     * 题号1
     */
	private String subject1;
    /**
     * 题号2
     */
	private String subject2;
    /**
     * 题号3
     */
	private String subject3;
    /**
     * 题号4
     */
	private String subject4;
    /**
     * 题号5
     */
	private String subject5;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBackstageTag() {
		return backstageTag;
	}

	public void setBackstageTag(String backstageTag) {
		this.backstageTag = backstageTag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSubject1() {
		return subject1;
	}

	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}

	public String getSubject2() {
		return subject2;
	}

	public void setSubject2(String subject2) {
		this.subject2 = subject2;
	}

	public String getSubject3() {
		return subject3;
	}

	public void setSubject3(String subject3) {
		this.subject3 = subject3;
	}

	public String getSubject4() {
		return subject4;
	}

	public void setSubject4(String subject4) {
		this.subject4 = subject4;
	}

	public String getSubject5() {
		return subject5;
	}

	public void setSubject5(String subject5) {
		this.subject5 = subject5;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalConstitution{" +
			", id=" + id +
			", backstageTag=" + backstageTag +
			", tag=" + tag +
			", subject1=" + subject1 +
			", subject2=" + subject2 +
			", subject3=" + subject3 +
			", subject4=" + subject4 +
			", subject5=" + subject5 +
			"}";
	}
}
