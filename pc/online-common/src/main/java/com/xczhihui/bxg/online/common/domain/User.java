package com.xczhihui.bxg.online.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.xczhihui.bxg.common.support.domain.BxgUser;

/**
 * 熊猫中医在线后台管理用户。
 * @author Haicheng Jiang
 */
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "login_name" }) })
public class User extends BxgUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String qq;

	/**
	 * 学历
	 */
	private String education;

	/**
	 * 身份证号
	 */
	private String identity;

	private String description;

	@Column(name = "big_head_photo")
	private String bigHeadPhoto;// 180x180

	@Column(name = "small_head_photo")
	private String smallHeadPhoto;// 45x45

	@Transient
	private String roleNames;
	
	@Transient
	private String roleIds;

	@Transient
	private String createTimeStr;

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getBigHeadPhoto() {
		return bigHeadPhoto;
	}

	public void setBigHeadPhoto(String bigHeadPhoto) {
		this.bigHeadPhoto = bigHeadPhoto;
	}

	public String getSmallHeadPhoto() {
		return smallHeadPhoto;
	}

	public void setSmallHeadPhoto(String smallHeadPhoto) {
		this.smallHeadPhoto = smallHeadPhoto;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
}
