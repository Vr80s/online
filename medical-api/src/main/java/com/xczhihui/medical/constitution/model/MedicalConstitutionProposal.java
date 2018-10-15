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
@TableName("medical_constitution_proposal")
public class MedicalConstitutionProposal extends Model<MedicalConstitutionProposal> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 体质id
     */
	@TableField("constitution_id")
	private String constitutionId;
	private String tag;
    /**
     * 生活注意
     */
	private String attention;
    /**
     * 饮食禁忌
     */
	private String taboo;
    /**
     * 穴位
     */
	private String acupoint;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConstitutionId() {
		return constitutionId;
	}

	public void setConstitutionId(String constitutionId) {
		this.constitutionId = constitutionId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getAttention() {
		return attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public String getTaboo() {
		return taboo;
	}

	public void setTaboo(String taboo) {
		this.taboo = taboo;
	}

	public String getAcupoint() {
		return acupoint;
	}

	public void setAcupoint(String acupoint) {
		this.acupoint = acupoint;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalConstitutionProposal{" +
			", id=" + id +
			", constitutionId=" + constitutionId +
			", tag=" + tag +
			", attention=" + attention +
			", taboo=" + taboo +
			", acupoint=" + acupoint +
			"}";
	}
}
