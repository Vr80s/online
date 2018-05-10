package com.xczhihui.course.vo;

import java.util.List;

public class TreeNode {

	private String id;

	private String pId = "";

	private String temp = "";

	private String name;

	private String actName;

	private Integer level;

	private String courseId;

	private String targetId;

	private String moveType;

	// 要统计的数字 用来存放评价数、或笔记数的
	private Integer cntNum;

	// 是否打开节点
	private boolean open = false;

	// 是否是父节点
	private boolean isParent = false;

	// 复选框是否选择
	private boolean checked = false;

	// 是否展示复选框
	private boolean nocheck = false;

	// 是否是根节点
	private boolean islast = false;

	// 是否可选
	private boolean chkDisabled = false;

	private String icon;

	public boolean isIslast() {
		return islast;
	}

	public void setIslast(boolean islast) {
		this.islast = islast;
	}

	private String type;
	private String contenttype;

	private List children;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean getNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public Integer getCntNum() {
		return cntNum;
	}

	public void setCntNum(Integer cntNum) {
		this.cntNum = cntNum;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
