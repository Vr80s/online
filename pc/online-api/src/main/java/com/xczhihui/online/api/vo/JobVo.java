package com.xczhihui.online.api.vo;

/**
 * 职业
 * @author duanqh
 *
 */
public class JobVo  implements java.io.Serializable{

	
	/** id */
	private String id;
	
	/** key */
	private String key;
	
	/** 工作名字 */
	private String value;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
