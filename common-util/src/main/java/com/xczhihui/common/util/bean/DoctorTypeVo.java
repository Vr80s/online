package com.xczhihui.common.util.bean;

public class DoctorTypeVo {

	
	private Integer code;
	private String value;
	
	
	
	public DoctorTypeVo(Integer code, String text) {
		this.code = code;
		this.value = text;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
