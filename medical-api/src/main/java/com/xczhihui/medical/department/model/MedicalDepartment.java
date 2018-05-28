package com.xczhihui.medical.department.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@TableName("medical_department")
public class MedicalDepartment extends Model<MedicalDepartment> {

    private static final long serialVersionUID = 1L;

    /**
     * 科室表
     */
	private String id;
    /**
     * 科室名称
     */
	private String name;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MedicalDepartment{" +
			", id=" + id +
			", name=" + name +
			"}";
	}
}
