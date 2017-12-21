package com.xczhihui.medical.field.vo;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public class MedicalFieldVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医疗领域表
     */
	private String id;
    /**
     * 领域名称
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
	public String toString() {
		return "MedicalFieldVo{" +
			", id=" + id +
			", name=" + name +
			"}";
	}
}
