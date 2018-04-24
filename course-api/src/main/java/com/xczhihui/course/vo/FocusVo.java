package com.xczhihui.course.vo;

import java.io.Serializable;

public class FocusVo implements Serializable{

	
	
	private String id; 					//主键id
	private String userId;  	 		//用户id
	private String name;        		//名字
	private String smallHeadPhoto; 		//头像
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmallHeadPhoto() {
		return smallHeadPhoto;
	}
	public void setSmallHeadPhoto(String smallHeadPhoto) {
		this.smallHeadPhoto = smallHeadPhoto;
	}
	
}
