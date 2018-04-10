package com.xczhihui.bxg.common.util.bean;

import java.io.Serializable;
/**
 * 数据字典
 * @author Haicheng Jiang
 */
public class DictionaryVo implements Serializable {
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 父级值
	 */
	private String parentValue;
	
	public DictionaryVo(){
		
	}
	public DictionaryVo(String name,String value,String parentValue){
		this.name=name;
		this.value=value;
		this.parentValue=parentValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getParentValue() {
		return parentValue;
	}
	public void setParentValue(String parentValue) {
		this.parentValue = parentValue;
	}
}
