package com.xczhihui.bxg.online.web.vo;

/**
 * 
 * @author snow
 *
 */
public class GroupLevelVo {
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 组名称
	 */
	private String  name;
	/**
	 * 层级
	 */
	private Integer level;
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
