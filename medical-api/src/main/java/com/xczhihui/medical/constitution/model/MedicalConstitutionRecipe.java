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
@TableName("medical_constitution_recipe")
public class MedicalConstitutionRecipe extends Model<MedicalConstitutionRecipe> {

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
     * 原料
     */
	private String name;
    /**
     * 原料
     */
	private String material;
    /**
     * 制作方法
     */
	private String method;


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalConstitutionRecipe{" +
			", id=" + id +
			", constitutionId=" + constitutionId +
			", tag=" + tag +
			", name=" + name +
			", material=" + material +
			", method=" + method +
			"}";
	}
}
