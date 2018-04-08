package com.xczhihui.course.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 教师VO
 * @author yxd
 */

public class LecturerVo {

	private int id;
	
	/**
	 * 讲师名
	 */
	private String name;
	/**
	 * 老师昵称
	 */
	private String nickname;
	/**
	 *讲师介绍信息
	 */
	private String description;

	/**
	 * 讲师排序
	 */
	private Integer sort;

	/**
	 * 头像
	 */
	private String headImg;
	
	/**
	 * 角色
	 */
	private String roleType;
	
	/**
	 * 角色名称
	 */
	private String roleTypeName;
	
	/**
	 * 所属学科id
	 */
	private Integer menuId;
	/**
	 * 所属学科
	 */
	private String menuName;
	
	/**
	 * 班级数量
	 */
	private Integer gradeCount;
	
	private String teachRecords;
	@JsonFormat(pattern = "yyyy-M-d", timezone = "GMT+8")
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getRoleTypeName() {
		return roleTypeName;
	}

	public void setRoleTypeName(String roleTypeName) {
		this.roleTypeName = roleTypeName;
	}

	public String getTeachRecords() {
		return teachRecords;
	}

	public void setTeachRecords(String teachRecords) {
		this.teachRecords = teachRecords;
	}

	public Integer getGradeCount() {
		return gradeCount;
	}

	public void setGradeCount(Integer gradeCount) {
		this.gradeCount = gradeCount;
	}
	
	
	
}
